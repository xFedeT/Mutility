package it.fedet.mutility.common.plugin;

import it.fedet.mutility.common.plugin.data.DataProvider;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BasePlugin extends JavaPlugin {

    protected LuckPerms luckPermsAPI;
    private final DataProvider<? extends BasePlugin> dataProvider = getDataProvider();
    private static final Map<Class<? extends BasePlugin>, BasePlugin> plugins = new HashMap<>();

    @Override
    public void onEnable() {
        plugins.put(this.getClass(), this);
        saveDefaultConfig();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) this.luckPermsAPI = provider.getProvider();
        else onDisable();

        dataProvider.registerConfigValues();
        enable();
        dataProvider.register();
    }

    @Override
    public void onDisable() {
        disable();
    }

    /**
     * @param plugin The Main class of the plugin that depends on MUtility
     * @return The required plugin
     */
    public Optional<BasePlugin> loadPlugin(Class<? extends BasePlugin> plugin) {
        return Optional.ofNullable(plugins.get(plugin));
    }

    /**
     * This method will be called in onEnable method
     */
    protected void enable() {
    }

    /**
     * This method will be called in onDisable method
     */
    protected void disable() {
    }

    /**
     * @return The dataProvider that contains all the values to register
     */
    protected abstract DataProvider<? extends BasePlugin> getDataProvider();

    public DataProvider<? extends BasePlugin> getData() {
        return dataProvider;
    }

    public LuckPerms getLuckPerms() {
        return luckPermsAPI;
    }

}