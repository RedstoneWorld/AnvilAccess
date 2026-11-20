package dev.gamecrash.anvilaccess.io;

import dev.gamecrash.anvilaccess.util.compression.CompressionType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;

public class RegionFile {
    private static final int SECTOR = 4096;
    private static final int EXT =  0x80;

    private final Path path;
    private final int regionX, regionZ;
    private final FileChannel channel;
    private final int[] locations = new int[1024];
    private final int[] timestamps = new int[1024];
    private final BitSet usedSectors = new BitSet();
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

    private void readFully(ByteBuffer buf, long pos) throws IOException {
        while (buf.hasRemaining()) {
            int read = channel.read(buf, pos + buf.position());
            if (read < 0) return; // chunks at end may be shorter than their sectors, so hitting end of file is fine
        }
    }
}
