package it.fedet.mutility.velocity.common.plugin;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.plugin.data.DataProvider;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class BasePlugin {

    protected final ProxyServer server;
    protected final Logger logger;
    protected final Path dataDirectory;

    private final DataProvider<? extends BasePlugin> dataProvider;

    protected BasePlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;

        dataProvider = getDataProvider();
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        //saveDefaultConfig();

        //dataProvider.registerConfigValues();
        enable(event);
        if (dataProvider != null) dataProvider.register();
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        disable(event);
    }

    /**
     * This method will be called in ProxyInitialize event
     */
    protected void enable(ProxyInitializeEvent event) {
    }

    /**
     * This method will be called in ProxyShutdown event
     */
    protected void disable(ProxyShutdownEvent event) {
    }

    /**
     * @return The dataProvider that contains all the values to register
     */
    protected abstract DataProvider<? extends BasePlugin> getDataProvider();

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

}