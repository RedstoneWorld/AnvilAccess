package dev.gamecrash.anvilaccess.nbt.snbt.deserializer;

import dev.gamecrash.anvilaccess.nbt.TagType;
import dev.gamecrash.anvilaccess.nbt.snbt.Scalar;
import dev.gamecrash.anvilaccess.nbt.tag.*;

import java.util.ArrayList;
import java.util.List;

public class Deserializer {
    private Deserializer() {}

    public static Tag<?> parse(String str) {
        CursorReader reader = new CursorReader(str);
        Tag<?> tag = readVal(reader);
        reader.expectEnd();
        return tag;
    }

    private static Tag<?> readVal(CursorReader r) {
        return switch (r.peek()) {
            case '{' -> readCompound(r);
            case '[' -> readList(r);
            case '"', '\'' -> new StringTag(r.readQuoted());
            default -> Scalar.parse(r.readUnquoted());
        };
    }

    private static CompoundTag readCompound(CursorReader r) {
        r.accept('{');
        CompoundTag c = new CompoundTag();
        while (!r.accept('}')) {
            String key = r.readKey();
            r.expect(':');
            c.put(key, readVal(r));

            if (!r.accept(',')) {
                r.expect('}');
                break;
            }
        }

        return c;
    }

    private static Tag<?> readList(CursorReader r) {
        r.accept('[');
        char kind = r.readArrayPrefix();
        List<Tag<?>> elements = new ArrayList<>();
        while (!r.accept(']')) {
            elements.add(readVal(r));
            if (!r.accept(',')) {
                r.expect(']');
                break;
            }
        }

        if (kind != 0) return toArray(kind, elements);

        ListTag tag = new ListTag(elements.isEmpty() ? TagType.END : elements.getFirst().getType());
        tag.addAll(elements);
        return tag;
    }

    private static Tag<?> toArray(char kind, List<Tag<?>> elements) {
        int n = elements.size();
        switch (kind) {
            case 'B': {
                byte[] array = new byte[n];
                for (int i = 0; i < n; i++) array[i] = ((Number) elements.get(i).getValue()).byteValue();
                return new ByteArrayTag(array);
            }
            case 'I': {
                int[] array = new int[n];
                for (int i = 0; i < n; i++) array[i] = ((Number) elements.get(i).getValue()).intValue();
                return new IntArrayTag(array);
            }
            default: {
                long[] array = new long[n];
                for (int i = 0; i < n; i++) array[i] = ((Number) elements.get(i).getValue()).longValue();
                return new LongArrayTag(array);
            }
        }
    }
}
