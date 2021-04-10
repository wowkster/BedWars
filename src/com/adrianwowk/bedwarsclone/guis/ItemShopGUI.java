package com.adrianwowk.bedwarsclone.guis;


import com.adrianwowk.bedwarsclone.CustomItems;
import com.adrianwowk.bedwarsclone.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class ItemShopGUI implements Listener, InventoryHolder {
    private  Inventory mainInv;
    private  Inventory blocksInv;
    private  Inventory meleeInv;
    private  Inventory armorInv;
    private  Inventory toolsInv;
    private  Inventory rangedInv;
    private  Inventory potsInv;
    private  Inventory utilsInv;


    public ItemShopGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory)
        mainInv = Bukkit.createInventory(this, 54, "Item Shop");
        blocksInv = Bukkit.createInventory(this, 54, "Item Shop");
        meleeInv = Bukkit.createInventory(this, 54, "Item Shop");
        armorInv = Bukkit.createInventory(this, 54, "Item Shop");
        toolsInv = Bukkit.createInventory(this, 54, "Item Shop");
        rangedInv = Bukkit.createInventory(this, 54, "Item Shop");
        potsInv = Bukkit.createInventory(this, 54, "Item Shop");
        utilsInv = Bukkit.createInventory(this, 54, "Item Shop");


        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {

        setHeader(mainInv);
        setHeader(blocksInv);
        setHeader(meleeInv);
        setHeader(armorInv);
        setHeader(toolsInv);
        setHeader(rangedInv);
        setHeader(potsInv);
        setHeader(utilsInv);

        mainInv.setItem(19, CustomItems.WOOL.getItem(16));
        mainInv.setItem(20, CustomItems.STONE_SWORD.getItem());
        mainInv.setItem(21, CustomItems.CHAINMAIL_ARMOR.getItem());
        mainInv.setItem(22, CustomItems.FIREBALL.getItem());
        mainInv.setItem(23, CustomItems.BOW.getItem());
        mainInv.setItem(24, CustomItems.SPEED.getItem());
        mainInv.setItem(25, CustomItems.TNT.getItem());
        mainInv.setItem(28, CustomItems.OAK_PLANKS.getItem(16));
        mainInv.setItem(29, CustomItems.IRON_SWORD.getItem());
        mainInv.setItem(30, CustomItems.IRON_ARMOR.getItem());
        mainInv.setItem(31, CustomItems.COMPACT_POP_UP_TOWER.getItem());
        mainInv.setItem(32, CustomItems.ARROW.getItem(8));
        mainInv.setItem(33, CustomItems.INVIS.getItem());
        mainInv.setItem(34, CustomItems.GOLDEN_APPLE.getItem());
        mainInv.setItem(37, CustomItems.END_STONE.getItem(12));
        mainInv.setItem(38, CustomItems.WOODEN_PICKAXE.getItem());
        mainInv.setItem(39, CustomItems.WOODEN_AXE.getItem());
        mainInv.setItem(40, CustomItems.SHEARS.getItem());
        mainInv.setItem(41, CustomItems.OBSIDIAN.getItem(4));
        mainInv.setItem(42, CustomItems.BLAST_PROOF_GLASS.getItem(4));

        blocksInv.setItem(19, CustomItems.WOOL.getItem(16));
        blocksInv.setItem(20, CustomItems.HARDENED_CLAY.getItem(16));
        blocksInv.setItem(21, CustomItems.BLAST_PROOF_GLASS.getItem(4));
        blocksInv.setItem(22, CustomItems.END_STONE.getItem(12));
        blocksInv.setItem(23, CustomItems.LADDER.getItem(12));
        blocksInv.setItem(24, CustomItems.OAK_PLANKS.getItem(16));
        blocksInv.setItem(25, CustomItems.OBSIDIAN.getItem(4));

        meleeInv.setItem(19, CustomItems.STONE_SWORD.getItem());
        meleeInv.setItem(20, CustomItems.IRON_SWORD.getItem());
        meleeInv.setItem(21, CustomItems.DIAMOND_SWORD.getItem());
        meleeInv.setItem(22, CustomItems.KNOCKBACK_STICK.getItem());

        armorInv.setItem(19, CustomItems.CHAINMAIL_ARMOR.getItem());
        armorInv.setItem(20, CustomItems.IRON_ARMOR.getItem());
        armorInv.setItem(21, CustomItems.DIAMOND_ARMOR.getItem());

        toolsInv.setItem(19, CustomItems.SHEARS.getItem());
        toolsInv.setItem(28, CustomItems.WOODEN_PICKAXE.getItem());
        toolsInv.setItem(29, CustomItems.IRON_PICKAXE.getItem());
        toolsInv.setItem(30, CustomItems.GOLDEN_PICKAXE.getItem());
        toolsInv.setItem(31, CustomItems.DIAMOND_PICKAXE.getItem());
        toolsInv.setItem(37, CustomItems.WOODEN_AXE.getItem());
        toolsInv.setItem(38, CustomItems.STONE_AXE.getItem());
        toolsInv.setItem(39, CustomItems.IRON_AXE.getItem());
        toolsInv.setItem(40, CustomItems.DIAMOND_AXE.getItem());

        rangedInv.setItem(19, CustomItems.ARROW.getItem(8));
        rangedInv.setItem(20, CustomItems.BOW.getItem());
        rangedInv.setItem(21, CustomItems.POWER_BOW.getItem());
        rangedInv.setItem(22, CustomItems.PUNCH_BOW.getItem());

        potsInv.setItem(19, CustomItems.SPEED.getItem());
        potsInv.setItem(20, CustomItems.JUMP.getItem());
        potsInv.setItem(21, CustomItems.INVIS.getItem());

        utilsInv.setItem(19, CustomItems.GOLDEN_APPLE.getItem());
        utilsInv.setItem(20, CustomItems.BEDBUG.getItem());
        utilsInv.setItem(21, CustomItems.DREAM_DEFENDER.getItem());
        utilsInv.setItem(22, CustomItems.FIREBALL.getItem());
        utilsInv.setItem(23, CustomItems.TNT.getItem());
        utilsInv.setItem(24, CustomItems.ENDER_PEARL.getItem());
        utilsInv.setItem(25, CustomItems.WATER_BUCKET.getItem());
        utilsInv.setItem(28, CustomItems.BRIDGE_EGG.getItem());
        utilsInv.setItem(29, CustomItems.MAGIC_MILK.getItem());
        utilsInv.setItem(30, CustomItems.SPONGE.getItem());
        utilsInv.setItem(31, CustomItems.COMPACT_POP_UP_TOWER.getItem());

    }

    private void setHeader(Inventory inv) {
        inv.setItem(0, CustomItems.QUICK_BUY.getItem());
        inv.setItem(1, CustomItems.BLOCKS.getItem());
        inv.setItem(2, CustomItems.MELEE.getItem());
        inv.setItem(3, CustomItems.ARMOR.getItem());
        inv.setItem(4, CustomItems.TOOLS.getItem());
        inv.setItem(5, CustomItems.RANGED.getItem());
        inv.setItem(6, CustomItems.POTIONS.getItem());
        inv.setItem(7, CustomItems.UTILS.getItem());
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent, Inventory inventory) {
        ent.openInventory(inventory);
    }

    public Inventory getMainInv() {
        return mainInv;
    }
    public Inventory getBlocksInv() {
        return blocksInv;
    }
    public Inventory getMeleeInv() {
        return meleeInv;
    }
    public Inventory getArmorInv() {
        return armorInv;
    }
    public Inventory getToolsInv() {
        return toolsInv;
    }
    public Inventory getRangedInv() {
        return rangedInv;
    }
    public Inventory getPotsInv() {
        return potsInv;
    }
    public Inventory getUtilsInv() {
        return utilsInv;
    }

    public void setToolTiers(GamePlayer gp){

    }


    public boolean guiIsSimilar(Inventory i) {
        return (i == mainInv || i == blocksInv || i == meleeInv || i == armorInv || i == toolsInv || i == rangedInv || i == potsInv || i == utilsInv);
    }

    @Override
    public Inventory getInventory() {
        System.out.println("Using Get Inventory");
        return null;
    }
}

