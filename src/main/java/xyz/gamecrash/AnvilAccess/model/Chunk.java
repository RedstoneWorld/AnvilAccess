package xyz.gamecrash.AnvilAccess.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.gamecrash.AnvilAccess.nbt.BlockEntityParser;
import xyz.gamecrash.AnvilAccess.nbt.TagType;
import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @RequiredArgsConstructor
public class Chunk {
    private final int chunkX;
    private final int chunkZ;
    private final CompoundTag nbt;
    private final List<Section> sections;

    public Block getBlock(int x, int y, int z) {
        int lX = x & 15;
        int lZ = z & 15;

        int sectionY = y >> 4;

        Optional<Section> section = sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();

        if (section.isEmpty()) return new Block(new BlockState("minecraft:air"), x, y, z);

        int lY = y & 15;
        BlockState state = section.get().getBlockState(lX, lY, lZ);

        return new Block(state, x, y, z);
    }

    public Block getLocalBlock(int localX, int y, int localZ) {
        int wX = chunkX << 4 + localX;
        int wZ = chunkZ << 4 + localZ;

        return getBlock(wX, y, wZ);
    }

    public Optional<Section> getSection(int y) {
        int sectionY = y >> 4;

        return sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();
    }

    public int getMinY() {
        return sections.stream()
            .mapToInt(Section::getYIndex)
            .min()
            .orElse(0);
    }

    public int getMaxY() {
        return sections.stream()
            .mapToInt(s -> s.getAbsoluteY() + 15)
            .max()
            .orElse(255);
    }

    public List<CompoundTag> getBlockEntityCompounds() {
        ListTag blockEntityTag = nbt.getList("block_entities", new ListTag(TagType.COMPOUND));
        if (blockEntityTag == null || blockEntityTag.isEmpty()) blockEntityTag = nbt.getList("TileEntities", new ListTag(TagType.COMPOUND)); // legacy format
        if (blockEntityTag == null) return List.of();

        List<CompoundTag> blockEntities = new ArrayList<>();
        for (int i = 0; i < blockEntityTag.size(); i++) {
            CompoundTag blockEntity = blockEntityTag.getCompound(i);
            if (blockEntity != null) blockEntities.add(blockEntity);
        }

        return blockEntities;
    }

    public List<BlockEntity> getBlockEntities() {
        return getBlockEntityCompounds().stream()
            .map(BlockEntityParser::fromNbt)
            .collect(Collectors.toList());
    }

    public Optional<CompoundTag> getBlockEntity(int x, int y, int z) {
        return getBlockEntityCompounds().stream()
            .filter(be -> be.getInt("x", Integer.MIN_VALUE) == x &&
                be.getInt("y", Integer.MIN_VALUE) == y &&
                be.getInt("z", Integer.MIN_VALUE) == z)
            .findFirst();
    }

    public int getWorldX() { return chunkX << 4; }

    public int getWorldZ() { return chunkZ << 4; }

    public int getDataVersion() { return nbt.getInt("DataVersion", 0); }

    public long getLastUpdate() { return nbt.getLong("LastUpdate", 0L); }

    public long getInhabitedTime() { return nbt.getLong("InhabitedTime", 0L); }

    public boolean isGenerated() { return !sections.isEmpty(); }

    @Override
    public String toString() { return String.format("Chunk(%d;%d)[Sections:%d]", chunkX, chunkZ, sections.size()); }
}
