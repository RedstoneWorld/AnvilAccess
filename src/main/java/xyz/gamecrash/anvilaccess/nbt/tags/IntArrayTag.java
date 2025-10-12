package xyz.gamecrash.anvilaccess.nbt.tags;

import xyz.gamecrash.anvilaccess.nbt.TagType;

import java.util.Arrays;

/**
 * Int array tag
 */
public class IntArrayTag extends Tag {
    private final int[] value;

    public IntArrayTag(int[] value) {
        super(TagType.INT_ARRAY);
        this.value = value != null ? value.clone() : new int[0];
    }

    @Override
    public int[] getValue() { return value.clone(); }

    @Override
    public IntArrayTag copy() { return new IntArrayTag(value); }

    @Override
    public String toString() { return "[I;" + value.length + " ints]"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntArrayTag tag)) return false;
        return Arrays.equals(value, tag.value);
    }

    @Override
    public int hashCode() { return Arrays.hashCode(value); }
}
