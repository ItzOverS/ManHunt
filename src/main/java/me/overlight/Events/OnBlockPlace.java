package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {
    @EventHandler
    public void OnBlockPlaced(BlockPlaceEvent e) {
        if (!VariableManager.PluginStats) {
            e.setCancelled(false);
            return;
        }
        if (!VariableManager.GameStats) {
            e.setCancelled(true);
        }
        if(!VariableManager.AllowMovement){
            if(VariableManager.MapRoles.get(e.getPlayer()) == VariableManager.Roles.HUNTER){
                e.setCancelled(true);
            }
        }
    }
}