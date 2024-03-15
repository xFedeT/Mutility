package it.fedet.mutility.common.server.item.nbt;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class ItemUUID {

    private static final String KEY = "UUID";

    private ItemUUID() {
    }

    public static ItemStack generate(ItemStack item) {
        if (get(item).isPresent()) return item;

        return ItemNBT.set(item, KEY, UUID.randomUUID().toString());
    }

    public static Optional<String> get(ItemStack item) {
        return ItemNBT.get(item, KEY);
    }

}