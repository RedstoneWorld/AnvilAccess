package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.Optional;

public class BeehiveBlockEntity extends BlockEntity {
    public BeehiveBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<ListTag> getBees() { return getList("bees"); }

    public Optional<int[]> getFlowerPos() { return Optional.ofNullable(getNbt().getIntArray("flower_pos", null)); }
}
