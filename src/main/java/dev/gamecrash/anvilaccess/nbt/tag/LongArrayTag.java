package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.ArraySerializer;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

import java.util.Arrays;

/**
 * Long array tag
 */
@Serializable(serializer = ArraySerializer.class)
public class LongArrayTag extends Tag<long[]> {
    private long[] value;

    public LongArrayTag(long[] value) {
        super(TagType.LONG_ARRAY);
        this.value = value;
    }

    public LongArrayTag() {
        super(TagType.LONG_ARRAY);
        value = new long[0];
    }

    @Override
    public long[] getValue() {
        return value;
    }

    @Override
    public void setValue(long[] value) {
        this.value = value;
    }

    @Override
    public LongArrayTag copy() {
        return new LongArrayTag(value.clone());
    }

    @Override
    public String toString() {
        return "[L;" + value.length + " longs]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LongArrayTag other)) return false;
        return Arrays.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
