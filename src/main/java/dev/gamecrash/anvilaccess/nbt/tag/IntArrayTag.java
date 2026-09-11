package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.ArraySerializer;
import dev.gamecrash.anvilaccess.nbt.snbt.Serializable;

import java.util.Arrays;

/**
 * Int array tag
 */
@Serializable(serializer = ArraySerializer.class)
public class IntArrayTag extends Tag<int[]> {
    private int[] value;

    public IntArrayTag(int[] value) {
        super(TagType.INT_ARRAY);
        this.value = value;
    }

    public IntArrayTag() {
        super(TagType.INT_ARRAY);
        value = new int[0];
    }

    @Override
    public int[] getValue() {
        return value.clone();
    }

    @Override
    public void setValue(int[] value) {
        this.value = value;
    }

    @Override
    public IntArrayTag copy() {
        return new IntArrayTag(value);
    }

    @Override
    public String toString() {
        return "[I;" + value.length + " ints]";
    }

    @Override
    public boolean equals(Tag<int[]> other) {
        if (this == other) return true;
        if (!(other instanceof IntArrayTag tag)) return false;
        return Arrays.equals(value, tag.value);
    }
}
