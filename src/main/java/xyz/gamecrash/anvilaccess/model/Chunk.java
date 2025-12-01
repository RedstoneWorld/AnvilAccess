package xyz.gamecrash.anvilaccess.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.gamecrash.anvilaccess.nbt.BlockEntityParser;
import xyz.gamecrash.anvilaccess.nbt.TagType;
import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a chunk with its sections and NBT data.
 */
@SuppressWarnings("ClassCanBeRecord")
@Getter
@RequiredArgsConstructor
public class Chunk {
    private final int chunkX;
    private final int chunkZ;
    private final CompoundTag nbt;
    private final List<Section> sections;

    /**
     * Gets a block at the given world coordinates
     *
     * @param x World X Coord
     * @param y World Y Coord
     * @param z World Z Coord
     * @return The block at the given coordinates
     */
    public Block getBlock(int x, int y, int z) {
        // convert world coordinates to local chunk coordinates
        int lX = x & 15;
        int lZ = z & 15;

        // get which section contains this Y coordinate
        int sectionY = y >> 4;

        // find the section that matches Y index
        Optional<Section> section = sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();

        // return air if section not found
        if (section.isEmpty()) return new Block(new BlockState("minecraft:air"), x, y, z);

        // get local Y coordinate in the section
        int lY = y & 15;
        BlockState state = section.get().getBlockState(lX, lY, lZ);

        // create/return the block with state and coordinates
        return new Block(state, x, y, z);
    }

    /**
     * Gets a block at the chunk-relative coordinates
     *
     * @param localX Local X Coord
     * @param y      World Y Coord
     * @param localZ Local Z Coord
     * @return The block at the given coordinates
     */
    public Block getLocalBlock(int localX, int y, int localZ) {
        int wX = (chunkX << 4) + localX;
        int wZ = (chunkZ << 4) + localZ;

        return getBlock(wX, y, wZ);
    }

    /**
     * Gets the section at the given Y coordinate
     */
    public Optional<Section> getSection(int y) {
        int sectionY = y >> 4;

        return sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();
    }

    /**
     * Gets the minimum Y level of the chunk
     */
    public int getMinY() {
        return sections.stream()
            .mapToInt(Section::getYIndex)
            .min()
            .orElse(0);
    }

    /**
     * Gets the maximum Y level of the chunk
     */
    public int getMaxY() {
        return sections.stream()
            .mapToInt(s -> s.getAbsoluteY() + 15)
            .max()
            .orElse(255);
    }

    /**
     * Gets a list of compounds containing all block entities within the chunk
     */
    public List<CompoundTag> getBlockEntityCompounds() {
        // try to get block entities using modern format or fallback to legacy if it is null
        ListTag blockEntityTag = nbt.getList("block_entities", new ListTag(TagType.COMPOUND));
        if (blockEntityTag.isEmpty())
            blockEntityTag = nbt.getList("TileEntities", new ListTag(TagType.COMPOUND)); // legacy format

        // oh noes, there are no block entities (or it is something completely different)
        if (blockEntityTag == null) return List.of();

        // get block entity compounds from the list
        List<CompoundTag> blockEntities = new ArrayList<>();
        for (int i = 0; i < blockEntityTag.size(); i++) {
            CompoundTag blockEntity = blockEntityTag.getCompound(i);
            if (blockEntity != null) blockEntities.add(blockEntity);
        }

        return blockEntities;
    }

    /**
     * Gets a list of all block entities within the chunk
     */
    public List<BlockEntity> getBlockEntities() {
        return getBlockEntityCompounds().stream()
            .map(BlockEntityParser::fromNbt)
            .collect(Collectors.toList());
    }

    /**
     * Gets a block entity at the given world coordinates
     *
     * @param x World X Coord
     * @param y World Y Coord
     * @param z World Z Coord
     * @return The block entity at the given coordinates, or empty if none exists
     */
    public Optional<CompoundTag> getBlockEntity(int x, int y, int z) {
        return getBlockEntityCompounds().stream()
            .filter(be -> be.getInt("x", Integer.MIN_VALUE) == x &&
                be.getInt("y", Integer.MIN_VALUE) == y &&
                be.getInt("z", Integer.MIN_VALUE) == z)
            .findFirst();
    }

    /**
     * Gets the world X coordinate of the first block in the chunk
     */
    public int getWorldX() {
        return chunkX << 4;
    }

    /**
     * Gets the world Z coordinate of the first block in the chunk
     */
    public int getWorldZ() {
        return chunkZ << 4;
    }

    /**
     * Gets the data version of the chunk (e.g. 4440 for 1.21.8)
     */
    public int getDataVersion() {
        return nbt.getInt("DataVersion", 0);
    }

    /**
     * Gets the tick the chunk was last saved
     */
    public long getLastUpdate() {
        return nbt.getLong("LastUpdate", 0L);
    }

    /**
     * Gets the time in ticks players have been in the chunk. Increases faster when more players are in the chunk
     */
    public long getInhabitedTime() {
        return nbt.getLong("InhabitedTime", 0L);
    }

    /**
     * Checks if the chunk has been generated yet (or if the sections are empty)
     */
    public boolean isGenerated() {
        return !sections.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Chunk(%d;%d)[Sections:%d]", chunkX, chunkZ, sections.size());
    }
}
