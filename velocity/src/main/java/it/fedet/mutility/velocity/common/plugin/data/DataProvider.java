package it.fedet.mutility.velocity.common.plugin.data;

import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.plugin.BasePlugin;
import it.fedet.mutility.velocity.common.server.command.RegistrableCommand;
import it.fedet.mutility.velocity.common.server.listener.RegistrableListener;

import java.util.Collections;
import java.util.List;

public abstract class DataProvider<T extends BasePlugin> {

    protected T plugin;
    protected ProxyServer server;

    protected DataProvider(ProxyServer server, T plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    /**
     * Register InteractItems, InteractGuis, Commands and Listeners returned by special methods
     */
    public void register() {

        registerCommands();
        registerListeners();
    }

    /**
     * Register ConfigValues returned by getConfigValues method
     */
    /*public void registerConfigValues() {
        getConfigValues().forEach(ConfigValue::loadValues);
    }*/

    /**
     * Register Commands returned by getCommands method
     */
    private void registerCommands() {
        getCommands().forEach(command -> command.register(server));
    }

    /**
     * Register Listeners returned by getListeners method
     */
    private void registerListeners() {
        getListeners().forEach(listener -> listener.register(plugin, server));
    }

    /**
     * @return The map of Class (that contains ConfigValues) and FileConfiguration (from which plugin will get the values)
     */
    /*public Map<Class<?>, FileConfiguration> getConfigValues() {
        return Collections.emptyMap();
    }*/

    /**
     * @return The list of commands to register
     */
    public List<RegistrableCommand> getCommands() {
        return Collections.emptyList();
    }

    /**
     * @return The list of listeners to register
     */
    public List<RegistrableListener> getListeners() {
        return Collections.emptyList();
    }

}