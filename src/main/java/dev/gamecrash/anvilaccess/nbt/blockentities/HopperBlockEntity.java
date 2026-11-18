package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class HopperBlockEntity extends ContainerBlockEntity {
    public HopperBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getTransferCooldown() {
        return this.getInt("TransferCooldown");
    }
}
