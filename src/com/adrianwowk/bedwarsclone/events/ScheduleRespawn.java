package com.adrianwowk.bedwarsclone.events;

import com.adrianwowk.bedwarsclone.BedWars;
import com.sun.prism.shader.DrawEllipse_LinearGradient_PAD_AlphaTest_Loader;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ScheduleRespawn{

    public static void schedule(Plugin plugin, Player player, long delayTicks) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                BedWars.game.respawnPLayer(player);
            }
        }, delayTicks);
    }
}
