package com.adrianwowk.bedwarsclone;

import com.adrianwowk.bedwarsclone.commands.CommandHandler;
import com.adrianwowk.bedwarsclone.events.*;
import com.adrianwowk.bedwarsclone.guis.ItemShopGUI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class BedWars extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    FileConfiguration config = getConfig();
    Server server;
    ConsoleCommandSender console;
    public static String prefix;

    public static ArrayList<GameTeam> teams;

    public static ScheduleForge schedule;
    public static ScheduleDiamondGens diamondScheduler;
    public static ScheduleEmeraldGens emeraldScheduler;
    public static ScheduleRespawn respawnScheduler;
    public String mode;

    public static ArrayList<Block> protectedBlocks = new ArrayList<>();

    public static Game game = null;

    public BedWars() {
        this.server = Bukkit.getServer();
        this.console = this.server.getConsoleSender();
        prefix = ChatColor.GRAY + "[" + ChatColor.RED + "BedWars" + ChatColor.GRAY + "] ";
    }


    public void onEnable() {

//        getServer().createWorld(new WorldCreator("original_map"));
        getServer().createWorld(new WorldCreator("bedwars_map"));
        getServer().createWorld(new WorldCreator("lobby"));

        for (World w : getServer().getWorlds()){
            System.out.println(w.getName());
        }

        World w = Bukkit.getWorld("bedwars_map");
        Block b = null;

        for (int x = 84; x < 233; x++){
            for (int y = 84; y < 111; y++){
                for (int z = -49; z < 95; z++){
                    b = w.getBlockAt(x, y, z);
                    if (b.getType() != Material.AIR && !b.getType().name().contains("BED")) {
                        protectedBlocks.add(b);
                    }
                }
            }
        }

        w = Bukkit.getWorld("lobby");

        for (int x = -105; x < 20; x++){
            for (int y = 90; y < 170; y++){
                for (int z = -40; z < 20; z++){
                    b = w.getBlockAt(x, y, z);
                    if (b.getType() != Material.AIR) {
                        protectedBlocks.add(b);
                    }
                }
            }
        }

        System.out.println(prefix + ChatColor.YELLOW + "Added " + ChatColor.GREEN + String.valueOf(protectedBlocks.size()) + ChatColor.YELLOW + " blocks to protected blocklist");

        CommandHandler handler =  new CommandHandler();

        getCommand("bedwars").setExecutor(handler);
        getCommand("map-reset").setExecutor(handler);
        getCommand("lobby").setExecutor(handler);
        getCommand("map-save").setExecutor(handler);
        getCommand("map-bedwars").setExecutor(handler);

        // Register Event Listeners
        server.getPluginManager().registerEvents((Listener) this, (Plugin) this);
        Bukkit.getServer().getPluginManager().registerEvents(new BedWarsEvents(), (Plugin) this);

        respawnScheduler = new ScheduleRespawn();

        diamondScheduler = new ScheduleDiamondGens((Plugin) this);
        diamondScheduler.schedule(20);

        emeraldScheduler = new ScheduleEmeraldGens((Plugin) this);
        emeraldScheduler.schedule(20);

        schedule = new ScheduleForge((Plugin) this);
        schedule.schedule(5);

        // Server Console Message
        this.getLogger().info(prefix + ChatColor.YELLOW + "Plugin Successfully Enabled.");

    }

    public void onDisable(){
//        config.set("mode", mode);
        saveConfig();
    }
}
