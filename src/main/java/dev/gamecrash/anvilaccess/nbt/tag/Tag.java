package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;
import lombok.Getter;

public abstract class Tag<T> {
    public static final int MAX_DEPTH = 512;

    @Getter
    protected final TagType type;

    protected Tag(TagType type) {
        this.type = type;
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    public int typeId() {
        return type.getId();
    }

    public abstract Tag<T> copy();

    public abstract String toString();

    public abstract boolean equals(Tag<T> other);
}
