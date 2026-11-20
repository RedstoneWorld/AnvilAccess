package dev.gamecrash.anvilaccess.model;

import dev.gamecrash.anvilaccess.nbt.BlockEntityParser;
import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;
import lombok.Getter;

import java.util.*;

public class Chunk {
    @Getter
    private final int chunkX, chunkZ;
    @Getter
    private final CompoundTag nbt;
    @Getter
    private final List<Section> sections;
    private final Map<Integer, Section> sectionsByY = new HashMap<>();

    public Chunk(int chunkX, int chunkZ, CompoundTag nbt, List<Section> sections) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.nbt = nbt;
        this.sections = sections;
        sections.forEach(s -> sectionsByY.put(s.getYIndex(), s));
    }

    public int getWorldX() {
        return chunkX << 4;
    }

    public int getWorldZ() {
        return chunkZ << 4;
    }

    /** Get section at world Y coord */
    public Optional<Section> getSection(int y) {
        return Optional.ofNullable(sectionsByY.get(y >> 4));
    }

    public Block getBlock(int x, int y, int z) {
        Section s = sectionsByY.get(y >> 4);
        BlockState state = s == null
            ? new BlockState("minecraft:air")
            : s.getBlockState(x & 15, y & 15, z & 15);

        return new Block(state, x, y, z);
    }

    public Block getLocalBlock(int lX, int y, int lZ) {
        return getBlock(getWorldX() + lX, y, getWorldZ() + lZ);
    }

    public void setBlock(int x, int y, int z, BlockState state) {
        Section s = sectionsByY.get(y >> 4);
        if (s == null) throw new IllegalArgumentException("Chunk has no section at given y " + y);
        s.setBlockState(x & 15, y & 15, z & 15, state);
    }

    public int getMinY() {
        return sections.stream().mapToInt(Section::getAbsY).min().orElse(0);
    }

    public int getMaxY() {
        return sections.stream().mapToInt(s -> s.getAbsY() + 15).max().orElse(255);
    }

    public List<CompoundTag> getBlockEntityCompounds() {
        List<CompoundTag> res = new ArrayList<>();
        ListTag tag = nbt.getList("block_entities", null);
        if (tag != null) {
            for (int i = 0; i < tag.size(); i++) {
                res.add(tag.getCompound(i));
            }
        }
        return res;
    }

    public List<BlockEntity> getBlockEntities() {
        return getBlockEntityCompounds().stream().map(BlockEntityParser::fromNbt).toList();
    }

    public Optional<CompoundTag> getBlockEntityCompound(int x, int y, int z) {
        return getBlockEntityCompounds().stream()
            .filter(t -> t.getInt("x", Integer.MIN_VALUE) == x
                && t.getInt("y", Integer.MIN_VALUE) == y
                && t.getInt("z", Integer.MIN_VALUE) == z)
            .findFirst();
    }

    public Optional<BlockEntity> getBlockEntity(int x, int y, int z) {
        return getBlockEntityCompound(x, y, z).map(BlockEntityParser::fromNbt);
    }

    public int getDataVersion() {
        return nbt.getInt("DataVersion", Integer.MIN_VALUE);
    }

    public long getLastUpdate() {
        return nbt.getLong("LastUpdate", Long.MIN_VALUE);
    }

    public long getInhabitedTime() {
        return nbt.getLong("InhabitedTime", Long.MIN_VALUE);
    }

    public boolean isGenerated() {
        return !sections.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Chunk(%d;%d)[Sections:%d]", chunkX, chunkZ, sections.size());
    }
}
