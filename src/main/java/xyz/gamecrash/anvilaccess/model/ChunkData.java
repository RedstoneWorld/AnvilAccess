package xyz.gamecrash.anvilaccess.model;

/**
 * Represents the raw chunk data of an MCA file, with its header
 * <p>
 * See under the <a href="https://minecraft.wiki/w/Region_file_format#Payload">Minecraft Wiki entry</a> -
 * the chunk data consists out of a 4B field indicating the length of the remaining chunk data, followed by one byte for the compression type, and the
 * rest of the data (length - 1) being the compressed, actual chunk data.
 * @see xyz.gamecrash.anvilaccess.util.compression.CompressionType
 * @see xyz.gamecrash.anvilaccess.io.MCAReader#readChunkData(RegionChunkEntry)
 */
public record ChunkData(int length, byte compressionType, byte[] data) {
}
