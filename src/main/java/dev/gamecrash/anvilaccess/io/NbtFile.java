package dev.gamecrash.anvilaccess.io;

import dev.gamecrash.anvilaccess.nbt.TagParser;
import dev.gamecrash.anvilaccess.nbt.TagWriter;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.NamedTag;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;
import dev.gamecrash.anvilaccess.util.compression.CompressionType;
import dev.gamecrash.anvilaccess.util.compression.CompressionUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NbtFile {
    private NbtFile() {}

    public static NamedTag read(Path path) throws IOException {
        byte[] data = Files.readAllBytes(path);
        boolean gzip = data.length > 2 && (data[0] & 0xFF) == 0x1F && (data[1] & 0xFF) == 0x8B;
        if (gzip) data = CompressionUtils.decompress(data, 0, data.length, CompressionType.GZIP);

        return TagParser.readNamed(new DataInputStream(new ByteArrayInputStream(data)));
    }

    public static CompoundTag readCompound(Path path) throws IOException {
        return (CompoundTag) read(path).tag();
    }

    public static void write(Path path, CompoundTag root) throws IOException {
        write(path, "", root, true);
    }

    public static void write(Path path, String rootName, Tag<?> root, boolean gzip) throws IOException {
        ByteArrayOutputStream raw = new ByteArrayOutputStream();
        TagWriter.writeNamed(new DataOutputStream(raw), rootName, root);
        byte[] data = gzip ? CompressionUtils.compress(raw.toByteArray(), CompressionType.GZIP) : raw.toByteArray();

        Files.write(path, data);
    }
}
