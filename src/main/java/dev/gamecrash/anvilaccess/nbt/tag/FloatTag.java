package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

/**
 * Float tag
 */
@Serializable
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FloatTag other)) return false;
        return Float.compare(value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }
}
