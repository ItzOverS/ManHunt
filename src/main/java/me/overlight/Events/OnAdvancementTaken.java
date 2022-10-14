package me.overlight.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class OnAdvancementTaken implements Listener {
    boolean AdvancementCollected = true;

    @EventHandler
    public void OnRecipeUnlocked (PlayerRecipeDiscoverEvent e){
        AdvancementCollected = false;
    }

    @EventHandler
    public void OnAdvancementTake(PlayerAdvancementDoneEvent e) {
        if (AdvancementCollected) {
            e.getPlayer().giveExp(1);
        }
        AdvancementCollected = true;
    }
}
