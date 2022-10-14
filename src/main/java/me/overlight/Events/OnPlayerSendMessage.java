package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerSendMessage implements Listener {
    @EventHandler
    public void OnPlayerSendChat(AsyncPlayerChatEvent e){
        if(!VariableManager.PluginStats){
            return;
        }
        e.setCancelled(true);
        if(!VariableManager.ChatEnabled){
            e.getPlayer().sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Chat was " + ChatColor.BOLD + "disabled" + ChatColor.RESET + ChatColor.RED + "!");
            return;
        }
        if(!e.getMessage().startsWith("/") || e.getMessage().startsWith("/say")) {
            Player[] ps = ArrayOptions.ToPlayerObject2(ArrayOptions.GetNotNullItems(VariableManager.MutedPlayers));
            if (ArrayOptions.ContainsPlayer(ps, e.getPlayer()) != -1) {
                e.getPlayer().sendMessage(ChatColor.RED + "You can't send message! because you has been muted!");
                return;
            }
        }
        if(!VariableManager.GameStats){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(ChatColor.RED + "<" + ChatColor.BLUE + e.getPlayer().getName() + ChatColor.RED + "> " + ChatColorSend(e.getMessage()));
            }
            return;
        }
        if(!e.getMessage().startsWith("!")) {
            Player[] list =
                    (ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1)? VariableManager.Hunters :
                            (ArrayOptions.ContainsPlayer(VariableManager.Runners, e.getPlayer()) != -1)? VariableManager.Runners :
                                    (ArrayOptions.ContainsPlayer(VariableManager.Spectators, e.getPlayer()) != -1)? VariableManager.Spectators :
                                            (ArrayOptions.ContainsPlayer(VariableManager.Spammer, e.getPlayer()) != -1)? VariableManager.Spammer :
                                                    (ArrayOptions.ContainsPlayer(VariableManager.Guardian, e.getPlayer()) != -1)? VariableManager.Guardian :
                                                            null;
            assert list != null;
            for(Player p: list){
                sendMessageColorable(e, p);
            }
        } else{
            String Tag =
                    (ArrayOptions.ContainsPlayer(VariableManager.Hunters, e.getPlayer()) != -1)? "Hunter" :
                            (ArrayOptions.ContainsPlayer(VariableManager.Runners, e.getPlayer()) != -1)? "Runner" :
                                    (ArrayOptions.ContainsPlayer(VariableManager.Spectators, e.getPlayer()) != -1)? "Spectator" :
                                            (ArrayOptions.ContainsPlayer(VariableManager.Spammer, e.getPlayer()) != -1)? "Spammer" :
                                                    (ArrayOptions.ContainsPlayer(VariableManager.Guardian, e.getPlayer()) != -1)? "Guardian" :
                                                            "";
            if(!Tag.equals("Spectator")) {
                for (Player x : Bukkit.getOnlinePlayers()) {
                    String MSG = ChatColorSend(e.getMessage().substring(1));
                    x.sendMessage(ChatColor.BLUE + "<" + ChatColor.RED + Tag + ChatColor.BLUE + "><" + ChatColor.RED + e.getPlayer().getName() + ChatColor.BLUE + "> " + MSG);
                }
            }
        }
    }

    private void sendMessageColorable(AsyncPlayerChatEvent e, Player y) {
        try {
            String MSG = e.getMessage();
            if(VariableManager.ColorableChat)
                MSG = ChatColor.translateAlternateColorCodes('&', MSG);
            y.sendMessage(ChatColor.BLUE + "<" + ChatColor.RED + e.getPlayer().getName() + ChatColor.BLUE + "> " + ((!VariableManager.ColorableChat)?ChatColor.GREEN:"") + MSG);

        } catch(Exception ignored) { }
    }

    private String ChatColorSend (String MSG){
        return (VariableManager.ColorableChat)?ChatColor.translateAlternateColorCodes('&', MSG):ChatColor.GREEN + MSG;
    }
}
