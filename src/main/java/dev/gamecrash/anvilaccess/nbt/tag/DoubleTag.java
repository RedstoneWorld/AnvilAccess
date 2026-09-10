package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

/**
 * Double tag
 */
public class DoubleTag extends Tag<Double> {
    private double value;

    public DoubleTag(double value) {
        super(TagType.DOUBLE);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public DoubleTag copy() {
        return new DoubleTag(value);
    }

    @Override
    public String toString() {
        return value + "d";
    }

    @Override
    public boolean equals(Tag<Double> other) {
        return other.getValue() == value;
    }
}
