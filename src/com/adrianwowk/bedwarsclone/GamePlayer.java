package com.adrianwowk.bedwarsclone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

public class GamePlayer {

    private Player player;
    private boolean alive;
    private int deaths = 0;
    private int kills = 0;
    private int finalKills = 0;
    private int bedsBroken = 0;
    private ArmorTier armorTier = ArmorTier.LEATHER;
    private PickaxeTier pickaxeTier = null;
    private AxeTier axeTier = null;
    private boolean unlockedShears = false;
    private GamePlayer lastHitBy = null;

    public GamePlayer(Player player) {
        this.player = player;

        PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 10000000, 1, false, false, false);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.addPotionEffect(saturation);
    }

    public void setSideBar(boolean show) {
        if (show) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective health = board.registerNewObjective("Health", "dummy", "Title");
            health.setDisplaySlot(DisplaySlot.BELOW_NAME);
            health.setDisplayName(ChatColor.RED + "❤");

            for (Player online : Bukkit.getOnlinePlayers()) {
                Score score = health.getScore(online);
                score.setScore((int) online.getHealth()); //Example
            }

//            health.getScore(ChatColor.GREEN + String.valueOf(20) + "❤").setScore(0);


            Objective obj = board.registerNewObjective("BedWarsServer", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "BED WARS");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            // Dynamic Line
            Team onlineCounter = board.registerNewTeam("onlineCounter");
            onlineCounter.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
            int players = Bukkit.getOnlinePlayers().size() == 0 ? 0 : Bukkit.getOnlinePlayers().size();
            onlineCounter.setPrefix("Online: " + ChatColor.GREEN + players + ChatColor.GRAY + "/" + ChatColor.GREEN + Bukkit.getMaxPlayers());
            obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(17);

            //Static Line
            Score space2 = obj.getScore(ChatColor.GRAY + "  ");
            space2.setScore(16);

            Team nextStageMessage = board.registerNewTeam("nextStageMessage");
            nextStageMessage.addEntry("Diamond II in " + ChatColor.GREEN + "4:32");
            obj.getScore("Diamond II in " + ChatColor.GREEN + "4:32").setScore(15);

            //Static Line
            Score space3 = obj.getScore(ChatColor.GRAY + "   ");
            space3.setScore(14);

            // Dynamic Line

            // (condition ? <if true> : <if false>)
            GameTeam orange = BedWars.game.getTeamFromTeamColor(GameTeam.TeamColor.ORANGE);
            String orangeStatus = (orange != null ? (orange.isBedBroken() ? (orange.alivePlayers > 0 ? String.valueOf(orange.alivePlayers) : "✗") : "✓") : "✗");
            orangeStatus = (isNumeric(orangeStatus) ? "" : ChatColor.BOLD) + orangeStatus;
            orangeStatus = (orangeStatus.contains("✗") ? ChatColor.RED : ChatColor.GREEN) + orangeStatus;

            GameTeam yellow = BedWars.game.getTeamFromTeamColor(GameTeam.TeamColor.YELLOW);
            String yellowStatus = (yellow != null ? (yellow.isBedBroken() ? (yellow.alivePlayers > 0 ? String.valueOf(yellow.alivePlayers) : "✗") : "✓") : "✗");
            yellowStatus = (isNumeric(yellowStatus) ? "" : ChatColor.BOLD) + yellowStatus;
            yellowStatus = (yellowStatus.contains("✗") ? ChatColor.RED : ChatColor.GREEN) + yellowStatus;

            GameTeam green = BedWars.game.getTeamFromTeamColor(GameTeam.TeamColor.GREEN);
            String greenStatus = (green != null ? (green.isBedBroken() ? (green.alivePlayers > 0 ? String.valueOf(green.alivePlayers) : "✗") : "✓") : "✗");
            greenStatus = (isNumeric(greenStatus) ? "" : ChatColor.BOLD) + greenStatus;
            greenStatus = (greenStatus.contains("✗") ? ChatColor.RED : ChatColor.GREEN) + greenStatus;

            GameTeam blue = BedWars.game.getTeamFromTeamColor(GameTeam.TeamColor.BLUE);
            String blueStatus = (blue != null ? (blue.isBedBroken() ? (blue.alivePlayers > 0 ? String.valueOf(blue.alivePlayers) : "✗") : "✓") : "✗");
            blueStatus = (isNumeric(blueStatus) ? "" : ChatColor.BOLD) + blueStatus;
            blueStatus = (blueStatus.contains("✗") ? ChatColor.RED : ChatColor.GREEN) + blueStatus;


            Team moneyCounter = board.registerNewTeam("moneyCounter");
            moneyCounter.addEntry(ChatColor.GOLD + "O " + ChatColor.RESET + "Orange: " + orangeStatus + (orange != null ? (orange.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : ""));
            moneyCounter.addEntry(ChatColor.YELLOW + "Y " + ChatColor.RESET + "Yellow: " + yellowStatus + (yellow != null ? (yellow.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : ""));
            moneyCounter.addEntry(ChatColor.GREEN + "G " + ChatColor.RESET + "Green: " + greenStatus + (green != null ? (green.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : ""));
            moneyCounter.addEntry(ChatColor.BLUE + "B " + ChatColor.RESET + "Blue: " + blueStatus + (blue != null ? (blue.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : ""));
            obj.getScore(ChatColor.GOLD + "O " + ChatColor.RESET + "Orange: " + orangeStatus + (orange != null ? (orange.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : "")).setScore(13);
            obj.getScore(ChatColor.YELLOW + "Y " + ChatColor.RESET + "Yellow: " + yellowStatus + (yellow != null ? (yellow.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : "")).setScore(12);
            obj.getScore(ChatColor.GREEN + "G " + ChatColor.RESET + "Green: " + greenStatus + (green != null ? (green.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : "")).setScore(11);
            obj.getScore(ChatColor.BLUE + "B " + ChatColor.RESET + "Blue: " + blueStatus + (blue != null ? (blue.hasPlayer(getPlayer()) ? (ChatColor.GRAY + " YOU") : "") : "")).setScore(10);

            Score space4 = obj.getScore(ChatColor.GRAY + "    ");
            space4.setScore(9);

            obj.getScore("Kills: " + ChatColor.GREEN + this.kills).setScore(8);
            obj.getScore("Final Kills: " + ChatColor.GREEN + this.finalKills).setScore(7);
            obj.getScore("Beds Broken: " + ChatColor.GREEN + this.bedsBroken).setScore(6);
            obj.getScore("Deaths: " + ChatColor.GREEN + this.deaths).setScore(5);

            obj.getScore(ChatColor.GRAY + "     ").setScore(4);

//            obj.getScore(ChatColor.GRAY + "08/03/20 m693K").setScore(3);

            obj.getScore(ChatColor.YELLOW + "play.adrianwowk.com").setScore(3);

            player.setScoreboard(board);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getFinalKills() {
        return finalKills;
    }

    public int getKills() {
        return kills;
    }

    public int getBedsBroken() {
        return this.bedsBroken;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void incrementDeaths() {
        this.deaths++;
    }

    public void incrementKills() {
        this.kills++;
    }

    public void incrementFinalKills() {
        this.finalKills++;
    }

    public void incrementBedsBroken() {
        this.bedsBroken++;
    }

    public void setFinalKills(int finalKills) {
        this.finalKills = finalKills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArmorTier getArmorTier() {
        return this.armorTier;
    }

    public void setArmorTier(ArmorTier armorTier) {
        this.armorTier = armorTier;
    }

    public PickaxeTier getPickaxeTier() {
        return pickaxeTier;
    }

    public void setPickaxeTier(PickaxeTier pickaxeTier) {
        this.pickaxeTier = pickaxeTier;
    }

    public AxeTier getAxeTier() {
        return axeTier;
    }

    public void setAxeTier(AxeTier axeTier) {
        this.axeTier = axeTier;
    }

    public void decrementPickaxeTier() {
        if (this.pickaxeTier == null || this.pickaxeTier == PickaxeTier.WOOD) {
            return;
        } else {
            if (this.pickaxeTier == PickaxeTier.IRON){
                setPickaxeTier(PickaxeTier.WOOD);
            } else if (this.pickaxeTier == PickaxeTier.GOLD){
                setPickaxeTier(PickaxeTier.IRON);
            } else if (this.pickaxeTier == PickaxeTier.DIAMOND){
                setPickaxeTier(PickaxeTier.GOLD);
            }
        }
    }

    public void decrementAxeTier() {
        if (this.axeTier == null || this.axeTier == AxeTier.WOOD) {
            return;
        } else {
            if (this.axeTier == AxeTier.STONE){
                setAxeTier(AxeTier.WOOD);
            } else if (this.axeTier == AxeTier.IRON){
                setAxeTier(AxeTier.STONE);
            } else if (this.axeTier == AxeTier.DIAMOND){
                setAxeTier(AxeTier.IRON);
            }
        }
    }

    public boolean hasUnlockedShears() {
        return unlockedShears;
    }

    public void setUnlockedShears(boolean unlockedShears) {
        this.unlockedShears = unlockedShears;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static ArmorTier getTierFromItem(CustomItems item) {
        switch (item) {
            case CHAINMAIL_ARMOR:
                return ArmorTier.CHAINMAIL;

            case IRON_ARMOR:
                return ArmorTier.IRON;

            case DIAMOND_ARMOR:
                return ArmorTier.DIAMOND;

            default:
                return null;
        }
    }

    public void replacePickaxe(PickaxeTier tier) {

        switch (tier){
            case IRON:
                player.getInventory().remove(CustomItems.WOODEN_PICKAXE.getItem());
                break;
            case GOLD:
                player.getInventory().remove(CustomItems.IRON_PICKAXE.getItem());
                break;
            case DIAMOND:
                player.getInventory().remove(CustomItems.GOLDEN_PICKAXE.getItem());
                break;
        }
        player.getInventory().addItem(tier.getPickaxe().getItem());
        return;
    }

    public void replaceAxe(AxeTier tier) {

        switch (tier){
            case STONE:
                player.getInventory().remove(CustomItems.WOODEN_AXE.getItem());
                break;
            case IRON:
                player.getInventory().remove(CustomItems.STONE_AXE.getItem());
                break;
            case DIAMOND:
                player.getInventory().remove(CustomItems.IRON_AXE.getItem());
                break;
        }
        player.getInventory().addItem(tier.getAxe().getItem());
        return;
    }

    public GamePlayer getLastHitBy() {
        return lastHitBy;
    }

    public void setLastHitBy(GamePlayer lastHitBy) {
        this.lastHitBy = lastHitBy;
    }

    public enum ArmorTier {
        LEATHER(Game.CustomArmor.LEATHER_LEGGINGS, Game.CustomArmor.LEATHER_BOOTS),
        CHAINMAIL(Game.CustomArmor.CHAINMAIL_LEGGINGS, Game.CustomArmor.CHAINMAIL_BOOTS),
        IRON(Game.CustomArmor.IRON_LEGGINGS, Game.CustomArmor.IRON_BOOTS),
        DIAMOND(Game.CustomArmor.DIAMOND_LEGGINGS, Game.CustomArmor.DIAMOND_BOOTS);

        private final Game.CustomArmor leggings;
        private final Game.CustomArmor boots;

        ArmorTier(Game.CustomArmor leggings, Game.CustomArmor boots) {
            this.leggings = leggings;
            this.boots = boots;
        }

        public Game.CustomArmor getLeggingsOfThis() {
            return this.leggings;
        }

        public Game.CustomArmor getBootsOfThis() {
            return this.boots;
        }

        public static ItemStack getLeggings(GamePlayer p) {
            if (p == null) {
                return null;
            }

            switch (p.getArmorTier()) {
                case LEATHER:
                    return (Game.CustomArmor.LEATHER_LEGGINGS.fromColor(BedWars.game.getTeamOfPlayer(p.getPlayer()).getTeamColor().getColor()));
                case CHAINMAIL:
                    return ArmorTier.CHAINMAIL.getLeggingsOfThis().getItem();
                case IRON:
                    return ArmorTier.IRON.getLeggingsOfThis().getItem();
                case DIAMOND:
                    return ArmorTier.DIAMOND.getLeggingsOfThis().getItem();
                default:
                    return null;
            }
        }

        public static ItemStack getBoots(GamePlayer p) {
            if (p == null) {
                return null;
            }

            switch (p.getArmorTier()) {
                case LEATHER:
                    return (Game.CustomArmor.LEATHER_BOOTS.fromColor(BedWars.game.getTeamOfPlayer(p.getPlayer()).getTeamColor().getColor()));
                case CHAINMAIL:
                    return ArmorTier.CHAINMAIL.getBootsOfThis().getItem();
                case IRON:
                    return ArmorTier.IRON.getBootsOfThis().getItem();
                case DIAMOND:
                    return ArmorTier.DIAMOND.getBootsOfThis().getItem();
                default:
                    return null;
            }
        }
    }

    public enum PickaxeTier {
        WOOD(CustomItems.WOODEN_PICKAXE),
        IRON(CustomItems.IRON_PICKAXE),
        GOLD(CustomItems.GOLDEN_PICKAXE),
        DIAMOND(CustomItems.DIAMOND_PICKAXE);

        private final CustomItems pickaxe;

        PickaxeTier(CustomItems pickaxe) {
            this.pickaxe = pickaxe;
        }

        public CustomItems getPickaxe() {
            return this.pickaxe;
        }

        public static ItemStack getPickaxeOfGamePlayer(GamePlayer p) {
            if (p == null) {
                return null;
            }

            switch (p.getPickaxeTier()) {
                case WOOD:
                    return CustomItems.WOODEN_PICKAXE.getItem();
                case IRON:
                    return CustomItems.IRON_PICKAXE.getItem();
                case GOLD:
                    return CustomItems.GOLDEN_PICKAXE.getItem();
                case DIAMOND:
                    return CustomItems.DIAMOND_PICKAXE.getItem();
                default:
                    return null;
            }
        }
    }

    public enum AxeTier {
        WOOD(CustomItems.WOODEN_AXE),
        STONE(CustomItems.STONE_AXE),
        IRON(CustomItems.IRON_AXE),
        DIAMOND(CustomItems.DIAMOND_AXE);

        private final CustomItems axe;

        AxeTier(CustomItems axe) {
            this.axe = axe;
        }

        public CustomItems getAxe() {
            return this.axe;
        }

        public static ItemStack getAxeOfGamePlayer(GamePlayer p) {
            if (p == null) {
                return null;
            }

            switch (p.getAxeTier()) {
                case WOOD:
                    return CustomItems.WOODEN_AXE.getItem();
                case STONE:
                    return CustomItems.STONE_AXE.getItem();
                case IRON:
                    return CustomItems.IRON_AXE.getItem();
                case DIAMOND:
                    return CustomItems.DIAMOND_AXE.getItem();
                default:
                    return null;
            }
        }
    }
}
