package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.ContainerBlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class ShelfBlockEntity extends ContainerBlockEntity {
    public ShelfBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public boolean alignItemsToBottom() {
        return getByte("align_items_to_bottom") == 1;
    }
}
