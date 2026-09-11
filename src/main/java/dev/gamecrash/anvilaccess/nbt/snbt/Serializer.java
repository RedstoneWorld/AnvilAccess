package dev.gamecrash.anvilaccess.nbt.snbt;

import dev.gamecrash.anvilaccess.nbt.tag.Tag;

public interface Serializer {
    String serialize(Tag<?> tag);
}
