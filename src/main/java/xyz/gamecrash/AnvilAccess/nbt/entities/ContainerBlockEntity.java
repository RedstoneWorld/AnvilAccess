package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.SlotItemStack;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.ListTag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ContainerBlockEntity extends BlockEntity {
    public ContainerBlockEntity(CompoundTag nbt) { super(nbt); }

    public List<SlotItemStack> getItems() {
        return getList("Items")
            .map(this::parseItems)
            .orElse(List.of());
    }

    private List<SlotItemStack> parseItems(ListTag itemsTag) {
        return IntStream.range(0, itemsTag.size())
            .mapToObj(itemsTag::getCompound)
            .map(SlotItemStack::new)
            .collect(Collectors.toList());
    }
}
