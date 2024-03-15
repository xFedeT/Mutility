package it.fedet.mutility.bukkit.api.dependency;

import it.fedet.mutility.bukkit.api.dependency.exception.NoDependencyFound;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class DependencyChecker {

    private DependencyChecker() {

    }

    public static <T> T getDependency(Class<T> clazz, String pluginName) {
        RegisteredServiceProvider<T> provider = Bukkit.getServicesManager().getRegistration(clazz);
        if (provider != null)
            return provider.getProvider();

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null)
            throw new NoDependencyFound(pluginName);

        return (T) plugin;
    }

    public static void registerApiProvider(JavaPlugin plugin, Class clazz) {
        Bukkit.getServicesManager().register(clazz, plugin, plugin, ServicePriority.Normal);
    }

    public static void unregisterApiProvider(JavaPlugin plugin) {
        Bukkit.getServicesManager().unregister(plugin);
    }


}
