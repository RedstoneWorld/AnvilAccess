package dev.gamecrash.anvilaccess.model;

public record RegionHeaderEntry(int offset, int sectorCount, int timestamp) {
    public boolean isEmpty() {
        return offset == 0 && sectorCount == 0;
    }

    public long getAbsoluteOffset() {
        return offset * 4096L;
    }

    public int getSizeInByte() {
        return sectorCount * 4096;
    }
}
