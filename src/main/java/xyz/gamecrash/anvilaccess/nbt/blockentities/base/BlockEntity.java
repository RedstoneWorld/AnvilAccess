package xyz.gamecrash.anvilaccess.nbt.blockentities.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.Optional;

/**
 * Class representing a basic Block Entity.
 * <p>
 * It has methods to get data every block entity has in common. It also has some other useful utility methods.
 */
@Getter
@RequiredArgsConstructor
public class BlockEntity {
    private final CompoundTag nbt;

    public String getId() {
        return nbt.getString("id", "unknown");
    }

    public int getX() {
        return nbt.getInt("x", 0);
    }

    public int getY() {
        return nbt.getInt("y", 0);
    }

    public int getZ() {
        return nbt.getInt("z", 0);
    }

    public boolean isAt(int x, int y, int z) {
        return getX() == x && getY() == y && getZ() == z;
    }

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

    public int getInt(String key) {
        return nbt.getInt(key, 0);
    }

    public boolean keepPacked() {
        return getByte("keepPacked") == 1;
    }

    public CompoundTag getRawNBT() {
        return nbt;
    }

    @Override
    public String toString() {
        return String.format("BlockEntity[%s](%d;%d;%d)", getId(), getX(), getY(), getZ());
    }
}
