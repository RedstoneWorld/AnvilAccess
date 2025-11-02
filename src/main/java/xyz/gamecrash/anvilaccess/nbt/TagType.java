package xyz.gamecrash.anvilaccess.nbt;

import lombok.Getter;

/**
 * Enum representing all existing NBT tag types
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

    TagType(int id) {
        this.id = id;
    }

    public static TagType fromId(int id) {
        for (TagType type : values()) {
            if (type.id == id) return type;
        }
        throw new IllegalArgumentException("Unknown tag type id: " + id);
    }
}
