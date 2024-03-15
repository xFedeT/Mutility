package it.fedet.mutility.common.server.config.value.impl.base;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import org.bukkit.configuration.file.FileConfiguration;

public class IntegerConfigValue extends ConfigValue<Integer> {

    public IntegerConfigValue(String path, Integer value) {
        super(path, value);
    }

    @Override
    protected Integer getFromConfig(FileConfiguration config) {
        return config.getInt(path);
    }

}