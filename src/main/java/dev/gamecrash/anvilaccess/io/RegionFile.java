package dev.gamecrash.anvilaccess.io;

import dev.gamecrash.anvilaccess.model.Chunk;
import dev.gamecrash.anvilaccess.model.RegionHeaderEntry;
import dev.gamecrash.anvilaccess.nbt.ChunkCodec;
import dev.gamecrash.anvilaccess.nbt.TagParser;
import dev.gamecrash.anvilaccess.nbt.TagWriter;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.util.compression.CompressionType;
import dev.gamecrash.anvilaccess.util.compression.CompressionUtils;
import dev.gamecrash.anvilaccess.util.io.FastByteArrayInputStream;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

public class RegionFile implements AutoCloseable {
    private static final int SECTOR = 4096;
    private static final int EXT =  0x80;
    private static final int MAX_SECTORS = 255;

    private final Path path;
    @Getter
    private final int regionX, regionZ;
    private final FileChannel channel;
    private final int[] locations = new int[1024];
    private final int[] timestamps = new int[1024];
    private final BitSet usedSectors = new BitSet();
    @Setter
    private CompressionType compression = CompressionType.ZLIB;

    public static RegionFile open(Path path) throws IOException {
        return new RegionFile(path, false);
    }

    public static RegionFile openRead(Path path) throws IOException {
        return new RegionFile(path, true);
    }

    public RegionFile(Path path, boolean readOnly) throws IOException {
        this.path = path;
        String[] name = path.getFileName().toString().split("\\."); // r.x.z.mca
        regionX = Integer.parseInt(name[1]);
        regionZ = Integer.parseInt(name[2]);

        channel = readOnly
            ? FileChannel.open(path, StandardOpenOption.READ)
            : FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        if (channel.size() == 0) {
            if (!readOnly) channel.write(ByteBuffer.allocate(SECTOR * 2), 0);
        } else {
            ByteBuffer header = ByteBuffer.allocate(SECTOR * 2);
            readFully(header, 0);
            header.rewind();
            header.asIntBuffer().get(locations).get(timestamps);
        }

        usedSectors.set(0, 2);
        for (int loc : locations) {
            if (loc != 0) usedSectors.set(loc >>> 8, (loc >>> 8) + (loc & 0xFF));
        }
    }

    public synchronized boolean hasChunk(int x, int z) {
        return locations[idx(x, z)] != 0;
    }

    public synchronized RegionHeaderEntry getEntry(int x, int z) {
        int i = idx(x, z);
        return new RegionHeaderEntry(locations[i] >>> 8, locations[i] & 0xFF, timestamps[i]);
    }

    public synchronized int getChunkCount() {
        int cnt  = 0;
        for (int loc : locations){
            if (loc != 0) cnt++;
        }
        return cnt;
    }

    public synchronized CompoundTag readChunk(int x, int z) throws IOException {
        int loc = locations[idx(x, z)];
        if (loc == 0) return null;

        ByteBuffer buf = ByteBuffer.allocate(SECTOR * (loc & 0xFF));
        readFully(buf, (long) SECTOR * (loc >>> 8));

        int len = buf.getInt(0);
        int type = buf.get(4) & 0xFF;
        CompressionType ct = CompressionType.fromId(type & ~EXT);

        byte[] nbt;
        if ((type & EXT) != 0) {
            byte[] ext = Files.readAllBytes(extPath(x, z));
            nbt = CompressionUtils.decompress(ext, 0, ext.length, ct);
        } else nbt = CompressionUtils.decompress(buf.array(), 5, len - 1, ct);

        return (CompoundTag) TagParser.readNamed(new DataInputStream(new FastByteArrayInputStream(nbt))).tag();
    }

    public synchronized void writeChunk(int x, int z, CompoundTag chunk) throws IOException {
        int idx = idx(x, z);

        ByteArrayOutputStream raw = new ByteArrayOutputStream(16 * 1024);
        TagWriter.writeNamed(new DataOutputStream(raw), "", chunk);
        byte[] data = CompressionUtils.compress(raw.toByteArray(), compression);

        int size = data.length + 5;
        int sectors = (size + SECTOR - 1) / SECTOR;
        boolean ext = sectors > MAX_SECTORS;
        if (ext) {
            Files.write(extPath(x, z), data);
            sectors = 1;
        }

        ByteBuffer buf = ByteBuffer.allocate(sectors + SECTOR);
        buf.putInt(ext ? 1 : data.length + 1);
        buf.put((byte) (compression.getId() | (ext ? EXT : 0)));
        if (!ext) buf.put(data);
        buf.rewind();

        int old = locations[idx];
        if (old != 0) usedSectors.clear(old >>> 8, (old >>> 8) + (old & 0xFF));

        int offset = allocate(sectors);
        writeFully(buf, (long) offset + SECTOR);
        writeHeader(idx, (offset << 8) | sectors, (int) (System.currentTimeMillis() / 1000));
    }

    public synchronized void deleteChunk(int x, int z) throws IOException {
        int idx = idx(x, z);
        int old = locations[idx];
        if (old == 0) return;

        usedSectors.clear(old >>> 8, (old >>> 8) + (old & 0xFF));
        writeHeader(idx, 0, 0);
        if ((old & 0xFF) == 1) Files.deleteIfExists(extPath(x, z));
    }

    public synchronized Chunk getChunk(int x, int z) throws IOException {
        return ChunkCodec.decode(x, z, readChunk(x, z));
    }

    @Override
    public synchronized void close() throws IOException {
        channel.close();
    }

    private void readFully(ByteBuffer buf, long pos) throws IOException {
        while (buf.hasRemaining()) {
            int read = channel.read(buf, pos + buf.position());
            if (read < 0) return; // chunks at end may be shorter than their sectors, so hitting end of file is fine
        }
    }

    private static int idx(int x, int z) {
        return Objects.checkIndex(z, 32) * 32 + Objects.checkIndex(x, 32);
    }

    private Path extPath(int x, int z) {
        return path.resolveSibling("c." + (regionX * 32 + x) + "." + (regionZ * 32 + z) + ".mcc");
    }

    private int allocate(int sectors) {
        int start = 2;
        while (true) {
            int free = usedSectors.nextClearBit(start);
            int nextUsed = usedSectors.nextSetBit(free);
            if (nextUsed == -1 || nextUsed - free >= sectors) {
                usedSectors.set(free, free + sectors);
                return free;
            }
            start = nextUsed;
        }
    }

    private void writeFully(ByteBuffer buf, long pos) throws IOException {
        while (buf.hasRemaining()) {
            pos += channel.write(buf, pos);
        }
    }

    private void writeHeader(int idx, int loc, int timestamp) throws IOException {
        writeFully(ByteBuffer.allocate(4).putInt(0, loc), idx * 4L);
        writeFully(ByteBuffer.allocate(4).putInt(0, timestamp), SECTOR + idx * 4L);
        locations[idx] = loc;
        timestamps[idx] = timestamp;
    }
}
