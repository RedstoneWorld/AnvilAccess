package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class PotentSulfurBlockEntity extends BlockEntity {

    public PotentSulfurBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getCountdown() {
        return getInt("countdown");
    }
}
