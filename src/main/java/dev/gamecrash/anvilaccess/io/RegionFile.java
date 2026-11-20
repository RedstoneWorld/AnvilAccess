package dev.gamecrash.anvilaccess.io;

import dev.gamecrash.anvilaccess.model.RegionHeaderEntry;
import dev.gamecrash.anvilaccess.nbt.TagParser;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.EndTag;
import dev.gamecrash.anvilaccess.util.compression.CompressionType;
import dev.gamecrash.anvilaccess.util.compression.CompressionUtils;
import dev.gamecrash.anvilaccess.util.io.FastByteArrayInputStream;
import lombok.Getter;
import lombok.Setter;

import javax.swing.plaf.synth.Region;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;

public class RegionFile {
    private static final int SECTOR = 4096;
    private static final int EXT =  0x80;

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

    private void readFully(ByteBuffer buf, long pos) throws IOException {
        while (buf.hasRemaining()) {
            int read = channel.read(buf, pos + buf.position());
            if (read < 0) return; // chunks at end may be shorter than their sectors, so hitting end of file is fine
        }
    }

    private static int idx(int x, int z) {
        return z * 32 + x;
    }

    private Path extPath(int x, int z) {
        return path.resolveSibling("c." + (regionX * 32 + x) + "." + (regionZ * 32 + z) + ".mcc");
    }
}
