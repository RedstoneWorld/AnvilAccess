package xyz.gamecrash.anvilaccess.nbt.blockentities.base;

import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class InventoryBlockEntity extends ContainerBlockEntity {
    public InventoryBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<String> getDisplayName() {
        return getCustomName();
    }

    public Optional<CompoundTag> getLock() {
        return getCompound("lock");
    }

    public boolean isLocked() {
        return getLock().isPresent();
    }
}
