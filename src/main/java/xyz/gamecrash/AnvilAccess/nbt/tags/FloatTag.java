package xyz.gamecrash.AnvilAccess.nbt.tags;

import xyz.gamecrash.AnvilAccess.nbt.TagType;

public class FloatTag extends Tag {
    private final float value;

    public FloatTag(float value) {
        super(TagType.FLOAT);
        this.value = value;
    }

    @Override
    public Float getValue() { return value; }

    @Override
    public FloatTag copy() { return new FloatTag(value); }

    @Override
    public String toString() { return value + "f"; }
}
