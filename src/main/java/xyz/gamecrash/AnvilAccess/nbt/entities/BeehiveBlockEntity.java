package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.Optional;

public class BeehiveBlockEntity extends BlockEntity {
    public BeehiveBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<ListTag> getBees() { return getList("bees"); }

    public Optional<int[]> getFlowerPos() { return Optional.ofNullable(getNbt().getIntArray("flower_pos", null)); }
}
