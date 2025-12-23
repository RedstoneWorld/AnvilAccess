package xyz.gamecrash.anvilaccess.io;

import xyz.gamecrash.anvilaccess.model.*;
import xyz.gamecrash.anvilaccess.nbt.*;
import xyz.gamecrash.anvilaccess.nbt.tags.*;
import xyz.gamecrash.anvilaccess.util.MCAReaderCache;
import xyz.gamecrash.anvilaccess.util.block.BlockStateDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for region (MCA) files bridging IO with the model classes.
 * <p>
 * See anvil / region file format specs under the <a href="https://minecraft.wiki/w/Anvil_file_format">Anvil File Format</a>
 * and <a href="https://minecraft.wiki/w/Region_file_format">Region File Format</a> Minecraft Wiki pages
 * <p>
 */
public class RegionFileLoader {

    /**
     * Validates a region file name at the given path, then loads its contents and sets up lazy loading for that file
     */
    public static RegionFile loadRegion(Path file) throws IOException {
        // Extract filename and validate format (r.x.z.mca)
        String fileName = file.getFileName().toString();
        if (!fileName.startsWith("r.") || !fileName.endsWith(".mca"))
            throw new IllegalArgumentException("Invalid region file name: " + fileName);

        // Parse region coordinates from filename (e.g., r.0.0.mca -> x=0, z=0)
        String[] parts = fileName.substring(2, fileName.length() - 4).split("\\.");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid region file name format: " + fileName);

        int rX = Integer.parseInt(parts[0]);
        int rZ = Integer.parseInt(parts[1]);

        // only read the chunk entries, not the chunk data
        try (MCAReader reader = new MCAReader(file)) {
            RegionChunkEntry[] entries = reader.readChunkEntries();
            RegionFile regionFile = new RegionFile(file, rX, rZ, entries);

            // set up lazy loading for the actual chunk data
            setupLazyLoading(regionFile, file);

            return regionFile;
        }
    }

    /**
     * Loads a single chunk through the given MCA reader - which "targets" a specific MCA file.
     */
    public static Chunk loadChunk(MCAReader reader, RegionChunkEntry entry, int regionX, int regionZ, int localX, int localZ) throws IOException {
        // Decompress chunk data from the MCA file
        byte[] decompressed = reader.decompressAndReadChunkData(entry);

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(decompressed))) {
            // Parse NBT data from the decompressed chunk bytes (this contains the actual chunk data)
            NamedTag namedTag = TagParser.readNamed(input);
            if (!(namedTag.tag() instanceof CompoundTag rootTag))
                throw new IOException("Root tag is not a compound tag");

            // Calculate world chunk coordinates from region and local coordinates
            int cX = regionX * 32 + localX;
            int cZ = regionZ * 32 + localZ;

            List<Section> sections = parseSections(rootTag);

            return new Chunk(cX, cZ, rootTag, sections);
        }
    }

    /**
     * Validate that a file has a valid MCA file name format
     * <p>
     * It follows this format: {@code r.x.z.mca} - where {@code x} and {@code z} are the region coordinates.
     */
    public static boolean isValidMCAFile(Path file) {
        try {
            String fileName = file.getFileName().toString();
            if (!fileName.startsWith("r.") || !fileName.endsWith(".mca")) return false;

            String[] parts = fileName.substring(2, fileName.length() - 4).split("\\.");
            if (parts.length != 2) return false;

            Integer.parseInt(parts[0]);
            Integer.parseInt(parts[1]);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets up lazy loading for chunks in the given region file
     */
    private static void setupLazyLoading(RegionFile regionFile, Path file) {
        regionFile.setChunkLoader((lX, lZ) -> {
            try {
                // Get (maybe cached) reader for the given region file
                MCAReader reader = MCAReaderCache.get(file);
                RegionChunkEntry entry = regionFile.getEntry(lX, lZ);

                // Only load if chunk exists in the region
                if (!entry.isEmpty())
                    return loadChunk(reader, entry, regionFile.getRegionX(), regionFile.getRegionZ(), lX, lZ);
                return null;
            } catch (Exception e) {
                throw new RuntimeException("Failed to load chunk", e);
            }
        });
    }

    /**
     * Parse chunk sections from the given chunk NBT data
     */
    private static List<Section> parseSections(CompoundTag chunkTag) {
        List<Section> sections = new ArrayList<>();

        // Look for sections in the chunk NBT
        ListTag sectionsTag = chunkTag.getList("sections", new ListTag(TagType.COMPOUND));
        if (sectionsTag == null || sectionsTag.isEmpty()) return sections;

        // Parse sections in the list
        for (int i = 0; i < sectionsTag.size(); i++) {
            CompoundTag sectionTag = sectionsTag.getCompound(i);
            Section section = parseSection(sectionTag);
            sections.add(section);
        }

        return sections;
    }

    /**
     * Parse a single chunk section from the given NBT data
     */
    private static Section parseSection(CompoundTag sectionTag) {
        // Get Y index / pos of the section
        int yIndex = 0;
        Tag yTag = sectionTag.get("Y");

        if (yTag == null) System.out.println("Could not find Y index in section.");
        else if (yTag.getValue() instanceof Number number) yIndex = number.intValue();
        else System.out.println("Y index tag is not a number. Value:" + yTag.getValue());

        // Get palette and block states (handling both new and old NBT formats, thus the duplicate checks)
        CompoundTag blockStatesTag = sectionTag.getCompound("block_states", null);
        if (blockStatesTag == null) blockStatesTag = sectionTag.getCompound("BlockStates", null);
        if (blockStatesTag == null) return new Section(yIndex, List.of(new BlockState("minecraft:air")), null);

        // Get block palette from the NBT data
        ListTag paletteTag = blockStatesTag.getList("palette", new ListTag(TagType.COMPOUND));
        if (paletteTag == null || paletteTag.isEmpty())
            paletteTag = blockStatesTag.getList("Palette", new ListTag(TagType.COMPOUND));

        List<BlockState> palette = new ArrayList<>();

        // Parse block states in the palette
        for (int i = 0; i < paletteTag.size(); i++) {
            CompoundTag blockStateTag = paletteTag.getCompound(i);
            BlockState blockState = BlockStateDecoder.decodeBlockState(blockStateTag);
            palette.add(blockState);
        }

        if (palette.isEmpty()) palette.add(new BlockState("minecraft:air"));

        // Get block state indices array
        long[] blockStates = blockStatesTag.getLongArray("data", null);
        if (blockStates == null) blockStates = blockStatesTag.getLongArray("Data", null);

        return new Section(yIndex, palette, blockStates);
    }
}
