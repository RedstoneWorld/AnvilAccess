package xyz.gamecrash.anvilaccess.nbt.tags;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.nbt.TagType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List tag storing a list of tags of the same type
 */
public class ListTag extends Tag {
    private final List<Tag> tags;
    @Getter private final TagType elementType;

    public ListTag(TagType elementType) {
        super(TagType.LIST);
        this.elementType = elementType;
        this.tags = new ArrayList<>();
    }

    public ListTag(TagType elementType, List<Tag> tags) {
        super(TagType.LIST);
        this.elementType = elementType;
        this.tags = new ArrayList<>(tags);

        for (Tag tag : tags) {
            if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        }
    }

    /**
     * Adds given tag to the list
     */
    public void add(Tag tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        tags.add(tag);
    }

    /**
     * Gets a tag at given index
     */
    public Tag get(int index) { return tags.get(index); }

    /**
     * Gets a compound tag at given index
     */
    public CompoundTag getCompound(int index) {
        Tag tag = get(index);
        if (tag instanceof CompoundTag compound) return compound;

        throw new ClassCastException("List tag type does not match CompoundTag");
    }

    /**
     * Gets an int tag at given index
     */
    public int getInt(int index) {
        Tag tag = get(index);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        throw new ClassCastException("List tag type does not match IntTag");
    }

    /**
     * Gets a string tag at given index
     */
    public String getString(int index) {
        Tag tag = get(index);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        throw new ClassCastException("List tag type does not match StringTag");
    }

    /**
     * Set a tag at specified index
     */
    public void set(int index, Tag tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        tags.set(index, tag);
    }

    /**
     * Remove a list entry at given index
     */
    public Tag remove(int index) { return tags.remove(index); }

    /**
     * Gets the size of the list
     */
    public int size() { return tags.size(); }

    /**
     * Checks if the list is empty
     */
    public boolean isEmpty() { return tags.isEmpty(); }

    /**
     * Returns an iterator over the elements in this list
     */
    public Iterator<Tag> iterator() { return tags.iterator(); }

    @Override
    public List<Tag> getValue() { return new ArrayList<>(tags); }

    @Override
    public ListTag copy() {
        List<Tag> copiedTags = new ArrayList<>();
        for (Tag tag : tags) copiedTags.add(tag.copy());
        return new ListTag(elementType, copiedTags);
    }

    @Override
    public String toString() {
        if (tags.isEmpty()) return "[]";

        StringBuilder builder = new StringBuilder("[");
        for (Tag tag : tags) builder.append(tag.toString()).append(",");
        builder.setCharAt(builder.length() - 1, ']');

        return builder.toString();
    }
}
