package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class ComparatorBlockEntity extends BlockEntity {
    public ComparatorBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getPowerLevel() { return getNbt().getInt("OutputSignal", 0); }
}
