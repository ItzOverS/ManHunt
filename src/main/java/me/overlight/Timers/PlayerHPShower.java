package me.overlight.Timers;

import me.overlight.Events.OnPlayerWalk;
import me.overlight.Managers.VariableManager;
import me.overlight.ManHunt;
import org.bukkit.Bukkit;

public class PlayerHPShower {
    public void ShowHP (){
        VariableManager.PlayerHPShowTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ManHunt.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                OnPlayerWalk.ShowPlayersHP();
            }
        }, 20, 18);
    }
}
