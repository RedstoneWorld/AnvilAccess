package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public abstract class SimpleBlockEntity extends BlockEntity {
    public SimpleBlockEntity(CompoundTag nbt) { super(nbt); }
}