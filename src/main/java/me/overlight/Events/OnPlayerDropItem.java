package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class OnPlayerDropItem implements Listener {
    @EventHandler
    public void OnPlayerDrop(PlayerDropItemEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
            ItemStack droppedItem = e.getItemDrop().getItemStack();
            if (Objects.requireNonNull(droppedItem.getItemMeta()).getDisplayName().substring(2).equals("Target Compass")) {
                e.setCancelled(true);
                Inventory s = Bukkit.createInventory(null, 18, ChatColor.GREEN + "Target: ");
                int index = 0;
                for (Player x : VariableManager.Runners) {
                    if (x != e.getPlayer()) {
                        ItemStack v = new ItemStack(Material.PLAYER_HEAD, 1);
                        ItemMeta vMeta = v.getItemMeta();
                        assert vMeta != null;
                        vMeta.setDisplayName(x.getDisplayName());
                        v.setItemMeta(vMeta);
                        s.setItem(index, v);
                        index += 1;
                    }
                }
                e.getPlayer().openInventory(s);
            }
        }
    }
}
