package it.fedet.mutility.common.server.item.enchantment;

import org.bukkit.enchantments.Enchantment;

public class FullEnchantment {

    private final Enchantment enchantment;
    private final int level;

    public FullEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

}