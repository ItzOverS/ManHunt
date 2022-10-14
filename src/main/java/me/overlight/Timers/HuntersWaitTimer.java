package me.overlight.Timers;

import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HuntersWaitTimer {
    public void HuntersTimer(){
        VariableManager.HuntersWaitTimerTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(VariableManager.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                for(Player hunter: VariableManager.Hunters) {
                    if (VariableManager.HuntersMoveTime < 1) {
                        try {
                            hunter.sendTitle(ChatColor.GREEN + "GO!", "");
                        } catch (Exception ignored) { }
                        VariableManager.AllowMovement = true;
                        Bukkit.getScheduler().cancelTask(VariableManager.HuntersWaitTimerTaskID);
                        return;
                    }
                    try {
                        hunter.sendTitle(ChatColor.GOLD + "Start in " + ChatColor.RED + String.valueOf(VariableManager.HuntersMoveTime), "");
                    } catch(Exception ignored) { }
                }
                VariableManager.HuntersMoveTime--;
            }
        }, 0, 20);
    }
}
