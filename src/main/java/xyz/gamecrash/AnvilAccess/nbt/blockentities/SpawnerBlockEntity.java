package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class SpawnerBlockEntity extends BlockEntity {
    public SpawnerBlockEntity(CompoundTag nbt) { super(nbt); }

    public CompoundTag getSpawnData() { return getNbt().getCompound("SpawnData", null); }

    public short getDelay() { return getNbt().getShort("Delay", (short) 20); }

    public short getMinSpawnDelay() { return getNbt().getShort("MinSpawnDelay", (short) 200); }

    public short getMaxSpawnDelay() { return getNbt().getShort("MaxSpawnDelay", (short) 800); }

    public short getMaxNearbyEntities() { return getNbt().getShort("MaxNearbyEntities", (short) 0); }

    public short getRequiredPlayerRange() { return getNbt().getShort("RequiredPlayerRange", (short) 16); }
}
