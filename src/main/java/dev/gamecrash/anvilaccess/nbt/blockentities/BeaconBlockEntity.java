package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

public class BeaconBlockEntity extends BlockEntity {
    public BeaconBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public int getLevels() {
        return getNbt().getInt("Levels", 0);
    }

    public String getPrimaryEffect() {
        return getNbt().getString("Primary", "");
    }

    public String getSecondaryEffect() {
        return getNbt().getString("Secondary", "");
    }


}
