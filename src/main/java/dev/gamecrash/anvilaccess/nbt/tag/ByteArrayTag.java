package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

import java.util.Arrays;

/**
 * Byte array tag
 */
public class ByteArrayTag extends Tag<byte[]> {
    private byte[] value;

    public ByteArrayTag(byte[] value) {
        super(TagType.BYTE_ARRAY);
        this.value = value;
    }

    public ByteArrayTag() {
        super(TagType.BYTE_ARRAY);
        value = new byte[0];
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public Tag<byte[]> copy() {
        return new ByteArrayTag(value);
    }

    @Override
    public String toString() {
        return "[B;" + value.length + " bytes]";
    }

    @Override
    public boolean equals(Tag<byte[]> other) {
        if (this == other) return true;
        if (!(other instanceof ByteArrayTag tag)) return false;
        return Arrays.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
