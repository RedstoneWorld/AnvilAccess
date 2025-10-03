package xyz.gamecrash.AnvilAccess.world;

import lombok.Getter;
import xyz.gamecrash.AnvilAccess.io.RegionFileLoader;
import xyz.gamecrash.AnvilAccess.model.Chunk;
import xyz.gamecrash.AnvilAccess.model.RegionFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public class WorldRegionManager {
    private final Path worldFolder;
    private final Path regionFolder;

    public WorldRegionManager(Path worldFolder) {
        this.worldFolder = worldFolder;
        regionFolder = worldFolder.resolve("region");

        if (!Files.exists(regionFolder)) throw new IllegalArgumentException("Region folder does not exist: " + regionFolder);
    }

    public List<RegionFile> getRegionFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(regionFolder)) {
            return paths
                .filter(Files::isRegularFile)
                .filter(RegionFileLoader::isValidMCAFile)
                .map(this::loadRegionSafely)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        }
    }

    public Optional<RegionFile> getRegion(int regionX, int regionZ) {
        Path regionFile = regionFolder.resolve(String.format("r.%d.%d.mca", regionX, regionZ));
        if (!Files.exists(regionFile)) return Optional.empty();

        return loadRegionSafely(regionFile);
    }

    public Optional<Chunk> getChunk(int chunkX, int chunkZ) {
        int regionX = chunkX >> 5;
        int regionZ = chunkZ >> 5;

        Optional<RegionFile> region = getRegion(regionX, regionZ);
        if (region.isEmpty()) return Optional.empty();

        return region.get().getWorldChunk(chunkX, chunkZ);
    }

    public Stream<Chunk> getAllChunks() throws IOException {
        return getRegionFiles().stream().flatMap(RegionFile::streamChunks);
    }

    public boolean validate() {
        try {
            if (!Files.exists(worldFolder)) return false;
            if (!Files.exists(regionFolder)) return false;

            try (Stream<Path> paths = Files.walk(regionFolder, 1)) {
                return paths
                    .filter(Files::isRegularFile)
                    .anyMatch(RegionFileLoader::isValidMCAFile);
            }
        } catch (IOException e) {
            return false;
        }
    }

    private Optional<RegionFile> loadRegionSafely(Path regionFile) {
        try {
            return Optional.of(RegionFileLoader.loadRegion(regionFile));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
