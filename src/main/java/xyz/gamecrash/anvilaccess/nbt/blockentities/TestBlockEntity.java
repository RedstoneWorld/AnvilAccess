package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

// TODO: validate with english minecraft wiki entry
/**
 * Note: this block entity has been created from articles on the german Minecraft wiki site,
 * as the english one does not have any of the block entity data listed as of this release.
 */
public class TestBlockEntity extends BlockEntity {
    public TestBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<String> getMode() {
        return getString("mode");
    }

    public Optional<String> getMessage() {
        return getString("message");
    }

    public boolean isPowered() {
        return getByte("powered") == 1;
    }
}
