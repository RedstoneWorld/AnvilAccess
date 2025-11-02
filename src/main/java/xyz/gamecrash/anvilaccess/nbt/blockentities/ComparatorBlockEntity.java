package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class ComparatorBlockEntity extends BlockEntity {
    public ComparatorBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getPowerLevel() {
        return getNbt().getInt("OutputSignal", 0);
    }
}
