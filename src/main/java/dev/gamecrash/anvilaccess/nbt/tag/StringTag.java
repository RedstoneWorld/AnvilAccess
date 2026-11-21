package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.ComplexSerializer;
import dev.gamecrash.anvilaccess.nbt.snbt.serializer.Serializable;

import java.util.Objects;

/**
 * String tag
 */
@Serializable(serializer = ComplexSerializer.class)
public class StringTag extends Tag<String> {
    private String value;

    public StringTag(String value) {
        super(TagType.STRING);
        this.value = value != null ? value : "";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value != null ? value : "";
    }

    @Override
    public StringTag copy() {
        return new StringTag(value);
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StringTag other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
