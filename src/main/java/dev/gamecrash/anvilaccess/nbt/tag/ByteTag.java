package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

/**
 * Byte tag
 */
public class ByteTag extends Tag<Byte> {
    private byte value;

    public ByteTag(byte value) {
        super(TagType.BYTE);
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public void setValue(Byte value) {
        this.value = value;
    }

    @Override
    public ByteTag copy() {
        return new ByteTag(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value) + "b";
    }

    @Override
    public boolean equals(Tag<Byte> other) {
        return this.value == other.getValue();
    }
}
