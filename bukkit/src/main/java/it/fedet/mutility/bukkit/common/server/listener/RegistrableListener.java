package it.fedet.mutility.common.server.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface RegistrableListener extends Listener {

    default void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}