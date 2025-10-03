package xyz.gamecrash.AnvilAccess.nbt.tags;

import xyz.gamecrash.AnvilAccess.nbt.TagType;

import java.util.Arrays;

public class ByteArrayTag extends Tag {
    private final byte[] value;

    public ByteArrayTag(byte[] value) {
        super(TagType.BYTE_ARRAY);
        this.value = value != null ? value.clone() : new byte[0];
    }

    @Override
    public byte[] getValue() { return value.clone(); }

    @Override
    public ByteArrayTag copy() { return new ByteArrayTag(value); }

    @Override
    public String toString() { return "[B;" + value.length + " bytes]" ; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByteArrayTag tag)) return false;
        return Arrays.equals(value, tag.value);
    }

    @Override
    public int hashCode() { return Arrays.hashCode(value); }
}
