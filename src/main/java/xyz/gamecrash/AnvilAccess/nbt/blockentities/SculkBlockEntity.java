package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class SculkBlockEntity extends BlockEntity {
    public SculkBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getLastVibrationFrequency() { return getNbt().getInt("last_vibration_frequency", 0); }

    public Optional<CompoundTag> getListener() {
        return getCompound("listener").isEmpty() ?
            getCompound("VibrationListener") :
            getCompound("listener");
    }
}