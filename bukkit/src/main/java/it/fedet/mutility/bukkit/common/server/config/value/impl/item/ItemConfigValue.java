package it.fedet.mutility.common.server.config.value.impl.item;

import it.fedet.mutility.common.server.config.value.ConfigValue;
import it.fedet.mutility.common.server.item.builder.ItemBuilder;
import it.fedet.mutility.common.server.item.enchantment.FullEnchantment;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemConfigValue extends ConfigValue<ItemStack> {

    public ItemConfigValue(String path, ItemStack value) {
        super(path, value);
    }

    @Override
    protected ItemStack getFromConfig(FileConfiguration config) {
        Material material = Material.valueOf(config.getString(path + ".material"));
        int amount = config.getInt(path + ".amount");
        int data = config.getInt(path + ".data");

        ItemBuilder itemBuilder = new ItemBuilder(material, amount, data);

        String displayName = config.getString(path + ".name");
        List<String> lore = config.getStringList(path + ".lore");

        if (displayName != null) itemBuilder.setDisplayName(displayName);
        if (!lore.isEmpty()) itemBuilder.setLore(lore);

        ConfigurationSection enchantSection = config.getConfigurationSection(path + ".enchantments");
        ConfigurationSection potionSection = config.getConfigurationSection(path + ".effects");

        itemBuilder.enableUUID(false);

        if (potionSection != null && material == Material.POTION) {
            Set<PotionEffect> potionEffect = new HashSet<>();
            for (String key : potionSection.getKeys(false)) {
                String potionType = config.getString(path + ".effects." + key + ".type");
                int duration = config.getInt(path + ".effects." + key + ".duration", 60);
                int level = config.getInt(path + ".effects." + key + ".level", 1);

                potionEffect.add(new PotionEffect(PotionType.valueOf(potionType).getEffectType(), duration*20, level));
            }

            String defaultType = config.getString(path + ".effects." + 0 + ".type");
            boolean splashable = config.getBoolean(path + ".splashable", false);
            itemBuilder.createPotion(PotionType.valueOf(defaultType), potionEffect, splashable);
        }

        if (enchantSection == null) return itemBuilder.build();

        for (String key : enchantSection.getKeys(false)) {
            FullEnchantment enchant = new EnchantConfigValue(path + ".enchantments." + key, null).getFromConfig(config);
            itemBuilder.addEnchant(enchant.getEnchantment(), enchant.getLevel());
        }

        return itemBuilder.build();
    }

}