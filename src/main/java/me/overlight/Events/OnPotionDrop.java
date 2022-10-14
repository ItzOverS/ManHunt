package me.overlight.Events;
import me.overlight.Managers.VariableManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

public class OnPotionDrop implements Listener {
    @EventHandler
    public void OnPotionDropped(PotionSplashEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
        }
    }
}
