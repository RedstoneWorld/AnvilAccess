package dev.gamecrash.anvilaccess.nbt.tag;

import dev.gamecrash.anvilaccess.nbt.TagType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Objects;

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
        return Collections.unmodifiableSet(value.keySet());
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
        this.value = new LinkedHashMap<>(Objects.requireNonNull(value, "value"));
    }

    @Override
    public CompoundTag copy() {
        Map<String, Tag<?>> copiedTags = new LinkedHashMap<>(hashMapCapacity(value.size()));
        for (Map.Entry<String, Tag<?>> entry : value.entrySet()) {
            copiedTags.put(entry.getKey(), entry.getValue().copy());
        }

        CompoundTag copy = new CompoundTag();
        copy.value = copiedTags;
        return copy;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CompoundTag other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private static int hashMapCapacity(int expectedSize) {
        // 0.75 is default HashMap load / capacity factor, if map is tiny, then we'll use the size + 1; this generally avoids immediate resizing
        if (expectedSize < 3) return expectedSize + 1;
        return (int) (expectedSize / 0.75f) + 1;
    }
}
