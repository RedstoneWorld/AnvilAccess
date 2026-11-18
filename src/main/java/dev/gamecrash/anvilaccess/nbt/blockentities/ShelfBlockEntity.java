package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class ShelfBlockEntity extends ContainerBlockEntity {
    public ShelfBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public boolean alignItemsToBottom() {
        return getByte("align_items_to_bottom") == 1;
    }
}
