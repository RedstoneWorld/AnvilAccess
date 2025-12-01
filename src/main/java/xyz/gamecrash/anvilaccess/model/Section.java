package xyz.gamecrash.anvilaccess.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a 16x16x16 section of blocks within a chunk.
 * Handles palette-based block storage and bit-packed block states
 */
@SuppressWarnings("ClassCanBeRecord")
@Getter
@RequiredArgsConstructor
public class Section {
    private final int yIndex;
    private final List<BlockState> palette;
    private final long[] blockStates;

    private final int bitsPerBlock;
    private final int blocksPerLong;
    private final long mask;

    public Section(int yIndex, List<BlockState> palette, long[] blockStates) {
        this.yIndex = yIndex;
        this.palette = palette;
        this.blockStates = blockStates;

        if (palette.size() <= 1) {
            this.bitsPerBlock = 0;
            this.blocksPerLong = 0;
            this.mask = 0;
        } else {
            this.bitsPerBlock = Math.max(4, Integer.SIZE - Integer.numberOfLeadingZeros(palette.size() - 1));
            this.blocksPerLong = 64 / bitsPerBlock;
            this.mask = (1L << bitsPerBlock) - 1;
        }
    }

    /**
     * Gets the total number of blocks this section can contain
     */
    public static int getBlockCount() {
        return 16 * 16 * 16; // literally 4096
    }

    /**
     * Gets the block state at the given local coordinates within this section
     *
     * @param x Local x coordinate (0-15)
     * @param y Local y coordinate (0-15)
     * @param z Local z coordinate (0-15)
     * @return The BlockState at the given position
     */
    public BlockState getBlockState(int x, int y, int z) {
        if (x < 0 || x > 15 || y < 0 || y > 15 || z < 0 || z > 15)
            throw new IllegalArgumentException("Coordinates must be within 0-15 range");

        if (blockStates == null || blockStates.length == 0)
            return palette.isEmpty() ? new BlockState("minecraft:air") : palette.getFirst();

        int paletteIndex = getPaletteIndex(x, y, z);
        if (paletteIndex >= palette.size()) return new BlockState("minecraft:air");

        return palette.get(paletteIndex);
    }

    /**
     * Gets the absolute Y level of this section
     */
    public int getAbsoluteY() {
        return yIndex << 4;
    }

    /**
     * Checks if this section contains air-only blocks
     */
    public boolean isEmpty() {
        return palette.isEmpty() || (palette.size() == 1 && palette.getFirst().id().equals("minecraft:air"));
    }

    /**
     * Extracts the palette index for a block at the given coordinates
     */
    private int getPaletteIndex(int x, int y, int z) {
        if (palette.size() <= 1) return 0;

        int blockIndex = y * 256 + z * 16 + x;
        int longIndex = blockIndex / blocksPerLong;
        int bitOffset = (blockIndex % blocksPerLong) * bitsPerBlock;

        if (longIndex >= blockStates.length) return 0;

        return (int) ((blockStates[longIndex] >>> bitOffset) & mask);
    }

    /**
     * Streams all blocks in this section as Block objects
     */
    public Stream<Block> streamBlocks() {
        return IntStream.range(0, getBlockCount())
            .mapToObj(i -> {
                int x = i & 15;
                int z = (i >> 4) & 15;
                int y = (i >> 8) & 15;
                return new Block(getBlockState(x, y, z), x, y, z);
            });
    }
}
