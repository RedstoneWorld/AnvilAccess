package xyz.gamecrash.AnvilAccess.util.validation;

import xyz.gamecrash.AnvilAccess.model.Chunk;
import xyz.gamecrash.AnvilAccess.model.RegionChunkEntry;
import xyz.gamecrash.AnvilAccess.model.RegionFile;
import xyz.gamecrash.AnvilAccess.model.Section;
import xyz.gamecrash.AnvilAccess.nbt.TagType;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.world.WorldRegionManager;

import java.io.IOException;
import java.nio.file.Files;

public class ValidationUtils {
    public static boolean isValidRegionFile(RegionFile regionFile) {
        try {
            if (!Files.exists(regionFile.getFile())) return false;
            if (Files.size(regionFile.getFile()) < 8192) return false;
            else return regionFile.getChunkCount() <= 1024;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidChunk(Chunk chunk) {
        if (chunk == null) return false;
        if (chunk.getNbt() == null) return false;
        CompoundTag nbt = chunk.getNbt();
        return nbt.contains("xPos", TagType.INT) && nbt.contains("zPos", TagType.INT);
    }

    public static boolean isValidWorld(WorldRegionManager world) {
        try {
            if (!world.validate()) return false;
            for (RegionFile region : world.getRegionFiles()) {
                if (!isValidRegionFile(region)) return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static ValidationResult validateRegionFile(RegionFile regionFile) {
        ValidationResult res = new ValidationResult();

        try {
            if (!Files.exists(regionFile.getFile())) {
                res.addError("File does not exist");
                return res;
            }

            if (Files.size(regionFile.getFile()) < 8192) res.addError("Region file size is smaller than the minimum required size of 8192 bytes");
            if (regionFile.getChunkCount() < 1) res.addWarning("Region file contains no chunks");
            else if (regionFile.getChunkCount() > 1024) res.addError("Region file contains too many chunks (max: 32*32)");

            for (int z = 0; z < 32; z++) {
                for (int x = 0; x < 32; x++) {
                    RegionChunkEntry entry = regionFile.getEntry(x, z);
                    if (!entry.isEmpty()) validateChunkEntry(entry, res, x, z);
                }
            }
        } catch (Exception e) {
            res.addError("IO error while accessing file: " + e.getMessage());
        }

        return res;
    }

    public static ValidationResult validateChunk(Chunk chunk) {
        ValidationResult result = new ValidationResult();

        if (chunk == null) {
            result.addError("Chunk is null");
            return result;
        }
        if (chunk.getSections().isEmpty()) result.addWarning("Chunk has no sections");
        else {
            for (Section section : chunk.getSections()) validateSection(section, result);
        }

        if (chunk.getNbt() == null) result.addError("Chunk NBT data is null");
        else validateChunkNBT(chunk.getNbt(), result);

        return result;
    }

    public static ValidationResult validateWorld(WorldRegionManager world) {
        ValidationResult result = new ValidationResult();

        try {
            if (!world.validate()) {
                result.addError("WorldRegionManager validation failed");
                return result;
            }

            long regionCount = world.getRegionFiles().size();
            if (regionCount == 0) result.addWarning("World has no regions");

            for (RegionFile region : world.getRegionFiles()) {
                ValidationResult regionResult = validateRegionFile(region);
                result.merge(regionResult, "Region " + region.getFile().getFileName());
            }

        } catch (IOException e) {
            result.addError("IO error while validating world: " + e.getMessage());
        }

        return result;
    }

    private static void validateChunkEntry(RegionChunkEntry entry, ValidationResult result, int x, int z) {
        if (entry.offset() < 2) result.addError(String.format("Invalid chunk offset at (%d;%d): %d", x, z, entry.offset()));
        if (entry.sectorCount() <= 0) result.addError(String.format("Invalid sector count at (%d;%d): %d", x, z, entry.sectorCount()));
        if (entry.timestamp() < 0) result.addWarning(String.format("Negative timestamp at (%d;%d): %d", x, z, entry.timestamp()));
    }

    private static void validateSection(Section section, ValidationResult result) {
        if (section.getPalette() == null || section.getPalette().isEmpty()) result.addError("Section has empty or null palette");

        int yIndex = section.getYIndex();
        if (yIndex < -20 || yIndex > 50) result.addWarning("Unusual Y index for section: " + yIndex);

        if (section.getBlockStates() != null) {
            long[] blockStates = section.getBlockStates();
            if (blockStates.length == 0) result.addWarning("Section has empty block states array");
        }
    }

    private static void validateChunkNBT(CompoundTag nbt, ValidationResult result) {
        if (!nbt.contains("xPos", TagType.INT)) result.addError("xPos not found");
        if (!nbt.contains("zPos", TagType.INT)) result.addError("zPos not found");
        if (!nbt.contains("DataVersion", TagType.INT)) result.addWarning("DataVersion not found");
        else {
            int dataVersion = nbt.getInt("DataVersion", 0);
            if (dataVersion < 2860) result.addWarning("Old (older than 1.18) data version: " + dataVersion);
        }
    }
}
