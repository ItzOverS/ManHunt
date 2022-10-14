package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class OnExplodeCreated implements Listener {
    public void ExplodeCreated (ExplosionPrimeEvent e){
        if(!VariableManager.GameStats){
            e.setCancelled(true);
            e.setRadius(0);
        }
    }
}