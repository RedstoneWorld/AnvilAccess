package xyz.gamecrash.AnvilAccess.nbt.blockentities;

import xyz.gamecrash.AnvilAccess.nbt.ItemStack;
import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.Optional;

public class LecternBlockEntity extends BlockEntity {
    public LecternBlockEntity(CompoundTag nbt) { super(nbt); }

    public Optional<ItemStack> getBook() { return getCompound("Book").map(ItemStack::new); }

    public int getPage() { return getInt("Page"); }
}
