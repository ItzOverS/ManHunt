package me.overlight.Timers;

import me.overlight.Managers.VariableManager;
import me.overlight.ManHunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameStartTimer {
    public void GameTimer(){
        VariableManager.GameStartTimerTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ManHunt.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Player hunter: Bukkit.getOnlinePlayers()) {
                    if (VariableManager.GameStartTime == 0) {
                        hunter.sendTitle(ChatColor.GREEN + "GO!", "", 10, 20, 10);
                        VariableManager.GameStarted = true;
                        Bukkit.getScheduler().cancelTask(VariableManager.GameStartTimerTaskID);
                        return;
                    }
                    hunter.sendTitle(ChatColor.GOLD + "Start in " + ChatColor.RED + String.valueOf(VariableManager.GameStartTime), "");
                    VariableManager.GameStartTime--;
                }
            }
        }, 0, 20);
    }
}
