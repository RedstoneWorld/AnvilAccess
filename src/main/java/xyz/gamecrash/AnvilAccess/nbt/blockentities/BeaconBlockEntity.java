package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class BeaconBlockEntity extends BlockEntity {
    public BeaconBlockEntity(CompoundTag nbt) { super(nbt); }

    public int getLevels() { return getNbt().getInt("Levels", 0); }

    public String getPrimaryEffect() { return getNbt().getString("Primary", ""); }

    public String getSecondaryEffect() { return getNbt().getString("Secondary", ""); }


}
