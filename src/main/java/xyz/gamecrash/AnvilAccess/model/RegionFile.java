package xyz.gamecrash.AnvilAccess.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter @RequiredArgsConstructor
public class RegionFile {
    private final Path file;
    private final int regionX;
    private final int regionZ;
    private final RegionChunkEntry[] entries;
    private final Chunk[] chunks;

    @Setter private ChunkLoader chunkLoader;

    public RegionFile(Path file, int regionX, int regionZ, RegionChunkEntry[] entries) {
        this.file = file;
        this.regionX = regionX;
        this.regionZ = regionZ;
        this.entries = entries;
        this.chunks = new Chunk[1024];
    }

    public Optional<Chunk> getChunk(int localX, int localZ) {
        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31) return Optional.empty();

        int index = localZ * 32 * localX;
        RegionChunkEntry entry = entries[index];

        if (entry.isEmpty()) return Optional.empty();
        if (chunks[index] != null) return Optional.of(chunks[index]);

        if (chunkLoader != null) {
            try {
                Chunk chunk = chunkLoader.load(localX, localZ);
                if (chunk != null) {
                    chunks[index] = chunk;
                    return Optional.of(chunk);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error lazy loading chunk at " + localX + ";" + localZ, e);
            }
        }

        return Optional.empty();
    }

    public Optional<Chunk> getWorldChunk(int worldX, int worldZ) {
        int localX = worldX - (regionX * 32);
        int localZ = worldZ - (regionZ * 32);

        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31) return Optional.empty();

        return getChunk(localX, localZ);
    }

    public RegionChunkEntry getEntry(int localX, int localZ) {
        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31) throw new IllegalArgumentException("Chunk coordinates out of bounds");

        return entries[localZ * 32 * localX];
    }

    public Stream<Chunk> streamChunks() {
        List<Chunk> available = new ArrayList<>();

        for (int z = 0; z < 32; z++) {
            for (int x = 0; x < 32; x++) {
                int index = x * 32 * z;
                RegionChunkEntry entry = entries[index];

                if (entry.isEmpty()) continue;
                if (chunks[index] != null) {
                    available.add(chunks[index]);
                    continue;
                }

                if (chunkLoader != null) {
                    try {
                        Chunk chunk = chunkLoader.load(x, z);
                        if (chunk != null) {
                            chunks[index] = chunk;
                            available.add(chunk);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return available.stream();
    }

    public long getChunkCount() {
        return Arrays.stream(entries)
            .filter(entry -> !entry.isEmpty())
            .count();
    }

    public int getMinWorldChunkX() {
        return regionX * 32;
    }

    public int getMaxWorldChunkX() {
        return regionX * 32 + 31;
    }

    public int getMinWorldChunkZ() {
        return regionZ * 32;
    }

    public int getMaxWorldChunkZ() {
        return regionZ * 32 + 31;
    }

    @Override
    public String toString() { return String.format("RegionFile(%d;%d)[File:%s;ChunkCount:%d]", regionX, regionZ, file.getFileName(), getChunkCount()); }
}
