package it.fedet.mutility.bukkit.api.plugin;

import it.fedet.mutility.bukkit.api.modules.BaseModule;
import it.fedet.mutility.bukkit.api.services.BaseService;

import java.util.List;

public interface Plugin {

    void onEnable();

    void onDisable();

    <T extends BaseModule> List<T> getModules();

    <T extends BaseService<BasePlugin>> List<T> getServices();


}
