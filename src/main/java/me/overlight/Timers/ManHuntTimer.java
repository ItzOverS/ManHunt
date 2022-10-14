package me.overlight.Timers;

import me.overlight.Events.OnPlayerWalk;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManHuntTimer {
    public void StartTimer (){
        VariableManager.ManHuntTimerTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(VariableManager.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                VariableManager.time[0]++;
                if(VariableManager.time[0] == 60){
                    VariableManager.time[0] = 0;
                    VariableManager.time[1]++;
                } else if (VariableManager.time[1] == 60){
                    VariableManager.time[1] = 0;
                    VariableManager.time[2]++;
                } else if (VariableManager.time[2] == 24){
                    VariableManager.time[2] = 0;
                    VariableManager.time[3]++;
                } else if (VariableManager.time[3] == 30){
                    VariableManager.time[3] = 0;
                    VariableManager.time[4]++;
                }
            }
        }, 20, 20);
    }
}
