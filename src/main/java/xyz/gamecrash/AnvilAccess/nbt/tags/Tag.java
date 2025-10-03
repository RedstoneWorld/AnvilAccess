package xyz.gamecrash.AnvilAccess.nbt.tags;

import lombok.Getter;
import xyz.gamecrash.AnvilAccess.nbt.TagType;

public abstract class Tag {
    @Getter protected final TagType type;

    protected Tag(TagType type) { this.type = type; }

    public int getTypeId() { return type.getId(); }

    public abstract Object getValue();

    public abstract Tag copy();

    @Override
    public abstract String toString();
}
