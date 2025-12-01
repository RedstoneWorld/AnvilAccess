package xyz.gamecrash.anvilaccess.model;

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

/**
 * Represents an MCA region file containing a 32x32 grid of chunks, and their entries in the region header.
 */
@Getter
@RequiredArgsConstructor
public class RegionFile {
    private final Path file;
    private final int regionX;
    private final int regionZ;
    private final RegionChunkEntry[] entries;
    private final Chunk[] chunks;

    @Setter
    private ChunkLoader chunkLoader;

    public RegionFile(Path file, int regionX, int regionZ, RegionChunkEntry[] entries) {
        this.file = file;
        this.regionX = regionX;
        this.regionZ = regionZ;
        this.entries = entries;
        this.chunks = new Chunk[1024];
    }

    /**
     * Gets a chunk at the given local coordinates within this region
     *
     * @param localX Local X coordinate within region (0-31)
     * @param localZ Local Z coordinate within region (0-31)
     * @return Optional containing the chunk, if it exists
     */
    public Optional<Chunk> getChunk(int localX, int localZ) {
        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31) return Optional.empty();

        // convert 2D coordinates to flat array index (row-major order thingy)
        int index = localZ * 32 + localX;
        RegionChunkEntry entry = entries[index];

        if (entry.isEmpty()) return Optional.empty();
        // return cached chunk if already loaded, else load (if lazy loader is available)
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

    /**
     * Gets a chunk at world chunk coordinates
     *
     * @param worldX World chunk X coordinate
     * @param worldZ World chunk Z coordinate
     * @return Optional containing the chunk, if it exists in this region
     */
    public Optional<Chunk> getWorldChunk(int worldX, int worldZ) {
        // convert world chunk coordinates to local region coordinates
        int localX = worldX - (regionX * 32);
        int localZ = worldZ - (regionZ * 32);

        // check if the world chunk is within the region bounds
        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31) return Optional.empty();

        return getChunk(localX, localZ);
    }

    /**
     * Gets the RegionChunkEntry at the given local coordinates
     */
    public RegionChunkEntry getEntry(int localX, int localZ) {
        if (localX < 0 || localX > 31 || localZ < 0 || localZ > 31)
            throw new IllegalArgumentException("Chunk coordinates out of bounds");

        // get flat array index from 2D coordinates
        return entries[localZ * 32 + localX];
    }

    /**
     * Streams all available chunks in this region.
     * Loads chunks lazily if not already cached
     */
    public Stream<Chunk> streamChunks() {
        List<Chunk> available = new ArrayList<>();

        // go through all 32x32 chunk coords in the region
        for (int z = 0; z < 32; z++) {
            for (int x = 0; x < 32; x++) {
                // get flat array index for given position
                int index = z * 32 + x;
                RegionChunkEntry entry = entries[index];
                if (entry.isEmpty()) continue;

                // use cached chunk if already loaded
                if (chunks[index] != null) {
                    available.add(chunks[index]);
                    continue;
                }

                // attempt lazy loading if chunk loader is available
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

    /**
     * Gets the number of chunks existing in this region
     */
    public long getChunkCount() {
        return Arrays.stream(entries)
            .filter(entry -> !entry.isEmpty())
            .count();
    }

    /**
     * Gets the minimum world chunk X coordinate covered by this region
     */
    public int getMinWorldChunkX() {
        return regionX * 32;
    }

    /**
     * Gets the maximum world chunk X coordinate covered by this region
     */
    public int getMaxWorldChunkX() {
        return regionX * 32 + 31;
    }

    /**
     * Gets the minimum world chunk Z coordinate covered by this region
     */
    public int getMinWorldChunkZ() {
        return regionZ * 32;
    }

    /**
     * Gets the maximum world chunk Z coordinate covered by this region
     */
    public int getMaxWorldChunkZ() {
        return regionZ * 32 + 31;
    }

    @Override
    public String toString() {
        return String.format("RegionFile(%d;%d)[File:%s;ChunkCount:%d]", regionX, regionZ, file.getFileName(), getChunkCount());
    }
}
