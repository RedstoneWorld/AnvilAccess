package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class EndGatewayBlockEntity extends BlockEntity {
    public EndGatewayBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public long getAge() {
        return getNbt().getLong("Age", 0);
    }

    public boolean isExactTeleport() {
        return getByte("ExactTeleport") == 1;
    }

    public int[] getExitPortal() {
        return getNbt().getIntArray("exit_portal", new int[]{0, 0, 0});
    }
}
