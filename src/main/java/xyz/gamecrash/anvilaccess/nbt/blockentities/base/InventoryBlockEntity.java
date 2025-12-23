package xyz.gamecrash.anvilaccess.nbt.blockentities.base;

import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

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
