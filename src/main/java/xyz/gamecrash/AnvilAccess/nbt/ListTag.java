package xyz.gamecrash.AnvilAccess.nbt;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void add(Tag tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        tags.add(tag);
    }

    public Tag get(int index) { return tags.get(index); }

    public CompoundTag getCompound(int index) {
        Tag tag = get(index);
        if (tag instanceof CompoundTag compound) return compound;

        throw new ClassCastException("List tag type does not match CompoundTag");
    }

    public int getInt(int index) {
        Tag tag = get(index);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        throw new ClassCastException("List tag type does not match IntTag");
    }

    public String getString(int index) {
        Tag tag = get(index);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        throw new ClassCastException("List tag type does not match StringTag");
    }

    public void set(int index, Tag tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        tags.set(index, tag);
    }

    public Tag remove(int index) { return tags.remove(index); }

    public int size() { return tags.size(); }

    public boolean isEmpty() { return tags.isEmpty(); }

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
