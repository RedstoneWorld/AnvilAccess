package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;

import java.util.Optional;

public class SculkCatalystBlockEntity extends BlockEntity {
    public SculkCatalystBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ListTag> getCursors() {
        return getList("Cursors");
    }
}
