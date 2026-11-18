package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class ChiseledBookshelfBlockEntity extends ContainerBlockEntity {
    public ChiseledBookshelfBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getLastInteractedSlot() {
        return getNbt().getInt("last_interacted_slot", -1);
    }
}
