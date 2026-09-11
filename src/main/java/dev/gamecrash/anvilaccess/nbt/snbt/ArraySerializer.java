package dev.gamecrash.anvilaccess.nbt.snbt;

import dev.gamecrash.anvilaccess.nbt.tag.ByteArrayTag;
import dev.gamecrash.anvilaccess.nbt.tag.IntArrayTag;
import dev.gamecrash.anvilaccess.nbt.tag.LongArrayTag;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

public class ArraySerializer implements Serializer {
    static final String START_TAG = "[";
    static final String END_TAG = "]";

    @Override
    public String serialize(Tag<?> tag) {
        assert tag instanceof ByteArrayTag
            || tag instanceof IntArrayTag
            || tag instanceof LongArrayTag;

        return switch (tag.getType()) {
            case BYTE_ARRAY -> serializeByteArray((ByteArrayTag) tag);
            case INT_ARRAY -> serializeIntArray((IntArrayTag) tag);
            case LONG_ARRAY -> serializeLongArray((LongArrayTag) tag);
            default -> throw new IllegalStateException("Unexpected value for array snbt serialization: " + tag.getType());
        };
    }

    private String serializeByteArray(ByteArrayTag tag) {
        StringBuilder builder = new StringBuilder(START_TAG);
        builder.append("B;");
        byte[] val = tag.getValue();
        for (int i = 0; i < val.length; i++) {
            builder.append(val[i]).append("b");
            if (i + 1 < val.length) builder.append(",");
        }

        return builder.append(END_TAG).toString();
    }

    private String serializeIntArray(IntArrayTag tag) {
        StringBuilder builder = new StringBuilder(START_TAG);
        builder.append("I;");
        int[] val = tag.getValue();
        for (int i = 0; i < val.length; i++) {
            builder.append(val[i]);
            if (i + 1 < val.length) builder.append(",");
        }

        return builder.append(END_TAG).toString();
    }

    private String serializeLongArray(LongArrayTag tag) {
        StringBuilder builder = new StringBuilder(START_TAG);
        builder.append("L;");
        long[] val = tag.getValue();
        for (int i = 0; i < val.length; i++) {
            builder.append(val[i]).append("l");
            if (i + 1 < val.length) builder.append(",");
        }

        return builder.append(END_TAG).toString();
    }
}
