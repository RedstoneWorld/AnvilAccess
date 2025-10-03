package xyz.gamecrash.AnvilAccess.nbt;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class ItemStack {
    private final CompoundTag nbt;

    public ItemStack(CompoundTag nbt) { this.nbt = nbt; }

    public String getId() { return nbt.getString("id", "minecraft:air"); }

    public byte getCount() { return nbt.getByte("Count", (byte) 1); }

    public byte getSlot() { return nbt.getByte("Slot", (byte) 0); }

    public Optional<CompoundTag> getTag() {
        CompoundTag tag = nbt.getCompound("tag", null);
        return Optional.ofNullable(tag);
    }

    @Override
    public String toString() {
        return String.format("ItemStack[%s*%d;Slot:%d]", getId(), getCount(), getSlot());
    }
}
