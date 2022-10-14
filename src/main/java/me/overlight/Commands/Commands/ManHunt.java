package me.overlight.Commands.Commands;

import me.overlight.Events.OnInventoryItemSelected;
import me.overlight.Managers.MethodManager;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static me.overlight.Managers.MethodManager.*;

public class ManHunt implements CommandExecutor {
    public static HashMap<String, String> PermissionIDs = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        {
            PermissionIDs.put("hunter.add", "manhunt.hunter.add");
            PermissionIDs.put("hunter.remove", "manhunt.hunter.remove");
            PermissionIDs.put("hunter.list", "manhunt.hunter.list");
            PermissionIDs.put("hunter.spawn", "manhunt.hunter.spawn");
            PermissionIDs.put("runner.add", "manhunt.runner.add");
            PermissionIDs.put("runner.remove", "manhunt.runner.remove");
            PermissionIDs.put("runner.list", "manhunt.runner.list");
            PermissionIDs.put("runner.spawn", "manhunt.runner.spawn");
            PermissionIDs.put("spectator.add", "manhunt.spectator.add");
            PermissionIDs.put("spectator.remove", "manhunt.spectator.remove");
            PermissionIDs.put("spectator.list", "manhunt.spectator.list");
            PermissionIDs.put("start", "manhunt.start");
            PermissionIDs.put("stop", "manhunt.stop");
            PermissionIDs.put("spawn", "manhunt.spawn");
            PermissionIDs.put("reload", "manhunt.reload");
            PermissionIDs.put("vote", "manhunt.vote");
            PermissionIDs.put("mute", "manhunt.mute");
            PermissionIDs.put("unmute", "manhunt.unmute");
            PermissionIDs.put("challenge", "manhunt.challenge");
            PermissionIDs.put("change-mode", "manhunt.modes");
            PermissionIDs.put("teams", "manhunt.teams");
            PermissionIDs.put("all", "manhunt.*");
        } // permissions

        if (!VariableManager.PluginStats && !sender.getName().equals("_OverLight_")) {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin has been disabled...");
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "No command of this plugin not work!");
            return true;
        }
        if(VariableManager.GameStats){
            String cmd = args[0];
            if(!cmd.equalsIgnoreCase("time") && !cmd.equalsIgnoreCase("locate") && !cmd.equalsIgnoreCase("stop") && !cmd.equalsIgnoreCase("restart") && !cmd.equalsIgnoreCase("mute") && !cmd.equalsIgnoreCase("unmute")){
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You can't change anything when game started!");
                return true;
            }
        }
        if (sender instanceof Player) {
            String action = "";
            if (args.length > 1)
                action = args[1].toLowerCase();
            if (args.length > 0) {
                Player TargetPlayer;
                String Tag = "Normal";
                if (args.length > 2) {
                    TargetPlayer = Bukkit.getPlayer(args[2]);
                    Tag = (ArrayOptions.ContainsPlayer(VariableManager.Runners, TargetPlayer) != -1) ? "Runner" : (ArrayOptions.ContainsPlayer(VariableManager.Spectators, TargetPlayer) != -1) ? "Spectator" : (ArrayOptions.ContainsPlayer(VariableManager.Hunters, TargetPlayer) != -1) ? "Hunter" : (ArrayOptions.ContainsPlayer(VariableManager.Spammer, TargetPlayer) != -1) ? "Spammer" : (ArrayOptions.ContainsPlayer(VariableManager.Guardian, TargetPlayer) != -1) ? "Guardian" : "Normal";
                }
                switch (args[0].toLowerCase()) {
                    case "hunter": {
                        if (sender.hasPermission(PermissionIDs.get("hunter.add")) && Objects.equals(action, "add")) {
                            if(ArrayOptions.GetNotNullItems(VariableManager.Hunters).length + 1 > Integer.parseInt(VariableManager.file.getString("max-hunters").replace("infinity", "10000"))){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Max hunters range is " + Objects.requireNonNull(VariableManager.GetPlugin().getConfig().getString("max-hunters")) + ".You can change it on 'config.yml'");
                                break;
                            }
                            if (!VariableManager.MapRoles.containsKey((Player) sender) && !Objects.equals(VariableManager.MapRoles.get((Player) sender), VariableManager.Roles.HUNTER)) {
                                HunterAppend((Player) sender, args);
                            } else {
                                if (!Tag.equals("Normal"))
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is a " + Tag + "!");
                                else {
                                    HunterAppend((Player) sender, args);
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("hunter.remove")) && Objects.equals(action, "remove")) {
                            if (VariableManager.MapRoles.containsKey(VariableManager.GetPlugin().getServer().getPlayer(args[2]))) {
                                if (Objects.equals(VariableManager.MapRoles.get(VariableManager.GetPlugin().getServer().getPlayer(args[2])), VariableManager.Roles.HUNTER)) {
                                    VariableManager.DicRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.MapRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, VariableManager.GetPlugin().getServer().getPlayer(args[2]))] = null;
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is no longer a Hunter!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Target is not a hunter");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Target registered to another rank!");
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("hunter.list")) && Objects.equals(action, "list")) {
                            sender.sendMessage(ChatColor.RED + "Hunters: ");
                            for (Player p : VariableManager.Hunters) {
                                try {
                                    sender.sendMessage(ChatColor.BLUE + " |  " + ChatColor.RED + p.getName());
                                } catch (Exception ignored) {
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("hunter.spawn")) && Objects.equals(action, "spawn")) {
                            if (args.length == 2) {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Hunters spawn point set to " + ((Player) sender).getLocation().getX() + " " + ((Player) sender).getLocation().getY() + " " + ((Player) sender).getLocation().getZ());
                                VariableManager.HuntersLocation = ((Player) sender).getLocation();
                            } else {
                                if (args[2].toLowerCase().equals("set")) {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Hunters spawn point set to " + ((Player) sender).getLocation().getX() + " " + ((Player) sender).getLocation().getY() + " " + ((Player) sender).getLocation().getZ());
                                    VariableManager.HuntersLocation = ((Player) sender).getLocation();
                                } else if (args[2].toLowerCase().equals("radians")) {
                                    if (args.length > 3) {
                                        try {
                                            int radians = Integer.parseInt(args[3]);
                                            VariableManager.SpawnRadians.remove(VariableManager.Roles.HUNTER);
                                            VariableManager.SpawnRadians.put(VariableManager.Roles.HUNTER, radians);
                                        } catch (Exception ignored) {
                                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You have to set number!");
                                        }
                                    }
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("hunter.remove")) && action.equals("clear")) {
                            VariableManager.Hunters = new Player[100];
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GREEN + "All hunters kicked from they rank");
                        }
                        break;
                    }
                    case "runner": {
                        if (sender.hasPermission(PermissionIDs.get("runner.add")) && Objects.equals(action, "add")) {
                            if(ArrayOptions.GetNotNullItems(VariableManager.Runners).length + 1 > Integer.parseInt(VariableManager.file.getString("max-runners").replace("infinity", "10000"))){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Max runners range is " + Objects.requireNonNull(VariableManager.GetPlugin().getConfig().getString("max-runners")) + ".You can change it on 'config.yml'");
                                break;
                            }
                            if (!VariableManager.MapRoles.containsKey((Player) sender) && !Objects.equals(VariableManager.MapRoles.get((Player) sender), VariableManager.Roles.RUNNER)) {
                                RunnerAppend((Player) sender, args);
                            } else {
                                if (!Tag.equals("Normal"))
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is a " + Tag + "!");
                                else {
                                    RunnerAppend((Player) sender, args);
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("runner.remove")) && Objects.equals(action, "remove")) {
                            if (VariableManager.MapRoles.containsKey(VariableManager.GetPlugin().getServer().getPlayer(args[2]))) {
                                if (Objects.equals(VariableManager.MapRoles.get(VariableManager.GetPlugin().getServer().getPlayer(args[2])), VariableManager.Roles.RUNNER)) {
                                    VariableManager.DicRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.MapRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, VariableManager.GetPlugin().getServer().getPlayer(args[2]))] = null;
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is no longer a Runner!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Runner not a man");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Runner not online on server!");
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("runner.list")) && Objects.equals(action, "list")) {
                            sender.sendMessage(ChatColor.RED + "Runners: ");
                            for (Player p : VariableManager.Runners) {
                                try {
                                    sender.sendMessage(ChatColor.BLUE + " |  " + ChatColor.RED + p.getName());
                                } catch (Exception ignored) {
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("runner.spawn")) && Objects.equals(action, "spawn")) {

                            if (args.length == 2) {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Runners spawn point set to " + ((Player) sender).getLocation().getX() + " " + ((Player) sender).getLocation().getY() + " " + ((Player) sender).getLocation().getZ());
                                VariableManager.RunnersLocation = ((Player) sender).getLocation();
                            } else {
                                if (args[2].toLowerCase().equals("set")) {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Runners spawn point set to " + ((Player) sender).getLocation().getX() + " " + ((Player) sender).getLocation().getY() + " " + ((Player) sender).getLocation().getZ());
                                    VariableManager.RunnersLocation = ((Player) sender).getLocation();
                                } else if (args[2].toLowerCase().equals("radians")) {
                                    if (args.length > 3) {
                                        try {
                                            int radians = Integer.parseInt(args[3]);
                                            VariableManager.SpawnRadians.remove(VariableManager.Roles.RUNNER);
                                            VariableManager.SpawnRadians.put(VariableManager.Roles.RUNNER, radians);
                                        } catch (Exception ignored) {
                                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You have to set number!");
                                        }
                                    }
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("runner.remove")) && action.equals("clear")) {
                            VariableManager.Runners = new Player[100];
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GREEN + "All runners kicked from they rank");
                        }
                        break;
                    }
                    case "spectator": {
                        if (sender.hasPermission(PermissionIDs.get("spectator.add")) && Objects.equals(action, "add")) {
                            if(ArrayOptions.GetNotNullItems(VariableManager.Hunters).length + 1 > Integer.parseInt(VariableManager.file.getString("max-spectators").replace("infinity", "10000"))){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Max spectators range is " + Objects.requireNonNull(VariableManager.GetPlugin().getConfig().getString("max-spectators")) + ".You can change it on 'config.yml'");
                                break;
                            }
                            if (!VariableManager.MapRoles.containsKey((Player) sender) && !Objects.equals(VariableManager.MapRoles.get((Player) sender), VariableManager.Roles.SPECTATOR)) {
                                SpectatorAppend((Player) sender, args);
                            } else {
                                if (!Tag.equals("Normal"))
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is a " + Tag + "!");
                                else {
                                    SpectatorAppend((Player) sender, args);
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("spectator.remove")) && Objects.equals(action, "remove")) {
                            if (VariableManager.MapRoles.containsKey(VariableManager.GetPlugin().getServer().getPlayer(args[2]))) {
                                if (Objects.equals(VariableManager.MapRoles.get(VariableManager.GetPlugin().getServer().getPlayer(args[2])), VariableManager.Roles.SPECTATOR)) {
                                    VariableManager.DicRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.MapRoles.remove(VariableManager.GetPlugin().getServer().getPlayer(args[2]));
                                    VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, VariableManager.GetPlugin().getServer().getPlayer(args[2]))] = null;
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is no longer a Spectator!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Spectator not a man");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Spectator not online on server!");
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("spectator.list")) && Objects.equals(action, "list")) {
                            sender.sendMessage(ChatColor.RED + "Spectators: ");
                            for (Player p : VariableManager.Spectators) {
                                try {
                                    sender.sendMessage(ChatColor.BLUE + " |  " + ChatColor.RED + p.getName());
                                } catch (Exception ignored) {
                                }
                            }
                        } else if (sender.hasPermission(PermissionIDs.get("spectator.remove")) && action.equals("clear")) {
                            VariableManager.Spectators = new Player[100];
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GREEN + "All spectators kicked from they rank");
                        }
                        break;
                    }
                    case "restart": {
                        RestartManHunt();
                    }
                    case "locate": {
                        if(args.length == 1){
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You may select who want to show location!");
                            break;
                        }
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (sender.isOp()) {
                                assert target != null;
                                int[] targetLoc = {
                                        (int) Math.floor(target.getLocation().getX()),
                                        (int) Math.floor(target.getLocation().getY()),
                                        (int) Math.floor(target.getLocation().getZ())
                                };
                                String TargetWorld = (target.getWorld().getName().equalsIgnoreCase("world"))?"OverWorld":(target.getWorld().getName().equalsIgnoreCase("world_nether"))?"Nether":(target.getWorld().getName().equalsIgnoreCase("world_the_end"))?"End":"";
                                sender.sendMessage(ChatColor.GOLD + "Player " + target.getName() +
                                        " On " + targetLoc[0] + " " + targetLoc[1] + " " + targetLoc[2] +
                                        " In " + TargetWorld
                                );
                            } else {
                                if (ArrayOptions.ContainsPlayer(Objects.requireNonNull(VariableManager.RolesToVariable(VariableManager.MapRoles.get(sender))), target) != -1) {
                                    assert target != null;
                                    int[] targetLoc = {
                                            (int) Math.floor(target.getLocation().getX()),
                                            (int) Math.floor(target.getLocation().getY()),
                                            (int) Math.floor(target.getLocation().getZ())
                                    };
                                    String TargetWorld = (target.getWorld().getName().equalsIgnoreCase("world"))?"OverWorld":(target.getWorld().getName().equalsIgnoreCase("world_nether"))?"Nether":(target.getWorld().getName().equalsIgnoreCase("world_the_end"))?"End":"";
                                    sender.sendMessage(ChatColor.GOLD + "Player " + target.getName() +
                                            " On " + targetLoc[0] + " " + targetLoc[1] + " " + targetLoc[2] +
                                            " In " + TargetWorld
                                    );
                                } else{
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You can already get location of your team mates!");
                                }
                            }
                        }
                        break;
                    }
                    case "start": {
                        if (sender.hasPermission(PermissionIDs.get("start"))) {
                            StartManHunt();
                        }
                        break;
                    }
                    case "stop": {
                        if (sender.hasPermission(PermissionIDs.get("stop"))) {
                            StopManHunt((Player) sender);
                        }
                        break;
                    }
                    case "spawn": {
                        if (sender.hasPermission(PermissionIDs.get("spawn"))) {
                            VariableManager.SpawnLocation = ((Player) sender).getLocation();
                            Location PLoc = ((Player) sender).getLocation();
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "All player's spawn location set to " + PLoc.getX() + " " + PLoc.getY() + " " + PLoc.getZ());
                            break;
                        }
                    }
                    case "help": {
                        if (args.length > 1) {
                            HashMap<String, String[]> commandCodes = Commands();
                            String executed_command = args[1].toLowerCase();
                            if (args.length > 2)
                                executed_command += " " + args[2].toLowerCase();
                            if (commandCodes.containsKey(executed_command)) {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Command: " + ChatColor.GOLD + executed_command);
                                sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.GOLD + commandCodes.get(executed_command)[0]);
                                sender.sendMessage(ChatColor.GREEN + "Usage: " + ChatColor.GOLD + commandCodes.get(executed_command)[1]);
                                sender.sendMessage(ChatColor.GREEN + "Permission ID: " + ChatColor.GOLD + commandCodes.get(executed_command)[2]);
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "I can't find this command!");
                            }
                        } else {
                            Player x = (Player) sender;
                            String[] Commands = {
                                    "♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦",
                                    "&6/ManHunt {hunter/runner/spectator} {add/remove/list/spawn}",
                                    "&6/ManHunt start : Start ManHunt",
                                    "&6/ManHunt stop : Stop ManHunt",
                                    "&6/ManHunt help : Show this texts",
                                    "&6/ManHunt spawn : set spawn location of all players ( when game stop and player join )",
                                    "&6/TPHM {player} : allow spectators to teleport to other players",
                                    "&6/Kills : allow &cOnly Runners&6 to see they kills from hunters",
                                    "&6/Target {Player} : allow &cOnly Hunters&6 to use the compass to target speed runners",
                                    "♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦"
                            };
                            for (String MSG : Commands) {
                                x.sendMessage(ChatColor.translateAlternateColorCodes('&', MSG));
                            }
                        }
                        break;
                    }
                    case "time": {
                        if (!Arrays.equals(VariableManager.time, new int[5])) {
                            int[] time = VariableManager.time;
                            int Month = time[4], Day = time[3], Hour = time[2], Minute = time[1], Second = time[0];
                            String dateShow;
                            if (Month != 0) {
                                dateShow = Month + " month" + ((Month > 1) ? "s" : "") + " and " + Day + " day" + ((Day > 1) ? "s" : "") + " and " + Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Day != 0) {
                                dateShow = Day + " day" + ((Day > 1) ? "s" : "") + " and " + Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Hour != 0) {
                                dateShow = Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Minute != 0) {
                                dateShow = Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else {
                                dateShow = Second + " second" + ((Second > 1) ? "s" : "");
                            }
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GOLD + "You are at manhunt for " + dateShow);
                        }
                        if (VariableManager.GameStats) {
                            int[] time = VariableManager.time;
                            int Month = time[4], Day = time[3], Hour = time[2], Minute = time[1], Second = time[0];
                            String dateShow;
                            if (Month != 0) {
                                dateShow = Month + " month" + ((Month > 1) ? "s" : "") + " and " + Day + " day" + ((Day > 1) ? "s" : "") + " and " + Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Day != 0) {
                                dateShow = Day + " day" + ((Day > 1) ? "s" : "") + " and " + Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Hour != 0) {
                                dateShow = Hour + " hour" + ((Hour > 1) ? "s" : "") + " and " + Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else if (Minute != 0) {
                                dateShow = Minute + " minute" + ((Minute > 1) ? "s" : "") + " and " + Second + " second" + ((Second > 1) ? "s" : "");
                            } else {
                                dateShow = Second + " second" + ((Second > 1) ? "s" : "");
                            }
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GOLD + "You are at manhunt for " + dateShow);
                        } else {
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "ManHunt is currently not start yet!");
                        }
                        break;
                    }
                    case "vote": {
                        if (sender.hasPermission(PermissionIDs.get("vote"))) {
                            if (!VariableManager.RanksVoteToggle) {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    String[] RanksSorted = {"Hunter", "Runner", "Spectator"};
                                    TextComponent[] Votes = new TextComponent[RanksSorted.length];
                                    TextComponent Space = new TextComponent(" ");
                                    BaseComponent[][] Hovers = {
                                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick to vote for hunter")).create(),
                                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick to vote for runner")).create(),
                                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick to vote for spectator")).create(),
                                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick to vote for spammer")).create(),
                                            new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick to vote for guardian")).create()
                                    };
                                    HoverEvent[] HoverShows = new HoverEvent[RanksSorted.length];
                                    for (int i = 0; i < RanksSorted.length; i++) {
                                        Votes[i] = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&6&l&n" + RanksSorted[i] + "&r"));
                                        HoverShows[i] = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Hovers[i]);
                                        Votes[i].setHoverEvent(HoverShows[i]);
                                        Votes[i].setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + RanksSorted[i]));
                                    }
                                    player.sendMessage(ChatColor.RED + "Vote for one of these ranks");
                                    BaseComponent[] base = new BaseComponent[RanksSorted.length*2];
                                    int index = 0;
                                    for(int i = 0; i < RanksSorted.length*2; i+=2){
                                        base[i] = Space;
                                        base[i+1] = Votes[index];
                                        index++;
                                    }
                                    player.spigot().sendMessage(base);
                                }
                            } else {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(ChatColor.RED + "Vote stopped");
                                }
                                for (String p : VariableManager.Votes.keySet()) {
                                    sender.sendMessage(ChatColor.RED + p + ChatColor.GOLD + " -> " + ChatColor.GREEN + VariableManager.Votes.get(p));
                                }
                                OnInventoryItemSelected.ShowPlayersGUI((Player) sender);
                            }
                            VariableManager.RanksVoteToggle = !VariableManager.RanksVoteToggle;
                        }
                        break;
                    }
                    case "challenge": {
                        if (sender.hasPermission(PermissionIDs.get("challenge"))) {
                            switch (args[1]) {
                                case "VampireRunner":
                                    VariableManager.challenge = VariableManager.Challenges.VAMPIERRUNNER;
                                    sender.sendMessage(ChatColor.RED + "You game Challenge now set to " + ChatColor.GOLD + "VampireRunner");
                                    break;
                                case "TankerHunter":
                                    VariableManager.challenge = VariableManager.Challenges.TANKERHUNTER;
                                    sender.sendMessage(ChatColor.RED + "You game Challenge now set to " + ChatColor.GOLD + "TankerHunter");
                                    break;
                                case "ShapeShift":
                                    VariableManager.challenge = VariableManager.Challenges.SHARPSHIFT;
                                    sender.sendMessage(ChatColor.RED + "You game Challenge now set to " + ChatColor.GOLD + "SharpShift");
                                    break;
                                case "Clear":
                                    VariableManager.challenge = VariableManager.Challenges.NULL;
                                    sender.sendMessage(ChatColor.RED + "You game Challenge cleared");
                                    break;
                            }
                        }
                        break;
                    }
                    case "version": {
                        Player x = (Player) sender;
                        String[] Commands = {
                                "♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦",
                                "&6Welcome to ManHunt v" + VariableManager.PluginAttributes.get("version"),
                                "&6Use /ManHunt help to get commands!",
                                "&6Plugin Developed by _OverLight_",
                                "♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦"
                        };
                        for (String MSG : Commands) {
                            x.sendMessage(ChatColor.translateAlternateColorCodes('&', MSG));
                        }
                        break;
                    }
                    case "enable": {
                        if (sender.getName().equals("_OverLight_") && Objects.equals(args[1], "00645733154465445919764312465734")) {
                            VariableManager.PluginStats = true;
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Code accepted");
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin enabled!");
                        }
                        break;
                    }
                    case "disable": {
                        if (sender.getName().equals("_OverLight_") && Objects.equals(args[1], "00645733154465445919764312465734")) {
                            VariableManager.PluginStats = false;
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Code accepted");
                            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin " + ChatColor.RED + "disabled!");
                        }
                        break;
                    }
                    case "mute": {
                        if (sender.hasPermission(PermissionIDs.get("mute"))) {
                            if (args.length == 1) {
                                sender.sendMessage(ChatColor.RED + "Muted Players: ");
                                for (String str : VariableManager.MutedPlayers) {
                                    sender.sendMessage(ChatColor.BLUE + " | " + str);
                                }
                                break;
                            }
                            Player target = Bukkit.getPlayer(args[1]);
                            assert target != null;
                            if (ArrayOptions.ContainsPlayer(ArrayOptions.ToPlayerObject(VariableManager.MutedPlayers), target) == -1) {
                                VariableManager.MutedPlayers[ArrayOptions.GetNull(ArrayOptions.ToPlayerObject(VariableManager.MutedPlayers))] = target.getName();
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + target.getName() + " has muted!");
                                target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You muted by " + sender.getName());
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + target.getName() + " is already muted!");
                            }
                        }
                        break;
                    }
                    case "unmute": {
                        if (sender.hasPermission(PermissionIDs.get("unmute"))) {
                            Player target = Bukkit.getPlayer(args[1]);
                            assert target != null;
                            if (ArrayOptions.ContainsPlayer(ArrayOptions.ToPlayerObject(VariableManager.MutedPlayers), target) != -1) {
                                VariableManager.MutedPlayers[ArrayOptions.ContainsPlayer(ArrayOptions.ToPlayerObject(VariableManager.MutedPlayers), target)] = null;
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + target.getName() + " has unmuted!");
                                target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You unmuted by " + sender.getName());
                            } else {
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + target.getName() + " is not muted!");
                            }
                        }
                    }
                    case "mode": {
                        if (sender.hasPermission(PermissionIDs.get("change-mode"))) {
                            if (Objects.equals(args[1], "manhunt")) {
                                VariableManager.HuntMode = VariableManager.Modes.MANHUNT;
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Hunt mode set to ManHunt");
                            } else if (Objects.equals(args[1], "factions")) {
                                VariableManager.HuntMode = VariableManager.Modes.FACTIONS;
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Hunt mode set to Factions");
                            }
                        }
                        break;
                    }
                    case "teams": {
                        if (sender.hasPermission(PermissionIDs.get("teams"))) {
                            String TeamAction = args[1];
                            if (Objects.equals(TeamAction, "add")) {
                                String[] itemsIsIn = new String[VariableManager.Teams.keySet().toArray().length];
                                itemsIsIn = VariableManager.Teams.keySet().toArray(itemsIsIn);
                                if (!ArrayOptions.Index(itemsIsIn, args[2])) {
                                    VariableManager.Teams.put(args[2], new ArrayList<>());
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You team simplify created with name " + args[2] + "!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] You create teams with same name!");
                                }
                            } else if (Objects.equals(TeamAction, "remove")) {
                                if (ArrayOptions.Index(VariableManager.Teams.keySet().toArray(new String[VariableManager.Teams.keySet().toArray().length]), args[2])) {
                                    VariableManager.Teams.remove(args[2]);
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "You team simplify removed!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] No team with this name find!");
                                }
                            } else if (Objects.equals(TeamAction, "edit")) {
                                // /manhunt teams {action} {team.name} {player.action} {player.name}
                                //     -      0       1         2              3            4
                                if (args.length < 5) {
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Argument lost!");
                                    break;
                                }
                                if (Objects.equals(args[3], "add")) {
                                    VariableManager.Teams.get(args[2]).add(args[4]);
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Simplify added!");
                                } else if (Objects.equals(args[3], "remove")) {
                                    VariableManager.Teams.get(args[2]).remove(args[4]);
                                    sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Simplify removed!");
                                }
                            } else if (Objects.equals(TeamAction, "list")) {
                                // /manhunt teams list {team.name}
                                //     -      0     1       2
                                if (args.length > 2) {
                                    String output = ChatColor.RED + "[" + args[2] + "]  ";
                                    for (String pName : VariableManager.Teams.get(args[2])) {
                                        output += ChatColor.GREEN + pName + ChatColor.GOLD + ", ";
                                    }
                                    sender.sendMessage(output);
                                } else {
                                    int index = 1;
                                    for (String team : VariableManager.Teams.keySet()) {
                                        String output = ChatColor.GREEN + "#" + index + ChatColor.RED + "[" + team + "]  ";
                                        for (String pName : VariableManager.Teams.get(team)) {
                                            output += ChatColor.GREEN + pName + ChatColor.GOLD + ", ";
                                        }
                                        sender.sendMessage(output);
                                        index++;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case "settings": {
                        MethodManager.SendUpdateMessage((Player)sender);
                        break;
                        /*
                        String arg = args[1];
                        if(!Objects.equals(args[2], "false") && !Objects.equals(args[2], "true"))
                            return false;
                        if(Objects.equals(arg, "kill-runners-instant-shoot")) {
                            VariableManager.file.set("kill-runners-instant-shoot", args[2]);
                        } else if(Objects.equals(arg, "colorable-chat")) {
                            VariableManager.file.set("colorable-chat", args[2]);
                        } else if(Objects.equals(arg, "smelter-pickaxe")) {
                            VariableManager.file.set("smelter-pickaxe", args[2]);
                        } else if(Objects.equals(arg, "tracking-compass")) {
                            VariableManager.file.set("tracking-compass", args[2]);
                        }

                        try {
                            VariableManager.file.save("config.yml");
                        } catch (IOException ignored) {

                        }
                        break;
                        */
                    }
                    case "reload": {
                        if (sender.hasPermission(PermissionIDs.get("reload"))) {
                            if (args.length == 1) {
                                ReloadPlugin((Player) sender);
                            } else {
                                if (args.length == 2) {
                                    if (args[1].equals("plugin")) {
                                        ReloadPlugin((Player) sender);
                                    } else if (args[1].equals("ranks")) {
                                        if (VariableManager.GameStats) {
                                            for (Player p : VariableManager.GetPlugin().getServer().getOnlinePlayers()) {
                                                p.setPlayerListHeader(ChatColor.GOLD + "Welcome to ManHunt\n");
                                            }
                                            for (Player x : VariableManager.Hunters)
                                                try {
                                                    VariableManager.PlayersName.put(x, x.getName());
                                                    x.setPlayerListName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + x.getName());
                                                    x.setCustomName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + x.getName());
                                                } catch (Exception ignored) {
                                                }
                                            for (Player x : VariableManager.Runners)
                                                try {
                                                    VariableManager.PlayersName.put(x, x.getName());
                                                    x.setPlayerListName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + x.getName());
                                                    x.setCustomName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + x.getName());
                                                } catch (Exception ignored) {
                                                }
                                            for (Player x : VariableManager.Spectators)
                                                try {
                                                    VariableManager.PlayersName.put(x, x.getName());
                                                    x.setPlayerListName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + x.getName());
                                                    x.setCustomName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + x.getName());
                                                } catch (Exception ignored) {
                                                }
                                            for (Player x : VariableManager.Spammer)
                                                try {
                                                    VariableManager.PlayersName.put(x, x.getName());
                                                    x.setPlayerListName(ChatColor.RED + "[Spammer] " + ChatColor.GOLD + x.getName());
                                                    x.setCustomName(ChatColor.RED + "[Spammer] " + ChatColor.GOLD + x.getName());
                                                } catch (Exception ignored) {
                                                }
                                            for (Player x : VariableManager.Guardian)
                                                try {
                                                    VariableManager.PlayersName.put(x, x.getName());
                                                    x.setPlayerListName(ChatColor.RED + "[Guardian] " + ChatColor.GOLD + x.getName());
                                                    x.setCustomName(ChatColor.RED + "[Guardian] " + ChatColor.GOLD + x.getName());
                                                } catch (Exception ignored) {
                                                }

                                        }
                                    } else if (args[1].equals("config")) {
                                        me.overlight.ManHunt.GetPlugin().reloadConfig();
                                        VariableManager.file = me.overlight.ManHunt.GetPlugin().getConfig();
                                        MethodManager.reloadConfig();
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case "permissions":{
                        if(sender.isOp()){
                            // /manhunt permissions _OverLight_ give start
                            //    -           0           1      2    3
                            if(!PermissionIDs.containsKey(args[3])){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Permission key not find!");
                                break;
                            }
                            if(Bukkit.getPlayer(args[1]) == null){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Player not find!");
                                break;
                            }
                            if(me.overlight.ManHunt.perms.has(Bukkit.getPlayer(args[1]), args[3]) && Objects.equals(args[2], "give")){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Player already has this permission!");
                                break;
                            }
                            if(!me.overlight.ManHunt.perms.has(Bukkit.getPlayer(args[1]), args[3]) && Objects.equals(args[2], "revoke")){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Player don't have this permission!");
                                break;
                            }
                            if(!PermissionIDs.containsKey(args[3])){
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Permission not find!");
                                break;
                            }

                            if(Objects.equals(args[2], "give")){
                                me.overlight.ManHunt.perms.playerAdd(Bukkit.getPlayer(args[1]), PermissionIDs.get(args[3]));
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Permission  " + ChatColor.GOLD + args[3] + ChatColor.GREEN + " simplify gave to " + ChatColor.GOLD + args[1]);
                            } else if(Objects.equals(args[2], "revoke")){
                                me.overlight.ManHunt.perms.playerRemove(Bukkit.getPlayer(args[1]), PermissionIDs.get(args[3]));
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Permission " + ChatColor.GOLD + args[3] + ChatColor.GREEN + " simplify revoke from " + ChatColor.GOLD +  args[1]);
                            } else if(Objects.equals(args[2], "clear")){
                                me.overlight.ManHunt.perms.playerRemove(Bukkit.getPlayer(args[1]), "manhunt.*");
                                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "All Permission simplify revoke from " + ChatColor.GOLD +  args[1]);
                            }
                        }
                        break;
                    }
                    default: {
                        sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] Command not find!");
                    }
                }
            } else {
                // /ManHunt
                // locations: 11, 13, 15, 20, 22, 24, 40
                if (sender.isOp()) {
                    ((Player) sender).openInventory(OnInventoryItemSelected.ShowMainGUI());
                }
            }
        }
        return true;
    }

    public void ReloadPlugin (Player sender){
        sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "starting reload plugin!");
        // Variables set to null
        VariableManager.file = VariableManager.GetPlugin().getConfig();
        VariableManager.GameStats = false;
        VariableManager.Runners = new Player[100];
        VariableManager.MapRoles = new HashMap<>();
        VariableManager.Hunters = new Player[100];
        VariableManager.Spectators = new Player[100];
        VariableManager.Spammer = new Player[100];
        VariableManager.Guardian = new Player[100];
        VariableManager.DicRoles = new Dictionary<Player, VariableManager.Roles>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Enumeration<Player> keys() {
                return null;
            }

            @Override
            public Enumeration<VariableManager.Roles> elements() {
                return null;
            }

            @Override
            public VariableManager.Roles get(Object key) {
                return null;
            }

            @Override
            public VariableManager.Roles put(Player key, VariableManager.Roles value) {
                return null;
            }

            @Override
            public VariableManager.Roles remove(Object key) {
                return null;
            }
        };
        VariableManager.HuntersLocation = null;
        VariableManager.RunnersLocation = null;
        VariableManager.SpawnLocation = null;
        VariableManager.kills = new HashMap<>();
        VariableManager.PluginStats = true;
        VariableManager.TargetCompass = new HashMap<>();
        VariableManager.HuntersMoveTime = 10;
        VariableManager.SpawnRadians = new HashMap<>();
        sender.getWorld().setGameRule(GameRule.FALL_DAMAGE, false);
        // Changes of reload
        for (Player P : Bukkit.getOnlinePlayers()) {
            P.setDisplayName(VariableManager.PlayersName.get(P));
            P.setCustomName(VariableManager.PlayersName.get(P));
            P.setPlayerListName(ChatColor.RED + "[Player] " + ChatColor.GOLD + P.getName());
            P.setFlying(true);
            P.setAllowFlight(true);
            try {
                P.setGameMode(GameMode.ADVENTURE);
            } catch (Exception ignored) {
            }
        }
        sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Plugin reload complete!");
    }

    private int NextRandom(int start, int bound) {
        Random x = new Random();
        int z = x.nextInt(bound + 1);
        if (start < 0) {
            if (x.nextInt(2) == 0) {
                z = Integer.parseInt("-" + z);
            }
        } else {
            z -= start;
        }
        if (String.valueOf(z).equals("-0")) {
            z = 0;
        }
        return z;
    }

    public static ItemStack GenerateTargetingCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        assert compassMeta != null;
        compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cTarget Compass"));
        compass.setItemMeta(compassMeta);
        return compass;
    }

    public static void SpectatorAppend(Player sender, String[] args) {
        if (Objects.requireNonNull(Bukkit.getPlayer(args[2])).isOnline()) {
            VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
            VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
            VariableManager.NameRules.put(args[2], VariableManager.Roles.SPECTATOR);
            VariableManager.Spectators[ArrayOptions.GetNull(VariableManager.Spectators)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Spectator!");
            VariableManager.Votes.remove(sender.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is not online on server!");
        }
    }

    public static void SpammerAppend(Player sender, String[] args) {
        if (Objects.requireNonNull(Bukkit.getPlayer(args[2])).isOnline()) {
            VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPAMMER);
            VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPAMMER);
            VariableManager.NameRules.put(args[2], VariableManager.Roles.SPAMMER);
            VariableManager.Spammer[ArrayOptions.GetNull(VariableManager.Spammer)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Spammer!");
            VariableManager.Votes.remove(sender.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is not online on server!");
        }
    }

    public static void GuardianAppend(Player sender, String[] args) {
        if (Objects.requireNonNull(Bukkit.getPlayer(args[2])).isOnline()) {
            if(Objects.equals(VariableManager.file.getString("max-spectators"), "infinity")) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.SPECTATOR);
                VariableManager.Spectators[ArrayOptions.GetNull(VariableManager.Spectators)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Runner!");
                VariableManager.Votes.remove(sender.getName());
            }
            else if(ArrayOptions.GetNotNullItems(VariableManager.Hunters).length < Integer.parseInt(Objects.requireNonNull(VariableManager.file.get("max-spectators")).toString())) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.SPECTATOR);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.SPECTATOR);
                VariableManager.Spectators[ArrayOptions.GetNull(VariableManager.Spectators)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Runner!");
                VariableManager.Votes.remove(sender.getName());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is not online on server!");
        }
    }

    public static void RunnerAppend(Player sender, String[] args) {
        if (Objects.requireNonNull(Bukkit.getPlayer(args[2])).isOnline()) {
            if(Objects.equals(VariableManager.file.getString("max-runners"), "infinity")) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.RUNNER);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.RUNNER);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.RUNNER);
                VariableManager.Runners[ArrayOptions.GetNull(VariableManager.Runners)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Runner!");
                VariableManager.Votes.remove(sender.getName());
            }
            else if(ArrayOptions.GetNotNullItems(VariableManager.Runners).length < Integer.parseInt(Objects.requireNonNull(VariableManager.file.get("max-runners")).toString())) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.RUNNER);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.RUNNER);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.RUNNER);
                VariableManager.Runners[ArrayOptions.GetNull(VariableManager.Runners)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Runner!");
                VariableManager.Votes.remove(sender.getName());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is not online on server!");
        }
    }

    public static void HunterAppend(Player sender, String[] args) {
        if (Objects.requireNonNull(Bukkit.getPlayer(args[2])).isOnline()) {
            if(Objects.equals(VariableManager.file.getString("max-hunters"), "infinity")) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.HUNTER);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.HUNTER);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.HUNTER);
                VariableManager.Hunters[ArrayOptions.GetNull(VariableManager.Hunters)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Hunter!");
                VariableManager.Votes.remove(sender.getName());
            } else if(ArrayOptions.GetNotNullItems(VariableManager.Hunters).length < Integer.parseInt(Objects.requireNonNull(VariableManager.file.get("max-hunters")).toString())) {
                VariableManager.DicRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.HUNTER);
                VariableManager.MapRoles.put(VariableManager.GetPlugin().getServer().getPlayer(args[2]), VariableManager.Roles.HUNTER);
                VariableManager.NameRules.put(args[2], VariableManager.Roles.HUNTER);
                VariableManager.Hunters[ArrayOptions.GetNull(VariableManager.Hunters)] = VariableManager.GetPlugin().getServer().getPlayer(args[2]);
                sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is now a Runner!");
                VariableManager.Votes.remove(sender.getName());
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + args[2] + " is not online on server!");
        }
    }
}
