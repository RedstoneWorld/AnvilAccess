package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.Optional;

public class BannerBlockEntity extends BlockEntity {
    public BannerBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<ListTag> getPatterns() { return getList("Patterns"); }
}
