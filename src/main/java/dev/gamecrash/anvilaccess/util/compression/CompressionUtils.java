package dev.gamecrash.anvilaccess.util.compression;

import lombok.Setter;
import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.*;

public class CompressionUtils {
    private static final ThreadLocal<Inflater> INFLATER = ThreadLocal.withInitial(Inflater::new);
    private static final ThreadLocal<Deflater> DEFLATER = ThreadLocal.withInitial(Deflater::new);

    @Setter
    private static CustomCompressor customCompressor;
    @Setter
    private static CustomDecompressor customDecompressor;

    private CompressionUtils() {}

    public static byte[] decompress(byte[] data, int offset, int len, CompressionType type) throws IOException {
        switch (type) {
            case GZIP: {
                try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(data, offset, len))) {
                    return in.readAllBytes();
                }
            }
            case ZLIB: return inflate(data, offset, len);
            case LZ4: {
                try (LZ4BlockInputStream in = new LZ4BlockInputStream(new ByteArrayInputStream(data, offset, len))) {
                    return in.readAllBytes();
                }
            }
            case CUSTOM: return customDecompressor.decompress(data, offset, len);
            default: return Arrays.copyOfRange(data, offset, offset + len);
        }
    }

    public static byte[] compress(byte[] data, CompressionType type) throws IOException {
        switch (type) {
            case GZIP: {
                ByteArrayOutputStream out = new ByteArrayOutputStream(data.length / 2 + 64);
                try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                    gzip.write(data);
                }
                return out.toByteArray();
            }
            case ZLIB: return deflate(data);
            case LZ4: {
                ByteArrayOutputStream out = new ByteArrayOutputStream(data.length / 2 + 64);
                try(LZ4BlockOutputStream lz4 = new LZ4BlockOutputStream(out, 1 << 16)) {
                    lz4.write(data);
                }
                return out.toByteArray();
            }
            case CUSTOM: return customCompressor.compress(data);
            default: return data;
        }
    }

    private static byte[] inflate(byte[] d, int off, int len) throws IOException {
        Inflater inflater = INFLATER.get();
        inflater.reset();
        inflater.setInput(d, off, len);
        byte[] out = new byte[Math.max(len * 4, 1024)];
        int n = 0;
        try {
            while (!inflater.finished()) {
                if (n == out.length) out = Arrays.copyOf(out, n * 2);
                int read = inflater.inflate(out, n, out.length - n);
                n += read;
            }
        } catch (DataFormatException e) {
            throw new IOException("Corrupt data?", e);
        }

        return Arrays.copyOf(out, n);
    }

    private static byte[] deflate(byte[] data) {
        Deflater deflater = DEFLATER.get();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();
        byte[] out = new byte[data.length / 2 + 64];
        int n = 0;
        while (!deflater.finished()) {
            if (n == out.length) out = Arrays.copyOf(out, n * 2);
            n += deflater.deflate(out, n, out.length - n);
        }

        return Arrays.copyOf(out, n);
    }

    @FunctionalInterface
    public interface CustomCompressor {
        byte[] compress(byte[] data);
    }
    @FunctionalInterface
    public interface CustomDecompressor {
        byte[] decompress(byte[] data, int offset, int len);
    }
}
