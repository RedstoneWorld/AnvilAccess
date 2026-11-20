package dev.gamecrash.anvilaccess.nbt;

import dev.gamecrash.anvilaccess.model.BlockState;
import dev.gamecrash.anvilaccess.model.Chunk;
import dev.gamecrash.anvilaccess.model.Section;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;
import dev.gamecrash.anvilaccess.nbt.tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkCodec {
    private ChunkCodec() {}

    public static Chunk decode(int chunkX, int chunkZ, CompoundTag root) {
        List<Section> sections = new ArrayList<>();
        ListTag list = root.getList("sections", null);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) sections.add(decodeSection(list.getCompound(i)));
        }

        return new Chunk(chunkX, chunkZ, root, sections);
    }

    private static Section decodeSection(CompoundTag tag) {
        List<BlockState> palette = new ArrayList<>();
        long[] d = null;
        CompoundTag blockStates = tag.getCompound("block_states", null);
        ListTag paletteTag = blockStates.getList("palette", null);
        if (paletteTag != null) {
            for (int i = 0; i < paletteTag.size(); i++) palette.add(decodeBlockState(paletteTag.getCompound(i)));
        }
        d = blockStates.getLongArray("data", null);

        if (palette.isEmpty()) palette.add(new BlockState("minecraft:air"));
        return new Section(sectionY(tag), palette, d);
    }

    private static BlockState decodeBlockState(CompoundTag tag) {
        Map<String, String> properties = new HashMap<>();
        CompoundTag propertiesTag = tag.getCompound("Properties", null);
        if (propertiesTag != null) {
            for (String key : propertiesTag.getKeys()) properties.put(key, propertiesTag.getString(key, ""));
        }

        return new BlockState(tag.getString("Name", "minecraft:air"), properties);
    }

    private static int sectionY(CompoundTag section) {
        Tag<?> y = section.get("Y");
        return y == null ? 0 : ((Number) y.getValue()).intValue();
    }
}
