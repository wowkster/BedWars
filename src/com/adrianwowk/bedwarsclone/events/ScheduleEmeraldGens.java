package com.adrianwowk.bedwarsclone.events;

import com.adrianwowk.bedwarsclone.BedWars;
import com.adrianwowk.bedwarsclone.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ScheduleEmeraldGens implements Runnable {

    private Plugin plugin;
    private int time = 0;

    public ScheduleEmeraldGens(Plugin p) {
        plugin = p;
    }

    @Override
    public void run() {
        if (BedWars.game != null) {
            for (ArmorStand as : BedWars.game.getEmeraldGenTimeLabels()) {
                as.setCustomName(ChatColor.YELLOW + "Spawns in " + ChatColor.RED + (30 - (time % 30)) + ChatColor.YELLOW + " seconds");
            }
            if (time % 30 == 0){
                for (ArmorStand as : BedWars.game.getEmeraldTierLabels()) {
                    Bukkit.getWorld("bedwars_map").dropItem(as.getLocation(), new ItemStack(Material.EMERALD));
                }
            }
            time++;
        }
    }

    public void schedule(long delayTicks) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> this.run(), 0L, delayTicks);
    }

    public void reset(){
        this.time = 0;
    }

}
