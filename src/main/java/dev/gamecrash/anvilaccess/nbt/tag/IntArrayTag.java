package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.ArraySerializer;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

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
        return value;
    }

    @Override
    public void setValue(int[] value) {
        this.value = value;
    }

    @Override
    public IntArrayTag copy() {
        return new IntArrayTag(value.clone());
    }

    @Override
    public String toString() {
        return "[I;" + value.length + " ints]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IntArrayTag other)) return false;
        return Arrays.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
