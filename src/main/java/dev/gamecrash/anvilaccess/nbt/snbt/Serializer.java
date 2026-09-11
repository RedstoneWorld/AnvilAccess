package dev.gamecrash.anvilaccess.nbt.snbt;

import dev.gamecrash.anvilaccess.nbt.tag.Tag;

public interface Serializer {
    default String serialize(Tag<?> tag) {
        return tag.toString();
    }
}
