package dev.gamecrash.anvilaccess.nbt.blockentities;

import dev.gamecrash.anvilaccess.nbt.blockentities.base.BlockEntity;
import dev.gamecrash.anvilaccess.nbt.ItemStack;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;

import java.util.Optional;

public class LecternBlockEntity extends BlockEntity {
    public LecternBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

    public Optional<ItemStack> getBook() {
        return getCompound("Book").map(ItemStack::new);
    }

    public int getPage() {
        return getInt("Page");
    }
}
