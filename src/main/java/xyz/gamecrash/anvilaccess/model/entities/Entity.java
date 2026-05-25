package xyz.gamecrash.anvilaccess.model.entities;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.IntArrayTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.util.UUID;

@Getter
public class Entity {
    private final CompoundTag nbt;
    private final UUID uuid;
    private final double x, y, z;
    private final String id;

    public Entity(CompoundTag nbt) {
        this.nbt = nbt;

        int[] uuidArray = nbt.getIntArray("UUID", null);
        if (uuidArray == null || uuidArray.length != 4) throw new RuntimeException("Entity nbt has no valid uuid");

        long msb = ((long) uuidArray[0] << 32) | (uuidArray[1] & 0xFFFFFFFFL);
        long lsb = ((long) uuidArray[2] << 32) | (uuidArray[3] & 0xFFFFFFFFL);
        uuid = new UUID(msb, lsb);

        ListTag pos = nbt.getList("Pos", null);
        if (pos == null || pos.size() != 3) throw new RuntimeException("Entity nbt has no valid position");
        x = pos.getDouble(0);
        y = pos.getDouble(1);
        z = pos.getDouble(2);

        id = nbt.getString("id", null);
        if (id == null) throw new RuntimeException("Entity has no valid id");
    }

    public short getAir() {
        return nbt.getShort("Air", (short) 0);
    }

    public boolean isCustomNameVisible() {
        return nbt.getBoolean("CustomNameVisible", false);
    }

    public CompoundTag getCustomData() {
        return nbt.getCompound("data", null);
    }

    public double getFallDistance() {
        return nbt.getDouble("fall_distance", 0);
    }

    public short getFire() {
        return nbt.getShort("Fire", (short) 0);
    }

    public boolean isGlowing() {
        return nbt.getBoolean("Glowing", false);
    }

    public boolean hasVisualFire() {
        return nbt.getBoolean("HasVisualFire", false);
    }

    public boolean isInvulnerable() {
        return nbt.getBoolean("Invulnerable", false);
    }

    public ListTag getMotion() {
        return nbt.getList("Motion", null);
    }

    public boolean hasNoGravity() {
        return nbt.getBoolean("NoGravity", false);
    }

    public boolean isOnGround() {
        return nbt.getBoolean("OnGround", true);
    }

    public ListTag getPassengers() {
        return nbt.getList("Passengers", null);
    }

    public int getPortalCooldown() {
        return nbt.getInt("PortalCooldown", 0);
    }

    public ListTag getRotation() {
        return nbt.getList("Rotation", null);
    }

    public boolean isSilent() {
        return nbt.getBoolean("Silent", false);
    }

    public ListTag getTags() {
        return nbt.getList("Tags", null);
    }

    public int getTicksFrozen() {
        return nbt.getInt("TicksFrozen", 0);
    }
}
