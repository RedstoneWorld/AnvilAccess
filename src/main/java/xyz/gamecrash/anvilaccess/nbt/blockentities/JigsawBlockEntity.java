package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class JigsawBlockEntity extends BlockEntity {
    public JigsawBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<String> getFinalState() {
        return getString("final_state");
    }

    public Optional<String> getJointType() {
        return getString("joint");
    }

    public Optional<String> getName() {
        return getString("name");
    }

    public Optional<String> getPool() {
        return getString("pool");
    }

    public Optional<String> getTarget() {
        return getString("target");
    }

    public int getSelectionPriority() {
        return getInt("selection_priority");
    }

    public int getPlacementPriority() {
        return getInt("placement_priority");
    }
}
