package me.overlight.Commands.Commands;

import me.overlight.Managers.VariableManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kills implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(!VariableManager.PluginStats){
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin has been disabled...");
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "No command of this plugin not work!");
                return true;
            }
            if(VariableManager.kills.containsKey((Player)sender)){
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You have " + ChatColor.GOLD + VariableManager.kills.get((Player)sender) + ChatColor.GREEN + " kills!");
            }
            else{
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You don't have any kill yet!");
            }
        }
        return false;
    }
}
