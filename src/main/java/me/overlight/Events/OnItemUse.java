package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Objects;

import static me.overlight.Libraries.InventoryGen.GenInv;

public class OnItemUse implements Listener {
    @EventHandler
    public void OnItemUsed(PlayerInteractEvent e) {
        if (!VariableManager.PluginStats) {
            e.setCancelled(true);
            return;
        }
        if (VariableManager.GameStats) {
            OnPlayerWalk.ShowPlayersHP();
        }
        PlayerInventory Inv = e.getPlayer().getInventory();
        if (e.getHand() == null)
            return;
        if (Inv.getItem(Objects.requireNonNull(e.getHand())).getType() == Material.COMPASS && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (Objects.requireNonNull(Inv.getItem(e.getHand()).getItemMeta()).getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&cTarget Compass"))) {
                if (ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1 || ArrayOptions.ContainsPlayer(VariableManager.Guardian, e.getPlayer()) != -1) {
                    if(VariableManager.GameStats) {
                        {
                            HashMap<Integer, ItemStack> LifePlayers = new HashMap<>();
                            int index = 0;
                            for(Player p: ArrayOptions.GetNotNullItems(VariableManager.Runners)){
                                if(!p.isDead()) {
                                    LifePlayers.put(index, InventoryGen.GenItem(1, ChatColor.YELLOW + p.getName(), null, Material.PLAYER_HEAD));
                                    index++;
                                }
                            }
                            LifePlayers.put(53, InventoryGen.GenItem(1, ChatColor.GOLD + "Particle track: " + ((VariableManager.ParticleTrack.get(e.getPlayer().getName()))?ChatColor.GREEN + "ON":ChatColor.RED + "OFF"), null, Material.GLOWSTONE_DUST));
                            e.getPlayer().openInventory(InventoryGen.GenInv(54, LifePlayers, ChatColor.RED + "Track"));
                        }}
                }
            }
        }
    }

    @EventHandler
    public void OnItemUsed(PlayerInteractEntityEvent e) {
        if (!VariableManager.PluginStats) {
            e.setCancelled(true);
            return;
        }
        PlayerInventory Inv = e.getPlayer().getInventory();
        if (e.getHand() == null)
            return;
        if (Inv.getItem(e.getHand()).getType() == Material.COMPASS) {
            if (ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1) {
                if (Objects.requireNonNull(Inv.getItem(e.getHand()).getItemMeta()).getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&cTarget Compass"))) {
                    if (VariableManager.GameStats) {
                        Inventory Inv2 = GenInv(54, VariableManager.Runners, new ItemStack(Material.PLAYER_HEAD, 1), ChatColor.RED + "Track");
                        e.getPlayer().openInventory(Inv2);
                    }
                }
            }
        }
    }
}
