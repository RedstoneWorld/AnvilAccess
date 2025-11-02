package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.ItemStack;
import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

public class JukeboxBlockEntity extends BlockEntity {
    public JukeboxBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public ItemStack getRecordItem() {
        return new ItemStack(getNbt().getCompound("RecordItem", new CompoundTag()));
    }

    public long getTicksSinceSongStarted() {
        return getNbt().getLong("ticks_since_song_started", 0);
    }
}
