package xyz.gamecrash.anvilaccess.model;

import java.io.IOException;

/**
 * Functional interface for lazy loading chunks
 */
@FunctionalInterface
public interface ChunkLoader {
    Chunk load(int localX, int localZ) throws IOException;
}
