package xyz.gamecrash.AnvilAccess.nbt.tags;

import xyz.gamecrash.AnvilAccess.nbt.TagType;

/**
 * String tag
 */
public class StringTag extends Tag {
    private final String value;

    public StringTag(String value) {
        super(TagType.STRING);
        this.value = value != null ? value : "";
    }

    @Override
    public String getValue() { return value; }

    @Override
    public StringTag copy() { return new StringTag(value); }

    @Override
    public String toString() { return "\"" + value + "\""; }
}
