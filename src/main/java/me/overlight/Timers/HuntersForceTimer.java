package me.overlight.Timers;

import me.overlight.Events.OnPlayerWalk;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.ManHunt;
import me.overlight.Managers.VariableManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HuntersForceTimer {
    public void run(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ManHunt.GetPlugin(), () -> {
            for(Player p: ArrayOptions.GetNotNullItems(VariableManager.Hunters)) {
                if(VariableManager.TargetCompass.containsKey(p) && VariableManager.TargetCompass.get(p) != null) {
                    if (VariableManager.ParticleTrack.get(p.getName())) {
                        OnPlayerWalk.DrawParticle(p.getLocation(), VariableManager.TargetCompass.get(p).getLocation(), 2);
                    }
                }
            }
            for(Player p: VariableManager.TargetCompass.keySet()) {
                if (VariableManager.TargetCompass.get(p) != null) {
                    p.setCompassTarget(VariableManager.TargetCompass.get(p).getLocation());
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + VariableManager.TargetCompass.get(p).getName() + " " + ChatColor.GOLD + "Distance: " + ArrayOptions.subFloat(p.getLocation().distance(VariableManager.TargetCompass.get(p).getLocation()), 2)));
                }
            }
        }, 5, 5);
    }
}
