package net.blay09.mods.inventoryessentials.client.sorting;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class AmountSorting {

    private AmountSorting() {
    }

    public static List<ItemStack> computeSortedList(List<ItemStack> stacks) {
        final Map<Item, Long> itemAmounts = new HashMap<>();
        for (ItemStack stack : stacks) {
            final long amount = stack.isStackable() ? stack.getCount() : 1L;
            itemAmounts.merge(stack.getItem(), amount, Long::sum);
        }

        return stacks.stream()
                .sorted(Comparator
                        .comparingLong((ItemStack stack) -> itemAmounts.get(stack.getItem()))
                        .reversed()
                        .thenComparing(stack -> stack.getHoverName().getString(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(stack -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())
                        .thenComparing(stack -> Objects.toString(stack.getComponents(), ""))
                        .thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed()))
                .toList();
    }
}
