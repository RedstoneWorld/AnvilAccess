package dev.gamecrash.anvilaccess.nbt.blockentities.base;

import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

import java.util.Optional;

/**
 * A class representing block entities that act as inventories.
 * <p>
 * See {@link ContainerBlockEntity}
 */
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
