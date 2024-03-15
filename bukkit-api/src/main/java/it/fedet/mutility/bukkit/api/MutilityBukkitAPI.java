package it.fedet.mutility.bukkit.api;

import it.fedet.mutility.bukkit.api.console.ConsoleLogger;
import it.fedet.mutility.bukkit.api.console.Logger;
import it.fedet.mutility.bukkit.api.modules.BaseModule;
import it.fedet.mutility.bukkit.api.plugin.BasePlugin;
import it.fedet.mutility.bukkit.api.services.BaseService;

import java.util.Optional;

public interface MutilityBukkitAPI {

    Logger LOGGER = new ConsoleLogger("Mutility", true);

    <T extends BasePlugin> void loadService(BasePlugin basePlugin, BaseService<T> service);

    <P extends BasePlugin, S extends BaseService<?>> S getService(Class<P> basePlugin, Class<S> service);

    void loadPlugin(BasePlugin plugin);

    void loadModule(BasePlugin basePlugin, BaseModule baseModule);

    <T extends BasePlugin> T getBasePlugin(Class<T> clazz);

    <P extends BasePlugin, T extends BaseModule> T getBaseModule(Class<P> pluginClazz, Class<T> moduleClazz);
}
