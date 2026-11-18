package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class ComparatorBlockEntity extends BlockEntity {
    public ComparatorBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getPowerLevel() {
        return getNbt().getInt("OutputSignal", 0);
    }
}
