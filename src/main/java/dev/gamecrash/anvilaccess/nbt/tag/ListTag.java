package dev.gamecrash.anvilaccess.nbt.tag;

import lombok.Getter;
import dev.gamecrash.anvilaccess.nbt.TagType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List tag storing a list of tags of the same type
 */
public class ListTag extends Tag<List<Tag<?>>> {
    private List<Tag<?>> value;
    @Getter
    private final TagType elementType;

    public ListTag(TagType elementType) {
        super(TagType.LIST);
        this.elementType = elementType;
        this.value = new ArrayList<>();
    }

    public ListTag(TagType elementType, List<Tag<?>> value) {
        super(TagType.LIST);
        this.elementType = elementType;
        this.value = new ArrayList<>(value);

        for (Tag<?> tag : value) {
            if (tag.getType() != elementType)
                throw new IllegalArgumentException("All tags must be of type " + elementType);
        }
    }

    public void add(Tag<?> tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        value.add(tag);
    }

    public Tag<?> get(int index) {
        return value.get(index);
    }

    public CompoundTag getCompound(int index) {
        Tag<?> tag = get(index);
        if (tag instanceof CompoundTag compound) return compound;

        throw new ClassCastException("List tag type does not match CompoundTag");
    }

    public int getInt(int index) {
        Tag<?> tag = get(index);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        throw new ClassCastException("List tag type does not match IntTag");
    }

    public String getString(int index) {
        Tag<?> tag = get(index);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        throw new ClassCastException("List tag type does not match StringTag");
    }

    public void set(int index, Tag<?> tag) {
        if (tag.getType() != elementType) throw new IllegalArgumentException("All tags must be of type " + elementType);
        value.set(index, tag);
    }

    public Tag<?> remove(int index) {
        return value.remove(index);
    }

    public int size() {
        return value.size();
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public List<Tag<?>> getValue() {
        return value;
    }

    @Override
    public void setValue(List<Tag<?>> value) {
        if (value.getFirst().getType() != elementType) throw new IllegalArgumentException("Given ListTag does not");
        this.value = value;
    }

    @Override
    public ListTag copy() {
        List<Tag<?>> copiedTags = new ArrayList<>();
        for (Tag<?> tag : value) copiedTags.add(tag.copy());
        return new ListTag(elementType, copiedTags);
    }

    @Override
    public String toString() {
        if (value.isEmpty()) return "[]";

        StringBuilder builder = new StringBuilder("[");
        for (Tag<?> tag : value) builder.append(tag.toString()).append(",");
        builder.setCharAt(builder.length() - 1, ']');

        return builder.toString();
    }

    @Override
    public boolean equals(Tag<List<Tag<?>>> other) {
        return false;
    }
}
