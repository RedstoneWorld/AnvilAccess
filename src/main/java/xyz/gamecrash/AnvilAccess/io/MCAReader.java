package xyz.gamecrash.AnvilAccess.io;

import lombok.Getter;
import xyz.gamecrash.AnvilAccess.model.ChunkData;
import xyz.gamecrash.AnvilAccess.model.RegionChunkEntry;
import xyz.gamecrash.AnvilAccess.util.compression.CompressionType;
import xyz.gamecrash.AnvilAccess.util.compression.CompressionUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * MCA-File reader for parsing headers and chunk data
 */
public class MCAReader implements AutoCloseable {
    private static final int HEADER_SIZE = 8192;

    @Getter private final Path file;
    private final byte[] fileData;

    public MCAReader(Path file) throws IOException {
        this.file = file;
        this.fileData = Files.readAllBytes(file);

        if (fileData.length < HEADER_SIZE) throw new IOException("MCA file too small: " + fileData.length + " bytes, smaller than normal header size");
    }

    // https://minecraft.wiki/w/Region_file_format#Structure

    /**
     * Reads chunk entries from the MCA file header
     */
    public RegionChunkEntry[] readChunkEntries() throws IOException {
        RegionChunkEntry[] entries = new RegionChunkEntry[1024];

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(fileData))) {
            // read location table (first 4kB)
            int[] offsets = new int[1024];
            int[] sectorCounts = new int[1024];

            for (int i = 0; i < 1024; i++) {
                int locationData = input.readInt();
                offsets[i] = (locationData >>> 8) & 0xFFFFFF; // first 3B
                sectorCounts[i] = locationData & 0xFF; // last B
            }

            // read timestamp table (next 4kB)
            int[] timestamps = new int[1024];
            for (int i = 0; i < 1024; i++) timestamps[i] = input.readInt();

            // create entires
            for (int i = 0; i < 1024; i++) entries[i] = new RegionChunkEntry(offsets[i], sectorCounts[i], timestamps[i]);
        }

        return entries;
    }

    /**
     * Reads raw (uncompressed) chunk data for the given entry
     */
    public ChunkData readChunkData(RegionChunkEntry entry) throws IOException {
        if (entry.isEmpty()) throw new IllegalArgumentException("Can't read data for empty chunk entry");

        long absoluteOffset = entry.getAbsoluteOffset();
        if (absoluteOffset + 5 > fileData.length) throw new IOException("Chunk data extends beyond file bounds");

        // read chunk header (4b length + 1b compression type)
        int dataOffset = (int) absoluteOffset;
        int length = ((fileData[dataOffset] & 0xFF) << 24) |
            ((fileData[dataOffset + 1] & 0xFF) << 16) |
            ((fileData[dataOffset + 2] & 0xFF) << 8) |
            ((fileData[dataOffset + 3] & 0xFF)
        );

        if (length < 1 || length > entry.getSizeInByte()) throw new IOException("Chunk data length out of range");

        byte compressionType = fileData[dataOffset + 4];

        // read compressed data
        byte[] compressedData = new byte[length - 1]; // for compression type byte
        System.arraycopy(fileData, dataOffset + 5, compressedData, 0, compressedData.length);

        return new ChunkData(length, compressionType, compressedData);
    }

    /**
     * Decompresses and reads chunk data
     */
    public byte[] decompressAndReadChunkData(RegionChunkEntry entry) throws IOException {
        ChunkData chunkData = readChunkData(entry);
        CompressionType type = CompressionType.fromId(chunkData.compressionType());

        return CompressionUtils.decompress(chunkData.data(), type);
    }

    /**
     * Gets the file size in bytes
     */
    public long getFileSize() { return fileData.length; }

    /**
     * Close any optional resources. It is currently a no-op due to all required data being loaded into memory during construction
     * <p>This only exists to allow MCAReader to be used in a try-with-resources-statement</p>
     */
    public void close() { }
}
