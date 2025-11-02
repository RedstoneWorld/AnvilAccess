package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.IntArrayTag;

public class CreakingHeartBlockEntity extends BlockEntity {
    public CreakingHeartBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int[] getCreaking() {
        return getNbt().getIntArray("creaking", new int[]{0, 0, 0, 0});
    }
}
