package xyz.gamecrash.anvilaccess.nbt.blockentities.base;

import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

/**
 * A class for representing block entities in which one can cook (e.g. furnaces or campfires)
 * <p>
 * See
 * {@link xyz.gamecrash.anvilaccess.nbt.blockentities.FurnaceBlockEntity} and
 * {@link xyz.gamecrash.anvilaccess.nbt.blockentities.CampfireBlockEntity}
 */
public abstract class CookingBlockEntity extends ContainerBlockEntity {
    public CookingBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public abstract int[] getProcessingTimes();
}
