package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.Serializable;

/**
 * Long tag
 */
@Serializable
public class LongTag extends Tag<Long> {
    private long value;

    public LongTag(long value) {
        super(TagType.LONG);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public LongTag copy() {
        return new LongTag(value);
    }

    @Override
    public String toString() {
        return value + "l";
    }

    @Override
    public boolean equals(Tag<Long> other) {
        return other.getValue() == value;
    }
}
