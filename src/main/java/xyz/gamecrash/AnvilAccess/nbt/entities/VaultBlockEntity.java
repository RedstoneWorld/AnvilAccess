package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class VaultBlockEntity extends BlockEntity {
    public VaultBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<CompoundTag> getConfig() { return getCompound("config"); }

    public Optional<CompoundTag> getServerData() { return getCompound("server_data"); }

    public Optional<CompoundTag> getSharedData() { return getCompound("shared_data"); }
}
