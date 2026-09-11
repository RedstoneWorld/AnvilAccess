package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.Serializable;

/**
 * Short tag
 */
@Serializable
public class ShortTag extends Tag<Short> {
    private short value;

    public ShortTag(short value) {
        super(TagType.SHORT);
        this.value = value;
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public void setValue(Short value) {
        this.value = value;
    }

    @Override
    public ShortTag copy() {
        return new ShortTag(value);
    }

    @Override
    public String toString() {
        return value + "s";
    }

    @Override
    public boolean equals(Tag<Short> other) {
        return other.getValue() == value;
    }
}
