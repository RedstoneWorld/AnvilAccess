package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.Optional;

public class SculkCatalystBlockEntity extends BlockEntity {
    public SculkCatalystBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<ListTag> getCursors() { return getList("Cursors"); }
}
