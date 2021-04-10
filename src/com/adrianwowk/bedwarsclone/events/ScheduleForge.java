package com.adrianwowk.bedwarsclone.events;

import com.adrianwowk.bedwarsclone.BedWars;
import com.adrianwowk.bedwarsclone.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ScheduleForge implements Runnable {

    private Plugin plugin;

    public ScheduleForge(Plugin p) {
        plugin = p;
    }

    @Override
    public void run() {
        if (BedWars.game != null) {
            for (GameTeam team : BedWars.game.getTeams()) {
                if (Math.random() < 0.15) {
                    Bukkit.getWorld("bedwars_map").dropItem(team.getTeamColor().getForgeLocation(), new ItemStack(Material.IRON_INGOT));
                }
                if (Math.random() < 0.02) {
                    Bukkit.getWorld("bedwars_map").dropItem(team.getTeamColor().getForgeLocation(), new ItemStack(Material.GOLD_INGOT));
                }
            }
        }

    }

    public void schedule(long delayTicks) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> this.run(), 0L, delayTicks);
    }
}
