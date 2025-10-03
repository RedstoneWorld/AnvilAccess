package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.ItemStack;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ContainerBlockEntity extends BlockEntity {
    public ContainerBlockEntity(CompoundTag nbt) { super(nbt); }

    public List<ItemStack> getItems() {
        return getList("Items")
            .map(this::parseItems)
            .orElse(List.of());
    }

    private List<ItemStack> parseItems(ListTag itemsTag) {
        return IntStream.range(0, itemsTag.size())
            .mapToObj(itemsTag::getCompound)
            .map(ItemStack::new)
            .collect(Collectors.toList());
    }
}
