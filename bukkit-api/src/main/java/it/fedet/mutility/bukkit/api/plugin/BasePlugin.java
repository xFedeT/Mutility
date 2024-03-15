package it.fedet.mutility.bukkit.api.plugin;

import it.fedet.mutility.bukkit.api.MutilityBukkitAPI;
import it.fedet.mutility.bukkit.api.dependency.DependencyChecker;
import it.fedet.mutility.bukkit.api.modules.BaseModule;
import it.fedet.mutility.bukkit.api.services.BaseService;

import java.util.List;

public abstract class BasePlugin implements Plugin {

    protected MutilityBukkitAPI mutility;

    public BasePlugin() {
        mutility = DependencyChecker.getDependency(MutilityBukkitAPI.class, "mutility");
        mutility.loadPlugin(this);

        getModules().forEach(module -> {
            mutility.loadModule(this, module);
        });

        getServices().forEach(service -> {
            mutility.loadService(this, service);
        });
    }

    public MutilityBukkitAPI getMutility() {
        return mutility;
    }

    public void enable() {
        this.onEnable();
    }

    public void disable() {
        this.onDisable();
    }


}
