package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.ItemStack;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class JukeboxBlockEntity extends BlockEntity {
    public JukeboxBlockEntity(CompoundTag nbt) { super(nbt); }

    public ItemStack getRecordItem() { return new ItemStack(getNbt().getCompound("RecordItem", new CompoundTag())); }

    public long getTicksSinceSongStarted() { return getNbt().getLong("ticks_since_song_started", 0); }
}
