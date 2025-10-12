package xyz.gamecrash.anvilaccess.nbt.blockentities;

import xyz.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class SkullBlockEntity extends BlockEntity {
    public SkullBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<String> getNoteBlockSound() { return getString("note_block_sound"); }

    public Optional<SkullProfile> getProfile() {
        Optional<SkullProfile> stringProfile = getString("profile").map(StringSkullProfile::new);
        Optional<SkullProfile> compoundProfile = getCompound("profile").map(CompoundSkullProfile::new);
        return stringProfile.isPresent() ? stringProfile : compoundProfile;
    }

    public sealed interface SkullProfile permits StringSkullProfile, CompoundSkullProfile { }

    public record StringSkullProfile(String value) implements SkullProfile { }

    public record CompoundSkullProfile(CompoundTag value) implements SkullProfile { }
}
