package com.adrianwowk.bedwarsclone;


import com.sun.istack.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.nio.cs.ext.EUC_CN;

import java.util.*;

public enum CustomItems {

    // Melee
    WOODEN_SWORD(makeItemStack(Material.WOODEN_SWORD, "Wooden Sword")),
    STONE_SWORD(makeItemStack(Material.STONE_SWORD, "Stone Sword"), Material.IRON_INGOT, 10),
    IRON_SWORD(makeItemStack(Material.IRON_SWORD, "Iron Sword"), Material.GOLD_INGOT, 7),
    DIAMOND_SWORD(makeItemStack(Material.DIAMOND_SWORD, "Diamond Sword"), Material.EMERALD, 3),
    KNOCKBACK_STICK(makeItemStack(Material.STICK, "Knockback Stick", Enchantment.KNOCKBACK, 2), Material.GOLD_INGOT, 5),

    // Tools
    SHEARS(makeItemStack(Material.SHEARS, "Shears"), Material.IRON_INGOT, 20),

    WOODEN_PICKAXE(makeItemStack(Material.WOODEN_PICKAXE, "Wooden Pickaxe", Enchantment.DIG_SPEED, 1), Material.IRON_INGOT, 10),
    IRON_PICKAXE(makeItemStack(Material.IRON_PICKAXE, "Iron Pickaxe", Enchantment.DIG_SPEED, 2), Material.IRON_INGOT, 10),
    GOLDEN_PICKAXE(makeItemStack(Material.GOLDEN_PICKAXE, "Golden Pickaxe", Enchantment.DIG_SPEED, 3), Material.GOLD_INGOT, 3),
    DIAMOND_PICKAXE(makeItemStack(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", Enchantment.DIG_SPEED, 3), Material.GOLD_INGOT, 6),

    WOODEN_AXE(makeItemStack(Material.WOODEN_AXE, "Wooden Axe", Enchantment.DIG_SPEED, 1), Material.IRON_INGOT, 10),
    STONE_AXE(makeItemStack(Material.STONE_AXE, "Stone Axe", Enchantment.DIG_SPEED, 1), Material.IRON_INGOT, 10),
    IRON_AXE(makeItemStack(Material.IRON_AXE, "Iron Axe", Enchantment.DIG_SPEED, 2), Material.GOLD_INGOT, 3),
    DIAMOND_AXE(makeItemStack(Material.DIAMOND_AXE, "Diamond Axe", Enchantment.DIG_SPEED, 3), Material.GOLD_INGOT, 6),

    // Ranged
    BOW(makeItemStack(Material.BOW, "Bow"), Material.GOLD_INGOT, 12),
    POWER_BOW(makeItemStack(Material.BOW, "Bow", Enchantment.ARROW_DAMAGE, 1), Material.GOLD_INGOT, 24),
    PUNCH_BOW(makeItemStack(Material.BOW, "Bow", Enchantment.ARROW_DAMAGE, 1, Enchantment.ARROW_KNOCKBACK, 1), Material.EMERALD, 6),
    ARROW(makeItemStack(Material.ARROW, "Arrow"), Material.GOLD_INGOT, 2),

    //Blocks
    WOOL(makeItemStack(Material.WHITE_WOOL, "Wool"), Material.IRON_INGOT, 4),
    HARDENED_CLAY(makeItemStack(Material.TERRACOTTA, "Hardened Clay"), Material.IRON_INGOT, 12),
    BLAST_PROOF_GLASS(makeItemStack(Material.LIGHT_GRAY_STAINED_GLASS, "Blast Proof Glass"), Material.IRON_INGOT, 12),
    END_STONE(makeItemStack(Material.END_STONE, "End Stone"), Material.IRON_INGOT, 24),
    LADDER(makeItemStack(Material.LADDER, "Ladder"), Material.IRON_INGOT, 4),
    OAK_PLANKS(makeItemStack(Material.OAK_PLANKS, "Oak Wood Planks"), Material.GOLD_INGOT, 4),
    OBSIDIAN(makeItemStack(Material.OBSIDIAN, "Obsidian"), Material.EMERALD, 4),

    // Armor
    CHAINMAIL_ARMOR(makeItemStack(Material.CHAINMAIL_BOOTS, "Permanent Chainmail Armor"), Material.IRON_INGOT, 40),
    IRON_ARMOR(makeItemStack(Material.IRON_BOOTS, "Permanent Iron Armor"), Material.GOLD_INGOT, 12),
    DIAMOND_ARMOR(makeItemStack(Material.DIAMOND_BOOTS, "Permanent Diamond Armor"), Material.EMERALD, 6),

    // Potions
    SPEED(makeItemStack(Material.POTION, "Speed 2 - 45 seconds", PotionEffectType.SPEED, 45, 1, Color.AQUA), Material.EMERALD, 1),
    JUMP(makeItemStack(Material.POTION, "Jump Boost 5 - 45 seconds", PotionEffectType.JUMP, 45, 4, Color.LIME), Material.EMERALD, 1),
    INVIS(makeItemStack(Material.POTION, "Invisibility - 30 seconds", PotionEffectType.INVISIBILITY, 30, 0, Color.GRAY), Material.EMERALD, 2),

    // Utilis
    GOLDEN_APPLE(makeItemStack(Material.GOLDEN_APPLE, "Golden Apple"), Material.GOLD_INGOT, 3),
    BEDBUG(makeItemStack(Material.SNOWBALL, "Bedbug"), Material.IRON_INGOT, 40),
    DREAM_DEFENDER(makeItemStack(Material.GHAST_SPAWN_EGG, "Dream Defender"), Material.IRON_INGOT, 120),
    FIREBALL(makeItemStack(Material.FIRE_CHARGE, "Fireball"), Material.IRON_INGOT, 40),
    TNT(makeItemStack(Material.TNT, "TNT"), Material.GOLD_INGOT, 4),
    ENDER_PEARL(makeItemStack(Material.ENDER_PEARL, "Ender Pearl"), Material.EMERALD, 4),
    WATER_BUCKET(makeItemStack(Material.WATER_BUCKET, "Water Bucket"), Material.GOLD_INGOT, 6),
    BRIDGE_EGG(makeItemStack(Material.EGG, "Bridge Egg"), Material.EMERALD, 2),
    MAGIC_MILK(makeItemStack(Material.MILK_BUCKET, "Magic Milk"), Material.GOLD_INGOT, 4),
    SPONGE(makeItemStack(Material.SPONGE, "Sponge"), Material.GOLD_INGOT, 6),
    COMPACT_POP_UP_TOWER(makeItemStack(Material.CHEST, "Compact Pop Up Tower"), Material.IRON_INGOT, 24),



    // Categories
    QUICK_BUY(makeItemStack(Material.NETHER_STAR, "Quick Buy")),
    BLOCKS(makeItemStack(Material.WHITE_WOOL, "Blocks")),
    MELEE(makeItemStack(Material.GOLDEN_SWORD, "Melee")),
    ARMOR(makeItemStack(Material.CHAINMAIL_BOOTS, "Armor")),
    TOOLS(makeItemStack(Material.STONE_PICKAXE, "Tools")),
    RANGED(makeItemStack(Material.BOW, "Ranged")),
    POTIONS(makeItemStack(Material.POTION, "Potions")),
    UTILS(makeItemStack(Material.TNT, "Utilities"));

    private final ItemStack item;
    private final Material priceItem;
    private final int amount;

    CustomItems(ItemStack value, Material p, int a) {
        this.item = value;
        this.priceItem = p;
        this.amount = a;
    }

    CustomItems(ItemStack value) {
        this.item = value;
        this.priceItem = null;
        this.amount = 0;
    }

    public ItemStack getItem() {
        return item;
    }

    public Material getPriceItem() {
        return priceItem;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getItem(int count) {

        ItemStack newItem = item.clone();
        newItem.setAmount(count);
        return newItem;
    }

    private static ItemStack makeItemStack(Material mat, String name) {
        ItemStack item = new ItemStack(mat, 1);

        ItemMeta itemLabel = item.getItemMeta();
        assert itemLabel != null;
        itemLabel.setDisplayName(ChatColor.GOLD + name);
        itemLabel.setUnbreakable(true);
        itemLabel.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(itemLabel);

        return item;
    }

    private static ItemStack makeItemStack(Material mat, String name, Enchantment ench, int lvl) {
        ItemStack item = new ItemStack(mat, 1);

        if (ench != null) {
            item.addUnsafeEnchantment(ench, lvl);
        }
        ItemMeta itemLabel = item.getItemMeta();
        assert itemLabel != null;
        itemLabel.setDisplayName(ChatColor.GOLD + name);
        itemLabel.setUnbreakable(true);
        itemLabel.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(itemLabel);

        return item;
    }

    private static ItemStack makeItemStack(Material mat, String name, Enchantment ench, int lvl, Enchantment ench2, int lvl2) {
        ItemStack item = new ItemStack(mat, 1);

        if (ench != null) {
            item.addUnsafeEnchantment(ench, lvl);
        }

        if (ench2 != null) {
            item.addUnsafeEnchantment(ench2, lvl2);
        }

        ItemMeta itemLabel = item.getItemMeta();
        assert itemLabel != null;
        itemLabel.setDisplayName(ChatColor.GOLD + name);
        itemLabel.setUnbreakable(true);
        itemLabel.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(itemLabel);

        return item;
    }

    private static ItemStack makeItemStack(Material mat, String name, PotionEffectType effectType, int duration, int amplifier, Color color) {
        ItemStack potion = new ItemStack(mat, 1);

        PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
        PotionEffect effect = new PotionEffect(effectType, duration * 20, amplifier);
        potionmeta.addCustomEffect(effect, true);
        potionmeta.setDisplayName(ChatColor.GOLD + name);
        potionmeta.setColor(color);
        potion.setItemMeta(potionmeta);

        return potion;
    }
}
