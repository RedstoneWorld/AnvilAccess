package dev.gamecrash.anvilaccess.nbt.snbt.serializer;

import dev.gamecrash.anvilaccess.nbt.snbt.SNBT;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

import java.util.Map;

public class ComplexSerializer implements Serializer {
    static final String COMPOUND_START = "{";
    static final String COMPOUND_END = "}";
    static final String LIST_START = "[";
    static final String LIST_END = "]";
    static final String SEPARATOR = ",";
    static final String NAME_VALUE_DELIMITER = ":";
    static final String KEY_DELIMITER = "\"";

    @Override
    public String serialize(Tag<?> tag, SNBT instance) {
        assert tag instanceof CompoundTag
            || tag instanceof ListTag;

        return switch (tag.getType()) {
            case COMPOUND -> serializeCompound((CompoundTag) tag, instance);
            case LIST -> serializeList((ListTag) tag, instance);
            default -> throw new IllegalStateException("Unexpected value for compound/list snbt serialization: " + tag.getType());
        };
    }

    private String serializeCompound(CompoundTag tag, SNBT instance) {
        if (tag.isEmpty()) return COMPOUND_START + COMPOUND_END;

        StringBuilder sb = new StringBuilder(COMPOUND_START);
        for (Map.Entry<String, Tag<?>> entry : tag.getValue().entrySet()) {
            sb.append(KEY_DELIMITER)
                .append(entry.getKey())
                .append(KEY_DELIMITER)
                .append(NAME_VALUE_DELIMITER)
                .append(
                    instance.serialize(entry.getValue())
                )
                .append(SEPARATOR);
        }
        sb.setCharAt(sb.length() - 1, COMPOUND_END.charAt(0));

        return sb.toString();
    }

    private String serializeList(ListTag tag, SNBT instance) {
        if (tag.isEmpty()) return LIST_START + LIST_END;

        StringBuilder builder = new StringBuilder(LIST_START);
        for (Tag<?> t : tag.getValue()) {
            builder.append(instance.serialize(t))
                .append(SEPARATOR);
        }
        builder.setCharAt(builder.length() - 1, LIST_END.charAt(0));

        return builder.toString();
    }
}
