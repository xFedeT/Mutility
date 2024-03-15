package it.fedet.mutility.common.server.gui.item.impl;

import it.fedet.mutility.common.server.gui.item.GuiActionItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GuiItem implements GuiActionItem {

    private final ItemStack item;
    private final Consumer<InventoryClickEvent> action;

    public GuiItem(ItemStack item, Consumer<InventoryClickEvent> action) {
        this.item = item;
        this.action = action;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public Consumer<InventoryClickEvent> getAction() {
        return action;
    }

}