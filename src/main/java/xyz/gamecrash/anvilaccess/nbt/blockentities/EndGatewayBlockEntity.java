package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

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
