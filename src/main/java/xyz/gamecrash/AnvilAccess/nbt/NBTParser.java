package xyz.gamecrash.AnvilAccess.nbt;

import xyz.gamecrash.AnvilAccess.nbt.tags.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NBTParser {

    public static NamedTag readNamed(DataInputStream input) throws IOException {
        byte typeId = input.readByte();
        if (typeId == 0) return new NamedTag("", null);

        TagType type = TagType.fromId(typeId);
        String name = readString(input);
        Tag tag = readTag(input, type);

        return new NamedTag(name, tag);
    }

    public static Tag readTag(DataInputStream input, TagType type) throws IOException {
        return switch (type) {
            case END -> null;
            case BYTE -> new ByteTag(input.readByte());
            case SHORT -> new ShortTag(input.readShort());
            case INT -> new IntTag(input.readInt());
            case LONG -> new LongTag(input.readLong());
            case FLOAT -> new FloatTag(input.readFloat());
            case DOUBLE -> new DoubleTag(input.readDouble());
            case BYTE_ARRAY -> readByteArray(input);
            case STRING -> new StringTag(readString(input));
            case LIST -> readList(input);
            case COMPOUND -> readCompound(input);
            case INT_ARRAY -> readIntArray(input);
            case LONG_ARRAY -> readLongArray(input);
        };
    }

    private static String readString(DataInputStream input) throws IOException {
        short length = input.readShort();
        if (length < 0) throw new IOException("Negative length: " + length);
        if (length == 0) return "";

        byte[] bytes = new byte[length];
        input.readFully(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static ByteArrayTag readByteArray(DataInputStream input) throws IOException {
        int length = input.readInt();
        if (length < 0) throw new IOException("Negative length: " + length);

        byte[] array = new byte[length];
        input.readFully(array);

        return new ByteArrayTag(array);
    }

    private static ListTag readList(DataInputStream input) throws IOException {
        byte typeId = input.readByte();
        int length = input.readInt();
        if (length < 0) throw new IOException("Negative length: " + length);

        TagType type = TagType.fromId(typeId);
        ListTag list = new ListTag(type);
        for (int i = 0; i < length; i++) {
            list.add(readTag(input, type));
        }

        return list;
    }

    private static CompoundTag readCompound(DataInputStream input) throws IOException {
        CompoundTag compound = new CompoundTag();

        while (true) {
            byte typeId = input.readByte();
            if (typeId == 0) break;

            TagType type = TagType.fromId(typeId);
            String name = readString(input);
            Tag tag = readTag(input, type);

            compound.put(name, tag);
        }

        return compound;
    }

    private static IntArrayTag readIntArray(DataInputStream input) throws IOException {
        int length = input.readInt();
        if (length < 0) throw new IOException("Negative length: " + length);

        int[] array = new int[length];
        for (int i = 0; i < length; i++) array[i] = input.readInt();

        return new IntArrayTag(array);
    }

    private static LongArrayTag readLongArray(DataInputStream input) throws IOException {
        int length = input.readInt();
        if (length < 0) throw new IOException("Negative length: " + length);

        long[] array = new long[length];
        for (int i = 0; i < length; i++) array[i] = input.readLong();

        return new LongArrayTag(array);
    }
}
