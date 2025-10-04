package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public abstract class InventoryBlockEntity extends ContainerBlockEntity {
    public InventoryBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<String> getDisplayName() { return getCustomName(); }

    public Optional<String> getLock() {
        String lock = getNbt().getString("Lock", null);
        return Optional.ofNullable(lock).filter(s -> !s.isEmpty());
    }

    public boolean isLocked() { return getLock().isPresent(); }
}
