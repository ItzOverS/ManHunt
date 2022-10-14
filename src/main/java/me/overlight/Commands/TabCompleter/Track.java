package me.overlight.Commands.TabCompleter;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Track implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> output = new ArrayList<>();
        String[] items = new String[100];

        if(args.length == 1){
            try {
                Player[] player = ArrayOptions.ToPlayerObject(ArrayOptions.ConvertNamesToPlayer(ArrayOptions.ObjectToStringConvert(VariableManager.KilledPlayers.values().toArray())));
                items = ArrayOptions.PlayerToStringObject(ArrayOptions.RemoveArray(VariableManager.Runners, player));
            } catch(Exception ignored) { }
        }

        items = ArrayOptions.GetNotNullItems(items);
        for(String item : items){
            String object = "";
            try {
                object = item.substring(0, args[0].length());
            } catch (Exception ignored) { }
            if (Objects.equals(args[0], object)) {
                output.add(item);
            }
        }
        return output;
    }
}
