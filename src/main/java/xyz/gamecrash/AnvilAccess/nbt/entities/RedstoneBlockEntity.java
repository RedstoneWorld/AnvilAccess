package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

// Really, comparator?!
public abstract class RedstoneBlockEntity extends BlockEntity {
    public RedstoneBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getPowerLevel() { return getNbt().getInt("powered", 0); }
}
