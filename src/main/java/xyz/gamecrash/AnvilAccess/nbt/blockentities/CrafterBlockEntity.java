package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.InventoryBlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class CrafterBlockEntity extends InventoryBlockEntity {
    public CrafterBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getCraftingTicksRemaining() { return getInt("crafting_ticks_remaining"); }

    public boolean isTriggered() { return getByte("triggered") == 1; }

    public int[] getDisabledSlots() { return getNbt().getIntArray("disabled_slots", new int[] {0}); }

    public Optional<String> getLootTable() { return getString("LootTable"); }

    public long getLootTableSeed() { return getNbt().getLong("LootTableSeed", 0L); }
}
