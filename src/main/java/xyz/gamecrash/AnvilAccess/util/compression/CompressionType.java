package xyz.gamecrash.AnvilAccess.util.compression;

import lombok.Getter;

/**
 * Enum for existing compression types stored within a chunk header
 */
public enum CompressionType {
    GZIP(1),
    ZLIB(2),
    UNCOMPRESSED(3),
    LZ4(4),
    ANY(127);

    @Getter private final int id;

    CompressionType(int id) { this.id = id; }

    public static CompressionType fromId(int id) {
        for (CompressionType type : values()) {
            if (id == 127) throw new UnsupportedOperationException("Compression type " + id + " is not supported");
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException("Unknown compression type: " + id);
    }
}
