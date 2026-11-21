package dev.gamecrash.anvilaccess.nbt.snbt.serializer;

import dev.gamecrash.anvilaccess.nbt.snbt.SNBT;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;
import dev.gamecrash.anvilaccess.nbt.tag.StringTag;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

import java.util.Map;

public class ComplexSerializer implements Serializer {
    static final String COMPOUND_START = "{";
    static final String COMPOUND_END = "}";
    static final String LIST_START = "[";
    static final String LIST_END = "]";
    static final String SEPARATOR = ",";
    static final String NAME_VALUE_DELIMITER = ":";
    static final char SINGLE_QUOTE = '\'';
    static final char DOUBLE_QUOTE = '"';
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    @Override
    public String serialize(Tag<?> tag, SNBT instance) {
        assert tag instanceof CompoundTag
            || tag instanceof ListTag
            || tag instanceof StringTag;

        return switch (tag.getType()) {
            case STRING -> serializeString(((StringTag) tag).getValue());
            case COMPOUND -> serializeCompound((CompoundTag) tag, instance);
            case LIST -> serializeList((ListTag) tag, instance);
            default -> throw new IllegalStateException("Unexpected value for compound/list snbt serialization: " + tag.getType());
        };
    }

    private String serializeString(String value) {
        if (value == null) throw new IllegalArgumentException("Cannot serialize a null string");

        char quote = value.indexOf('\'') < 0 && value.indexOf('"') >= 0 ? SINGLE_QUOTE : DOUBLE_QUOTE;
        boolean escapeDouble = quote == DOUBLE_QUOTE;
        boolean escapeSingle = quote == SINGLE_QUOTE;

        StringBuilder sb = new StringBuilder(value.length() + 2).append(quote);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"' -> sb.append(escapeDouble ? "\\\"" : DOUBLE_QUOTE);
                case '\'' -> sb.append(escapeSingle ? "\\'" : SINGLE_QUOTE);
                case '\\' -> sb.append("\\\\");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> sb.append(c);
            }
        }
        return sb.append(quote).toString();
    }

    private String serializeCompound(CompoundTag tag, SNBT instance) {
        if (tag.isEmpty()) return COMPOUND_START + COMPOUND_END;

        StringBuilder sb = new StringBuilder(COMPOUND_START);
        for (Map.Entry<String, Tag<?>> entry : tag.getValue().entrySet()) {
            sb.append(serializeString(entry.getKey()))
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
