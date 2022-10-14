package me.overlight.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class OnPlayerPickUpArrow implements Listener {
    @EventHandler
    public void OnPlayerPickupArrow (PlayerPickupArrowEvent e){
        e.setCancelled(true);
    }
}
