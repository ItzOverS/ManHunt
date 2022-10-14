package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.ManHunt;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

import static me.overlight.Managers.MethodManager.StopManHunt;

public class OnPlayerDied implements Listener {
    @EventHandler
    public void OnEntityDeath(EntityDeathEvent e) {
        if (!VariableManager.PluginStats) {
            return;
        }
        if (!VariableManager.GameStats) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            e.setDroppedExp(0);
            Player p = (Player) e.getEntity();
            for (Player x : VariableManager.Runners) {
                if (x == p) {
                    ((Player)e.getEntity()).setGameMode(GameMode.SPECTATOR);
                    e.getEntity().sendMessage(ChatColor.RED + "You died!");
                    ((Player)e.getEntity()).setPlayerListName(ChatColor.RED + "[Died]" + e.getEntity().getName());
                    /*BanList s = new BanList() {
                        @Override
                        public BanEntry getBanEntry(String target) {
                            return null;
                        }

                        @Override
                        public BanEntry addBan(String target, String reason, Date expires, String source) {
                            return null;
                        }

                        @Override
                        public Set<BanEntry> getBanEntries() {
                            return null;
                        }

                        @Override
                        public boolean isBanned(String target) {
                            return false;
                        }

                        @Override
                        public void pardon(String target) {

                        }
                    };
                    //s.addBan(p.getName(), "Thanks for playing", null, null);
                }
            }*/
                }

            }
        }
        if(e.getEntity() instanceof EnderDragon){
            try {
                StopManHunt(null);
            } catch (Exception ignored) { }
            for(Player runner : ArrayOptions.GetNotNullItems(VariableManager.Runners))
                try{
                    runner.sendTitle(ChatColor.GOLD + "Victory", "", 20, 100, 20);
                } catch(Exception ignored) { }
            for (Player hunter : ArrayOptions.GetNotNullItems(VariableManager.Hunters)) {
                try {
                    hunter.sendTitle(ChatColor.RED + "Game Over", "", 20, 100, 20);
                } catch (Exception ignored) {
                }
                for (int i = 0; i < 20; i++) {
                    try {
                        Objects.requireNonNull(ManHunt.GetPlugin().getServer().getWorld("world")).spawnEntity(hunter.getLocation(), EntityType.FIREWORK, true);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnPlayerDepth (PlayerDeathEvent e){
        if (!VariableManager.PluginStats) {
            return;
        }
        if (!VariableManager.GameStats) {
            e.setDeathMessage("");
            return;
        }

        e.setDroppedExp(0);
        String DepthMessage = e.getDeathMessage();
        e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + " died!");
        assert DepthMessage != null;
        if(DepthMessage.contains("was slain by")){
            e.setDeathMessage(ChatColor.RED + DepthMessage.split(" ")[DepthMessage.split(" ").length-1] + ChatColor.GOLD + " killed " + ChatColor.RED + DepthMessage.split(" ")[0]);
        }

        Player[] Runners = ArrayOptions.GetNotNullItems(VariableManager.Runners);
        boolean[] deadRunners = new boolean[Runners.length];
        for(int i = 0; i < Runners.length; i++)
            deadRunners[i] = Runners[i].isDead();
        if(ArrayOptions.AllArrayLike(deadRunners, true)) {
            try {
                StopManHunt(null);
            } catch (Exception ignored) {
            }
            for (Player hunter : ArrayOptions.GetNotNullItems(VariableManager.Hunters)) {
                try {
                    hunter.sendTitle(ChatColor.GOLD + "Victory", "", 20, 100, 20);
                } catch (Exception ignored) {
                }
                for (int i = 0; i < 20; i++) {
                    try {
                        Objects.requireNonNull(ManHunt.GetPlugin().getServer().getWorld("world")).spawnEntity(hunter.getLocation(), EntityType.FIREWORK, true);
                    } catch (Exception ignored) {
                    }
                }
            }
            for (Player runner : ArrayOptions.GetNotNullItems(VariableManager.Runners)){
                try {
                    runner.sendTitle(ChatColor.RED + "Game Over", "", 20, 100, 20);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
