package me.overlight.Commands.Commands;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.overlight.Libraries.InventoryGen.GenInv;

public class Target implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!VariableManager.PluginStats){
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin has been disabled...");
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "No command of this plugin not work!");
            return true;
        }
        if(!VariableManager.GameStats){
            return true;
        }
        if(!VariableManager.TrackingCompass){
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Compasses are disabled!");
        }
        // /target _OverLight_
        // /track _OverLight_
        if(args.length > 0){
            int IsHunter = ArrayOptions.ContainsPlayer(VariableManager.Hunters, (Player)sender);
            if(IsHunter != -1){
                if(sender instanceof Player) {
                    VariableManager.TargetCompass.remove((Player) sender);
                    for (Player x : VariableManager.Runners) {
                        if (x == Bukkit.getPlayer(args[0])) {
                            VariableManager.TargetCompass.put((Player) sender, Bukkit.getPlayer(args[0]));
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Now tracking " + ChatColor.RED + args[0]);
                            return true;
                        }
                    }
                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[0] + " is not a runner");
                }
            }
            else{
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Only " + ChatColor.RED + "hunters" + ChatColor.GREEN + " can use this command");
            }
        }
        else {
            ItemStack Item = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta IMeta = Item.getItemMeta(); try{ IMeta.setDisplayName(""); } catch(Exception ignored) { }
            Item.setItemMeta(IMeta);
            Inventory Inv = InventoryGen.GenInv(54, VariableManager.Runners, Item, ChatColor.RED + "Track");
            ((Player)sender).openInventory(Inv);
        }
        return true;
    }
}
