package xyz.gamecrash.AnvilAccess.util.validation;

import xyz.gamecrash.AnvilAccess.model.RegionChunkEntry;
import xyz.gamecrash.AnvilAccess.model.RegionFile;

import java.nio.file.Files;

public class ValidationUtils {
    public static boolean isValidRegionFile(RegionFile regionFile) {
        try {
            if (!Files.exists(regionFile.getFile())) return false;
            if (Files.size(regionFile.getFile()) < 8192) return false;
            if (regionFile.getChunkCount() < 1) return false;
            else return regionFile.getChunkCount() <= 1024;
        } catch (Exception e) {
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

    private static void validateChunkEntry(RegionChunkEntry entry, ValidationResult result, int x, int z) {
        if (entry.offset() < 2) result.addError(String.format("Invalid chunk offset at (%d, %d): %d", x, z, entry.offset()));
        if (entry.sectorCount() <= 0) result.addError(String.format("Invalid sector count at (%d, %d): %d", x, z, entry.sectorCount()));
        if (entry.timestamp() < 0) result.addWarning(String.format("Negative timestamp at (%d, %d): %d", x, z, entry.timestamp()));
    }
}
