package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
/**
 * Float tag
 */
public class FloatTag extends Tag<Float> {
    private float value;

    public FloatTag(float value) {
        super(TagType.FLOAT);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public FloatTag copy() {
        return new FloatTag(value);
    }

    @Override
    public String toString() {
        return value + "f";
    }

    @Override
    public boolean equals(Tag<Float> other) {
        return other.getValue() == value;
    }
}
