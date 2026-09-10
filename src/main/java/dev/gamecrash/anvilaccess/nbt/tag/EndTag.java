package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

public class EndTag extends Tag<Void> {
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
        return new EndTag();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Tag<Void> other) {
        return false;
    }
}
