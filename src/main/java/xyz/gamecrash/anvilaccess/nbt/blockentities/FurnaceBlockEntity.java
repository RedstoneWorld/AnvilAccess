package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.InventoryBlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class FurnaceBlockEntity extends InventoryBlockEntity {
    public FurnaceBlockEntity(CompoundTag nbt) { super(nbt); }

    public short getLitTimeRemaining() { return getNbt().getShort("lit_time_remaining", (short) 0); }

    public short getCookingTimeSpent() { return getNbt().getShort("cooking_time_spent", (short) 0); }

    public short getCookingTotalTime() { return getNbt().getShort("cooking_total_time", (short) 200); }

    public short getLitTotalTime() { return getNbt().getShort("lit_total_time", (short) 0); }
}
