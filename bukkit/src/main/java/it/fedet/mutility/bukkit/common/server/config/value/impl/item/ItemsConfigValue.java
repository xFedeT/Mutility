package it.fedet.mutility.common.server.config.value.impl.item;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemsConfigValue extends ConfigValue<List<ItemStack>> {

    public ItemsConfigValue(String path, List<ItemStack> value) {
        super(path, value);
    }

    @Override
    protected List<ItemStack> getFromConfig(FileConfiguration config) {
        List<ItemStack> items = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection(path);

        if (section == null) return items;

        for (String key : section.getKeys(false)) {
            ConfigValue<ItemStack> item = new ItemConfigValue(path + "." + key, null);
            item.load(config);

            items.add(item.get());
        }

        return items;
    }

}