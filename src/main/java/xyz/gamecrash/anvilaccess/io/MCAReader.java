package xyz.gamecrash.anvilaccess.io;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.model.ChunkData;
import xyz.gamecrash.anvilaccess.model.RegionChunkEntry;
import xyz.gamecrash.anvilaccess.util.compression.CompressionType;
import xyz.gamecrash.anvilaccess.util.compression.CompressionUtils;

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

    @Getter
    private final Path file;
    private byte[] fileData;
    private volatile long lastModified;

    public MCAReader(Path file) throws IOException {
        this.file = file;
        // read MCA file (maybe not the best approach, but yeah)
        reloadFileData();
    }

    // https://minecraft.wiki/w/Region_file_format#Structure

    /**
     * Reads chunk entries from the MCA file header
     */
    public RegionChunkEntry[] readChunkEntries() throws IOException {
        refreshFileDataIfModified();
        // create array for max. 32*32 chunk entries
        RegionChunkEntry[] entries = new RegionChunkEntry[1024];

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(fileData))) {
            // read location table (first 4kB)
            int[] offsets = new int[1024];
            int[] sectorCounts = new int[1024];

            for (int i = 0; i < 1024; i++) {
                int locationData = input.readInt();
                // get offset (first 3 bytes) and sector count (last byte)
                offsets[i] = (locationData >>> 8) & 0xFFFFFF; // first 3B
                sectorCounts[i] = locationData & 0xFF; // last B
            }

            // read timestamp table (next 4kB)
            int[] timestamps = new int[1024];
            for (int i = 0; i < 1024; i++) timestamps[i] = input.readInt();

            // create entries from data
            for (int i = 0; i < 1024; i++)
                entries[i] = new RegionChunkEntry(offsets[i], sectorCounts[i], timestamps[i]);
        }

        return entries;
    }

    /**
     * Reads raw (uncompressed) chunk data for the given entry
     */
    public ChunkData readChunkData(RegionChunkEntry entry) throws IOException {
        if (entry.isEmpty()) throw new IllegalArgumentException("Can't read data for empty chunk entry");
        refreshFileDataIfModified();

        // calculate position of chunk data in file and validate bounds (we'll validate fully after reading length)
        long absoluteOffset = entry.getAbsoluteOffset();
        if (absoluteOffset + 4 > fileData.length) throw new IOException("Chunk data extends beyond file bounds");

        // read chunk header (4b length + 1b compression type)
        int dataOffset = (int) absoluteOffset;
        int length = ((fileData[dataOffset] & 0xFF) << 24) |
            ((fileData[dataOffset + 1] & 0xFF) << 16) |
            ((fileData[dataOffset + 2] & 0xFF) << 8) |
            ((fileData[dataOffset + 3] & 0xFF)
            );

        // validate chunk data length
        if (length < 1 || length > entry.getSizeInByte()) throw new IOException("Chunk data length out of range");

        // get compression type from header
        byte compressionType = fileData[dataOffset + 4];

        // read compressed chunk data (without byte for compression type)
        byte[] compressedData = new byte[length - 1]; // for compression type byte
        System.arraycopy(fileData, dataOffset + 5, compressedData, 0, compressedData.length);

        return new ChunkData(length, compressionType, compressedData);
    }

    /**
     * Decompresses and reads chunk data
     */
    public byte[] decompressAndReadChunkData(RegionChunkEntry entry) throws IOException {
        // read compressed chunk data
        ChunkData chunkData = readChunkData(entry);

        // get compression type
        CompressionType type = CompressionType.fromId(chunkData.compressionType());

        // decompress chunk data
        return CompressionUtils.decompress(chunkData.data(), type);
    }

    /**
     * Gets the file size in bytes
     */
    public long getFileSize() {
        return fileData.length;
    }

    /**
     * Close any optional resources. It is currently a no-op due to all required data being loaded into memory during construction
     * <p>This only exists to allow MCAReader to be used in a try-with-resources-statement</p>
     */
    public void close() {
    }

    /**
     * Re-read the MCA file
     */
    public synchronized void reloadFileData() throws IOException {
        long m = Files.getLastModifiedTime(file).toMillis();
        byte[] newData = Files.readAllBytes(file);
        if (newData.length < HEADER_SIZE)
            throw new IOException("MCA file too small: " + newData.length + " bytes, smaller than normal header size");

        this.fileData = newData;
        this.lastModified = m;
    }

    private void refreshFileDataIfModified() throws IOException {
        long current = Files.getLastModifiedTime(file).toMillis();
        if (current != lastModified) reloadFileData();
    }
}