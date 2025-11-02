package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.SlotItemStack;
import xyz.gamecrash.anvilaccess.nbt.blockentities.base.CookingBlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.List;

public class CampfireBlockEntity extends CookingBlockEntity {
    public CampfireBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public List<SlotItemStack> getCookingItems() {
        return getItems();
    }

    public int[] getCookingTimes() {
        return getNbt().getIntArray("CookingTimes", new int[4]);
    }

    public int[] getCookingTotalTimes() {
        return getNbt().getIntArray("CookingTotalTimes", new int[4]);
    }

    @Override
    public int[] getProcessingTimes() {
        return getCookingTimes();
    }
}
