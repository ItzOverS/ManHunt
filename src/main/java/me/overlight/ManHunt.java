package me.overlight;

import me.overlight.Commands.Commands.Kills;
import me.overlight.Commands.Commands.ManTP;
import me.overlight.Commands.Commands.Target;
import me.overlight.Commands.Commands.Vote;
import me.overlight.Commands.TabCompleter.Track;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Managers.MethodManager;
import me.overlight.Managers.VariableManager;
import me.overlight.Timers.GamePowerManager;
import me.overlight.Events.*;
import me.overlight.Timers.HuntersForceTimer;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

public final class ManHunt extends JavaPlugin {


    private static ManHunt plugin;
    public static ManHunt GetPlugin() {
        return plugin;
    }
    public static Permission perms;
    public static World WorldGeneratingLobby = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        VariableManager.file = getConfig();
        saveDefaultConfig();
        try {
            setupPermissions();
        } catch(Exception ignored) { }

        VariableManager.SpawnLocation = new Location(ManHunt.GetPlugin().getServer().getWorld("world"), 0, Objects.requireNonNull(ManHunt.GetPlugin().getServer().getWorld("world")).getHighestBlockYAt(0, 0) + 2, 0);
        VariableManager.SpawnRadians.put(VariableManager.Roles.HUNTER, 0);
        VariableManager.SpawnRadians.put(VariableManager.Roles.RUNNER, 0);
        VariableManager.PluginAttributes.put("name", "ManHunt++");
        VariableManager.PluginAttributes.put("version", "9.0.0");
        VariableManager.PluginAttributes.put("copy-right", "2022");
        VariableManager.PluginAttributes.put("author", "_OverLight_");
        VariableManager.PluginAttributes.put("publisher", "_OverLight_");
        VariableManager.PluginAttributes.put("idea-generator", "_OverLight_");
        VariableManager.PluginAttributes.put("bug-hunter", "_OverLight_ HeroBrineX2020");
        VariableManager.PluginAttributes.put("developer", "_OverLight_");
        VariableManager.PluginAttributes.put("mc-version", "1.16.x");
        VariableManager.PluginAttributes.put("description", "Create a man hunt on server");
        VariableManager.PluginAttributes.put("java", "1.8");
        VariableManager.PluginAttributes.put("compiler", "Maven");
        VariableManager.PluginAttributes.put("software-required", "SPIGoT");

        // Commands
        Objects.requireNonNull(getServer().getPluginCommand("ManHunt")).setExecutor(new me.overlight.Commands.Commands.ManHunt());
        Objects.requireNonNull(getServer().getPluginCommand("ManHunt")).setTabCompleter(new me.overlight.Commands.TabCompleter.ManHunt());
        Objects.requireNonNull(getServer().getPluginCommand("Vote")).setExecutor(new Vote());
        Objects.requireNonNull(getServer().getPluginCommand("Vote")).setTabCompleter(new me.overlight.Commands.TabCompleter.Vote());
        Objects.requireNonNull(getServer().getPluginCommand("Track")).setExecutor(new Target());
        Objects.requireNonNull(getServer().getPluginCommand("Track")).setTabCompleter(new Track());
        Objects.requireNonNull(getServer().getPluginCommand("TPM")).setExecutor(new ManTP());
        Objects.requireNonNull(getServer().getPluginCommand("Kills")).setExecutor(new Kills());

        //timers enabled
        GamePowerManager powerManager = new GamePowerManager();
        powerManager.Manager();
        HuntersForceTimer huntersForceTimer = new HuntersForceTimer();
        huntersForceTimer.run();

        //Config.yml
        MethodManager.reloadConfig();

        // Events
        getServer().getPluginManager().registerEvents(new OnEntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDied(), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerSleep(), this);
        getServer().getPluginManager().registerEvents(new OnPotionDrop(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerKicked(), this);
        getServer().getPluginManager().registerEvents(new OnAdvancementTaken(), this);
        //getServer().getPluginManager().registerEvents(new OnBukkitUse(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerWalk(), this);
        getServer().getPluginManager().registerEvents(new OnItemUse(), this);
        getServer().getPluginManager().registerEvents(new OnInventoryItemSelected(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerSendMessage(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinServer(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeftServer(), this);
        //getServer().getPluginManager().registerEvents(new OnPlayerPickUpArrow(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerSendCommands(), this);
        getServer().getPluginManager().registerEvents(new OnExplodeCreated(), this);

        //Rank set
        for (Player p : getServer().getOnlinePlayers()) {
            p.setPlayerListName(ChatColor.RED + "[Player] " + ChatColor.GOLD + p.getName());
            try{ p.setGameMode(GameMode.ADVENTURE); } catch(Exception ignored){ }
            try{ p.setAllowFlight(true); } catch(Exception ignored){ }
            try{ p.setFlying(true); } catch(Exception ignored){ }
            try{ p.getWorld().setGameRule(GameRule.FALL_DAMAGE, false); } catch(Exception ignored){ }
            try{ p.setSaturation(20); } catch(Exception ignored){ }
            try{ p.setHealth((float) Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()); } catch(Exception ignored){ }
        }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GREEN + " Simplify turned on!");
    }

    @Override
    public void onDisable() {
        if(VariableManager.GameStats){
            YamlConfiguration dataBackup = new YamlConfiguration();
            dataBackup.set("gameStats", VariableManager.GameStats);
            dataBackup.set("hunters", Arrays.asList(ArrayOptions.PlayerToStringObject(VariableManager.Hunters)));
            dataBackup.set("runners", Arrays.asList(ArrayOptions.PlayerToStringObject(VariableManager.Runners)));
            dataBackup.set("spectator", Arrays.asList(ArrayOptions.PlayerToStringObject(VariableManager.Spectators)));
            dataBackup.set("spammer", Arrays.asList(ArrayOptions.PlayerToStringObject(VariableManager.Spammer)));
            dataBackup.set("guardian", Arrays.asList(ArrayOptions.PlayerToStringObject(VariableManager.Guardian)));
            dataBackup.set("huntMode", VariableManager.HuntMode.toString());
            for(Player p: VariableManager.OnlinePlayers){
                List<String> location = new ArrayList<>();
                location.add(p.getLocation().getWorld().getName());
                location.add(String.valueOf(p.getLocation().getX()));
                location.add(String.valueOf(p.getLocation().getY()));
                location.add(String.valueOf(p.getLocation().getZ()));
                dataBackup.set("players." + p.getName(), location);
            }
            try {
                dataBackup.save("plugins\\ManHunt\\load-save.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}