package xyz.gamecrash.anvilaccess.world;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.io.RegionFileLoader;
import xyz.gamecrash.anvilaccess.model.Block;
import xyz.gamecrash.anvilaccess.model.Chunk;
import xyz.gamecrash.anvilaccess.model.RegionFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Manager for world regions that provides access to all chunks in the world
 */
@Getter
public class WorldRegionManager {
    private final Path worldDirectory;
    private final Path regionDirectory;

    public WorldRegionManager(Path worldFolder) {
        this.worldDirectory = worldFolder;
        regionDirectory = worldFolder.resolve("region");

        if (!Files.exists(regionDirectory))
            throw new IllegalArgumentException("Region directory does not exist: " + regionDirectory);
    }

    public WorldRegionManager(String path) {
        this.worldDirectory = Path.of(path);
        regionDirectory = worldDirectory.resolve("region");

        if (!Files.exists(regionDirectory))
            throw new IllegalArgumentException("Region directory does not exist: " + regionDirectory);
    }

    /**
     * Gets all region files in the world
     */
    public List<RegionFile> getRegionFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(regionDirectory)) {
            return paths
                .filter(Files::isRegularFile)
                .filter(RegionFileLoader::isValidMCAFile)
                .map(this::loadRegionSafely)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        }
    }

    /**
     * Gets a region file by coordinates
     */
    public Optional<RegionFile> getRegion(int regionX, int regionZ) {
        Path regionFile = regionDirectory.resolve(String.format("r.%d.%d.mca", regionX, regionZ));
        if (!Files.exists(regionFile)) return Optional.empty();

        return loadRegionSafely(regionFile);
    }

    /**
     * Gets a chunk by world coordinates
     */
    public Optional<Chunk> getChunk(int chunkX, int chunkZ) {
        int regionX = chunkX >> 5;
        int regionZ = chunkZ >> 5;

        Optional<RegionFile> region = getRegion(regionX, regionZ);
        if (region.isEmpty()) return Optional.empty();

        return region.get().getWorldChunk(chunkX, chunkZ);
    }

    /**
     * Gets a block at the given coordinates
     */
    public Optional<Block> getBlock(int x, int y, int z) {
        Optional<Chunk> chunk = getChunk(x >> 4, z >> 4);
        return chunk.map(c -> c.getBlock(x, y, z));
    }

    /**
     * Streams all chunks in the world
     */
    public Stream<Chunk> getAllChunks() throws IOException {
        return getRegionFiles().stream().flatMap(RegionFile::streamChunks);
    }

    /**
     * Validates the world structure
     */
    public boolean validate() {
        try {
            if (!Files.exists(worldDirectory)) return false;
            if (!Files.exists(regionDirectory)) return false;

            try (Stream<Path> paths = Files.walk(regionDirectory, 1)) {
                return paths
                    .filter(Files::isRegularFile)
                    .anyMatch(RegionFileLoader::isValidMCAFile);
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Gets the total number of regions
     */
    public long getRegionCount() throws IOException {
        try (Stream<Path> paths = Files.walk(regionDirectory, 1)) {
            return paths
                .filter(Files::isRegularFile)
                .filter(RegionFileLoader::isValidMCAFile)
                .count();
        }
    }

    /**
     * Gets the total number of chunks across all regions
     */
    public long getTotalChunkCount() throws IOException {
        return getRegionFiles().stream().mapToLong(RegionFile::getChunkCount).sum();
    }

    /**
     * Safely loads a region file, returning an empty optional on error
     */
    private Optional<RegionFile> loadRegionSafely(Path regionFile) {
        try {
            return Optional.of(RegionFileLoader.loadRegion(regionFile));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
