package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class PotentSulfurBlockEntity extends BlockEntity {

    public PotentSulfurBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getCountdown() {
        return getInt("countdown");
    }
}
