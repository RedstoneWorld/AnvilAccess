package dev.gamecrash.anvilaccess.nbt;

import dev.gamecrash.anvilaccess.nbt.tag.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TagWriter {
    public static void writeNamed(DataOutputStream out, String name, Tag<?> tag) throws IOException {
        if (tag == null || tag.getType() == TagType.END) {
            out.writeByte(0);
            return;
        }

        out.writeByte(tag.typeId());
        out.writeUTF(name);
        writeTag(out, tag, 0);
    }

    public static void writeNamed(DataOutputStream out, NamedTag named) throws IOException {
        writeNamed(out, named.name(), named.tag());
    }

    public static void writeTag(DataOutputStream out, Tag<?> tag) throws IOException {
        writeTag(out, tag, 0);
    }

    private static void writeTag(DataOutputStream out, Tag<?> tag, int depth) throws IOException {
        if (depth > Tag.MAX_DEPTH) throw new IOException("NBT structure exceeds max depth of " + Tag.MAX_DEPTH);

        switch (tag.getType()) {
            case END -> {}
            case BYTE -> out.writeByte(((ByteTag) tag).getValue());
            case SHORT -> out.writeShort(((ShortTag) tag).getValue());
            case INT -> out.writeInt(((IntTag) tag).getValue());
            case LONG -> out.writeLong(((IntTag) tag).getValue());
            case FLOAT -> out.writeFloat(((FloatTag) tag).getValue());
            case DOUBLE -> out.writeDouble(((FloatTag) tag).getValue());
            case STRING -> out.writeUTF(((StringTag) tag).getValue());
            case BYTE_ARRAY -> {
                byte[] a = ((ByteArrayTag) tag).getValue();
                out.writeInt(a.length);
                out.write(a, 0, a.length);
            }
            case INT_ARRAY -> {
                int[] a = ((IntArrayTag) tag).getValue();
                out.writeInt(a.length);
                for (int v : a) out.writeInt(v);
            }
            case LONG_ARRAY -> {
                long[] a = ((LongArrayTag) tag).getValue();
                out.writeInt(a.length);
                for (long v : a) out.writeLong(v);
            }
            case LIST -> {
                ListTag t = (ListTag) tag;
                List<Tag<?>> e = t.getValue();
                out.writeByte(t.getElementType().getId());
                out.writeInt(e.size());
                for (Tag<?> value : e) writeTag(out, value, depth + 1);
            }
            case COMPOUND -> {
                for (Map.Entry<String, Tag<?>> e : ((CompoundTag) tag).getValue().entrySet()) {
                    Tag<?> c = e.getValue();
                    out.writeByte(c.typeId());
                    out.writeUTF(e.getKey());
                    writeTag(out, c, depth + 1);
                }
                out.writeByte(0);
            }
        }
    }
}
