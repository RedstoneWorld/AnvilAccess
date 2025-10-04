package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.SlotItemStack;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.List;

public class CampfireBlockEntity extends CookingBlockEntity {
    public CampfireBlockEntity(CompoundTag nbt) { super(nbt); }

    public List<SlotItemStack> getCookingItems() { return getItems(); }

    public int[] getCookingTimes() { return getNbt().getIntArray("CookingTimes", new int[4]); }

    public int[] getCookingTotalTimes() { return getNbt().getIntArray("CookingTotalTimes", new int[4]); }

    @Override
    public int[] getProcessingTimes() { return getCookingTimes(); }
}
