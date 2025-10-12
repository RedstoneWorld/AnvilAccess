package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.Optional;

public class TrialSpawnerBlockEntity extends BlockEntity {
    public TrialSpawnerBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getRequiredPlayerRange() { return getInt("required_player_range"); }

    public int getTargetCooldownLength() { return getInt("target_cooldown_length"); }

    public Optional<ListTag> getRegisteredPlayers() { return getList("registered_players"); }

    public Optional<ListTag> getCurrentMobs() { return getList("current_mobs"); }

    public long getCooldownEndsAt() { return getNbt().getLong("cooldown_ends_at", 0); }

    public long getNextMobSpawnsAt() { return getNbt().getLong("next_mob_spawns_at", 0); }

    public int getTotalMobsSpawned() { return getInt("total_mobs_spawned"); }

    public Optional<CompoundTag> getSpawnData() { return getCompound("spawn_data"); }

    public Optional<String> getEjectingLootTable() { return getString("ejecting_loot_table"); }
}
