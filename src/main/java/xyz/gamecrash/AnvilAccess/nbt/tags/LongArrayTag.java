package xyz.gamecrash.AnvilAccess.nbt.tags;

import xyz.gamecrash.AnvilAccess.nbt.TagType;

import java.util.Arrays;

/**
 * Long array tag
 */
public class LongArrayTag extends Tag {
    private final long[] value;

    public LongArrayTag(long[] value) {
        super(TagType.LONG_ARRAY);
        this.value = value != null ? value.clone() : new long[0];
    }

    @Override
    public long[] getValue() { return value.clone(); }

    @Override
    public LongArrayTag copy() { return new LongArrayTag(value); }

    @Override
    public String toString() { return "[L;" + value.length + " longs]"; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LongArrayTag other)) return false;
        return Arrays.equals(value, other.value);
    }

    @Override
    public int hashCode() { return Arrays.hashCode(value); }
}
