package it.fedet.mutility.common.server.config.value.impl.item;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import it.fedet.mutility.common.server.item.enchantment.FullEnchantment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

public class EnchantConfigValue extends ConfigValue<FullEnchantment> {

    public EnchantConfigValue(String path, FullEnchantment value) {
        super(path, value);
    }

    @Override
    protected FullEnchantment getFromConfig(FileConfiguration config) {
        Enchantment enchantment = Enchantment.getByName(config.getString(path + ".enchantment"));

        int level = config.getInt(path + ".level");

        return new FullEnchantment(enchantment, level);
    }

}