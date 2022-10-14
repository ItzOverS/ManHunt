package me.overlight.Commands.Commands;

import me.overlight.Libraries.ArrayOptions;
import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vote implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;
        if(!VariableManager.PluginStats){
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin has been disabled...");
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "No command of this plugin not work!");
            return true;
        }
        if(args.length == 0)
            return false;
        if(VariableManager.RanksVoteToggle) {
            String voteID = args[0].toLowerCase();
            if (!ArrayOptions.OR(new String[]{"hunter", "runner", "spammer", "guardian", "spectator"}, voteID))
                return false;
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GOLD + " You voted for " + ChatColor.BLUE + voteID);
            VariableManager.Votes.put(sender.getName(),
                    (voteID.equals("hunter")) ? VariableManager.Roles.HUNTER :
                            (voteID.equals("runner")) ? VariableManager.Roles.RUNNER :
                                    (voteID.equals("spectator")) ? VariableManager.Roles.SPECTATOR :
                                            (voteID.equals("spammer")) ? VariableManager.Roles.SPAMMER :
                                                    (voteID.equals("guardian")) ? VariableManager.Roles.GUARDIAN :
                                                            VariableManager.Roles.NULL
            );
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp())
                    p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + sender.getName() + " " + ChatColor.GOLD + "voted for " + ChatColor.BLUE + voteID);
            }
        } else{
            sender.sendMessage(ChatColor.RED + "Vote has stopped...");
        }
        return true;
    }
}
