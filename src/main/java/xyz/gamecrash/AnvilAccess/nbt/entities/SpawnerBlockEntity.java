package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class SpawnerBlockEntity extends BlockEntity {
    public SpawnerBlockEntity(CompoundTag nbt) { super(nbt); }

    public String getSpawnedEntityType() {
        CompoundTag spawnData = getNbt().getCompound("SpawnData", null);
        if (spawnData != null) {
            CompoundTag entity = spawnData.getCompound("entity", null);
            if (entity != null) return entity.getString("id", "unknown");
        }
        return "unknown";
    }

    public short getDelay() { return getNbt().getShort("Delay", (short) 20); }

    public short getMinSpawnDelay() { return getNbt().getShort("MinSpawnDelay", (short) 200); }

    public short getMaxSpawnDelay() { return getNbt().getShort("MaxSpawnDelay", (short) 800); }
}
