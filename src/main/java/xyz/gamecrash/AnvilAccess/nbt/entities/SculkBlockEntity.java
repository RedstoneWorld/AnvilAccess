package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public abstract class SculkBlockEntity extends BlockEntity {
    public SculkBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getLastVibrationFrequency() {
        return getNbt().getInt("last_vibration_frequency", 0);
    }
}
