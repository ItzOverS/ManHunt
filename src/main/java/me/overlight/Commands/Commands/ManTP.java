package me.overlight.Commands.Commands;

import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ManTP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!VariableManager.PluginStats){
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin has been disabled...");
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "No command of this plugin not work!");
            return true;
        }
        if(args.length > 0){
            if(sender instanceof Player){
                if(Objects.requireNonNull(Bukkit.getPlayer(args[0])).isOnline()){
                    for(Player x: VariableManager.Spectators) {
                        if((Player)sender == x) {
                            ((Player) sender).teleport(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getLocation());
                        }
                    }
                }
            }
        }
        return true;
    }
}
