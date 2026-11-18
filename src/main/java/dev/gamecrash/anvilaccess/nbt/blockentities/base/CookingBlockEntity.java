package dev.gamecrash.anvilaccess.nbt.blockentities.base;

import dev.gamecrash.anvilaccess.nbt.blockentities.CampfireBlockEntity;
import dev.gamecrash.anvilaccess.nbt.blockentities.FurnaceBlockEntity;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

/**
 * A class for representing block entities in which one can cook (e.g. furnaces or campfires)
 * <p>
 * See
 * {@link FurnaceBlockEntity} and
 * {@link CampfireBlockEntity}
 */
public abstract class CookingBlockEntity extends ContainerBlockEntity {
    public CookingBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public abstract int[] getProcessingTimes();
}
