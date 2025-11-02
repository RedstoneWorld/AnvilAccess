package xyz.gamecrash.anvilaccess.nbt.tags;

import xyz.gamecrash.anvilaccess.nbt.TagType;

/**
 * Long tag
 */
public class LongTag extends Tag {
    private final long value;

    public LongTag(long value) {
        super(TagType.LONG);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public LongTag copy() {
        return new LongTag(value);
    }

    @Override
    public String toString() {
        return value + "L";
    }
}
