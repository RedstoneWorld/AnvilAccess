package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Compound tag storing key-value-pairs of other tags
 */
public class CompoundTag extends Tag<Map<String, Tag<?>>> {
    private Map<String, Tag<?>> value;

    public CompoundTag() {
        super(TagType.COMPOUND);
        this.value = new LinkedHashMap<>();
    }

    public CompoundTag(Map<String, Tag<?>> value) {
        super(TagType.COMPOUND);
        this.value = new LinkedHashMap<>(value);
    }

    public void put(String name, Tag<?> tag) {
        value.put(name, tag);
    }

    public Tag<?> get(String name) {
        return value.get(name);
    }

    public boolean contains(String name) {
        return value.containsKey(name);
    }

    public boolean contains(String name, TagType type) {
        Tag<?> tag = value.get(name);
        return tag != null && tag.getType() == type;
    }

    public Tag<?> remove(String name) {
        return value.remove(name);
    }

    public Set<String> getKeys() {
        return Set.copyOf(value.keySet());
    }

    public int size() {
        return value.size();
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public byte getByte(String name, byte defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof ByteTag byteTag) return byteTag.getValue();

        return defaultValue;
    }

    public short getShort(String name, short defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof ShortTag shortTag) return shortTag.getValue();

        return defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        return defaultValue;
    }

    public long getLong(String name, long defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof LongTag longTag) return longTag.getValue();

        return defaultValue;
    }

    public float getFloat(String name, float defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof FloatTag floatTag) return floatTag.getValue();

        return defaultValue;
    }

    public double getDouble(String name, double defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof DoubleTag doubleTag) return doubleTag.getValue();

        return defaultValue;
    }

    public String getString(String name, String defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        return defaultValue;
    }

    public byte[] getByteArray(String name, byte[] defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof ByteArrayTag byteArrayTag) return byteArrayTag.getValue();

        return defaultValue;
    }

    public int[] getIntArray(String name, int[] defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof IntArrayTag intArrayTag) return intArrayTag.getValue();

        return defaultValue;
    }

    public long[] getLongArray(String name, long[] defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof LongArrayTag longArrayTag) return longArrayTag.getValue();

        return defaultValue;
    }

    public ListTag getList(String name, ListTag defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof ListTag listTag) return listTag;

        return defaultValue;
    }

    public CompoundTag getCompound(String name, CompoundTag defaultValue) {
        Tag<?> tag = value.get(name);
        if (tag instanceof CompoundTag compoundTag) return compoundTag;

        return defaultValue;
    }

    @Override
    public Map<String, Tag<?>> getValue() {
        return value;
    }

    @Override
    public void setValue(Map<String, Tag<?>> value) {
        this.value = value;
    }

    @Override
    public CompoundTag copy() {
        return new CompoundTag(new LinkedHashMap<>(value));
    }

    @Override
    public String toString() {
        if (value.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, Tag<?>> entry : value.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        sb.setCharAt(sb.length() - 1, '}');

        return sb.toString();
    }

    @Override
    public boolean equals(Tag<Map<String, Tag<?>>> other) {
        if (this == other) return true;
        if (!(other instanceof CompoundTag)) return false;
        return value.equals(other.getValue());
    }
}
