package model;

public record RegionChunkEntry(int offset, int sectorCount, int timestamp) {

    public RegionChunkEntry {
        if (offset < 0) throw new IllegalArgumentException("Offset cannot be negative");
        if (sectorCount < 0) throw new IllegalArgumentException("Sector-Count cannot be negative");
    }

    public boolean isEmpty() { return offset == 0 && sectorCount == 0; }

    public long getAbsoluteOffset() { return offset * 4096L; }

    public int getSizeInByte() { return sectorCount * 4096; }
}
