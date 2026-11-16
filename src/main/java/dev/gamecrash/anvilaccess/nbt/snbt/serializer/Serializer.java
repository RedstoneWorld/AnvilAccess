package dev.gamecrash.anvilaccess.nbt.snbt.serializer;

import dev.gamecrash.anvilaccess.nbt.snbt.SNBT;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

@FunctionalInterface
public interface Serializer {
    String serialize(Tag<?> tag, SNBT instance);
}
