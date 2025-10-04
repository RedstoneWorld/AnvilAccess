package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class HopperBlockEntity extends ContainerBlockEntity {
    public HopperBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getTransferCooldown() { return this.getInt("TransferCooldown"); }
}
