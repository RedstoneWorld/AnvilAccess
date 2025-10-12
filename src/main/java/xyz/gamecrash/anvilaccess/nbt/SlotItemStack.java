package xyz.gamecrash.anvilaccess.nbt;

import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

/**
 * Class representing an ItemStack with a given slot its in
 */
public class SlotItemStack extends ItemStack {
    public SlotItemStack(CompoundTag nbt) { super(nbt); }

    /**
     * Gets the inventory slot the ItemStack is in
     */
    public byte getSlot() { return getNbt().getByte("Slot", (byte) 0); }

    @Override
    public String toString() {
        return String.format("ItemStack[%s*%d;Slot:%d]", getId(), getCount(), getSlot());
    }
}
