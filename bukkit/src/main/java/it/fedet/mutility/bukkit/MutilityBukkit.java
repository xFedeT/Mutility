package it.fedet.mutility.bukkit;

import it.fedet.mutility.bukkit.api.MutilityBukkitAPI;
import it.fedet.mutility.bukkit.api.dependency.DependencyChecker;
import it.fedet.mutility.bukkit.api.modules.BaseModule;
import it.fedet.mutility.bukkit.api.plugin.BasePlugin;
import it.fedet.mutility.bukkit.api.services.BaseService;
import it.fedet.mutility.bukkit.services.ModuleService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class MutilityBukkit extends JavaPlugin implements MutilityBukkitAPI {
    private final Map<Class<? extends BasePlugin>, Map<Class<? extends BaseService<? extends BasePlugin>>, BaseService<?>>> loadedServices = new LinkedHashMap<>();
    private final Map<Class<? extends BasePlugin>, Map<Class<? extends BaseModule>, BaseModule>> loadedModules = new LinkedHashMap<>();
    private final Map<Class<? extends BasePlugin>, BasePlugin> loadedPlugins = new HashMap<>();

    private ModuleService moduleService;

    public void onEnable() {
        MutilityBukkitAPI mutility = DependencyChecker.getDependency(MutilityBukkitAPI.class, "mutility");
        this.moduleService = new ModuleService(this);
    }


    public void onDisable() {

    }

    @Override
    public <T extends BasePlugin> T getBasePlugin(Class<T> clazz) {
        return (T) loadedPlugins.get(clazz);
    }

    @Override
    public void loadPlugin(BasePlugin basePlugin) {
        try {
            basePlugin.enable();
            loadedPlugins.put(basePlugin.getClass(), basePlugin);
            LOGGER.info("Loading Plugin " + basePlugin.getClass().getSimpleName() + " in Mutility...");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Cannot load Plugin: " + basePlugin.getClass().getSimpleName());
            LOGGER.info("Plugin shutdown...");
            basePlugin.disable();
        }
    }


    @Override
    public <P extends BasePlugin, S extends BaseService<?>> S getService(Class<P> basePlugin, Class<S> service) {
        return (S) loadedServices.get(basePlugin).get(service);
    }

    @Override
    public <T extends BasePlugin> void loadService(BasePlugin basePlugin, BaseService<T> service) {
        try {
            service.start();
            loadedServices.get(basePlugin.getClass()).put((Class<? extends BaseService<? extends BasePlugin>>) service.getClass(), service);

            LOGGER.info("Loading Service " + service.getClass().getSimpleName() + " in Mutility...");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Cannot load Service: " + service.getClass().getSimpleName());
            LOGGER.info("Service shutdown...");
            Bukkit.shutdown();
        }
    }

    @Override
    public <P extends BasePlugin, T extends BaseModule> T getBaseModule(Class<P> pluginClazz, Class<T> moduleClazz) {
        return (T) loadedModules.get(pluginClazz).get(moduleClazz);
    }

    @Override
    public void loadModule(BasePlugin basePlugin, BaseModule baseModule) {
        try {
            loadedModules.put(basePlugin.getClass(), Map.of(baseModule.getClass(), baseModule));
            LOGGER.info("Loading Module " + basePlugin.getClass().getSimpleName() + " in Mutility...");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Cannot load Module: " + baseModule.getClass().getSimpleName());
            LOGGER.info("Module shutdown...");
        }
    }

    public ModuleService getModuleService() {
        return moduleService;
    }


}
