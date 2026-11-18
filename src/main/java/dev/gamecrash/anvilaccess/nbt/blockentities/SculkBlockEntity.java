package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

import java.util.Optional;

public class SculkBlockEntity extends BlockEntity {
    public SculkBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getLastVibrationFrequency() {
        return getNbt().getInt("last_vibration_frequency", 0);
    }

    public Optional<CompoundTag> getListener() {
        return getCompound("listener").isEmpty() ?
            getCompound("VibrationListener") :
            getCompound("listener");
    }
}