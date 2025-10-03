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
    public RegionChunkEntry[] readChunkEntries() throws IOException {
        RegionChunkEntry[] entries = new RegionChunkEntry[1024];

        try (DataInputStream input = new DataInputStream(new ByteArrayInputStream(fileData))) {
            int[] offsets = new int[1024];
            int[] sectorCounts = new int[1024];

            for (int i = 0; i < 1024; i++) {
                int locationData = input.readInt();
                offsets[i] = (locationData >>> 8) & 0xFFFFFF;
                sectorCounts[i] = locationData & 0xFF;
            }

            int[] timestamps = new int[1024];
            for (int i = 0; i < 1024; i++) timestamps[i] = input.readInt();

            for (int i = 0; i < 1024; i++) entries[i] = new RegionChunkEntry(offsets[i], sectorCounts[i], timestamps[i]);
        }

        return entries;
    }

    public ChunkData readChunkData(RegionChunkEntry entry) throws IOException {
        if (entry.isEmpty()) throw new IllegalArgumentException("Can't read data for empty chunk entry");

        long absoluteOffset = entry.getAbsoluteOffset();
        if (absoluteOffset + 5 > fileData.length) throw new IOException("Chunk data extends beyond file bounds");

        int dataOffset = (int) absoluteOffset;
        int length = ((fileData[dataOffset] & 0xFF) << 24) |
            ((fileData[dataOffset + 1] & 0xFF) << 16) |
            ((fileData[dataOffset + 2] & 0xFF) << 8) |
            ((fileData[dataOffset + 3] & 0xFF)
        );

        if (length < 1 || length > entry.getSizeInByte()) throw new IOException("Chunk data length out of range");

        byte compressionType = fileData[dataOffset + 4];
        byte[] compressedData = new byte[length - 1];
        System.arraycopy(fileData, dataOffset + 5, compressedData, 0, compressedData.length);

        return new ChunkData(length, compressionType, compressedData);
    }

    public byte[] decompressAndReadChunkData(RegionChunkEntry entry) throws IOException {
        ChunkData chunkData = readChunkData(entry);
        CompressionType type = CompressionType.fromId(chunkData.compressionType());

        return CompressionUtils.decompress(chunkData.data(), type);
    }

    public long getFileSize() { return fileData.length; }

    public void close() { }
}
