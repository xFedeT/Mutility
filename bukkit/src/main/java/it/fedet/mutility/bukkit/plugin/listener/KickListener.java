package it.fedet.mutility.bukkit.plugin.listener;

import it.fedet.mutility.common.server.listener.RegistrableListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements RegistrableListener {

    @EventHandler
    public void onDisconnectSpam(PlayerKickEvent event) {
        if(event.getReason().equalsIgnoreCase("disconnect.spam")) event.setCancelled(true);
    }
}
