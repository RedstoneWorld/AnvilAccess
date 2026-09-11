package dev.gamecrash.anvilaccess.nbt;


import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enum representing all existing NBT tag types
 * <p>
 * A complete table with descriptions of these tags can be found <a href="https://minecraft.wiki/w/NBT_format#TAG_definition">here</a>,
 * a table with the data types <a href="https://minecraft.wiki/w/NBT_format#Data_types">here</a>.
 */
public enum TagType {

    // https://minecraft.wiki/w/NBT_format#TAG_definition
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    @Getter
    private final int id;

    private static final TagType[] BY_ID = values();

    TagType(int id) {
        this.id = id;
    }

    public static TagType fromId(int id) {
        if (id >= 0 && id < BY_ID.length) return BY_ID[id];
        throw new IllegalArgumentException("Unknown tag type id: " + id);
    }
}