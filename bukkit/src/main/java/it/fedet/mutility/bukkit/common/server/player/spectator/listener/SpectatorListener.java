package it.fedet.mutility.common.server.player.spectator.listener;

import it.fedet.mutility.common.server.listener.RegistrableListener;
import it.fedet.mutility.common.server.player.spectator.SpectatorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import static it.fedet.mutility.common.server.player.spectator.SpectatorUtil.isSpectator;

public class SpectatorListener implements RegistrableListener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!isSpectator((Player) event.getEntity())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!isSpectator((Player) event.getDamager())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!isSpectator((Player) event.getEntity())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        if (!isSpectator(event.getPlayer())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (!isSpectator(player)) return;
        if (player.getSpectatorTarget() == null) return;

        SpectatorUtil.spectateOff(player);

        event.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) return;

        Player player = event.getPlayer();
        if (!isSpectator(player)) return;

        SpectatorUtil.spectateOff(player);

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        event.setCancelled(isSpectator((Player) event.getWhoClicked()));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(isSpectator(event.getPlayer()));
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(isSpectator(event.getPlayer()));
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!SpectatorUtil.isSpectator(player)) return;

        if (!(event.getRightClicked() instanceof Player)) return;
        SpectatorUtil.spectateOn(player, (Player) event.getRightClicked());
    }

}