package xyz.gamecrash.AnvilAccess.nbt;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class SlotItemStack extends ItemStack {
    public SlotItemStack(CompoundTag nbt) { super(nbt); }

    public byte getSlot() { return getNbt().getByte("Slot", (byte) 0); }

    @Override
    public String toString() {
        return String.format("ItemStack[%s*%d;Slot:%d]", getId(), getCount(), getSlot());
    }
}
