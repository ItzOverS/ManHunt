package me.overlight.Events;

import me.overlight.Libraries.InventoryGen;
import me.overlight.Managers.MethodManager;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class OnEntityDamageByEntity implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void OnEntityDamagedByEntity (EntityDamageByEntityEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
            return;
        }
        e.setCancelled(false);
        boolean IsFriend = false;
        if(e.getDamager() instanceof Player){
            if(e.getEntity() instanceof Player){
                for(Player x: VariableManager.Hunters){
                    for(Player y: VariableManager.Hunters){
                        if(x != y){
                            if(e.getDamager() == x){
                                if(e.getEntity() == y){
                                    e.setCancelled(true);
                                    IsFriend = true;
                                }
                            }
                        }
                    }
                }
                for(Player x: VariableManager.Runners){
                    for(Player y: VariableManager.Runners){
                        if(x != y){
                            if(e.getDamager() == x){
                                if(e.getEntity() == y){
                                    e.setCancelled(true);
                                    IsFriend = true;
                                }
                            }
                        }
                    }
                }
                if(!VariableManager.AllowMovement){
                    if(ArrayOptions.ContainsPlayer(VariableManager.Hunters, (Player)e.getEntity()) != -1 || ArrayOptions.ContainsPlayer(VariableManager.Hunters, (Player)e.getDamager()) != -1){
                        e.setCancelled(true);
                        return;
                    }
                }
                if(VariableManager.HuntMode == VariableManager.Modes.FACTIONS)
                    if(VariableManager.Teams.get(ArrayOptions.GetGroupName(e.getEntity().getName(), VariableManager.Teams)).contains(e.getDamager().getName()))
                        IsFriend = true;
                if(!IsFriend){
                    if(VariableManager.ShowPlayersHealth) {
                        double TargetHP = Math.floor(((Player) e.getEntity()).getHealth());
                        ((Player) e.getDamager()).sendTitle("", (TargetHP < 1) ? ChatColor.RED + "Killed" : TargetHP + "/" + Math.floor(((Player) e.getEntity()).getAttribute(GENERIC_MAX_HEALTH).getValue()));
                        for (Player x : VariableManager.Runners) {
                            if (e.getEntity() == x) {
                                ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 3, false, false, false));
                            }
                        }
                    }
                    if(e.getEntity() instanceof Player) {
                        OnPlayerWalk.ShowPlayersHP();
                    }
                }
                if(((Player) e.getEntity()).getHealth() - e.getDamage() < 1){
                    if(e.getEntity() instanceof Player) {
                        if (MethodManager.getPlayerRank((Player)e.getEntity())== VariableManager.Roles.HUNTER ||
                                MethodManager.getPlayerRank((Player)e.getEntity())== VariableManager.Roles.GUARDIAN
                        ){
                            ((Player) e.getEntity()).getInventory().clear(InventoryGen.GetIndexItemAt(((Player) e.getEntity()).getInventory(), me.overlight.Commands.Commands.ManHunt.GenerateTargetingCompass()));
                            ((Player) e.getEntity()).getInventory().clear(InventoryGen.GetIndexItemAt(((Player) e.getEntity()).getInventory(), InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_AXE)));
                            ((Player) e.getEntity()).getInventory().clear(InventoryGen.GetIndexItemAt(((Player) e.getEntity()).getInventory(), InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_SWORD)));
                            ((Player) e.getEntity()).getInventory().setHelmet(null);
                            ((Player) e.getEntity()).getInventory().setChestplate(null);
                            ((Player) e.getEntity()).getInventory().setLeggings(null);
                            ((Player) e.getEntity()).getInventory().setBoots(null);
                        }
                    }
                    int runnersSearch = ArrayOptions.ContainsPlayer(VariableManager.Runners, (Player)e.getDamager());
                    int huntersSearch = ArrayOptions.ContainsPlayer(VariableManager.Hunters, (Player)e.getDamager());
                    VariableManager.KilledPlayers.put(e.getEntity().getName(), e.getDamager().getName());
                    if(runnersSearch != -1 && huntersSearch == -1){
                        if(VariableManager.kills.containsKey((Player)e.getDamager())){
                            VariableManager.kills.put((Player)e.getDamager(), VariableManager.kills.get((Player)e.getDamager()) + 1);
                        }
                        else{
                            VariableManager.kills.put((Player)e.getDamager(), 1);
                        }
                    }
                }
            }
        }
    }
}