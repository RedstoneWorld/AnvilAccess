package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class ChiseledBookshelfBlockEntity extends ContainerBlockEntity {
    public ChiseledBookshelfBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getLastInteractedSlot() { return getNbt().getInt("last_interacted_slot", -1); }
}
