package xyz.gamecrash.AnvilAccess.nbt.blockentities.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.Optional;

@Getter @RequiredArgsConstructor
public class BlockEntity {
    private final CompoundTag nbt;

    public String getId() { return nbt.getString("id", "unknown"); }

    public int getX() { return nbt.getInt("x", 0); }

    public int getY() { return nbt.getInt("y", 0); }

    public int getZ() { return nbt.getInt("z", 0); }

    public boolean isAt(int x, int y, int z) {  return getX() == x && getY() == y && getZ() == z; }

    public Optional<String> getCustomName() {
        String customName = nbt.getString("CustomName", null);
        return Optional.ofNullable(customName);
    }

    public Optional<CompoundTag> getCompound(String key) {
        CompoundTag compound = nbt.getCompound(key, null);
        return Optional.ofNullable(compound);
    }

    public Optional<ListTag> getList(String key) {
        ListTag list = nbt.getList(key, null);
        return Optional.ofNullable(list);
    }

    public Optional<String> getString(String key) {
        String string = nbt.getString(key, null);
        return Optional.ofNullable(string);
    }

    public byte getByte(String key) {
        return nbt.getByte(key, (byte) 0);
    }

    public int getInt(String key) { return nbt.getInt(key, 0); }

    public CompoundTag getRawNBT() { return nbt; }

    @Override
    public String toString() { return String.format("BlockEntity[%s](%d;%d;%d)", getId(), getX(), getY(), getZ()); }
}
