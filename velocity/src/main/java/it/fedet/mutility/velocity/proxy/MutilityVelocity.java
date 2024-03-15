package it.fedet.mutility.velocity.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.plugin.BasePlugin;
import it.fedet.mutility.velocity.common.plugin.data.DataProvider;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "mutility-velocity",
        name = "Mutility-Velocity",
        version = "1.0-DEV",
        authors = {"xFedeT", "Granatino", "RedBoy"}
)
public class MutilityVelocity extends BasePlugin {

    public static Logger LOGGER;

    @Inject
    public MutilityVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        super(server, logger, dataDirectory);
        LOGGER = logger;
    }

    @Override
    protected void enable(ProxyInitializeEvent event) {
        PluginContainer container = server.getPluginManager().getPlugin("mutility-velocity").get();

        logger.info("");
        logger.info(" &b█▀▄▀█ █░█  &a" + container.getDescription().getName().get() + " &l&bv" + container.getDescription().getVersion().get());
        logger.info(" &b█░▀░█ █▄█  &l&8By " + container.getDescription().getAuthors().get(0));
        logger.info("");
    }

    @Override
    protected void disable(ProxyShutdownEvent event) {
        PluginContainer container = server.getPluginManager().getPlugin("mutility-velocity").get();

        logger.info("");
        logger.info(" &b█▀▄▀█ █░█  &c" + container.getDescription().getName().get() + " &l&bv" + container.getDescription().getVersion().get());
        logger.info(" &b█░▀░█ █▄█  &l&8By " + container.getDescription().getAuthors().get(0));
        logger.info("");
    }

    @Override
    protected DataProvider<? extends BasePlugin> getDataProvider() {
        return null;
    }

}