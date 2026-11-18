package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.CookingBlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class BrewingStandBlockEntity extends CookingBlockEntity {
    public BrewingStandBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public short getBrewTime() {
        return getNbt().getShort("BrewTime", (short) 0);
    }

    public byte getFuel() {
        return getNbt().getByte("Fuel", (byte) 0);
    }

    @Override
    public int[] getProcessingTimes() {
        return new int[]{getBrewTime()};
    }
}
