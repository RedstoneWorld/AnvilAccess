package xyz.gamecrash.AnvilAccess.nbt.tags;

import xyz.gamecrash.AnvilAccess.nbt.TagType;

/**
 * Double tag
 */
public class DoubleTag extends Tag {
    private final double value;

    public DoubleTag(double value) {
        super(TagType.DOUBLE);
        this.value = value;
    }

    @Override
    public Double getValue() { return value; }

    @Override
    public DoubleTag copy() { return new DoubleTag(value); }

    @Override
    public String toString() { return value + "d"; }
}
