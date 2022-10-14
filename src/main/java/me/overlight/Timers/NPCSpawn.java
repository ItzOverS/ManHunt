package me.overlight.Timers;

import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NPCSpawn {
    public void NPCTimerStart (){
        VariableManager.NPCSpawnTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(VariableManager.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                VariableManager.NPCSpawnTimer--;
                if(VariableManager.NPCSpawnTimer % 10 == 0 || VariableManager.NPCSpawnTimer < 6){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage(ChatColor.RED + "Warn: " + ChatColor.GOLD + "The killers will spawn in " + VariableManager.NPCSpawnTimer);
                    }
                }
            }
        }, 0, 20);
    }
}
