package xyz.gamecrash.AnvilAccess.io;

import xyz.gamecrash.AnvilAccess.model.*;
import xyz.gamecrash.AnvilAccess.nbt.*;
import xyz.gamecrash.AnvilAccess.util.block.BlockStateDecoder;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RegionFileLoader {

    public static RegionFile loadRegion(Path file) throws IOException {
        String fileName = file.getFileName().toString();
        if (!fileName.startsWith("r.") || !fileName.endsWith(".mca")) throw new IllegalArgumentException("Invalid region file name: " + fileName);

        String[] parts = fileName.substring(2, fileName.length() - 4).split("\\.");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid region file name format: " + fileName);

        int rX = Integer.parseInt(parts[0]);
        int rZ = Integer.parseInt(parts[1]);

        try (MCAReader reader = new MCAReader(file)) {
            RegionChunkEntry[] entries = reader.readChunkEntries();
            RegionFile regionFile = new RegionFile(file, rX, rZ, entries);

            setupLazyLoading(regionFile, file);

            return regionFile;
        }
    }

    private static void setupLazyLoading(RegionFile regionFile, Path file) {
        regionFile.setChunkLoader((lX, lZ) -> {
            try (MCAReader reader = new MCAReader(file)) {
                RegionChunkEntry entry = regionFile.getEntry(lX, lZ);
                if (!entry.isEmpty()) return loadChunk(reader, entry, regionFile.getRegionX(), regionFile.getRegionZ(),  lX, lZ);
                return null;
            }
        });
    }

    public static Chunk loadChunk(MCAReader reader, RegionChunkEntry entry, int regionX, int regionZ, int localX, int localZ) throws IOException {
        byte[] decompressed = reader.decompressAndReadChunkData(entry);

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(decompressed))) {
            NamedTag namedTag = NBTParser.readNamed(input);
            if (!(namedTag.tag() instanceof CompoundTag rootTag)) throw new IOException("Root tag is not a compound tag");

            int cX = regionX * 32 + localX;
            int cZ = regionZ * 32 + localZ;
            List<Section> sections = parseSections(rootTag);

            return new Chunk(cX, cZ, rootTag, sections);
        }
    }

    private static List<Section> parseSections(CompoundTag chunkTag) {
        List<Section> sections = new ArrayList<>();

        ListTag sectionsTag = chunkTag.getList("sections", new ListTag(TagType.COMPOUND));
        if (sectionsTag == null || sectionsTag.isEmpty()) return sections;

        for (int i = 0; i < sectionsTag.size(); i++) {
            CompoundTag sectionTag = sectionsTag.getCompound(i);
            Section section = parseSection(sectionTag);
            if (section != null) sections.add(section);
        }

        return sections;
    }

    private static Section parseSection(CompoundTag sectionTag) {
        int yIndex = -999;

        try {
            yIndex = sectionTag.getByte("Y", (byte) - 99);
            if (yIndex != -99) { }
            else {
                yIndex = sectionTag.getInt("Y", -999);
                if (yIndex == -999) yIndex = sectionTag.getShort("Y", (short) -999);
            }
        } catch (Exception e) {
            yIndex = sectionTag.getInt("Y", -999);
            if (yIndex == -999) yIndex = sectionTag.getShort("Y", (short) -999);
        }

        if (yIndex == -999 || yIndex == -99) {
            System.out.println("Could not find Y index in section.");
            Tag yTag = sectionTag.get("Y");
            if (yTag != null) {
                System.out.println("Y tag type: " + yTag.getType() + ", value: " + yTag.getValue());
            }
            yIndex = 0;
        }

        CompoundTag blockStatesTag = sectionTag.getCompound("block_states", null);
        if (blockStatesTag == null) blockStatesTag = sectionTag.getCompound("BlockStates", null);
        if (blockStatesTag == null) return new Section(yIndex, List.of(new BlockState("minecraft:air")), null);

        ListTag paletteTag = blockStatesTag.getList("palette", new ListTag(TagType.COMPOUND));
        if (paletteTag == null || paletteTag.isEmpty()) paletteTag = blockStatesTag.getList("Palette", new ListTag(TagType.COMPOUND));

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
}
