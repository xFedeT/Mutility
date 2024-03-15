package it.fedet.mutility.common.server.item.action;

import it.fedet.mutility.common.server.item.nbt.ItemUUID;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public interface ActionItem<T> {

    ItemStack getItem();

    Consumer<T> getAction();

    default boolean checkUUID(ItemStack targetItem) {
        Optional<String> itemUUID = ItemUUID.get(getItem());

        if (!itemUUID.isPresent()) return false;

        Optional<String> targetItemUUID = ItemUUID.get(targetItem);

        return targetItemUUID.filter(s -> itemUUID.get().equals(s)).isPresent();
    }

}