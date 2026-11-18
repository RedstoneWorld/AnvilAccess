package dev.gamecrash.anvilaccess.nbt.blockentities.base;

import dev.gamecrash.anvilaccess.nbt.SlotItemStack;
import dev.gamecrash.anvilaccess.nbt.tag.CompoundTag;
import dev.gamecrash.anvilaccess.nbt.tag.ListTag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class for representing block entities that can store items.
 * <p>
 * See {@link InventoryBlockEntity}
 */
public abstract class ContainerBlockEntity extends BlockEntity {
    public ContainerBlockEntity(CompoundTag nbt) {
        super(nbt);
    }

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
