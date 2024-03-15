package it.fedet.mutility.common.server.staffmode.listener;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import it.fedet.mutility.common.server.staffmode.playermode.godmode.GodMode;
import it.fedet.mutility.common.server.staffmode.playermode.staffmode.StaffMode;
import it.fedet.mutility.common.server.staffmode.playermode.vanish.Vanish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.event.EventPriority.HIGHEST;

public class GeneralListener implements RegistrableListener {

    private final Mutility plugin;

    public GeneralListener(Mutility plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        StaffMode.Listener.INVENTORY_CLICK.accept(event, plugin);
    }

    @EventHandler(priority = HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Vanish.Listener.INVENTORY_OPEN.accept(event, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        GodMode.Listener.ON_ENTITY_DAMAGE.accept(event, plugin);
    }

    @EventHandler(priority = HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        StaffMode.Listener.JOIN.accept(event, plugin);
    }

    @EventHandler(priority = HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        StaffMode.Listener.QUIT.accept(event, plugin);
    }

    @EventHandler(priority = HIGHEST)
    public void onPickupItem(PlayerPickupItemEvent event) {
        StaffMode.Listener.PICKUP_ITEM.accept(event, plugin);
    }

}