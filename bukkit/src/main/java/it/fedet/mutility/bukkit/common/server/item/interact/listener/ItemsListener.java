package it.fedet.mutility.common.server.item.interact.listener;

import it.fedet.mutility.common.server.item.action.ActionItem;
import it.fedet.mutility.common.server.item.interact.InteractActionItem;
import it.fedet.mutility.common.server.item.interact.InteractEntityActionItem;
import it.fedet.mutility.common.server.item.nbt.ItemUUID;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public class ItemsListener implements RegistrableListener {

    private final Map<String, ActionItem> interactItems;

    public ItemsListener(Map<String, ActionItem> interactItems) {
        this.interactItems = interactItems;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack eventItem = event.getItem();
        if (eventItem == null) return;

        Optional<String> itemUUID = ItemUUID.get(eventItem);

        itemUUID.ifPresent(uuid -> {
            ActionItem actionItem = interactItems.get(uuid);
            if (actionItem == null) return;

            if (!(actionItem instanceof InteractActionItem interactItem)) return;

            interactItem.getAction().accept(event);
        });
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        ItemStack eventItem = event.getPlayer().getItemInHand();
        if (eventItem == null || eventItem.getType().equals(Material.AIR)) return;

        Optional<String> itemUUID = ItemUUID.get(eventItem);

        itemUUID.ifPresent(uuid -> {
            ActionItem actionItem = interactItems.get(uuid);
            if (!(actionItem instanceof InteractEntityActionItem interactItem)) return;

            interactItem.getAction().accept(event);
        });
    }

}