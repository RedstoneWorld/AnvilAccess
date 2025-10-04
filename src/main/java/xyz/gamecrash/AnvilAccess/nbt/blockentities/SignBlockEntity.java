package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.TagType;
import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.List;
import java.util.Optional;

public class SignBlockEntity extends BlockEntity {
    public SignBlockEntity(CompoundTag nbt) { super(nbt); }

    public List<String> getTextLines(boolean backSide) {
        return List.of(
            getTextLine(1, backSide),
            getTextLine(2, backSide),
            getTextLine(3, backSide),
            getTextLine(4, backSide)
        );
    }

    public String getTextLine(int line, boolean backSide) {
        if (line < 1 || line > 4) throw new IllegalArgumentException("Line must be between 1 and 4");

        Optional<CompoundTag> compound = getCompound(backSide ? "back_text" : "front_text");
        if (compound.isEmpty()) return "";
        ListTag list = compound.get().getList("messages", new ListTag(TagType.STRING, List.of()));

        return list.getString(line - 1);
    }

    public String getColor(boolean backSide) {
        Optional<CompoundTag> compound = getCompound(backSide ? "back_text" : "front_text");
        if (compound.isEmpty()) return "black";
        return compound.get().getString("color", "black");
    }

    public boolean isGlowing(boolean backSide) {
        Optional<CompoundTag> compound = getCompound(backSide ? "back_text" : "front_text");
        return compound.filter(compoundTag -> compoundTag.getByte("has_glowing_text", (byte) 0) == 1).isPresent();
    }

    public boolean isWaxed() { return getByte("is_waxed") == 1; }

    public String getFullText(boolean back) { return String.join("\n", getTextLines(back)); }
}
