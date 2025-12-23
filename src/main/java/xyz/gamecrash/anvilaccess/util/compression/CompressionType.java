package xyz.gamecrash.anvilaccess.util.compression;

import lombok.Getter;

/**
 * Enum for possible compression types / schemes stored within a chunk header
 *
 * <p>
 * Possible values:
 * <table border="1">
 *  <tr><td>Value</td><td>Description</td></tr>
 *  <tr><td>1</td><td>GZip (not in use anymore)</td></tr>
 *  <tr><td>2</td><td>ZLib</td></tr>
 *  <tr><td>3</td><td>Uncompressed</td></tr>
 *  <tr><td>4</td><td>LZ4 (since 24w04a)</td></tr>
 *  <tr><td>127</td><td>Custom compression algorithm (since 24w05a), see wiki entry linked down here for more information</td></tr>
 * </table>
 *  More information about this is viewable in the <a href="https://minecraft.wiki/w/Region_file_format#Payload">Minecraft-Wiki</a>.
 */
public enum CompressionType {
    GZIP(1),
    ZLIB(2),
    UNCOMPRESSED(3),
    LZ4(4),
    ANY(127);

    @Getter
    private final int id;

    CompressionType(int id) {
        this.id = id;
    }

    public static CompressionType fromId(int id) {
        if (id == 127) throw new UnsupportedOperationException("Compression type " + id + " is not supported or implemented");
        for (CompressionType type : values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException("Unknown compression type: " + id);
    }
}
