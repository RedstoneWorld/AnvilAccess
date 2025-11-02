package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class ConduitBlockEntity extends BlockEntity {
    public ConduitBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int[] getTarget() {
        return getNbt().getIntArray("Target", new int[]{0, 0, 0});
    }
}
