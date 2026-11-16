package dev.gamecrash.anvilaccess.nbt.snbt.serializer;

import dev.gamecrash.anvilaccess.nbt.snbt.SNBT;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

public class PrimitiveSerializer implements Serializer {
    @Override
    public String serialize(Tag<?> tag, SNBT unused) {
        return tag.toString();
    }
}
