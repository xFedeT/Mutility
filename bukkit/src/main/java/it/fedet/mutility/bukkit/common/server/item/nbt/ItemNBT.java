package it.fedet.mutility.common.server.item.nbt;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemNBT {

    private ItemNBT() {
    }

    public static ItemStack set(ItemStack item, String key, String value) {
        return NBTEditor.set(item, value, key);
    }

    public static Optional<String> get(ItemStack item, String key) {
        if (!NBTEditor.contains(item, key))
            return Optional.empty();

        return Optional.of(NBTEditor.getString(item, key));
    }

}
