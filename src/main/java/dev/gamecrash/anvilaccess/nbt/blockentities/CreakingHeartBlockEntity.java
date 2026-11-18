package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class CreakingHeartBlockEntity extends BlockEntity {
    public CreakingHeartBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int[] getCreaking() {
        return getNbt().getIntArray("creaking", new int[]{0, 0, 0, 0});
    }
}
