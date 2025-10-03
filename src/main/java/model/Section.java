package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Section {
    private final int yIndex;
    private final List<BlockState> palette;
    private final long[] blockStates;

    public BlockState getBlockState(int x, int y, int z) {
        if (x < 0 || x > 15 || y < 0 || y > 15 || z < 0 || z > 15) throw new IllegalArgumentException("Coordinates must be within 0-15 range");

        if (blockStates == null || blockStates.length == 0) { return palette.isEmpty() ? new BlockState("minecraft:air") : palette.getFirst(); }

        int paletteIndex = getPaletteIndex(x, y, z);
        if (paletteIndex >= palette.size()) return new BlockState("minecraft:air");

        return palette.get(paletteIndex);
    }

    public int getAbsoluteY() { return yIndex << 4; }

    public boolean isEmpty() { return palette.isEmpty() || (palette.size() == 1 && palette.getFirst().id().equals("minecraft:air")); }

    public static int getBlockCount() { return 16 * 16 * 16; }

    private int getPaletteIndex(int x, int y, int z) {
        if (palette.size() <= 1) return 0;

        int bitsPerBlock = Math.max(4, Integer.SIZE - Integer.numberOfLeadingZeros(palette.size() - 1));
        int blocksPerLong = 64 / bitsPerBlock;
        int blockIndex = y * 256 + z * 16 + x;

        int longIndex = blockIndex / blocksPerLong;
        int bitOffset = (blockIndex % blocksPerLong) * bitsPerBlock;

        if (longIndex >= blockStates.length) return 0;

        long mask = (1L << bitsPerBlock) - 1;
        return (int) ((blockStates[longIndex] >>> bitOffset ) & mask);
    }
}
