package me.overlight.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class OnPlayerKicked implements Listener {
    @EventHandler
    public void OnPlayerKick (PlayerKickEvent e){
        e.setLeaveMessage("");
        e.setCancelled(false);
    }
}
