package it.fedet.mutility.common.server.config.value.impl.base;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import org.bukkit.configuration.file.FileConfiguration;

public class StringConfigValue extends ConfigValue<String> {

    public StringConfigValue(String path, String value) {
        super(path, value);
    }

    @Override
    protected String getFromConfig(FileConfiguration config) {
        return config.getString(path);
    }

}