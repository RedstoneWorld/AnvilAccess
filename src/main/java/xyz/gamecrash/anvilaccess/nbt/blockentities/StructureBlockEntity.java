package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class StructureBlockEntity extends BlockEntity {
    public StructureBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<String> getAuthor() {
        return getString("author");
    }

    public boolean ignoreEntities() {
        return getByte("ignoreEntities") == 1;
    }

    public float getIntegrity() {
        return getNbt().getFloat("integrity", 1);
    }

    public Optional<String> getMetadata() {
        return getString("metadata");
    }

    public Optional<String> getMirror() {
        return getString("mirror");
    }

    public Optional<String> getMode() {
        return getString("mode");
    }

    public Optional<String> getName() {
        return getString("name");
    }

    public int getPosX() {
        return getInt("posX");
    }

    public int getPosY() {
        return getInt("posY");
    }

    public int getPosZ() {
        return getInt("posZ");
    }

    public boolean isPowered() {
        return getByte("powered") == 1;
    }

    public Optional<String> getRotation() {
        return getString("rotation");
    }

    public long getSeed() {
        return getNbt().getLong("seed", 1);
    }

    public boolean showBoundingBox() {
        return getByte("showboundingbox") == 1;
    }

    public int getSizeX() {
        return getInt("sizeX");
    }

    public int getSizeY() {
        return getInt("sizeY");
    }

    public int getSizeZ() {
        return getInt("sizeZ");
    }
}
