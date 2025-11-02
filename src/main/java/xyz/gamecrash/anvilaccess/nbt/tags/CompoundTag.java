package xyz.gamecrash.anvilaccess.nbt.tags;

import xyz.gamecrash.anvilaccess.nbt.*;

import java.util.*;

/**
 * Compound tag storing key-value-pairs of other tags
 */
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

    /**
     * Puts a tag with given name
     */
    public void put(String name, Tag tag) {
        tags.put(name, tag);
    }

    /**
     * Gets a tag by given name
     */
    public Tag get(String name) {
        return tags.get(name);
    }

    /**
     * Checks if a tag with given name exists
     */
    public boolean contains(String name) {
        return tags.containsKey(name);
    }

    /**
     * Checks if a tag with given name and type exists
     */
    public boolean contains(String name, TagType type) {
        Tag tag = tags.get(name);
        return tag != null && tag.getType() == type;
    }

    /**
     * Removes a tag with given name
     */
    public Tag remove(String name) {
        return tags.remove(name);
    }

    /**
     * Returns a set containing all tag names
     */
    public Set<String> getKeys() {
        return new HashSet<>(tags.keySet());
    }

    /**
     * Gets number of tags
     */
    public int size() {
        return tags.size();
    }

    /**
     * Checks if compound is empty
     */
    public boolean isEmpty() {
        return tags.isEmpty();
    }

    /**
     * Gets a byte value, with a fallback value
     */
    public byte getByte(String name, byte defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ByteTag byteTag) return byteTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a short value, with a fallback value
     */
    public short getShort(String name, short defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ShortTag shortTag) return shortTag.getValue();

        return defaultValue;
    }

    /**
     * Gets an integer value, with a fallback value
     */
    public int getInt(String name, int defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof IntTag intTag) return intTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a long value, with a fallback value
     */
    public long getLong(String name, long defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof LongTag longTag) return longTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a flot value, with a fallback value
     */
    public float getFloat(String name, float defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof FloatTag floatTag) return floatTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a double value, with a fallback value
     */
    public double getDouble(String name, double defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof DoubleTag doubleTag) return doubleTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a string value, with a fallback value
     */
    public String getString(String name, String defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof StringTag stringTag) return stringTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a byte array, with a fallback value
     */
    public byte[] getByteArray(String name, byte[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ByteArrayTag byteArrayTag) return byteArrayTag.getValue();

        return defaultValue;
    }

    /**
     * Gets an int array, with a fallback value
     */
    public int[] getIntArray(String name, int[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof IntArrayTag intArrayTag) return intArrayTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a long array, with a fallback value
     */
    public long[] getLongArray(String name, long[] defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof LongArrayTag longArrayTag) return longArrayTag.getValue();

        return defaultValue;
    }

    /**
     * Gets a NBT list tag, with a fallback value
     */
    public ListTag getList(String name, ListTag defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof ListTag listTag) return listTag;

        return defaultValue;
    }

    /**
     * Gets a NBT compound, with a fallback value
     */
    public CompoundTag getCompound(String name, CompoundTag defaultValue) {
        Tag tag = tags.get(name);
        if (tag instanceof CompoundTag compoundTag) return compoundTag;

        return defaultValue;
    }

    /**
     * Returns the key-value-pair of this NBT compound
     */
    @Override
    public Map<String, Tag> getValue() {
        return new HashMap<>(tags);
    }

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
