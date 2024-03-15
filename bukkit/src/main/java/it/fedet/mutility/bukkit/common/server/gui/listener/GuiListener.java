package it.fedet.mutility.common.server.gui.listener;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.gui.InteractGui;
import it.fedet.mutility.common.server.gui.item.GuiActionItem;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.function.Consumer;

public class GuiListener<T extends BasePlugin> implements RegistrableListener {

    private final Map<String, InteractGui<T>> interactGuis;

    public GuiListener(Map<String, InteractGui<T>> interactGuis) {
        this.interactGuis = interactGuis;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        InteractGui<T> interactGui = interactGuis.get(event.getClickedInventory().getTitle());
        if (interactGui == null) return;

        if (interactGui.cancelEvent()) event.setCancelled(true);

        GuiActionItem item = interactGui.getGuiItems().get(event.getSlot());

        if (item == null) return;
        item.getAction().accept(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        InteractGui<T> interactGui = interactGuis.get(event.getInventory().getTitle());
        if (interactGui == null) return;

        Consumer<InventoryCloseEvent> onClose = interactGui.onClose();
        if (onClose != null) onClose.accept(event);

        if (interactGui.removeOnClose()) interactGuis.remove(interactGui.getTitle());
    }

}