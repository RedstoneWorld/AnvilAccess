package xyz.gamecrash.anvilaccess.io;

import xyz.gamecrash.anvilaccess.model.*;
import xyz.gamecrash.anvilaccess.nbt.*;
import xyz.gamecrash.anvilaccess.nbt.tags.*;
import xyz.gamecrash.anvilaccess.util.block.BlockStateDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for region (MCA) files bridging IO with the model classes
 */
public class RegionFileLoader {

    /**
     * Loads a region file at the given path
     */
    public static RegionFile loadRegion(Path file) throws IOException {
        String fileName = file.getFileName().toString();
        if (!fileName.startsWith("r.") || !fileName.endsWith(".mca"))
            throw new IllegalArgumentException("Invalid region file name: " + fileName);

        String[] parts = fileName.substring(2, fileName.length() - 4).split("\\.");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid region file name format: " + fileName);

        int rX = Integer.parseInt(parts[0]);
        int rZ = Integer.parseInt(parts[1]);

        // only read the chunk entries, not the chunk data
        try (MCAReader reader = new MCAReader(file)) {
            RegionChunkEntry[] entries = reader.readChunkEntries();
            RegionFile regionFile = new RegionFile(file, rX, rZ, entries);

            setupLazyLoading(regionFile, file);

            return regionFile;
        }
    }

    /**
     * Loads a single chunk through the MCA reader
     */
    public static Chunk loadChunk(MCAReader reader, RegionChunkEntry entry, int regionX, int regionZ, int localX, int localZ) throws IOException {
        byte[] decompressed = reader.decompressAndReadChunkData(entry);

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(decompressed))) {
            NamedTag namedTag = TagParser.readNamed(input);
            if (!(namedTag.tag() instanceof CompoundTag rootTag))
                throw new IOException("Root tag is not a compound tag");

            int cX = regionX * 32 + localX;
            int cZ = regionZ * 32 + localZ;
            List<Section> sections = parseSections(rootTag);

            return new Chunk(cX, cZ, rootTag, sections);
        }
    }

    /**
     * Validate that a file is a valid MCA file (checking through the name)
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
            try (MCAReader reader = new MCAReader(file)) {
                RegionChunkEntry entry = regionFile.getEntry(lX, lZ);
                if (!entry.isEmpty())
                    return loadChunk(reader, entry, regionFile.getRegionX(), regionFile.getRegionZ(), lX, lZ);
                return null;
            }
        });
    }

    /**
     * Parse sections from the given chunk NBT data
     */
    private static List<Section> parseSections(CompoundTag chunkTag) {
        List<Section> sections = new ArrayList<>();

        ListTag sectionsTag = chunkTag.getList("sections", new ListTag(TagType.COMPOUND));
        if (sectionsTag == null || sectionsTag.isEmpty()) return sections;

        for (int i = 0; i < sectionsTag.size(); i++) {
            CompoundTag sectionTag = sectionsTag.getCompound(i);
            Section section = parseSection(sectionTag);
            sections.add(section);
        }

        return sections;
    }

    /**
     * Parse a single section from the given NBT data
     */
    private static Section parseSection(CompoundTag sectionTag) {
        int yIndex = 0;
        Tag yTag = sectionTag.get("Y");

        if (yTag == null) System.out.println("Could not find Y index in section.");
        else if (yTag.getValue() instanceof Number number) yIndex = number.intValue();
        else System.out.println("Y index tag is not a number. Value:" + yTag.getValue());

        CompoundTag blockStatesTag = sectionTag.getCompound("block_states", null);
        if (blockStatesTag == null) blockStatesTag = sectionTag.getCompound("BlockStates", null);
        if (blockStatesTag == null) return new Section(yIndex, List.of(new BlockState("minecraft:air")), null);

        ListTag paletteTag = blockStatesTag.getList("palette", new ListTag(TagType.COMPOUND));
        if (paletteTag == null || paletteTag.isEmpty())
            paletteTag = blockStatesTag.getList("Palette", new ListTag(TagType.COMPOUND));

        List<BlockState> palette = new ArrayList<>();

        for (int i = 0; i < paletteTag.size(); i++) {
            CompoundTag blockStateTag = paletteTag.getCompound(i);
            BlockState blockState = BlockStateDecoder.decodeBlockState(blockStateTag);
            palette.add(blockState);
        }

        if (palette.isEmpty()) palette.add(new BlockState("minecraft:air"));

        long[] blockStates = blockStatesTag.getLongArray("data", null);
        if (blockStates == null) blockStates = blockStatesTag.getLongArray("Data", null);

        return new Section(yIndex, palette, blockStates);
    }
}
