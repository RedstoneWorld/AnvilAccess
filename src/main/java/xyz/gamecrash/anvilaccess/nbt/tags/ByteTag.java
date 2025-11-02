package xyz.gamecrash.anvilaccess.nbt.tags;

import xyz.gamecrash.anvilaccess.nbt.TagType;

/**
 * Byte tag
 */
public class ByteTag extends Tag {
    private final byte value;

    public ByteTag(byte value) {
        super(TagType.BYTE);
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public ByteTag copy() {
        return new ByteTag(value);
    }

    @Override
    public String toString() {
        return value + "b";
    }
}
