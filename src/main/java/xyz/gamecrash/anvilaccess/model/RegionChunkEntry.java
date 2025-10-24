package xyz.gamecrash.anvilaccess.model;

/**
 * Represents a single chunk entry in an MCA region file header.
 */
public record RegionChunkEntry(int offset, int sectorCount, int timestamp) {

    public RegionChunkEntry {
        if (offset < 0) throw new IllegalArgumentException("Offset cannot be negative");
        if (sectorCount < 0) throw new IllegalArgumentException("Sector-Count cannot be negative");
    }

    /**
     * Checks if this chunk entry represents an empty or nonexistent chunk
     */
    public boolean isEmpty() { return offset == 0 && sectorCount == 0; }

    /**
     * Gets the absolute byte offset in the region file
     */
    public long getAbsoluteOffset() { return offset * 4096L; }

    /**
     * Gets the chunk data size in bytes
     */
    public int getSizeInByte() { return sectorCount * 4096; }
}
