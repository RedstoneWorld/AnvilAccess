package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class MovingPistonBlockEntity extends BlockEntity {
    public MovingPistonBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<CompoundTag> getBlockState() { return getCompound("blockState"); }

    public boolean isExtending() { return getByte("isExtending") == 1; }

    public int getFacing() { return getInt("facing"); }

    public float getProgress() { return getNbt().getFloat("progress", 0); }

    public boolean isSource() { return getByte("isSource") == 1; }
}
