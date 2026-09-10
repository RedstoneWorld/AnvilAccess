package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

/**
 * Int tag
 */
public class IntTag extends Tag<Integer> {
    private int value;

    public IntTag(int value) {
        super(TagType.INT);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public IntTag copy() {
        return new IntTag(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Tag<Integer> other) {
        return other.getValue() == value;
    }
}
