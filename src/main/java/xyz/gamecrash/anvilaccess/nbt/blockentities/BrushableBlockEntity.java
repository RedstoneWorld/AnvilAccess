package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.SlotItemStack;
import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

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
