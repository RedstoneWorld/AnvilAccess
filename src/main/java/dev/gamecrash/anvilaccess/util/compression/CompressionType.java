package dev.gamecrash.anvilaccess.util.compression;

import lombok.Getter;

import java.io.IOException;

public enum CompressionType {
    GZIP(1),
    ZLIB(2),
    UNCOMPRESSED(3),
    LZ4(4),
    CUSTOM(127);

    @Getter
    private final int id;

    CompressionType(int id) {
        this.id = id;
    }

    public static CompressionType fromId(int id) throws IOException {
        return switch (id) {
            case 1 -> GZIP;
            case 2 -> ZLIB;
            case 3 -> UNCOMPRESSED;
            case 4 -> LZ4;
            case 127 -> CUSTOM;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }
}
