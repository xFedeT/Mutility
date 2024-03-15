package it.fedet.mutility.common.server.item.interact.impl;

import it.fedet.mutility.common.server.item.interact.InteractActionItem;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InteractItem implements InteractActionItem {

    private final ItemStack item;
    private final Consumer<PlayerInteractEvent> action;

    public InteractItem(ItemStack item, Consumer<PlayerInteractEvent> action) {
        this.item = item;
        this.action = action;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public Consumer<PlayerInteractEvent> getAction() {
        return action;
    }

}