package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;

import java.util.Optional;

public class BeehiveBlockEntity extends BlockEntity {
    public BeehiveBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ListTag> getBees() {
        return getList("bees");
    }

    public Optional<int[]> getFlowerPos() {
        return Optional.ofNullable(getNbt().getIntArray("flower_pos", null));
    }
}
