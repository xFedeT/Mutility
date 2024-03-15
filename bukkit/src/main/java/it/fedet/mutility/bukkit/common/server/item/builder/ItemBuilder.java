package it.fedet.mutility.common.server.item.builder;

import it.fedet.mutility.common.server.item.nbt.ItemUUID;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack item;
    private ItemMeta meta;
    private boolean enableUUID = true;
    private UnaryOperator<ItemStack> customize = t -> t;

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(Material material, int amount, int data) {
        this(new ItemStack(material, amount, (short) data));
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public ItemBuilder setCustomize(UnaryOperator<ItemStack> customize) {
        this.customize = customize;
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        if (material != null) item.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        if (amount > 0 && amount < 65) item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        if (name != null) meta.setDisplayName(Textify.colorize(name));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        return setLore(lore.toArray(new String[0]));
    }

    public ItemBuilder setLore(String... lore) {
        if (lore.length > 0) meta.setLore(Arrays.stream(lore)
                .map(Textify::colorize)
                .collect(Collectors.toList())
        );
        return this;
    }

    public ItemBuilder setLore(String line, int pos) {
        if (pos >= meta.getLore().size() || pos < 0) return this;

        meta.getLore().set(pos, line);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        List<String> metaLore = meta.getLore();
        metaLore.addAll(Arrays.stream(lore).toList());

        meta.setLore(metaLore);

        return this;
    }

    public List<String> getLore() {
        return meta.getLore();
    }

    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor color) {
        if (!item.getType().equals(Material.INK_SACK)) return this;

        return setDurability((short) (15 - color.ordinal()));
    }

    public ItemBuilder createPotion(PotionType type, Set<PotionEffect> potionEffects, boolean splashable) {
        if (item.getType() != Material.POTION)
            return this;

        if (splashable) {
            Potion potion = new Potion(type);

            potion.getEffects().clear();
            for (PotionEffect potionEffect : potionEffects)
                potion.getEffects().add(potionEffect);

            potion.setSplash(true);
            potion.apply(item);
            return this;
        }

        PotionMeta potionMeta = (PotionMeta) meta;

        for (PotionEffect potionEffect : potionEffects)
            potionMeta.addCustomEffect(potionEffect, true);

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta enchantMeta = (EnchantmentStorageMeta) meta;
            enchantMeta.addStoredEnchant(enchant, level, true);
            return this;
        }

        meta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchant) {
        meta.removeEnchant(enchant);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setData(byte data) {
        MaterialData materialData = item.getData();
        materialData.setData(data);
        item.setData(materialData);

        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(color);
        meta = leatherArmorMeta;

        return this;
    }

    public ItemBuilder setPlayerSkull(String playerName) {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwner(playerName);
        meta = skullMeta;

        return this;
    }

    public ItemBuilder enableUUID(boolean enable) {
        this.enableUUID = enable;

        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return enableUUID ? customize.apply(ItemUUID.generate(item)) : item;
    }

    public static ItemBuilder clone(ItemStack item) {
        return new ItemBuilder(item.clone());
    }

    public static ItemBuilder builder(ItemStack item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder builder(Material material, int amount, int data) {
        return new ItemBuilder(material, amount, (short) data);
    }

    public static ItemBuilder builder(Material material, int amount) {
        return builder(material, amount, (short) 0);
    }

}