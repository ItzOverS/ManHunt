package me.overlight.Events;

import com.google.errorprone.annotations.Var;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Managers.VariableManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnPlayerSendCommands implements Listener {
    @EventHandler
    public void OnPlayerWantToSendCommand (PlayerCommandPreprocessEvent e){
        if(VariableManager.GameStats){
            String[] Commands = {
                    "manhunt",
                    "manhuntplusplus",
                    "mh",
                    "mhpp",
                    "track",
                    "kills",
                    "target",
                    "rl",
                    "reload"
            };
            List<String> str = new ArrayList<>();
            str.addAll(Arrays.asList(Commands));
            if(!str.contains(e.getMessage().substring(1).split(" ")[0])){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "ManHunt is already started!");
                e.getPlayer().sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "You can't send commands!");
            }
        }
    }

    @EventHandler
    public void OnPlayerSendCommand (PlayerCommandSendEvent e){
    }
}
