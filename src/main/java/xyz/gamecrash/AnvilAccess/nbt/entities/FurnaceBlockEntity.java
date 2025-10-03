package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public abstract class FurnaceBlockEntity extends ContainerBlockEntity {
    public FurnaceBlockEntity(CompoundTag nbt) { super(nbt); }

    public short getBurnTime() { return getNbt().getShort("BurnTime", (short) 0); }

    public short getCookTime() { return getNbt().getShort("CookTime", (short) 0); }

    public short getCookTimeTotal() { return getNbt().getShort("CookTimeTotal", (short) 200); }
}
