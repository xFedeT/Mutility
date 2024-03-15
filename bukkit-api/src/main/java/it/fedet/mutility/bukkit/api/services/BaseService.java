package it.fedet.mutility.bukkit.api.services;

import it.fedet.mutility.bukkit.api.plugin.BasePlugin;

public abstract class BaseService<T extends BasePlugin> implements Service {

    protected final T plugin;

    protected BaseService(T plugin) {
        this.plugin = plugin;
    }

}
