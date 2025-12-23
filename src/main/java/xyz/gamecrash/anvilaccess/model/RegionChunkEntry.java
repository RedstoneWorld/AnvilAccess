package xyz.gamecrash.anvilaccess.model;

/**
 * Represents a single chunk entry in an MCA region file header.
 * <p>
 * As seen under the <a href="https://minecraft.wiki/w/Region_file_format#Chunk_location">Wiki Entry</a>, a chunk entry in a header consists out of location
 * information (where to find the chunk in the file) - represented by {@code offset} and {@code sectorCount}, and a {@code timestamp} stating when the chunk has
 * last been modified.
 * @see RegionFile
 * @see xyz.gamecrash.anvilaccess.io.MCAReader#readChunkData(RegionChunkEntry)
 */
public record RegionChunkEntry(int offset, int sectorCount, int timestamp) {

    public RegionChunkEntry {
        if (offset < 0) throw new IllegalArgumentException("Offset cannot be negative");
        if (sectorCount < 0) throw new IllegalArgumentException("Sector-Count cannot be negative");
    }

    /**
     * Checks if this chunk entry represents an empty or nonexistent chunk
     */
    public boolean isEmpty() {
        return offset == 0 && sectorCount == 0;
    }

    /**
     * Gets the absolute byte offset in the region file
     */
    public long getAbsoluteOffset() {
        return offset * 4096L;
    }

    /**
     * Gets the chunk data size in bytes
     */
    public int getSizeInByte() {
        return sectorCount * 4096;
    }
}
