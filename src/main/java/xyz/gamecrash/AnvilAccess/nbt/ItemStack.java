package xyz.gamecrash.AnvilAccess.nbt;

import lombok.Getter;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class ItemStack {
    @Getter private final CompoundTag nbt;

    public ItemStack(CompoundTag nbt) { this.nbt = nbt; }

    public String getId() { return nbt.getString("id", "minecraft:air"); }

    public byte getCount() { return nbt.getByte("count", (byte) 1); }

    public Optional<CompoundTag> getComponents() {
        CompoundTag tag = nbt.getCompound("components", null);
        return Optional.ofNullable(tag);
    }

    @Override
    public String toString() {
        return String.format("ItemStack[%s*%d]", getId(), getCount());
    }
}
