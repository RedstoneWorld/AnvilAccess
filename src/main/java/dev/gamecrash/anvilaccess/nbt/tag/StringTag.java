package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

/**
 * String tag
 */
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
        this.value = value;
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
    public boolean equals(Tag<String> other) {
        return other.getValue().equals(value);
    }
}
