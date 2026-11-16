package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

/**
 * Byte tag
 */
@Serializable
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

    public boolean asBoolean() {
        return value == 1;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ByteTag other)) return false;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }
}
