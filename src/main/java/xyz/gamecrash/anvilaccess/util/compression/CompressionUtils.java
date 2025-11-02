package xyz.gamecrash.anvilaccess.util.compression;

import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Util class for handling different compression formats used in MCA files
 */
public class CompressionUtils {
    private static final LZ4Factory lz4Factory = LZ4Factory.fastestInstance();

    /**
     * Decompresses data based on compression type
     */
    public static byte[] decompress(byte[] data, CompressionType compressionType) throws IOException {
        return switch (compressionType) {
            case GZIP -> decompressGzip(data);
            case ZLIB -> decompressZlib(data);
            case UNCOMPRESSED -> data;
            case LZ4 -> decompressLz4(data);
            case ANY -> throw new UnsupportedOperationException("Compression type 127 is not supported");
        };
    }

    /**
     * Decompresses Gzip data
     */
    private static byte[] decompressGzip(byte[] data) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            GZIPInputStream gzipIn = new GZIPInputStream(input);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buf = new byte[8192];
            int length;
            while ((length = gzipIn.read(buf)) != -1) output.write(buf, 0, length);

            return output.toByteArray();
        }
    }

    /**
     * Decompresses data using Zlib
     */
    private static byte[] decompressZlib(byte[] data) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            InflaterInputStream inflaterIn = new InflaterInputStream(input);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buf = new byte[8192];
            int length;
            while ((length = inflaterIn.read(buf)) != -1) output.write(buf, 0, length);

            return output.toByteArray();
        }
    }

    /**
     * Decompresses data using LZ4
     * <p>
     * NOTE: LZ4 requires the decompressed size to be known beforehand.
     * This implementation assumes the first 4B contain the decompressed size.
     * </p>
     */
    private static byte[] decompressLz4(byte[] data) throws IOException {
        if (data.length < 4) throw new IOException("Data too short to contain size header");

        int decompressedSize = ((data[0] & 0xFF) << 24) |
            ((data[1] & 0xFF) << 16) |
            ((data[2] & 0xFF) << 8) |
            (data[3] & 0xFF);
        if (decompressedSize < 1 || decompressedSize > 100 * 1024 * 1024)
            throw new IOException("Invalid decompressed size: " + decompressedSize);

        LZ4FastDecompressor decompressor = lz4Factory.fastDecompressor();
        byte[] compressed = new byte[data.length - 4];
        System.arraycopy(data, 4, compressed, 0, compressed.length);

        return decompressor.decompress(compressed, decompressedSize);
    }
}
