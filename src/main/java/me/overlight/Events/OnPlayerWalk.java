package me.overlight.Events;

import jdk.internal.org.jline.utils.Colors;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class OnPlayerWalk implements Listener {
    @EventHandler
    public void OnPlayerWalked(PlayerMoveEvent e) {
        if (!VariableManager.PluginStats) {
            return;
        }

        {
            if(!VariableManager.GameStats) {
                // Send back player from moving around the world
                Location from, to, PlayerLocation = e.getPlayer().getLocation();
                int range = 40, max_flight = 50, min_flight = 0;
                if (VariableManager.SpawnLocation != null) {
                    from = new Location(e.getPlayer().getWorld(), VariableManager.SpawnLocation.getX() + range, VariableManager.SpawnLocation.getY() + max_flight, VariableManager.SpawnLocation.getZ() + range);
                    to = new Location(e.getPlayer().getWorld(), VariableManager.SpawnLocation.getX() - range, min_flight, VariableManager.SpawnLocation.getZ() - range);
                } else {
                    from = new Location(e.getPlayer().getWorld(), range, e.getPlayer().getWorld().getHighestBlockYAt(0, 0) + max_flight, range);
                    to = new Location(e.getPlayer().getWorld(), -range, min_flight, -range);
                }
                boolean X_Check = PlayerLocation.getX() < to.getX() || PlayerLocation.getX() > from.getX();
                boolean Y_Check = PlayerLocation.getY() < to.getY() || PlayerLocation.getY() > from.getY();
                boolean Z_Check = PlayerLocation.getZ() < to.getZ() || PlayerLocation.getZ() > from.getZ();
                if (X_Check || Y_Check || Z_Check) {
                    if (!e.getPlayer().isOp()) {
                        if (VariableManager.SpawnLocation != null)
                            e.getPlayer().teleport(VariableManager.SpawnLocation);
                        else
                            e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 0, e.getPlayer().getWorld().getHighestBlockYAt(0, 0) + 5, 0));
                    }
                }
            }
        }
        if (VariableManager.GameStats) {
            {
                ShowPlayersHP();
            }
            for (Player x : VariableManager.Hunters) {
                if (x == e.getPlayer()) {
                    if (VariableManager.TargetCompass.containsKey(e.getPlayer()) && VariableManager.TargetCompass.get(e.getPlayer()) != null) {
                        Player Target = VariableManager.TargetCompass.get(e.getPlayer());
                        Player Current = e.getPlayer();
                        double Distance = Current.getLocation().distance(Target.getLocation());
                        e.getPlayer().setCompassTarget(VariableManager.TargetCompass.get(e.getPlayer()).getLocation());
                        Current.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + Target.getName() + " " + ChatColor.GOLD + "Distance: " + ArrayOptions.subFloat(Distance, 2)));
                        if(VariableManager.ParticleTrack.get(e.getPlayer().getName())){
                            DrawParticle(e.getPlayer().getLocation(), Target.getLocation(), 2);
                        }
                    } else
                        e.getPlayer().setCompassTarget(e.getPlayer().getLocation());
                }
            }
            for (Player x : VariableManager.Runners) {
                if (x == e.getPlayer()) {
                    for (Player z : VariableManager.TargetCompass.values()) {
                        if (z == x) {
                            Player Target = GetKey(VariableManager.TargetCompass, x);
                            e.getPlayer().setCompassTarget(VariableManager.TargetCompass.get(Target).getLocation());
                        }
                    }
                }
            }
        }
        if (e.getPlayer().getAllowFlight()) {
            if (!VariableManager.GameStats) {
                if (e.getPlayer().getLocation().getY() > 250) {
                    Location PLocation = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(), 246, e.getPlayer().getLocation().getZ());
                    e.getPlayer().teleport(PLocation);
                }
            }
        }
        if(!VariableManager.GameStarted){
            if(VariableManager.GameStats)
                e.setCancelled(true);
        }
        if (ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1)
            if(!VariableManager.AllowMovement)
                if(VariableManager.GameStats)
                    e.setCancelled(true);
    }

    public static void ShowPlayersHP() {
        if(VariableManager.ShowPlayersHealth) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() != GameMode.SPECTATOR) {
                    String Tag = "";
                    if (ArrayOptions.ContainsPlayer(VariableManager.Hunters, player) != -1)
                        Tag = "Hunter";
                    else if (ArrayOptions.ContainsPlayer(VariableManager.Runners, player) != -1)
                        Tag = "Runner";
                    else if (ArrayOptions.ContainsPlayer(VariableManager.Spammer, player) != -1)
                        Tag = "Spammer";
                    else if (ArrayOptions.ContainsPlayer(VariableManager.Guardian, player) != -1)
                        Tag = "Guardian";
                    double PlayerHealth = Math.round(player.getHealth());
                    player.setPlayerListName(ChatColor.BLUE + "[" + ChatColor.RED + "❤" + String.valueOf(PlayerHealth) + ChatColor.BLUE + "]" + ChatColor.RED + "[" + Tag + "] " + ChatColor.GOLD + player.getName());
                }
            }
        }
    }

    private Player GetKey(HashMap<Player, Player> map, Player value) {
        for (Player key : map.keySet()) {
            if (map.get(key) == value) {
                return key;
            }
        }
        return null;
    }
    public static void DrawParticle (Location loc1, Location loc2, double range){
        if(loc1.getWorld() == loc2.getWorld()){
            Particle.DustOptions dust = new Particle.DustOptions(Color.fromBGR(255, 100, 255), 1.0f);
            loc1 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY() + 1, loc1.getZ());
            loc2 = new Location(loc2.getWorld(), loc2.getX(), loc2.getY() + 1, loc2.getZ());
            double distance = loc1.distance(loc2);
            org.bukkit.util.Vector p1 = loc1.toVector();
            org.bukkit.util.Vector p2 = loc2.toVector();
            Vector vector = p2.clone().subtract(p1).normalize().multiply(range);
            for (double length = 0.0; length < distance; length += range) {
                if (p1.distance(loc1.toVector()) > 1.0 && p1.distance(p2) > 1.0) {
                    loc1.getWorld().spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 0, 0.0, 0.0, 0.0, (Object)dust);
                }
                p1.add(vector);
            }
        }
    }
}