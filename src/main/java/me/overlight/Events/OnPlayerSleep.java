package me.overlight.Events;

import me.overlight.ManHunt;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.Objects;

public class OnPlayerSleep implements Listener {
    @EventHandler
    public void OnPlayerSleepX (PlayerBedEnterEvent e) {
        ManHunt thisS = ManHunt.GetPlugin();
        World sleepWorld = e.getPlayer().getWorld();
        long worldTime = sleepWorld.getTime();
        if(worldTime > 1000 && worldTime < 10000){
            // day
            Objects.requireNonNull(thisS.getServer().getWorld(sleepWorld.getUID())).setTime(13000);
        }
        else{
            // night
            Objects.requireNonNull(thisS.getServer().getWorld(sleepWorld.getUID())).setTime(1000);
        }
    }
}
