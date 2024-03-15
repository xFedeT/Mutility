package it.fedet.mutility.common.server.config.value.impl.base;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.configuration.file.FileConfiguration;

public class ColoredStringConfigValue extends ConfigValue<String> {
    public ColoredStringConfigValue(String path, String value) {
        super(path, value);
    }

    @Override
    protected String getFromConfig(FileConfiguration config) {
        return Textify.colorize(config.getString(path));
    }
}
