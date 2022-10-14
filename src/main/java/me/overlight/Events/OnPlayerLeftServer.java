package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;

public class OnPlayerLeftServer implements Listener {
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        VariableManager.OnlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
        VariableManager.DicRoles.remove(e.getPlayer());
        VariableManager.MapRoles.remove(e.getPlayer());
        if(ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1)
            VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer())] = null;
        else if(ArrayOptions.ContainsPlayer(VariableManager.Runners, e.getPlayer()) != -1)
            VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, e.getPlayer())] = null;
        else if(ArrayOptions.ContainsPlayer(VariableManager.Spectators, e.getPlayer()) != -1)
            VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, e.getPlayer())] = null;

        String QuitMSG;
        if(!VariableManager.GameStats) {
            QuitMSG = String.valueOf(VariableManager.file.get("Quit-Message"));
            QuitMSG = ChatColor.translateAlternateColorCodes('&', QuitMSG);
            QuitMSG = QuitMSG.replace("%USERNAME%", e.getPlayer().getName());
            QuitMSG = QuitMSG.replace("%ID%", String.valueOf(Bukkit.getOnlinePlayers().size()-1));
        }
        else {
            QuitMSG = String.valueOf(VariableManager.file.get("Disconnect-Message"));
            QuitMSG = ChatColor.translateAlternateColorCodes('&', QuitMSG);
            QuitMSG = QuitMSG.replace("%USERNAME%", e.getPlayer().getName());
            QuitMSG = QuitMSG.replace("%ID%", String.valueOf(Bukkit.getOnlinePlayers().size()));
        }
        if(VariableManager.KickedPlayer){
            e.setQuitMessage("");
            VariableManager.KickedPlayer = false;
            return;
        }
        e.setQuitMessage(QuitMSG);

        try {
            Bukkit.getScheduler().cancelTask(VariableManager.BukkitTaskIDs.get(e.getPlayer().getName()));
        } catch(Exception ignored) { }
    }
}