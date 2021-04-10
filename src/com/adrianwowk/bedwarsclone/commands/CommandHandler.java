package com.adrianwowk.bedwarsclone.commands;

import com.adrianwowk.bedwarsclone.BedWars;
import com.adrianwowk.bedwarsclone.Game;
import com.adrianwowk.bedwarsclone.GamePlayer;
import com.adrianwowk.bedwarsclone.GameTeam;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("bedwars")) {

            ArrayList<GameTeam> teams = new ArrayList<>();

            ArrayList<GamePlayer> players = new ArrayList<>();
            players.add(new GamePlayer(Bukkit.getPlayer("wowkster")));

            ArrayList<GamePlayer> players2 = new ArrayList<>();
            players2.add(new GamePlayer(Bukkit.getPlayer("melwowk")));

            ArrayList<GamePlayer> players3 = new ArrayList<>();
            players3.add(new GamePlayer(Bukkit.getPlayer("Roar_Ostrich")));

            teams.add(new GameTeam(players, GameTeam.TeamColor.ORANGE));
            teams.add(new GameTeam(players2, GameTeam.TeamColor.YELLOW));
            teams.add(new GameTeam(players3, GameTeam.TeamColor.BLUE));

            BedWars.game = new Game(teams);
            BedWars.game.updatePlayersSideBars();

            for (GameTeam t : BedWars.game.getTeams()){
                for (GamePlayer p : t.getPlayers()){
                    BedWars.game.respawnPLayer(p.getPlayer());
                }
            }
        } else if (command.getName().equalsIgnoreCase("map-reset")) {
            commandSender.sendMessage(ChatColor.AQUA + "Resetting Map...");
            for (Player p : Bukkit.getOnlinePlayers()){
                p.getInventory().clear();
                p.teleport(new Location(Bukkit.getWorld("lobby"), 0, 100, 0));
            }
            if (Bukkit.unloadWorld("bedwars_map", false)){
                System.out.println(ChatColor.GREEN + "SUCCESSFULLY UNLOADED WORLD 'bedwars_map'");
            } else{
                System.out.println(ChatColor.RED + "COULD NOT UNLOAD WORLD 'bedwars_map'");
            }

            for (World w : Bukkit.getServer().getWorlds()){
                System.out.println(w.getName());
            }

            commandSender.sendMessage(ChatColor.BLUE + "Unloaded Worlds...");

            File dir = new File("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\bedwars_map");

            removeDirectory(dir);

            commandSender.sendMessage(ChatColor.RED + "Deleted Old Files...");

            try {
                // source & destination directories
                Path src = Paths.get("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\original_map");
                Path dest = Paths.get("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\bedwars_map");

                // create stream for `src`
                Stream<Path> files = Files.walk(src);

                // copy all files and folders from `src` to `dest`
                files.forEach(file -> {
                    try {
                        Files.copy(file, dest.resolve(src.relativize(file)),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // close the stream
                files.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            commandSender.sendMessage(ChatColor.YELLOW + "Coppied Schematic To Destination...");

            Bukkit.getServer().createWorld(new WorldCreator("bedwars_map"));
            commandSender.sendMessage(ChatColor.GREEN + "Loaded New World...");

            commandSender.sendMessage(ChatColor.GREEN + "Reloading Server...");
            Bukkit.getServer().reload();



        } else if (command.getName().equalsIgnoreCase("lobby")) {
            ((Player) commandSender).getInventory().clear();
            ((Player) commandSender).teleport(new Location(Bukkit.getWorld("lobby"), 0, 100, 0));

        } else if (command.getName().equalsIgnoreCase("map-bedwars")) {
            ((Player) commandSender).teleport(new Location(Bukkit.getWorld("bedwars_map"), 158, 113, 23));

        }else if (command.getName().equalsIgnoreCase("map-save")) {

            commandSender.sendMessage(ChatColor.AQUA + "Saving Map...");
            for (Player p : Bukkit.getOnlinePlayers()){
                p.teleport(new Location(Bukkit.getWorld("lobby"), 0, 100, 0));
            }
            if (Bukkit.unloadWorld("bedwars_map", true)){
                System.out.println(ChatColor.GREEN + "SUCCESSFULLY UNLOADED WORLD 'bedwars_map'");
            } else{
                System.out.println(ChatColor.RED + "COULD NOT UNLOAD WORLD 'bedwars_map'");
            }

            for (World w : Bukkit.getServer().getWorlds()){
                System.out.println(w.getName());
            }

            commandSender.sendMessage(ChatColor.BLUE + "Unloaded Worlds...");

            File dir = new File("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\original_map");

            removeDirectory(dir);

            commandSender.sendMessage(ChatColor.RED + "Deleted Old Files...");

            try {
                // source & destination directories
                Path src = Paths.get("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\bedwars_map");
                Path dest = Paths.get("C:\\Users\\adyow\\Desktop\\Minecraft\\Servers\\BedWarsTest\\original_map");

                // create stream for `src`
                Stream<Path> files = Files.walk(src);

                // copy all files and folders from `src` to `dest`
                files.forEach(file -> {
                    try {
                        Files.copy(file, dest.resolve(src.relativize(file)),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // close the stream
                files.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            commandSender.sendMessage(ChatColor.YELLOW + "Coppied Schematic To Destination...");

            Bukkit.getServer().createWorld(new WorldCreator("bedwars_map"));
            commandSender.sendMessage(ChatColor.GREEN + "Loaded New World...");

            commandSender.sendMessage(ChatColor.GREEN + "Reloading Server...");
            Bukkit.getServer().reload();


        }

        return false;
    }

    public static void removeDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    removeDirectory(aFile);
                }
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
}
