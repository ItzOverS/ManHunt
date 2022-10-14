package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class OnBukkitUse implements Listener {
    @EventHandler
    public void OnBukkitUsed(PlayerBucketEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBukkitEmpty(PlayerBucketEmptyEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBukkitFilled(PlayerBucketFillEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
        }
    }
}
