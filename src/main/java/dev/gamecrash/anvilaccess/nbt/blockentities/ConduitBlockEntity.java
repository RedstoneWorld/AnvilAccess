package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class ConduitBlockEntity extends BlockEntity {
    public ConduitBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int[] getTarget() {
        return getNbt().getIntArray("Target", new int[]{0, 0, 0});
    }
}
