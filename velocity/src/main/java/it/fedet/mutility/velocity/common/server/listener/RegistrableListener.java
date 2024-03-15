package it.fedet.mutility.velocity.common.server.listener;

import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.plugin.BasePlugin;

public interface RegistrableListener {

    default void register(BasePlugin plugin, ProxyServer server) {
        server.getEventManager().register(plugin, this);
    }

}