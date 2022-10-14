package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Objects;

public class OnPlayerJoinServer implements Listener {
    @EventHandler
    public void OnPlayerJoin (PlayerJoinEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        VariableManager.OnlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
        if(Bukkit.getOnlinePlayers().size() > VariableManager.MaxServerPlayers) {
            if (!e.getPlayer().isOp()) {
                VariableManager.KickedPlayer = true;
                e.getPlayer().kickPlayer(ChatColor.RED + "Server is full");
                e.setJoinMessage("");
                return;
            }
        }
        if(VariableManager.GameStats){
            if(VariableManager.NameRules.containsKey(e.getPlayer().getName())){
                Player p = e.getPlayer();
                if(Objects.equals(VariableManager.NameRules.get(p.getName()), VariableManager.Roles.HUNTER)){
                    try { p.setGameMode(GameMode.SURVIVAL); } catch (Exception ignored) { }
                    p.setDisplayName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + p.getName());
                    p.setPlayerListName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + p.getName());
                    VariableManager.Hunters[ArrayOptions.GetNull(VariableManager.Hunters)] = p;
                    VariableManager.DicRoles.put(p, VariableManager.Roles.HUNTER);
                    VariableManager.MapRoles.put(p, VariableManager.Roles.HUNTER);
                    try { p.setAllowFlight(false); p.setFlying(false); } catch (Exception ignored) { }
                }
                else if(Objects.equals(VariableManager.NameRules.get(p.getName()), VariableManager.Roles.RUNNER)){
                    try {
                        if(p.isDead()){
                            p.setGameMode(GameMode.SPECTATOR);
                        } else{
                            p.setGameMode(GameMode.SURVIVAL);
                        }
                    } catch (Exception ignored) { }
                    p.setDisplayName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + p.getName());
                    p.setPlayerListName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + p.getName());
                    VariableManager.Runners[ArrayOptions.GetNull(VariableManager.Runners)] = p;
                    VariableManager.DicRoles.put(p, VariableManager.Roles.RUNNER);
                    VariableManager.MapRoles.put(p, VariableManager.Roles.RUNNER);
                    try { p.setAllowFlight(false); p.setFlying(false); } catch (Exception ignored) { }
                }
                else if(Objects.equals(VariableManager.NameRules.get(p.getName()), VariableManager.Roles.SPECTATOR)){
                    try { p.setGameMode(GameMode.SPECTATOR); } catch (Exception ignored) { }
                    p.setDisplayName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + p.getName());
                    p.setPlayerListName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + p.getName());
                    VariableManager.Spectators[ArrayOptions.GetNull(VariableManager.Spectators)] = p;
                    VariableManager.DicRoles.put(p, VariableManager.Roles.SPECTATOR);
                    VariableManager.MapRoles.put(p, VariableManager.Roles.SPECTATOR);
                    try { p.setAllowFlight(true); p.setFlying(true); } catch (Exception ignored) { }
                }
                {
                    String JoinMSG = String.valueOf(VariableManager.file.get("Reconnect-Message"));
                    JoinMSG = ChatColor.translateAlternateColorCodes('&', JoinMSG);
                    JoinMSG = JoinMSG.replace("%USERNAME%", e.getPlayer().getName());
                    JoinMSG = JoinMSG.replace("%ID%", String.valueOf(Bukkit.getOnlinePlayers().size()+1));
                    e.setJoinMessage(JoinMSG);
                }
            }
            else{
                VariableManager.KickedPlayer = true;
                e.getPlayer().kickPlayer(ChatColor.RED + "Man Hunt is already started!\nYou can't join this server until Manhunt finished");
            }
            return;
        }
        {
            String JoinMSG = String.valueOf(VariableManager.file.get("Join-Message"));
            JoinMSG = ChatColor.translateAlternateColorCodes('&', JoinMSG);
            JoinMSG = JoinMSG.replace("%USERNAME%", e.getPlayer().getName());
            JoinMSG = JoinMSG.replace("%ID%", String.valueOf(Bukkit.getOnlinePlayers().size()));
            e.setJoinMessage(JoinMSG);
        }
        e.getPlayer().setPlayerListName(ChatColor.RED + "[Player] " + ChatColor.GOLD + e.getPlayer().getName());
        try {
            if(VariableManager.SpawnLocation != null)
                e.getPlayer().teleport(VariableManager.SpawnLocation);
            else
                e.getPlayer().teleport(new Location(VariableManager.GetPlugin().getServer().getWorld("world"), 0, Objects.requireNonNull(VariableManager.GetPlugin().getServer().getWorld("world")).getHighestBlockYAt(0, 0), 0));
        } catch(Exception ignored) { }
        try {
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
        } catch(Exception ignored) { }
        try {
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(true);
        } catch(Exception ignored) { }
        Objects.requireNonNull(VariableManager.GetPlugin().getServer().getWorld("world")).setGameRule(GameRule.FALL_DAMAGE, false);
        try{
            if(VariableManager.SpawnLocation != null){
                e.getPlayer().teleport(VariableManager.SpawnLocation);
            }
            else{
                e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0, e.getPlayer().getWorld().getHighestBlockYAt(0, 0) + 5, 0));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 3, 5));
            }
        } catch(Exception ignored) { }
    }
}
