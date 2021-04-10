package com.adrianwowk.bedwarsclone;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class GameTeam {
    private ArrayList<GamePlayer> players;
    private boolean bedBroken;
    public int alivePlayers;
    private HashMap<TeamUpgrade, Integer> upgrades;
    private TeamColor teamColor;

    public GameTeam(ArrayList<GamePlayer> players, TeamColor teamColor) {
        this.teamColor = teamColor;
        this.players = players;
        this.bedBroken = false;
        this.upgrades = new HashMap<TeamUpgrade, Integer>();

        for (GamePlayer p : this.players) {
            String prefix = this.teamColor.chatColor + "[" + this.teamColor.name + "] " + ChatColor.RESET;
            p.getPlayer().setDisplayName(p.getPlayer().getName());
            p.getPlayer().setPlayerListName(p.getPlayer().getName());

            p.getPlayer().setDisplayName(prefix + p.getPlayer().getDisplayName());
            p.getPlayer().setCustomName(prefix + p.getPlayer().getDisplayName());
            p.getPlayer().setCustomNameVisible(true);
            p.getPlayer().setPlayerListName(prefix + p.getPlayer().getName());


        }

        this.alivePlayers = players.size();
    }

    public boolean hasPlayer(Player player) {
        for (GamePlayer p : this.players) {
            if (p.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBedBroken() {
        return bedBroken;
    }
    public void setBedBroken(boolean broken) {
        this.bedBroken = broken;
    }

    public Color getColor() {
        return this.teamColor.color;
    }

    public void breakBed(Player p) {
        Bukkit.broadcastMessage(this.teamColor.chatColor + this.teamColor.getName() + ChatColor.RESET + ChatColor.GRAY  + " Bed was destroyed by " + BedWars.game.getTeamOfPlayer(p).teamColor.chatColor + p.getDisplayName());

        for (Player player : Bukkit.getOnlinePlayers()){
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, SoundCategory.AMBIENT, 1.0f, 1.0f);
        }

        this.bedBroken = true;
        BedWars.game.updatePlayersSideBars();
    }

    public ArrayList<GamePlayer> getPlayers() {
        return players;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public enum TeamColor{
        ORANGE(new Location(Bukkit.getWorld("bedwars_map"), 156, 91, -26 - 20),
               new Location(Bukkit.getWorld("bedwars_map"), 156, 91, -23 - 20),
                new Location(Bukkit.getWorld("bedwars_map"), 156, 91, -14 - 20),
               Color.ORANGE, "Orange", ChatColor.GOLD.toString(), "Orange", Material.ORANGE_WOOL, Material.ORANGE_TERRACOTTA, Material.ORANGE_STAINED_GLASS),
        YELLOW(new Location(Bukkit.getWorld("bedwars_map"), 210 + 20, 91, 21),
                new Location(Bukkit.getWorld("bedwars_map"), 207 + 20, 91, 21),
                new Location(Bukkit.getWorld("bedwars_map"), 197 + 20, 91, 21),
            Color.YELLOW, "Yellow", ChatColor.YELLOW.toString(), "Yellow", Material.YELLOW_WOOL, Material.YELLOW_TERRACOTTA, Material.YELLOW_STAINED_GLASS),
        GREEN(new Location(Bukkit.getWorld("bedwars_map"), 159, 91, 72 + 20),
                new Location(Bukkit.getWorld("bedwars_map"), 159, 91, 69 + 20),
                new Location(Bukkit.getWorld("bedwars_map"), 159, 91, 59 + 20),
                Color.GREEN, "Green", ChatColor.GREEN.toString(), "Lime", Material.GREEN_WOOL, Material.GREEN_TERRACOTTA, Material.GREEN_STAINED_GLASS),
        BLUE(new Location(Bukkit.getWorld("bedwars_map"), 107 - 20, 91, 22),
                new Location(Bukkit.getWorld("bedwars_map"), 110 - 20, 91, 22),
                new Location(Bukkit.getWorld("bedwars_map"), 120 - 20, 91, 22),
                Color.BLUE, "Blue", ChatColor.BLUE.toString(), "Blue", Material.BLUE_WOOL, Material.BLUE_TERRACOTTA, Material.BLUE_STAINED_GLASS);

        private final String chatColor;
        private final Color color;
        private final Location forgeLocation;
        private final Location teamRespawnPoint;
        private String name;
        private String minecraftName;
        private Material woolMat;
        private Material clayMat;
        private Material glassMat;

        TeamColor(Location forgeLocation, Location teamRespawnPoint, Location bedLocation, Color color, String name, String chatColor, String minecraftName, Material woolMat, Material clayMat, Material glassMat){
            this.name = name;
            this.color = color;
            this.chatColor = chatColor;
            this.forgeLocation = forgeLocation;
            this.teamRespawnPoint = teamRespawnPoint;
            this.minecraftName = minecraftName;
            this.woolMat = woolMat;
            this.clayMat = clayMat;
            this.glassMat = glassMat;
        }

        public String getName(){
            return this.name;
        }

        public String getMinecraftName(){
            return this.minecraftName;
        }

        public String getChatColor() {
            return this.chatColor;
        }

        public Location getForgeLocation() {
            return forgeLocation;
        }

        public Color getColor() {
            return color;
        }

        public Location getTeamRespawnPoint() {
            return teamRespawnPoint;
        }

        public ItemStack getWool(int amount) {
            return new ItemStack(this.woolMat, amount);
        }

        public ItemStack getClay(int amount) {
            return new ItemStack(this.clayMat, amount);
        }

        public ItemStack getGlass(int amount) {
            return new ItemStack(this.glassMat, amount);
        }
    }
}
