package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class ChiseledBookshelfBlockEntity extends ContainerBlockEntity {
    public ChiseledBookshelfBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getLastInteractedSlot() {
        return getNbt().getInt("last_interacted_slot", -1);
    }
}
