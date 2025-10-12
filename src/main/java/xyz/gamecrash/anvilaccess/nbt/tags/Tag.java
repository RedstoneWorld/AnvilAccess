package xyz.gamecrash.anvilaccess.nbt.tags;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.nbt.TagType;

/**
 * Base class for all NBT tags
 */
public abstract class Tag {
    @Getter protected final TagType type;

    protected Tag(TagType type) { this.type = type; }

    /**
     * Gets the ID of the tag type
     */
    public int getTypeId() { return type.getId(); }

    /**
     * Gets the value of the tag. Type depends on the specific tag implementation
     */
    public abstract Object getValue();

    /**
     * Creates a deep copy of the tag
     */
    public abstract Tag copy();

    @Override
    public abstract String toString();
}
