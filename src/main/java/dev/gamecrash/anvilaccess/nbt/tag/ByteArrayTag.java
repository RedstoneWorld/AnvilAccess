package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.ArraySerializer;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

import java.util.Arrays;

/**
 * Byte array tag
 */
@Serializable(serializer = ArraySerializer.class)
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
        return new ByteArrayTag(value.clone());
    }

    @Override
    public String toString() {
        return "[B;" + value.length + " bytes]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ByteArrayTag other)) return false;
        return Arrays.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
