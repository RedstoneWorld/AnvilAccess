package dev.gamecrash.anvilaccess.io;

import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/** A directory of region files, e.g. {@code region/} or {@code entities/} */
@RequiredArgsConstructor
public class RegionStorage implements AutoCloseable {
    private static final Pattern NAME_PATTERN = Pattern.compile("r\\.-?\\d+\\.-?\\d+\\.mca");
    private static final int MAX_OPEN = 16;

    private final Path path;
    private final boolean readOnly;
    private final Map<Long, RegionFile> open = new LinkedHashMap<>(32, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, RegionFile> eldest) {
            if (size() <= MAX_OPEN) return false;
            try {
                eldest.getValue().close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return true;
        }
    };

    public synchronized CompoundTag read(int chunkX, int chunkZ) throws IOException {
        RegionFile r = region(chunkX >> 5, chunkZ >> 5, false);
        return r == null ? null : r.readChunk(chunkX & 31, chunkZ & 31);
    }

    public synchronized void write(int chunkX, int chunkZ, CompoundTag chunk) throws IOException {
        region(chunkX >> 5, chunkZ >> 5, true).writeChunk(chunkX & 31, chunkZ & 31, chunk);
    }

    public synchronized void delete(int chunkX, int chunkZ) throws IOException {
        RegionFile region = region(chunkX >> 5, chunkZ >> 5, false);
        if (region != null) region.deleteChunk(chunkX & 31, chunkZ & 31);
    }

    public synchronized RegionFile region(int regionX, int regionZ, boolean c) throws IOException {
        long k = ((long) regionX << 32) | (regionZ & 0xFFFFFFFFL);
        RegionFile r = open.get(k);
        if (r != null) return r;

        Path p = path.resolve("r." + regionX + "." + regionZ + ".mca");
        if (!Files.exists(p)) {
            if (!c) return null;
            Files.createDirectories(path);
        }

        r = new RegionFile(p, readOnly);
        open.put(k, r);
        return r;
    }

    public List<int[]> listRegions() throws IOException {
        List<int[]> regions = new ArrayList<>();
        if (!Files.isDirectory(path)) return regions;

        try(Stream<Path> paths = Files.list(path)) {
            paths.map(p -> p.getFileName().toString())
                .filter(name -> NAME_PATTERN.matcher(name).matches())
                .forEach(name -> {
                    String[] parts = name.split("\\.");
                    regions.add(new int[]{Integer.parseInt(parts[1]), Integer.parseInt(parts[2])});
                });
        }

        return regions;
    }

    @Override
    public void close() throws IOException {
        for (RegionFile f : open.values()) f.close();
        open.clear();
    }
}
