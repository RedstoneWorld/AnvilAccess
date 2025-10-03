package xyz.gamecrash.AnvilAccess.nbt;

import java.util.*;

public class CompoundTag extends Tag {
    private final Map<String, Tag> tags;

    public CompoundTag() {
        super(TagType.COMPOUND);
        this.tags = new LinkedHashMap<>();
    }

    public CompoundTag(Map<String, Tag> tags) {
        super(TagType.COMPOUND);
        this.tags = new LinkedHashMap<>(tags);
    }

    public void put(String name, Tag tag) { tags.put(name, tag); }

    public Tag get(String name) { return tags.get(name); }

    public boolean contains(String name, TagType type) {
        Tag tag = tags.get(name);
        return tag != null && tag.getType() == type;
    }

    public Tag remove(String name) { return tags.remove(name); }

    public Set<String> getKeys() { return new HashSet<>(tags.keySet()); }

    public int size() { return tags.size(); }

    public boolean isEmpty() { return tags.isEmpty(); }

    public byte getByte(String name, byte defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ByteTag byteTag) return byteTag.getValue();

        return defaultValue;
    }

    public short getShort(String name, short defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ShortTag shortTag) return shortTag.getValue();

        return defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        return defaultValue;
    }

    public long getLong(String name, long defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof LongTag longTag) return longTag.getValue();

        return defaultValue;
    }

    public float getFloat(String name, float defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof FloatTag floatTag) return floatTag.getValue();

        return defaultValue;
    }

    public double getDouble(String name, double defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof DoubleTag doubleTag) return doubleTag.getValue();

        return defaultValue;
    }

    public String getString(String name, String defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        return defaultValue;
    }

    public byte[] getByteArray(String name, byte[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ByteArrayTag byteArrayTag) return byteArrayTag.getValue();

        return defaultValue;
    }

    public int[] getIntArray(String name, int[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof IntArrayTag intArrayTag) return intArrayTag.getValue();

        return defaultValue;
    }

    public long[] getLongArray(String name, long[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof LongArrayTag longArrayTag) return longArrayTag.getValue();

        return defaultValue;
    }

    public ListTag getList(String name, ListTag defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ListTag listTag) return listTag;

        return defaultValue;
    }

    public CompoundTag getCompound(String name, CompoundTag defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof CompoundTag compoundTag) return compoundTag;

        return defaultValue;
    }

    @Override
    public Map<String, Tag> getValue() { return new HashMap<>(tags); }

    @Override
    public CompoundTag copy() {
        Map<String, Tag> copiedTags = new LinkedHashMap<>();
        for (Map.Entry<String, Tag> entry : tags.entrySet()) {
            copiedTags.put(entry.getKey(), entry.getValue().copy());
        }
        return new CompoundTag(copiedTags);
    }

    @Override
    public String toString() {
        if (tags.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, Tag> entry : tags.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        sb.setCharAt(sb.length() - 1, '}');

        return sb.toString();
    }
}
