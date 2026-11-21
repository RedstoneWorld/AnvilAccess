package dev.gamecrash.anvilaccess.nbt;

import dev.gamecrash.anvilaccess.model.BlockState;
import dev.gamecrash.anvilaccess.model.Chunk;
import dev.gamecrash.anvilaccess.model.Section;
import dev.gamecrash.anvilaccess.nbt.tag.*;

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

    public static CompoundTag encode(Chunk chunk) {
        CompoundTag root = chunk.getNbt();
        ListTag list = root.getList("sections", null);
        if (list == null) return root;

        for (int i = 0; i < list.size(); i++) {
            CompoundTag sectionTag = list.getCompound(i);
            int y = sectionY(sectionTag);

            chunk.getSections().stream()
                .filter(section -> section.getYIndex() == y && section.isModified())
                .findFirst()
                .ifPresent(section -> sectionTag.put("block_states", encodeBlockStates(section)));
        }

        return root;
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

    private static CompoundTag encodeBlockStates(Section section) {
        ListTag palette = new ListTag(TagType.COMPOUND);
        for (BlockState state : section.getPalette()) {
            CompoundTag stateTag = new CompoundTag();
            stateTag.put("Name", new StringTag(state.id()));

            if (!state.properties().isEmpty()) {
                CompoundTag properties = new CompoundTag();
                state.properties().forEach((key, value) -> properties.put(key, new StringTag(value)));
                stateTag.put("Properties", properties);
            }

            palette.add(stateTag);
        }

        CompoundTag blockStates = new CompoundTag();
        blockStates.put("palette", palette);
        if (section.getData() != null) blockStates.put("data", new LongArrayTag(section.getData()));
        return blockStates;
    }

    private static int sectionY(CompoundTag section) {
        Tag<?> y = section.get("Y");
        return y == null ? 0 : ((Number) y.getValue()).intValue();
    }
}
