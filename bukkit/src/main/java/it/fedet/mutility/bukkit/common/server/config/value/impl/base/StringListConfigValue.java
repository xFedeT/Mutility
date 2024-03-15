package it.fedet.mutility.common.server.config.value.impl.base;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class StringListConfigValue extends ConfigValue<List<String>> {

    public StringListConfigValue(String path, List<String> value) {
        super(path, value);
    }

    @Override
    protected List<String> getFromConfig(FileConfiguration config) {
        return config.getStringList(path);
    }

}