package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class CommandBlockEntity extends BlockEntity {
    public CommandBlockEntity(CompoundTag nbt) { super(nbt); }

    public boolean isAuto() { return getByte("auto") == 1; }

    public Optional<String> getCommand() { return getString("Command"); }

    public boolean isConditionMet() { return getByte("conditionMet") == 1; }

    public long getLastExecution() { return getNbt().getLong("LastExecution", 0); }

    public Optional<String> getLastOutput() { return getString("LastOutput"); }

    public boolean isPowered() { return getByte("powered") == 1; }

    public int getSuccessCount() { return getInt("SuccessCount"); }

    public boolean tracksOutput() { return getByte("TrackOutput") == 1; }

    public boolean isUpdateLastExecution() { return getByte("UpdateLastExecution") == 1; }
}
