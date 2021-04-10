package com.adrianwowk.bedwarsclone.events;

import com.adrianwowk.bedwarsclone.*;
import com.adrianwowk.bedwarsclone.guis.ItemShopGUI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.ArrayList;

public class BedWarsEvents implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        if (!(e.getClickedInventory().getHolder() instanceof ItemShopGUI)) {
            System.out.println("Not instanceof ItemShop");
            return;
        }

        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            e.setCancelled(true);
            ((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1.0f, 1.0f);
            return;
        }

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        int amountClicked = e.getClickedInventory().getItem(e.getSlot()).getAmount();

        ItemShopGUI gui = new ItemShopGUI();

        if (clickedItem.isSimilar(CustomItems.BLOCKS.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getBlocksInv());
        } else if (clickedItem.isSimilar(CustomItems.MELEE.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getMeleeInv());
        } else if (clickedItem.isSimilar(CustomItems.ARMOR.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getArmorInv());
        } else if (clickedItem.isSimilar(CustomItems.TOOLS.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getToolsInv());
        } else if (clickedItem.isSimilar(CustomItems.RANGED.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getRangedInv());
        } else if (clickedItem.isSimilar(CustomItems.POTIONS.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getPotsInv());
        } else if (clickedItem.isSimilar(CustomItems.UTILS.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getUtilsInv());
        } else if (clickedItem.isSimilar(CustomItems.QUICK_BUY.getItem())) {
            gui.openInventory(e.getWhoClicked(), gui.getMainInv());
        } else {
            for (CustomItems item : CustomItems.values()) {
                if (clickedItem.isSimilar(item.getItem())) {
                    if (p.getInventory().containsAtLeast(new ItemStack(item.getPriceItem()), item.getAmount())) {

                        boolean success = true;

                        GamePlayer gp = BedWars.game.getGamePlayerFromPlayer(p);

                        // Check if Clicked Item is Armor, and if so, then set their armor to that
                        if (item == CustomItems.CHAINMAIL_ARMOR || item == CustomItems.IRON_ARMOR || item == CustomItems.DIAMOND_ARMOR) {
                            gp.setArmorTier(GamePlayer.getTierFromItem(item));
                            p.getInventory().setBoots(GamePlayer.ArmorTier.getBoots(gp));
                            p.getInventory().setLeggings(GamePlayer.ArmorTier.getLeggings(gp));

                            // Check If they bought a colored building block, and if so, than give them the correct color
                        } else if (item == CustomItems.WOOL) {
                            p.getInventory().addItem(BedWars.game.getTeamOfPlayer(p).getTeamColor().getWool(amountClicked));
                        } else if (item == CustomItems.HARDENED_CLAY) {
                            p.getInventory().addItem(BedWars.game.getTeamOfPlayer(p).getTeamColor().getClay(amountClicked));
                        } else if (item == CustomItems.BLAST_PROOF_GLASS) {
                            p.getInventory().addItem(BedWars.game.getTeamOfPlayer(p).getTeamColor().getGlass(amountClicked));

                            // Check if they clicked on a pickaxe, and if so, then increase their pickaxe tier
                        } else if (item == CustomItems.WOODEN_PICKAXE) {

                            if (gp.getPickaxeTier() == null) {
                                gp.setPickaxeTier(GamePlayer.PickaxeTier.WOOD);
                                gp.getPlayer().getInventory().addItem(CustomItems.WOODEN_PICKAXE.getItem());
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.IRON_PICKAXE) {

                            if (gp.getPickaxeTier() != GamePlayer.PickaxeTier.GOLD &&
                                    gp.getPickaxeTier() != GamePlayer.PickaxeTier.DIAMOND &&
                                    gp.getPickaxeTier() != GamePlayer.PickaxeTier.IRON &&
                                    gp.getPickaxeTier() != null) {
                                gp.setPickaxeTier(GamePlayer.PickaxeTier.IRON);
                                gp.replacePickaxe(GamePlayer.PickaxeTier.IRON);
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.GOLDEN_PICKAXE) {

                            if (gp.getPickaxeTier() != GamePlayer.PickaxeTier.DIAMOND &&
                                    gp.getPickaxeTier() != GamePlayer.PickaxeTier.GOLD &&
                                    gp.getPickaxeTier() != null) {
                                gp.setPickaxeTier(GamePlayer.PickaxeTier.GOLD);
                                gp.replacePickaxe(GamePlayer.PickaxeTier.GOLD);
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.DIAMOND_PICKAXE) {

                            if (gp.getPickaxeTier() != GamePlayer.PickaxeTier.DIAMOND &&
                                    gp.getPickaxeTier() != null) {
                                gp.setPickaxeTier(GamePlayer.PickaxeTier.DIAMOND);
                                gp.replacePickaxe(GamePlayer.PickaxeTier.DIAMOND);
                            } else {
                                success = false;
                            }

                            // Check if they clicked on an axe, and if so, then increase their axe tier
                        } else if (item == CustomItems.WOODEN_AXE) {

                            if (gp.getAxeTier() == null) {

                                gp.setAxeTier(GamePlayer.AxeTier.WOOD);
                                gp.getPlayer().getInventory().addItem(CustomItems.WOODEN_AXE.getItem());
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.STONE_AXE) {

                            if (gp.getAxeTier() != GamePlayer.AxeTier.IRON &&
                                    gp.getAxeTier() != GamePlayer.AxeTier.DIAMOND &&
                                    gp.getAxeTier() != GamePlayer.AxeTier.STONE &&
                                    gp.getAxeTier() != null) {
                                gp.setAxeTier(GamePlayer.AxeTier.STONE);
                                gp.replaceAxe(GamePlayer.AxeTier.STONE);
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.IRON_AXE) {

                            if (gp.getAxeTier() != GamePlayer.AxeTier.DIAMOND &&
                                    gp.getAxeTier() != GamePlayer.AxeTier.IRON &&
                                    gp.getAxeTier() != null) {
                                gp.setAxeTier(GamePlayer.AxeTier.IRON);
                                gp.replaceAxe(GamePlayer.AxeTier.IRON);
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.DIAMOND_AXE) {

                            if (gp.getAxeTier() != GamePlayer.AxeTier.DIAMOND &&
                                    gp.getAxeTier() != null) {
                                gp.setAxeTier(GamePlayer.AxeTier.DIAMOND);
                                gp.replaceAxe(GamePlayer.AxeTier.DIAMOND);
                            } else {
                                success = false;
                            }

                        } else if (item == CustomItems.SHEARS) {

                            if (!gp.hasUnlockedShears()) {
                                gp.setUnlockedShears(true);
                                gp.getPlayer().getInventory().addItem(CustomItems.SHEARS.getItem());
                            } else {
                                success = false;
                            }

                        } else {
                            p.getInventory().addItem(item.getItem(amountClicked));
                        }

                        if (success) {

                            p.getInventory().removeItem(new ItemStack(item.getPriceItem(), item.getAmount()));

                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.AMBIENT, 1.0f, 1.0f);
                            p.sendMessage(ChatColor.GREEN + "You Purchased " + ChatColor.GOLD + item.getItem().getItemMeta().getDisplayName() + ".");
                        } else {
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1.0f, 1.0f);
                        }
                    } else {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1.0f, 1.0f);
                        p.sendMessage(ChatColor.RED + "You cannot afford " + ChatColor.GOLD + item.getItem().getItemMeta().getDisplayName() + ".");
                    }
                    e.getClickedInventory().setItem(e.getSlot(), item.getItem(amountClicked));
                    return;
                }
            }
        }

        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.AMBIENT, 1.0f, 1.0f);
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof ItemShopGUI) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onVillagerClick(final PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            ItemShopGUI gui = new ItemShopGUI();
            gui.openInventory((HumanEntity) event.getPlayer(), gui.getMainInv());
        }
    }

    @EventHandler
    public void onFireBallSpawn(final EntitySpawnEvent event) {
        if (event.getEntity() instanceof Fireball) {
            ((Fireball) event.getEntity()).setYield(4);
        }
    }

    @EventHandler
    public void onBreakBreak(final BlockBreakEvent event) {
        Block block = event.getBlock();

        System.out.println(block.getType().name());

        // Bed Logic

        if (block.getType().name().contains("_BED")) {
            if (block.getType().name().contains(BedWars.game.getTeamOfPlayer(event.getPlayer()).getTeamColor().getMinecraftName().toUpperCase())) {
                System.out.println("Bed Broken By Player on Team of Bed");
                event.setCancelled(true);
                event.getPlayer().sendMessage(BedWars.prefix + ChatColor.YELLOW + "You can not break that block.");

            } else {
                System.out.println("Bed Broken By Player NOT on Team of Bed");

                BedWars.game.getGamePlayerFromPlayer(event.getPlayer()).incrementBedsBroken();
                BedWars.game.updatePlayersSideBars();

                System.out.println(block.getType().name());

                for (GameTeam team : BedWars.game.getTeams()) {
                    System.out.println(team.getTeamColor().getMinecraftName().toUpperCase());
                    if (block.getType().name().contains(team.getTeamColor().getMinecraftName().toUpperCase())) {
                        team.breakBed(event.getPlayer());
                    }
                }

                event.setDropItems(false);

                return;
            }
        }

        if (BedWars.protectedBlocks.contains(block) && event.getPlayer().getGameMode() != GameMode.CREATIVE){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Server" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "You can not break that block.");
        }


    }

    @EventHandler
    public void onEntityExplosion(final EntityExplodeEvent event) {
        for (Block block : new ArrayList<>(event.blockList())) {
            if (block.getType().name().contains("GLASS")) {
                event.blockList().remove(block);
            } else if (!block.getType().name().contains("WOOL")) {
                event.blockList().remove(block);
            }
        }
    }

    @EventHandler
    public void onTntPlace(final BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() == Material.TNT) {
            block.setType(Material.AIR);
            block.getWorld().spawnEntity(new Location(block.getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5), EntityType.PRIMED_TNT);
        }
    }

    @EventHandler
    public void onFireChargeThrow(final PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE) {
            event.setCancelled(true);

            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
            }

            Location loc = new Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.25, event.getPlayer().getLocation().getZ(), event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch() - 2);
            event.getPlayer().getWorld().spawnEntity(loc, EntityType.FIREBALL);
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
        }
    }

    @EventHandler
    public void onPlayerFallInVoid(final PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getY() <= 15) {
            BedWars.game.killPlayer(event.getPlayer(), EntityDamageEvent.DamageCause.VOID);
        }
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        BedWars.game.updatePlayersSideBars();
        event.getPlayer().setGravity(true);
        event.getPlayer().setFlying(false);
    }

    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
            @Override
            public void run() {
                BedWars.game.updatePlayersSideBars();
            }
        }, 5);

    }


    @EventHandler
    public void onPlayerDeath(final EntityDamageEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
            @Override
            public void run() {
                if (e.getEntityType() == EntityType.PLAYER) {
                    if (((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                        e.setCancelled(true);
                        Bukkit.getWorld("bedwars_map").playSound(((Player) e.getEntity()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.AMBIENT, 1.0f, 1.0f);
                        BedWars.game.killPlayer((Player) e.getEntity(), e.getCause());
                        ((Player) e.getEntity()).playSound(((Player) e.getEntity()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.AMBIENT, 1.0f, 1.0f);
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
                        @Override
                        public void run() {
                            if (BedWars.game != null){
                                BedWars.game.updatePlayersSideBars();
                            }
                        }
                    }, 1);
                }
            }
        }, 1);
    }

    @EventHandler
    public void onPlayerHitByPlayer(final EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (e.getDamager() instanceof Player){
                BedWars.game.getGamePlayerFromPlayer((Player)e.getEntity()).setLastHitBy(BedWars.game.getGamePlayerFromPlayer((Player) e.getDamager()));
            }
        }
    }

    @EventHandler
    public void onPlayerHeal(final EntityRegainHealthEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
                @Override
                public void run() {
                    if (BedWars.game != null){
                        BedWars.game.updatePlayersSideBars();
                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void playerArmorStandInteract(final PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void playerConsumePotion(final PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.POTION) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().getInventory().remove(new ItemStack(Material.GLASS_BOTTLE));
                }
            }, 1);
        }
    }

    @EventHandler
    public void waterBucketPlace(final PlayerBucketEmptyEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
            @Override
            public void run() {
                event.getPlayer().getInventory().remove(new ItemStack(Material.BUCKET));
            }
        }, 1);
    }

    @EventHandler
    public void ghastSpawn(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getItem() == null) {
                return;
            }
            if (e.getItem().getType() == Material.GHAST_SPAWN_EGG) {

                Location loc = new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getX(), e.getClickedBlock().getLocation().getY() + 1, e.getClickedBlock().getLocation().getZ());

                e.setCancelled(true);
                IronGolem ig = (IronGolem) Bukkit.getWorld("bedwars_map").spawnEntity(
                        loc, EntityType.IRON_GOLEM);
                ig.setCustomName(ChatColor.RED + "Dream Defender");
                ig.setCustomNameVisible(true);
                ig.setPlayerCreated(false);
                ig.setCanPickupItems(false);

                ig.setMetadata("igteam", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("BedWars"), BedWars.game.getTeamOfPlayer(e.getPlayer()).getTeamColor().getName()));

                Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (!ig.getMetadata("igteam").get(0).asString().equalsIgnoreCase(
                                    BedWars.game.getTeamOfPlayer(p).getTeamColor().getName())) {
                                ig.damage(0, p);
                                ig.setTarget(p);
                            }
                        }
                    }
                }, 1);

                e.getPlayer().getInventory().removeItem(CustomItems.DREAM_DEFENDER.getItem(1));
            }
        }
    }

    @EventHandler
    public void ironGolemTargetPlayer(final EntityTargetLivingEntityEvent e) {
        if (e.getEntity().getType() == EntityType.IRON_GOLEM && e.getTarget() instanceof Player) {
            if (((LivingEntity) e.getEntity()).getMetadata("igteam").get(0).asString().equalsIgnoreCase(BedWars.game.getTeamOfPlayer(((Player) e.getTarget())).getTeamColor().getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void eggBridge(ProjectileLaunchEvent e) {

        if (e.getEntity().getType() != EntityType.EGG) {
            return;
        }

        Egg egg = (Egg) e.getEntity();
        final Location[] loc = {null, null, null, null, null, null, null, null, null, null, null};

        // Start timer
        BukkitRunnable eggTimer = new BukkitRunnable() {
            @Override
            public void run() {

                if (egg.isDead()) { // If it hits something
                    this.cancel();
                    return;
                }

                if (loc[0] != null) {
                    loc[0].getBlock().setType(Material.GREEN_WOOL);
                    loc[1].getBlock().setType(Material.GREEN_WOOL);
                    loc[2].getBlock().setType(Material.GREEN_WOOL);
                    loc[3].getBlock().setType(Material.GREEN_WOOL);// Use any material you want
                }
                loc[10] = egg.getLocation();
                loc[9] = loc[10].clone();
                loc[8] = loc[9].clone();
                loc[7] = loc[8].clone();
                loc[6] = loc[7].clone();
                loc[5] = loc[6].clone();
                loc[4] = loc[5].clone();
                loc[4] = loc[5].clone();
                loc[0] = loc[4].clone();
                loc[1] = loc[0].clone().add(0, -1, 0);
                loc[2] = loc[0].clone().add(1, 0, 0);
                loc[3] = loc[0].clone().add(0, 0, 1);
            }
        };
        eggTimer.runTaskTimer(Bukkit.getPluginManager().getPlugin("BedWars"), 2L, 1);
    }

}
