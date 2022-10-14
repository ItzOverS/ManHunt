package me.overlight.Events;

import me.overlight.Libraries.InventoryGen;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static me.overlight.Managers.VariableManager.challenge;

public class OnPlayerRespawn implements Listener {
    @EventHandler
    public void OnPlayerRespawned (PlayerRespawnEvent e){
        if(ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1 || ArrayOptions.ContainsPlayer(VariableManager.Guardian, e.getPlayer()) != -1){
            if(VariableManager.TrackingCompass)
                e.getPlayer().getInventory().setItem(8, me.overlight.Commands.Commands.ManHunt.GenerateTargetingCompass());
            e.getPlayer().teleport(VariableManager.HuntersLocation);
            if(challenge == VariableManager.Challenges.TANKERHUNTER){
                e.getPlayer().getInventory().setBoots(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_BOOTS));
                e.getPlayer().getInventory().setChestplate(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_CHESTPLATE));
                e.getPlayer().getInventory().setLeggings(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_LEGGINGS));
                e.getPlayer().getInventory().setHelmet(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_HELMET));
                e.getPlayer().getInventory().setItem(0, InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_SWORD));
                e.getPlayer().getInventory().setItem(1, InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_AXE));
            }
        }
        if(!VariableManager.GameStats){
            if(VariableManager.SpawnLocation != null)
                e.getPlayer().teleport(VariableManager.SpawnLocation);
            else
                e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0, e.getPlayer().getWorld().getHighestBlockYAt(0, 0) + 5, 0));
        }
    }
}
