package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.Optional;

public class BannerBlockEntity extends BlockEntity {
    public BannerBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ListTag> getPatterns() {
        return getList("Patterns");
    }
}
