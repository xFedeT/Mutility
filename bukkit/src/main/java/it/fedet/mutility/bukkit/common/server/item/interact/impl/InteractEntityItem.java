package it.fedet.mutility.common.server.item.interact.impl;

import it.fedet.mutility.common.server.item.interact.InteractEntityActionItem;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InteractEntityItem implements InteractEntityActionItem {
    private final ItemStack item;
    private final Consumer<PlayerInteractAtEntityEvent> action;

    public InteractEntityItem(ItemStack item, Consumer<PlayerInteractAtEntityEvent> action) {
        this.item = item;
        this.action = action;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public Consumer<PlayerInteractAtEntityEvent> getAction() {
        return action;
    }
}
