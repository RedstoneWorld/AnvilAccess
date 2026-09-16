package dev.gamecrash.anvilaccess.model;

import xyz.gamecrash.anvilaccess.model.Chunk;

import java.nio.file.Path;

public class Region {
    private final Path file;
    private final int x, z;
    private final RegionHeaderEntry[] entries;
    private final Chunk[] chunks;

    public Region(Path file, int x, int z, RegionHeaderEntry[] entries) {
        this.x = x;
        this.z = z;
        this.file = file;
        this.entries = entries;
        chunks = new Chunk[1024];
    }
}
