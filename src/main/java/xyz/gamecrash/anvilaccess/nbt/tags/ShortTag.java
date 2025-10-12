package xyz.gamecrash.anvilaccess.nbt.tags;

import xyz.gamecrash.anvilaccess.nbt.TagType;

/**
 * Short tag
 */
public class ShortTag extends Tag {
    private final short value;

    public ShortTag(short value) {
        super(TagType.SHORT);
        this.value = value;
    }

    @Override
    public Short getValue() { return value; }

    @Override
    public ShortTag copy() { return new ShortTag(value); }

    @Override
    public String toString() { return value + "s"; }
}
