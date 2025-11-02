package xyz.gamecrash.anvilaccess.nbt;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

/**
 * Class representing a wrapper for an ItemStack NBT
 */
public class ItemStack {
    @Getter
    private final CompoundTag nbt;

    public ItemStack(CompoundTag nbt) {
        this.nbt = nbt;
    }

    /**
     * Gets the block ID associated with the item stack
     */
    public String getId() {
        return nbt.getString("id", "minecraft:air");
    }

    /**
     * Gets the count of items in the stack
     */
    public byte getCount() {
        return nbt.getByte("count", (byte) 1);
    }

    /**
     * Gets the components of the item stack
     */
    public Optional<CompoundTag> getComponents() {
        CompoundTag tag = nbt.getCompound("components", null);
        return Optional.ofNullable(tag);
    }

    @Override
    public String toString() {
        return String.format("ItemStack[%s*%d]", getId(), getCount());
    }
}
