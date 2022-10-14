package me.overlight.Timers;

import me.overlight.Commands.Commands.ManHunt;
import me.overlight.Managers.MethodManager;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class GamePowerManager {
    public void Manager(){
        VariableManager.GamePowerManagerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(VariableManager.GetPlugin(), new Runnable() {
            @Override
            public void run() {
                if(!VariableManager.GameStats){
                    for(Player p: Bukkit.getOnlinePlayers()){
                        p.getInventory().clear();
                        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 255, false, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100, 255, false, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 100, 255, false, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 255, false, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 255, false, false, false));
                        p.setAllowFlight(true);
                        if(VariableManager.SpawnLocation == null)
                            p.setBedSpawnLocation(new Location(p.getWorld(), 0, p.getWorld().getHighestBlockAt(0, 0).getY() + 5, 0));
                        else
                            p.setBedSpawnLocation(VariableManager.SpawnLocation);
                        p.setGameMode(GameMode.ADVENTURE);
                        p.setExp(0);
                        if(VariableManager.MapRoles.containsKey(p)){
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are " + ChatColor.GOLD + ArrayOptions.ToBiggerChar(VariableManager.RolesToStringConvert(VariableManager.MapRoles.get(p)), 0)));
                        }
                    }
                    Objects.requireNonNull(me.overlight.ManHunt.GetPlugin().getServer().getWorld("world")).setTime(6000);
                } else{
                    for(Player p: Bukkit.getOnlinePlayers()){
                        p.setAllowFlight(false);
                        p.setCompassTarget(p.getLocation());
                        if(ArrayOptions.ContainsPlayer(VariableManager.Hunters, p) == -1 && ArrayOptions.ContainsPlayer(VariableManager.Guardian, p) == -1){
                            if(InventoryGen.GetIndexItemAt(p.getInventory(), ManHunt.GenerateTargetingCompass()) != -1){
                                p.getInventory().clear(InventoryGen.GetIndexItemAt(p.getInventory(), ManHunt.GenerateTargetingCompass()));
                            }
                        }
                    }
                }
            }
        }, 0, 60);
    }
}
