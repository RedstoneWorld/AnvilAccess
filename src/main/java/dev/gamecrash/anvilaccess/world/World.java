package dev.gamecrash.anvilaccess.world;

import dev.gamecrash.anvilaccess.io.RegionStorage;
import dev.gamecrash.anvilaccess.model.Block;
import dev.gamecrash.anvilaccess.model.Chunk;
import dev.gamecrash.anvilaccess.nbt.ChunkCodec;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter @Accessors(fluent = true)
public class World implements AutoCloseable {
    private final RegionStorage chunks;
    private final RegionStorage entities;
    private final RegionStorage poi;

    public World(Path path, boolean readOnly) {
        chunks = new RegionStorage(path.resolve("region"), readOnly);
        entities = new RegionStorage(path.resolve("entities"), readOnly);
        poi = new RegionStorage(path.resolve("poi"), readOnly);
    }

    public World(Path path) {
        this(path, false);
    }

    public Optional<Chunk> getChunk(int chunkX, int chunkZ) throws IOException {
        CompoundTag nbt = chunks.read(chunkX, chunkZ);
        return Optional.ofNullable(nbt).map(t -> ChunkCodec.decode(chunkX, chunkZ, t));
    }

    public void setChunk(Chunk chunk) throws IOException {
        chunks.write(chunk.getChunkX(), chunk.getChunkZ(), chunk.getNbt());
    }

    public Optional<Block> getBlock(int x, int y, int z) throws IOException {
        return getChunk(x >> 4, z >> 4).map(chunk -> chunk.getBlock(x, y, z));
    }

    public Stream<Chunk> streamChunks() throws IOException {
        return chunks.listRegions().stream().flatMap(region -> IntStream.range(0, 1024)
            .mapToObj(i -> {
                try {
                    return getChunk(region[0] * 32 + (i & 31), region[1] * 32 + (i >> 5)).orElse(null);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            })
            .filter(Objects::nonNull));
    }

    @Override
    public void close() throws Exception {
        chunks.close();
        entities.close();
        poi.close();
    }
}
