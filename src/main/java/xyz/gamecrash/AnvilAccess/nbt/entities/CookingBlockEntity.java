package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public abstract class CookingBlockEntity extends BlockEntity {
    public CookingBlockEntity(CompoundTag nbt) { super(nbt); }

    public abstract int[] getProcessingTimes();
}
