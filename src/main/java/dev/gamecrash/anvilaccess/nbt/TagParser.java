package dev.gamecrash.anvilaccess.nbt;

import dev.gamecrash.anvilaccess.nbt.tag.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TagParser {
    public static Tag<?> readTag(DataInputStream in, TagType type) throws IOException {
        return readTag(in, type, 0);
    }

    private static Tag<?> readTag(DataInputStream in, TagType type, int depth) throws IOException {
        if (depth > Tag.MAX_DEPTH) {
            throw new IOException("NBT structure exceeds max depth of " + Tag.MAX_DEPTH);
        }

        return switch (type) {
            case END -> EndTag.val;
            case BYTE -> new ByteTag(in.readByte());
            case SHORT -> new ShortTag(in.readShort());
            case INT -> new IntTag(in.readInt());
            case LONG -> new LongTag(in.readLong());
            case FLOAT -> new FloatTag(in.readFloat());
            case DOUBLE -> new DoubleTag(in.readDouble());
            case BYTE_ARRAY -> readByteArray(in);
            case STRING -> new StringTag(readString(in));
            case LIST -> readList(in, depth);
            case COMPOUND -> readCompound(in, depth);
            case INT_ARRAY -> readIntArray(in);
            case LONG_ARRAY -> readLongArray(in);
        };
    }

    public static NamedTag readNamed(DataInputStream in) throws IOException {
        byte typeId = in.readByte();
        if (typeId == 0) return new NamedTag("", null);

        TagType type = TagType.fromId(typeId);
        String name = readString(in);
        Tag<?> tag = readTag(in, type);

        return new NamedTag(name, tag);
    }

    private static ByteArrayTag readByteArray(DataInputStream in) throws IOException {
        int len = in.readInt();
        if (len < 0) throw new IOException("negative length: " + len);

        byte[] array = new byte[len];
        in.readFully(array);

        return new ByteArrayTag(array);
    }

    private static String readString(DataInputStream in) throws IOException {
        int len = in.readUnsignedShort();
        if (len == 0) return "";

        byte[] bytes = new byte[len];
        in.readFully(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static ListTag readList(DataInputStream in, int depth) throws IOException {
        byte typeId = in.readByte();
        int len = in.readInt();
        if (len < 0) throw new IOException("Negative length: " + len);

        TagType type = TagType.fromId(typeId);
        if (type == TagType.END && len > 0) {
            throw new IOException("List with END element type must be empty");
        }

        ListTag list = new ListTag(type);
        for (int i = 0; i < len; i++) {
            list.add(readTag(in, type, depth + 1));
        }

        return list;
    }

    private static CompoundTag readCompound(DataInputStream in, int depth) throws IOException {
        CompoundTag compound = new CompoundTag();
        while (true) {
            byte typeId = in.readByte();
            if (typeId == TagType.END.getId()) break;

            TagType type = TagType.fromId(typeId);
            compound.put(readString(in), readTag(in, type, depth + 1));
        }

        return compound;
    }

    private static IntArrayTag readIntArray(DataInputStream in) throws IOException {
        int len = in.readInt();
        if (len < 0) throw new IOException("Negative length: " + len);

        int[] array = new int[len];
        for (int i = 0; i < len; i++) array[i] = in.readInt();

        return new IntArrayTag(array);
    }

    private static LongArrayTag readLongArray(DataInputStream in) throws IOException {
        int len = in.readInt();
        if (len < 0) throw new IOException("Negative length: " + len);

        long[] array = new long[len];
        for (int i = 0; i < len; i++) array[i] = in.readLong();

        return new LongArrayTag(array);
    }
}
