package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.Optional;

public class SculkCatalystBlockEntity extends BlockEntity {
    public SculkCatalystBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ListTag> getCursors() {
        return getList("Cursors");
    }
}
