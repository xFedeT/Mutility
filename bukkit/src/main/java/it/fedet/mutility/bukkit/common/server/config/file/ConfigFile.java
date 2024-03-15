package it.fedet.mutility.common.server.config.file;

import it.fedet.mutility.common.server.chat.ConsoleLogger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private final Plugin plugin;
    private final File file;
    private final FileConfiguration config;
    private final String fileName;

    public ConfigFile(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;

        file = new File(plugin.getDataFolder(), fileName);
        config = new YamlConfiguration();

        load();
    }

    public void load() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
            return;
        }

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            ConsoleLogger.error("Error while loading " + file.getName(), exception);
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException exception) {
            ConsoleLogger.error("Error while saving " + file.getName(), exception);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}