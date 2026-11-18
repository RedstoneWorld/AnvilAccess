package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.ItemStack;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

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
