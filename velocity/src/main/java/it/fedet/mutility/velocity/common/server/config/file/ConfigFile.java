package it.fedet.mutility.velocity.common.server.config.file;

import it.fedet.mutility.velocity.common.plugin.BasePlugin;

import java.io.File;

public class ConfigFile {

    private final BasePlugin plugin;
    private final File file;

    public ConfigFile(BasePlugin plugin, String fileName) {
        this.plugin = plugin;

        file = new File(plugin.getDataDirectory().toAbsolutePath().toString(), fileName + ".yml");
        //config = new YamlConfiguration();

        //load();
    }

    /*public void load() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(config.getCurrentPath(), false);
            return;
        }

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            ChatLogger.error("Error while loading " + file.getName(), exception);
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException exception) {
            ChatLogger.error("Error while saving " + file.getName(), exception);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }*/

}