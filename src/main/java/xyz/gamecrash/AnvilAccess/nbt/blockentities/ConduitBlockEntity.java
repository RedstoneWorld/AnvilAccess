package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class ConduitBlockEntity  extends BlockEntity {
    public ConduitBlockEntity(CompoundTag nbt) { super(nbt); }

    public int[] getTarget() { return getNbt().getIntArray("Target", new int[] {0, 0, 0} ); }
}
