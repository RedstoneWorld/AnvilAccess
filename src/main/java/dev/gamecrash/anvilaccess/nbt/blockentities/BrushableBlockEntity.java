package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.SlotItemStack;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

import java.util.Optional;

public class BrushableBlockEntity extends BlockEntity {
    public BrushableBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<String> getLootTable() {
        return getString("LootTable");
    }

    public long getLootTableSeed() {
        return getNbt().getLong("LootTableSeed", 0);
    }

    public Optional<SlotItemStack> getItem() {
        return getCompound("item").map(SlotItemStack::new);
    }
}
