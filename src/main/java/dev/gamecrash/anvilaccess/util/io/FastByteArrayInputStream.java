package dev.gamecrash.anvilaccess.util.io;

import java.io.IOException;
import java.io.InputStream;

public class FastByteArrayInputStream extends InputStream {
    private final byte[] buf;
    private int pos;

    public FastByteArrayInputStream(byte[] buf) {
        this.buf = buf;
    }

    @Override
    public int read() throws IOException {
        return pos < buf.length ? buf[pos++] & 0xFF : -1;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        int n = Math.min(len, buf.length - pos);
        if (n <= 0) return len == 0 ? 0 : -1;
        System.arraycopy(buf, pos, b, off, n);
        pos += n;
        return n;
    }
}
