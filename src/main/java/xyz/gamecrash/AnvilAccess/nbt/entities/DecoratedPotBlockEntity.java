package xyz.gamecrash.AnvilAccess.nbt.entities;

import xyz.gamecrash.AnvilAccess.nbt.ItemStack;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DecoratedPotBlockEntity extends BlockEntity {
    public DecoratedPotBlockEntity(CompoundTag nbt) { super(nbt); }

    public List<String> getSherds() {
        return getList("sherds")
            .map(tag -> IntStream.range(0, tag.size())
                .mapToObj(i -> tag.getString(i))
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    public Optional<ItemStack> getItem() { return getCompound("item").map(ItemStack::new); }
}
