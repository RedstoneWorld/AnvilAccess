package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class EndGatewayEntity extends BlockEntity {
    public EndGatewayEntity(CompoundTag nbt) { super(nbt); }

    public long getAge() { return getNbt().getLong("Age", 0); }

    public boolean isExactTeleport() { return getByte("ExactTeleport") == 1; }

    public int[] getExitPortal() { return getNbt().getIntArray("exit_portal", new int[] {0, 0, 0} ); }
}
