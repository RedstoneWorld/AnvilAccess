package xyz.gamecrash.AnvilAccess.model;

import java.io.IOException;

@FunctionalInterface
public interface ChunkLoader {
    Chunk load(int localX, int localZ) throws IOException; // for future reference: reading through io
}
