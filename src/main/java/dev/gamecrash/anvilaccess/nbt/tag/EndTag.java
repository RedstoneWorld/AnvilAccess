package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

public class EndTag extends Tag<Void> {
    public static EndTag val = new EndTag();

    public EndTag() {
        super(TagType.END);
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void setValue(Void value) {}

    @Override
    public Tag<Void> copy() {
        return this;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EndTag;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
