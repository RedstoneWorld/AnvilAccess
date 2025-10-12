package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class HopperBlockEntity extends ContainerBlockEntity {
    public HopperBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getTransferCooldown() { return this.getInt("TransferCooldown"); }
}
