package me.overlight.Commands.TabCompleter;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

import static me.overlight.Managers.MethodManager.Commands;
import static org.bukkit.Bukkit.getOnlinePlayers;

public class ManHunt implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> output = new ArrayList<>();
        HashMap<String, String[]> Items = new HashMap<>();
        String[] items = new String[10];
        Items.put("manhunt", new String[]{ "hunter", "runner", "spectator", "spawn", "start", "version", "stop", "reload", "time", "help", "locate", "challenge", "mute", "unmute", "mode", "restart", "settings", "vote", "permissions"});
        Items.put("faction", new String[]{ "start", "version", "stop", "reload", "time", "help", "locate", "challenge", "mute", "unmute", "teams", "mode", "restart", "settings", "permissions"});
        Items.put("setting", new String[]{ "kill-runners-instant-shoot", "tracking-compass", "colorable-chat", "smelter-pickaxe"});
        Items.put("setting.options", new String[]{ "true", "false"});
        Items.put("06", new String[]{ "add", "remove", "edit", "list"});
        Items.put("07", new String[]{ "add", "remove"});
        Items.put("vote", new String[]{ "gui", "chat"});
        Items.put("05", new String[]{ "Clear", /*"VampireRunner",*/ "TankerHunter", /*"ShapeShift",*/ "ColorBlock"});
        Items.put("08", new String[]{ "manhunt", "factions"});
        Items.put("normal-player", new String[]{ "time", "help", "locate", "version"});
        Items.put("02", new String[]{ "add", "remove", "list", "clear"});
        Items.put("03", new String[]{ "ranks", "plugin", "config"});
        Items.put("permissions.options", new String[]{ "give", "revoke", "clear"});
        Items.put("permissions.perms", ArrayOptions.ObjectToStringConvert(me.overlight.Commands.Commands.ManHunt.PermissionIDs.keySet().toArray()));
        if(args.length == 1){
            if(sender.isOp()) {
                if (VariableManager.HuntMode == VariableManager.Modes.MANHUNT)
                    items = Items.get("manhunt");
                else if (VariableManager.HuntMode == VariableManager.Modes.FACTIONS)
                    items = Items.get("faction");
            } else {
                String[] UserPermissions = getPermissions(ArrayOptions.ObjectToStringConvert(me.overlight.Commands.Commands.ManHunt.PermissionIDs.values().toArray()), (Player) sender);
                int NormalPlayerCommandPermissions = Items.get("normal-player").length;
                items = new String[NormalPlayerCommandPermissions + UserPermissions.length];
                System.arraycopy(Items.get("normal-player"), 0, items, 0, NormalPlayerCommandPermissions);
                for(int i = NormalPlayerCommandPermissions; i < UserPermissions.length; i++){
                    items[i] = ValuesToKeys(UserPermissions[i- NormalPlayerCommandPermissions], me.overlight.Commands.Commands.ManHunt.PermissionIDs);
                }
            }
        } else if(args.length == 2){
            if(Objects.equals(args[0], "hunter") || Objects.equals(args[0], "runner") || Objects.equals(args[0], "spectator")){
                if(sender.isOp()) {
                    items = new String[4];
                    for(int i = 0; i < 3; i++)
                        items[i] = Items.get("02")[i];
                    if (Objects.equals(args[0], "hunter") || Objects.equals(args[0], "runner")) {
                        items[3] = "spawn";
                    }
                }
            } else if(Objects.equals(args[0], "help")){
                HashMap<String, String[]> commandCodes = new HashMap<>();
                if(sender.isOp()) {
                    commandCodes = Commands();
                }
                else{
                    commandCodes.put("time", new String[]{"show how many times manhunt started!", "/ManHunt time"});
                }
                items = ArrayOptions.ObjectToStringConvert(commandCodes.keySet().toArray());
            } else if (Objects.equals(args[0], "reload")){
                items = Items.get("03");
            } else if(Objects.equals(args[0], "locate")){
                if(sender.isOp()){
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.ToPlayerObject(getOnlinePlayers().toArray()));
                }
                else{
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(Objects.requireNonNull(VariableManager.RolesToVariable(VariableManager.MapRoles.get((Player) sender)))));
                }
            } else if(Objects.equals(args[0], "challenge")){
                items = Items.get("05");
            } else if(Objects.equals(args[0], "teams")){
                items = Items.get("06");
            } else if(Objects.equals(args[0], "settings")){
                items = Items.get("setting");
            } else if(Objects.equals(args[0], "mode")){
                items = Items.get("08");
            } else if(Objects.equals(args[0], "vote")){
                //items = Items.get("vote");
            } else if(Objects.equals(args[0], "permissions")){
                items = ArrayOptions.PlayerToListObject(ArrayOptions.ToPlayerObject(Bukkit.getOnlinePlayers().toArray())).toArray(new String[0]);
            }
        } else if(args.length == 3){
            if(Objects.equals(args[1], "add")){
                if(Objects.equals(args[0], "hunter") || Objects.equals(args[0], "runner") || Objects.equals(args[0], "spectator")){
                    items = ArrayOptions.PlayerToStringObject(GetPlayersWithOutRank());
                }
            } else if(Objects.equals(args[1], "remove")){
                if(Objects.equals(args[0], "hunter"))
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(VariableManager.Hunters));
                else if(Objects.equals(args[0], "runner"))
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(VariableManager.Runners));
                else if(Objects.equals(args[0], "spectator"))
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(VariableManager.Spectators));
                else if(Objects.equals(args[0], "spammer"))
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(VariableManager.Spammer));
                else if(Objects.equals(args[0], "guardian"))
                    items = ArrayOptions.PlayerToStringObject(ArrayOptions.GetNotNullItems(VariableManager.Guardian));
            } else if(Objects.equals(args[0], "teams")) {
                items = ArrayOptions.ObjectToStringConvert(VariableManager.Teams.keySet().toArray());
            } else if(Objects.equals(args[0], "settings")){
                items = Items.get("setting.options");
            } else if(Objects.equals(args[0], "permissions")){
                items = Items.get("permissions.options");
            }
        } else if(args.length == 4) {
            if(Objects.equals(args[0], "teams")) {
                if(Objects.equals(args[1], "remove")){
                    String[] itemsIsIn = new String[VariableManager.Teams.keySet().toArray().length];
                    itemsIsIn = VariableManager.Teams.keySet().toArray(itemsIsIn);
                    items = itemsIsIn;
                } else if(Objects.equals(args[1], "edit")){
                    items = Items.get("07");
                } else if(Objects.equals(args[1], "list")){
                    items = VariableManager.Teams.keySet().toArray(new String[VariableManager.Teams.keySet().toArray().length]);
                } else if(Objects.equals(args[0], "permissions")){
                    if(!Objects.equals(args[2], "clear")){
                        try {
                            if (Objects.equals(args[2], "give")) {
                                items = ArrayOptions.RemoveArray(Items.get("permissions.perms"), getPermissions(Items.get("permissions.perms"), Bukkit.getPlayer(args[1])));
                            } else {
                                items = getPermissions(Items.get("permissions.perms"), Bukkit.getPlayer(args[1]));
                            }
                        } catch(Exception ignored){ }
                    }
                }
            }
        } else if(args.length == 5) {
            if(Objects.equals(args[1], "edit")) {
                if (Objects.equals(args[3], "add")) {
                    String[] PlayersWithOutTeam = ArrayOptions.PlayerToStringObject(ArrayOptions.ToPlayerObject(getOnlinePlayers().toArray()));
                    for (String item : VariableManager.Teams.keySet())
                        PlayersWithOutTeam = ArrayOptions.PlayerToStringObject(ArrayOptions.RemoveArray(ArrayOptions.ToPlayerObject2(PlayersWithOutTeam), ArrayOptions.ToPlayerObject3(VariableManager.Teams.get(item).toArray())));
                    items = PlayersWithOutTeam;
                } else if (Objects.equals(args[3], "remove")) {
                    items = ArrayOptions.ObjectToStringConvert(VariableManager.Teams.get(args[2]).toArray());
                }
            }
        }
        items = ArrayOptions.GetNotNullItems(items);
        for(String item : items){
            try {
                if (Objects.equals(args[args.length-1], item.substring(0, args[args.length-1].length()))){
                    output.add(item);
                }
            } catch(Exception ignored) { }
        }
        return output;
    }

    private Player[] GetPlayersWithOutRank(){
        return ArrayOptions.RemoveArray(ArrayOptions.ToPlayerObject(getOnlinePlayers().toArray()), ArrayOptions.RemoveArray(ArrayOptions.ToPlayerObject(getOnlinePlayers().toArray()), ArrayOptions.ToPlayerObject(VariableManager.MapRoles.keySet().toArray())));
    }
    private String ValuesToKeys (String value, HashMap<String, String> map) {
       for(String key: map.keySet()){
           if(Objects.equals(map.get(key), value)){
               return key;
           }
       }
       return null;
    }
    private String[] getPermissions(String[] Permissions, Player p){
        String[] perms = new String[Permissions.length];
        int index = 0;
        for(int i = 0; i < perms.length; i++){
            if(me.overlight.ManHunt.perms.has(p, Permissions[i])){
                perms[index] = Permissions[i];
                index++;
            }
        }
        return ArrayOptions.GetNotNullItems(perms);
    }
}
