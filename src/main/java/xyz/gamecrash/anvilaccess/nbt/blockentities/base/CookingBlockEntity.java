package xyz.gamecrash.anvilaccess.nbt.blockentities.base;

import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public abstract class CookingBlockEntity extends ContainerBlockEntity {
    public CookingBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public abstract int[] getProcessingTimes();
}
