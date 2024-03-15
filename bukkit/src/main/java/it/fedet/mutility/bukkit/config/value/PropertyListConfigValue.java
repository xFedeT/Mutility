package it.fedet.mutility.bukkit.config.value;

import com.mojang.authlib.properties.Property;
import it.fedet.mutility.common.server.config.value.ConfigValue;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PropertyListConfigValue extends ConfigValue<List<Property>> {

    public PropertyListConfigValue(String path, List<Property> value) {
        super(path, value);
    }

    @Override
    protected List<Property> getFromConfig(FileConfiguration config) {
        List<Property> properties = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection(path);

        if (section == null) return value;

        for (String key : section.getKeys(false)) {
            String texture = config.getString(path + "." + key + ".texture");
            String signature = config.getString(path + "." + key + ".signature");

            properties.add(new Property("textures",
                    texture,
                    signature
            ));
        }

        return properties;
    }
}
