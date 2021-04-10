package com.adrianwowk.bedwarsclone;

import com.adrianwowk.bedwarsclone.events.ScheduleDiamondGens;
import jdk.internal.org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

public class Game {
    private final ArrayList<GameTeam> teams;
    public int aliveTeams;

    private final ArrayList<ArmorStand> diamondGens;
    private final ArrayList<ArmorStand> diamondTierLabels;
    private final ArrayList<ArmorStand> diamondGenTimeLabels;

    private final ArrayList<ArmorStand> emeraldGens;
    private final ArrayList<ArmorStand> emeraldTierLabels;
    private final ArrayList<ArmorStand> emeraldGenTimeLabels;

    public Game(ArrayList<GameTeam> teams) {
        this.teams = teams;
        aliveTeams = teams.size();

        this.diamondGens = new ArrayList<>();
        this.diamondTierLabels = new ArrayList<>();
        this.diamondGenTimeLabels = new ArrayList<>();

        this.emeraldGens = new ArrayList<>();
        this.emeraldTierLabels = new ArrayList<>();
        this.emeraldGenTimeLabels = new ArrayList<>();
        newGame();
    }

    private void newGame() {
        for (GameTeam team : this.teams) {
            for (Object player : team.getPlayers()) {
                ((GamePlayer) player).getPlayer().getInventory().clear();
                ((GamePlayer) player).getPlayer().getInventory().setHelmet(CustomArmor.LEATHER_HELMET.fromColor(team.getColor()));
                ((GamePlayer) player).getPlayer().getInventory().setChestplate(CustomArmor.LEATHER_CHESTPLATE.fromColor(team.getColor()));
                ((GamePlayer) player).getPlayer().getInventory().setLeggings(CustomArmor.LEATHER_LEGGINGS.fromColor(team.getColor()));
                ((GamePlayer) player).getPlayer().getInventory().setBoots(CustomArmor.LEATHER_BOOTS.fromColor(team.getColor()));
                ((GamePlayer) player).getPlayer().getInventory().setHeldItemSlot(0);
                ((GamePlayer) player).getPlayer().getInventory().setItemInMainHand(CustomItems.WOODEN_SWORD.getItem());
            }
        }

        for (Entity e : Bukkit.getWorld("bedwars_map").getEntities()) {
            if (!(e instanceof Player)) {
                e.remove();
            } else {
                LivingEntity le = (LivingEntity) e;
                le.setInvulnerable(false);
                le.setSilent(false);
                le.setGravity(true);
                le.setCustomNameVisible(false);
                le.setAI(false);
                le.setCanPickupItems(true);
                le.setCollidable(false);
            }
        }

        Location orangeShop = new Location(Bukkit.getWorld("bedwars_map"), 161.5, 91, -42.5, 90, 0);
        Location yellowShop = new Location(Bukkit.getWorld("bedwars_map"), 226 + 0.5, 91, 26 + 0.5, 180, 0);
        Location greenShop = new Location(Bukkit.getWorld("bedwars_map"), 154 + 0.5, 91, 88 + 0.5, -90, 0);
        Location blueShop = new Location(Bukkit.getWorld("bedwars_map"), 91 + 0.5, 91, 17 + 0.5, 0, 0);

        Entity orangeShopVillager = Bukkit.getWorld("bedwars_map").spawnEntity(orangeShop, EntityType.VILLAGER);
        Entity yellowhopVillager = Bukkit.getWorld("bedwars_map").spawnEntity(yellowShop, EntityType.VILLAGER);
        Entity greenShopVillager = Bukkit.getWorld("bedwars_map").spawnEntity(greenShop, EntityType.VILLAGER);
        Entity blueShopVillager = Bukkit.getWorld("bedwars_map").spawnEntity(blueShop, EntityType.VILLAGER);

        //summon armor_stand ~ ~-0.5 ~ {Invisible:1b,Invulnerable:1b,NoBasePlate:1b,NoGravity:1b,
        // CustomName:"{\"text\":\"Diamond Generator\",\"color\":\"aqua\"}",CustomNameVisible:1b,DisabledSlots:2031616}


        Location netherDiamondGen = new Location(Bukkit.getWorld("bedwars_map"), 188 + 0.5, 95, -9 + 0.5);
        Location warpedDiamondGen = new Location(Bukkit.getWorld("bedwars_map"), 129 + 0.5, 95, -5 + 0.5);
        Location boneDiamondGen = new Location(Bukkit.getWorld("bedwars_map"), 131 + 0.5, 95, 49 + 0.5);
        Location melonDiamondGen = new Location(Bukkit.getWorld("bedwars_map"), 187 + 0.5, 95, 48 + 0.5);

        Location netherDiamondTierLabel = new Location(Bukkit.getWorld("bedwars_map"), 188 + 0.5, 95 + 0.25, -9 + 0.5);
        Location warpedDiamondTierLabel = new Location(Bukkit.getWorld("bedwars_map"), 129 + 0.5, 95 + 0.25, -5 + 0.5);
        Location boneDiamondTierLabel = new Location(Bukkit.getWorld("bedwars_map"), 131 + 0.5, 95 + 0.25, 49 + 0.5);
        Location melonDiamondTierLabel = new Location(Bukkit.getWorld("bedwars_map"), 187 + 0.5, 95 + 0.25, 48 + 0.5);

        Location netherDiamondGenTimeLabel = new Location(Bukkit.getWorld("bedwars_map"), 188 + 0.5, 95 - 0.25, -9 + 0.5);
        Location warpedDiamondGenTimeLabel = new Location(Bukkit.getWorld("bedwars_map"), 129 + 0.5, 95 - 0.25, -5 + 0.5);
        Location boneDiamondGenTimeLabel = new Location(Bukkit.getWorld("bedwars_map"), 131 + 0.5, 95 - 0.25, 49 + 0.5);
        Location melonDiamondGenTimeLabel = new Location(Bukkit.getWorld("bedwars_map"), 187 + 0.5, 95 - 0.25, 48 + 0.5);

        Location emeraldGenOne = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 97, 22.5 + 0.5);
        Location emeraldTierLabelOne = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 97 + 0.25, 22.5 + 0.5);
        Location emeraldGenTimeLabelOne = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 97 - 0.25, 22.5 + 0.5);

        Location emeraldGenTwo = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 91, 22.5 + 0.5);
        Location emeraldTierLabelTwo = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 91 + 0.25, 22.5 + 0.5);
        Location emeraldGenTimeLabelTwo = new Location(Bukkit.getWorld("bedwars_map"), 157.5 + 0.5, 91 - 0.25, 22.5 + 0.5);

        this.diamondGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(netherDiamondGen, ArmorStand.class));
        this.diamondGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(warpedDiamondGen, ArmorStand.class));
        this.diamondGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(boneDiamondGen, ArmorStand.class));
        this.diamondGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(melonDiamondGen, ArmorStand.class));

        this.diamondTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(netherDiamondTierLabel, ArmorStand.class));
        this.diamondTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(warpedDiamondTierLabel, ArmorStand.class));
        this.diamondTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(boneDiamondTierLabel, ArmorStand.class));
        this.diamondTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(melonDiamondTierLabel, ArmorStand.class));

        this.diamondGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(netherDiamondGenTimeLabel, ArmorStand.class));
        this.diamondGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(warpedDiamondGenTimeLabel, ArmorStand.class));
        this.diamondGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(boneDiamondGenTimeLabel, ArmorStand.class));
        this.diamondGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(melonDiamondGenTimeLabel, ArmorStand.class));

        this.emeraldGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldGenOne, ArmorStand.class));
        this.emeraldTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldTierLabelOne, ArmorStand.class));
        this.emeraldGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldGenTimeLabelOne, ArmorStand.class));

        this.emeraldGens.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldGenTwo, ArmorStand.class));
        this.emeraldTierLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldTierLabelTwo, ArmorStand.class));
        this.emeraldGenTimeLabels.add((ArmorStand) Bukkit.getWorld("bedwars_map").spawn(emeraldGenTimeLabelTwo, ArmorStand.class));

        for (ArmorStand ams : this.diamondGens) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + "Diamond");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (ArmorStand ams : this.diamondTierLabels) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.YELLOW + "Tier " + ChatColor.RED + "I");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (ArmorStand ams : this.diamondGenTimeLabels) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.YELLOW + "Spawns in " + ChatColor.RED + 30 + ChatColor.YELLOW + " seconds");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (ArmorStand ams : this.emeraldGens) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Emerald");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (ArmorStand ams : this.emeraldTierLabels) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.YELLOW + "Tier " + ChatColor.RED + "I");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (ArmorStand ams : this.emeraldGenTimeLabels) {
            ams.setGravity(false);
            ams.setCanPickupItems(false);
            ams.setCustomName(ChatColor.YELLOW + "Spawns in " + ChatColor.RED + 30 + ChatColor.YELLOW + " seconds");
            ams.setCustomNameVisible(true);
            ams.setVisible(false);
            ams.setInvulnerable(true);
            ams.setBasePlate(false);
        }

        for (Entity e : Bukkit.getWorld("bedwars_map").getEntities()) {
            if (e instanceof Villager) {
                LivingEntity le = (LivingEntity) e;
                le.setInvulnerable(true);
                le.setSilent(true);
                le.setGravity(false);
                le.setCustomName(ChatColor.AQUA + "ITEM SHOP");
                le.setCustomNameVisible(true);
                le.setAI(false);
                le.setCanPickupItems(false);
                le.setCollidable(false);
            }

            ((LivingEntity) e).setCollidable(false);
        }

        BedWars.diamondScheduler.reset();
        BedWars.emeraldScheduler.reset();
    }

    public void updatePlayersSideBars() {
        for (GameTeam team : this.teams) {
            for (Object p : team.getPlayers()) {
                ((GamePlayer) p).setSideBar(true);
            }
        }
    }

    public void gameEnd(GameTeam team) {
        for (GameTeam gt : getTeams()){
            if (gt.equals(team)){
                // Victory
                for (GamePlayer player : gt.getPlayers()){
                    player.getPlayer().sendTitle(ChatColor.GOLD + "Victory!", "", 10, 70, 20);
                }
            } else {
                // Loss
                for (GamePlayer player : gt.getPlayers()){
                    player.getPlayer().sendTitle(ChatColor.RED + "GAME OVER!", "", 10, 70, 20);

                }
            }

            for (GamePlayer p : gt.getPlayers()){
                PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 10000000, 1, false, false, false);
                p.getPlayer().setHealth(20);
                p.getPlayer().setFoodLevel(20);
                p.getPlayer().setSaturation(20);
                p.getPlayer().addPotionEffect(saturation);

                p.getPlayer().getInventory().clear();
                p.getPlayer().setAllowFlight(true);
                p.getPlayer().setFlying(true);
            }

        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(Bukkit.getPluginManager().getPlugin("BedWars"), p);
            }
        }
    }

    public void respawnPLayer(Player p) {
        if (p.getGameMode() != GameMode.CREATIVE) {
            PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 10000000, 1, false, false, false);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.addPotionEffect(saturation);

            p.getInventory().clear();
            p.teleport(getTeamOfPlayer(p).getTeamColor().getTeamRespawnPoint());
            p.setAllowFlight(false);
            p.setFlying(false);

            GameTeam team = getTeamOfPlayer(p);

            p.getInventory().setHelmet(CustomArmor.LEATHER_HELMET.fromColor(team.getColor()));
            p.getInventory().setChestplate(CustomArmor.LEATHER_CHESTPLATE.fromColor(team.getColor()));
            p.getInventory().setBoots(GamePlayer.ArmorTier.getBoots(BedWars.game.getGamePlayerFromPlayer(p)));
            p.getInventory().setLeggings(GamePlayer.ArmorTier.getLeggings(BedWars.game.getGamePlayerFromPlayer(p)));

//            p.getPlayer().getInventory().setLeggings(CustomArmor.LEATHER_LEGGINGS.fromColor(team.getColor()));
//            p.getPlayer().getInventory().setBoots(CustomArmor.LEATHER_BOOTS.fromColor(team.getColor()));
            p.getInventory().setHeldItemSlot(0);
            p.getInventory().setItemInMainHand(CustomItems.WOODEN_SWORD.getItem());

            GamePlayer gp = getGamePlayerFromPlayer(p);

            gp.decrementPickaxeTier();
            gp.decrementAxeTier();

            if (gp.getPickaxeTier() != null){
                gp.replacePickaxe(getGamePlayerFromPlayer(p).getPickaxeTier());
            }
            if (gp.getAxeTier() != null){
                gp.replaceAxe(getGamePlayerFromPlayer(p).getAxeTier());
            }
            if (gp.hasUnlockedShears()){
                gp.getPlayer().getInventory().addItem(CustomItems.SHEARS.getItem());
            }

        }
    }

    public void killPlayer(Player p, EntityDamageEvent.DamageCause cause) {
        for (Object player : getTeamOfPlayer(p).getPlayers()) {
            if (((GamePlayer) player).getPlayer().equals(p)) {
                ((GamePlayer) player).incrementDeaths();
            }
        }

        updatePlayersSideBars();

        for (PotionEffect potionEffect : p.getActivePotionEffects()){
            p.removePotionEffect(potionEffect.getType());
        }

        PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 10000000, 1, false, false, false);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setSaturation(20);
        p.addPotionEffect(saturation);

        p.getInventory().clear();
        p.getInventory().setHeldItemSlot(0);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, SoundCategory.AMBIENT, 1.0f, 1.0f);

        if (!getTeamOfPlayer(p).isBedBroken()) {
            p.sendTitle(ChatColor.RED + "You Have Died!", ChatColor.YELLOW + "Respawning in: " + ChatColor.RED + 5, 10, 10, 0);
            BedWars.respawnScheduler.schedule(Bukkit.getPluginManager().getPlugin("BedWars"), p, 20 * 5);
            for (int i = 0; i < 4; i++) {
                int finalI = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("BedWars"), new Runnable() {
                    @Override
                    public void run() {
                        p.resetTitle();
                        p.sendTitle(ChatColor.RED + "You Have Died!", ChatColor.YELLOW + "Respawning in: " + ChatColor.RED + (5 - (finalI + 1)), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, SoundCategory.AMBIENT, 1.0f, 1.0f);
                    }
                }, 20 * (i + 1));
            }

            if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Bukkit.broadcastMessage(p.getDisplayName() + " was killed by " + getGamePlayerFromPlayer(p).getLastHitBy().getPlayer().getDisplayName());
            } else if (cause == EntityDamageEvent.DamageCause.VOID){

            }

        } else {
//            Bukkit.getWorld("bedwars_map").strikeLightning(p.getLocation());
            Bukkit.getWorld("bedwars_map").strikeLightningEffect(p.getLocation());
            p.sendTitle(ChatColor.RED + "You Have Died!", "", 10, 70, 20);
//            for (Player player : Bukkit.getOnlinePlayers()){
//                player.hidePlayer(Bukkit.getPluginManager().getPlugin("BedWars"), p);
//            }
            getTeamOfPlayer(p).alivePlayers--;
            System.out.println(getTeamOfPlayer(p).alivePlayers);

            if (getTeamOfPlayer(p).isBedBroken() && getTeamOfPlayer(p).alivePlayers <= 0){
                this.aliveTeams--;
                System.out.println(this.aliveTeams);
            }

            if (this.aliveTeams == 1){
                for (GameTeam t : getTeams()){
                    if (t.alivePlayers > 0){
                        gameEnd(t);
                    }
                }
            }

            updatePlayersSideBars();
            //Final Kill Lightning
        }
        p.teleport(new Location(p.getWorld(), 158, 115, 23));
    }

    public void elliminateTeam(GameTeam t) {
        t.alivePlayers = 0;
    }

    public void nextStage() {

    }

    public GameTeam getTeamFromTeamColor(GameTeam.TeamColor teamColor) {
        GameTeam team = null;

        for (GameTeam t : this.teams) {
            if (t.getTeamColor() == teamColor) {
                team = t;
            }
        }

        return team;
    }

    public GameTeam getTeamOfPlayer(Player player) {
        GameTeam theTeam = null;
        for (GameTeam team : this.teams) {
            if (team.hasPlayer(player)) {
                theTeam = team;
            }
        }

        return theTeam;
    }

    public ArrayList<GameTeam> getTeams() {
        return teams;
    }

    public GamePlayer getGamePlayerFromPlayer(Player player) {
        GameTeam team = getTeamOfPlayer(player);
        GamePlayer thePlayer = null;

        for (GamePlayer p : team.getPlayers()) {
            if (p.getPlayer().equals(player)) {
                thePlayer = p;
            }
        }

        return thePlayer;
    }

    public ArrayList<ArmorStand> getDiamondGens() {
        return diamondGens;
    }

    public ArrayList<ArmorStand> getDiamondGenTimeLabels() {
        return diamondGenTimeLabels;
    }

    public ArrayList<ArmorStand> getEmeraldTierLabels() {
        return emeraldTierLabels;
    }

    public ArrayList<ArmorStand> getEmeraldGens() {
        return emeraldGens;
    }

    public ArrayList<ArmorStand> getEmeraldGenTimeLabels() {
        return emeraldGenTimeLabels;
    }

    public ArrayList<ArmorStand> getDiamondTierLabels() {
        return diamondTierLabels;
    }

    public enum GameStages {

    }

    public enum CustomArmor {
        LEATHER_HELMET(Material.LEATHER_HELMET),
        LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS),
        LEATHER_BOOTS(Material.LEATHER_BOOTS),

        CHAINMAIL_LEGGINGS(Material.CHAINMAIL_LEGGINGS),
        CHAINMAIL_BOOTS(Material.CHAINMAIL_BOOTS),

        IRON_LEGGINGS(Material.IRON_LEGGINGS),
        IRON_BOOTS(Material.IRON_BOOTS),

        DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS),
        DIAMOND_BOOTS(Material.DIAMOND_BOOTS);

        private ItemStack item;

        CustomArmor(Material mat) {
            ItemStack newItem = new ItemStack(mat);
            ItemMeta meta = newItem.getItemMeta();
            meta.setUnbreakable(true);
            meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

            newItem.setItemMeta(meta);

            this.item = newItem;
        }

        public ItemStack getItem(){
            return this.item;
        }

        public ItemStack fromColor(Color color) {
            ItemStack newItem = this.item.clone();
            ItemMeta meta = newItem.getItemMeta();

            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;

            leatherMeta.setColor(color);
            newItem.setItemMeta(leatherMeta);

            return newItem;
        }
    }

}
