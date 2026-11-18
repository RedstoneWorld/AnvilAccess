package dev.gamecrash.anvilaccess.model;

import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class Section {
    public static final int BLOCK_COUNT = 16*16*16;

    private final int yIndex;
    private final List<BlockState> palette;
    private long[] data;
    private boolean modified;

    public Section(int yIndex, List<BlockState> palette, long[] data) {
        this.yIndex = yIndex;
        this.palette = palette;
        this.data = data;
    }

    public int getAbsY() {
        return yIndex << 4;
    }

    public boolean isEmpty() {
        return palette.size() == 1 && palette.getFirst().id().equals("minecraft:air");
    }

    /** @param x,y,z Local Coords (0-15) */
    public BlockState getBlockState(int x, int y, int z) {
        if (data == null || palette.size() == 1) return palette.getFirst();

        return palette.get(get(data, bitsFor(palette.size()), y * 256 + z * 16 + x));
    }

    /** @param x,y,z Local Coords (0-15) */
    public void setBlockState(int x, int y, int z, BlockState state) {
        int old = bitsFor(palette.size());

        int idx = palette.indexOf(state);
        if (idx < 0) {
            palette.add(state);
            idx = palette.size() - 1;
        }

        /// bigger palette -> maybe more bpb -> repack :(
        int bits = bitsFor(palette.size());
        if (bits != old) repack(old, bits);
        if (bits > 0) set(data, bits, y * 256 + z * 16 + x, idx);

        modified = true;
    }

    public Stream<Block> streamBlocks() {
        return IntStream.range(0, BLOCK_COUNT).mapToObj(i -> {
           int x = i & 15, z = (i >> 4) & 15, y = i >> 8;
           return new Block(getBlockState(x, y, z), x, y, z);
        });
    }

    private static int get(long[] data, int bits, int i) {
        int perL = 64 / bits;
        return (int) ((data[i / perL] >>> (i % perL * bits)) & ((1L << bits) - 1));
    }

    private static void set(long[] data, int bits, int i, int value) {
        int perLong = 64 / bits;
        int shift = i % perLong * bits;
        long mask = ((1L << bits) - 1) << shift;
        data[i / perLong] = (data[i / perLong] & ~mask) | ((long) value << shift);
    }

    private static int bitsFor(int paletteSize) {
        return paletteSize <= 1 ? 0 : Math.max(4, 32 - Integer.numberOfLeadingZeros(paletteSize - 1)); // atleast 4 if palette isnt single entry
    }

    private void repack(int oldB, int newB) {
        int perL = 64 / newB;
        long[] packed = new long[(BLOCK_COUNT + perL - 1) / perL];
        if (data != null && oldB > 0) {
            for (int i = 0; i < BLOCK_COUNT; i++) {
                set(packed, newB, i, get(data, oldB, i));
            }
        }

        data = packed;
    }
}
