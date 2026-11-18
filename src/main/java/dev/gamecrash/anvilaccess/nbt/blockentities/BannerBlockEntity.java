package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;

import java.util.Optional;

public class BannerBlockEntity extends BlockEntity {
    public BannerBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ListTag> getPatterns() {
        return getList("Patterns");
    }
}
