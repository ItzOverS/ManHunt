package me.overlight.Managers;

import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import me.overlight.ManHunt;
import me.overlight.Timers.HuntersWaitTimer;
import me.overlight.Timers.ManHuntTimer;
import me.overlight.Timers.PlayerHPShower;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import static me.overlight.Commands.Commands.ManHunt.GenerateTargetingCompass;
import static me.overlight.Managers.VariableManager.*;

public class MethodManager {
    public static void SendUpdateMessage(Player player){
        player.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "This option is currently updating...");
    }
    public static HashMap<String, String[]> Commands (){
        HashMap<String, String[]> commandCodes = new HashMap<>();
        commandCodes.put("hunter add", new String[]{"add a new hunter!", "/ManHunt hunter add {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("hunter.add")});
        commandCodes.put("runner add", new String[]{"add a new runner!", "/ManHunt runner add {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.add")});
        commandCodes.put("spectator add", new String[]{"add a new spectator", "/ManHunt spectator add {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("spectator.add")});
        commandCodes.put("hunter remove", new String[]{"remove a player from hunter permissions!", "/ManHunt hunter remove {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("hunter.remove")});
        commandCodes.put("runner remove", new String[]{"remove a player from runner permissions!", "/ManHunt runner remove {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.remove")});
        commandCodes.put("spectator remove", new String[]{"remove a player from spectator permissions!", "/ManHunt spectator remove {player}", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("spectator.remove")});
        commandCodes.put("hunter list", new String[]{"show list of registered hunters!", "/ManHunt hunter list", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("hunter.list")});
        commandCodes.put("runner list", new String[]{"show list of registered runners!", "/ManHunt runner list", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.list")});
        commandCodes.put("spectator list", new String[]{"show list of registered spectators!", "/ManHunt spectator list", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("spectator.list")});
        commandCodes.put("hunter spawn", new String[]{"set hunter's spawn location when game start!", "/ManHunt hunter spawn set", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("hunter.spawn")});
        commandCodes.put("hunter spawn set", new String[]{"set hunter's spawn location when game start!", "/ManHunt hunter spawn set", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("hunter.spawn")});
        commandCodes.put("runner spawn", new String[]{"set runner's spawn location when game start!", "/ManHunt runner spawn set", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.spawn")});
        commandCodes.put("runner spawn set", new String[]{"set runner's spawn location when game start!", "/ManHunt runner spawn set", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.spawn")});
        commandCodes.put("time", new String[]{"show how many times manhunt started!", "/ManHunt time"});
        commandCodes.put("hunter spawn radians", new String[]{"change hunter's spawn radians!", "/ManHunt hunter spawn radians", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.spawn")});
        commandCodes.put("runner spawn radians", new String[]{"change runner's spawn radians!", "/ManHunt runner spawn radians", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("runner.spawn")});
        commandCodes.put("spawn", new String[]{"set all players spawn location", "/manhunt spawn", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("spawn")});
        commandCodes.put("start", new String[]{"start the man hunt!", "/ManHunt start", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("start")});
        commandCodes.put("stop", new String[]{"stop the man hunt!", "/ManHunt stop", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("stop")});
        commandCodes.put("reload", new String[]{"reload plugin!", "/ManHunt reload", me.overlight.Commands.Commands.ManHunt.PermissionIDs.get("reload")});
        commandCodes.put("tphm", new String[]{"teleport the sender(spectator) to another player!", "/TPHM {player}"});
        commandCodes.put("kills", new String[]{"show kill's of your self!", "/Kills"});
        commandCodes.put("target", new String[]{"target a runner!(Only hunters)", "/Target {player.runner"});
        commandCodes.put("track", new String[]{"target a runner!(Only hunters)", "/track {player.runner"});
        return commandCodes;
    }
    public static void StartManHunt() {
        boolean IF = false;
        if(HuntMode == Modes.MANHUNT)
            IF = ArrayOptions.ArrayLength(Hunters) != 0 && ArrayOptions.ArrayLength(Runners) != 0 && HuntersLocation != null && RunnersLocation != null && SpawnLocation != null;
        else
            IF = VariableManager.Teams.size() > 1;
        if (IF) {
            /*
            for(Player p: Hunters)
                Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(file.getDouble("max-hunters-hp"));
            for(Player p: Runners)
                Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(file.getDouble("max-runners-hp"));
            */
            HuntersMoveTime = 10;
            GameStartTime = 3;
            GameStats = true;
            AllowMovement = false;
            time = new int[5];
            if(ShowPlayersHealth) {
                PlayerHPShower HPPlayer = new PlayerHPShower();
                HPPlayer.ShowHP();
            }
            if(VariableManager.HuntMode == Modes.MANHUNT) {
                HuntersWaitTimer HuntersWaiter = new HuntersWaitTimer();
                HuntersWaiter.HuntersTimer();
            }
            ManHuntTimer GameTimer = new ManHuntTimer();
            GameTimer.StartTimer();
            //GameStartTimer GameStarter = new GameStartTimer();
            //GameStarter.GameTimer();
            Objects.requireNonNull(GetPlugin().getServer().getWorld("world")).setGameRule(GameRule.FALL_DAMAGE, true);
            for (Player p : GetPlugin().getServer().getOnlinePlayers()) {
                for(PotionEffect effect: p.getActivePotionEffects()){
                    p.removePotionEffect(effect.getType());
                }
                PlayersName.put(p, p.getName());
                p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Man Hunt has Started!");
                p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "You can now only talk with your team mates!");
                p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GOLD + "To Talk with other teams use '!' before your message");
                try {
                    p.getInventory().clear();
                } catch (Exception ignored) {
                }
                try {
                    if(VariableManager.HuntMode == Modes.MANHUNT)
                        p.teleport(SpawnLocation);
                } catch (Exception ignored) {
                }
                try {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3, 255, false, false, false));
                } catch (Exception ignored) {
                }
                try {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 3, 255, false, false, false));
                } catch (Exception ignored) {
                }
                try {
                    p.setGameMode(GameMode.SURVIVAL);
                } catch (Exception ignored) {
                }
                try {
                    p.setAllowFlight(false);
                } catch (Exception ignored) {
                }
                try {
                    p.setFlying(false);
                } catch (Exception ignored) {
                }
                try {
                    p.setExp(0);
                } catch (Exception ignored) {
                }
                try {
                    Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
                    while (iterator.hasNext()) {
                        AdvancementProgress progress = p.getAdvancementProgress(iterator.next());
                        for (String criteria : progress.getAwardedCriteria())
                            progress.revokeCriteria(criteria);
                    }
                } catch (Exception ignored) {
                }
            }
            if(VariableManager.HuntMode == Modes.MANHUNT) {
                for (Player p : ArrayOptions.GetNotNullItems(Hunters)) {
                    ParticleTrack.put(p.getName(), false);
                    try {
                        if (TrackingCompass)
                            p.getInventory().setItem(8, GenerateTargetingCompass());
                        if (WoodenAttack)
                            p.getInventory().setItem(0, InventoryGen.GenItem(1, ChatColor.GOLD + "Hunter Sword", null, Material.WOODEN_SWORD));
                    } catch (Exception ignored) {
                    }
                    if(challenge == Challenges.TANKERHUNTER){
                        p.getInventory().setBoots(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_BOOTS));
                        p.getInventory().setChestplate(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_CHESTPLATE));
                        p.getInventory().setLeggings(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_LEGGINGS));
                        p.getInventory().setHelmet(InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_HELMET));
                        p.getInventory().setItem(0, InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_SWORD));
                        p.getInventory().setItem(1, InventoryGen.GenItem(1, "Hunter", null, Material.DIAMOND_AXE));
                    }
                    try {
                        Location HunterLocation = new Location(
                                Bukkit.getWorld("world"),
                                HuntersLocation.getX() + RandomNum(SpawnRadians.get(Roles.HUNTER)),
                                HuntersLocation.getY() + RandomNum(SpawnRadians.get(Roles.HUNTER)),
                                HuntersLocation.getZ() + RandomNum(SpawnRadians.get(Roles.HUNTER))
                        );
                        p.teleport(HunterLocation);
                        if (ArrayOptions.GetNull(Runners) == 1) {
                            if (TrackingCompass)
                                TargetCompass.put(p, Runners[0]);
                        }
                    } catch (Exception ignored) {
                    }
                    try {
                        p.setPlayerListName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + p.getName());
                        p.setCustomName(ChatColor.RED + "[Hunter] " + ChatColor.GOLD + p.getName());
                    } catch (Exception ignored) {
                    }
                }
                for (Player p : ArrayOptions.GetNotNullItems(Spectators)) {
                    try {
                        p.setGameMode(GameMode.SPECTATOR);
                    } catch (Exception ignored) {
                    }
                    try {
                        p.setPlayerListName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + p.getName());
                        p.setCustomName(ChatColor.RED + "[Spectator] " + ChatColor.GOLD + p.getName());
                    } catch (Exception ignored) {
                    }
                }
                for (Player p : ArrayOptions.GetNotNullItems(Runners)) {
                    try {
                        Location RunnerLocation = new Location(
                                Bukkit.getWorld("world"),
                                RunnersLocation.getX() + RandomNum(SpawnRadians.get(Roles.RUNNER)),
                                RunnersLocation.getY() + RandomNum(SpawnRadians.get(Roles.RUNNER)),
                                RunnersLocation.getZ() + RandomNum(SpawnRadians.get(Roles.RUNNER))
                        );
                        p.teleport(RunnerLocation);
                    } catch (Exception ignored) {
                    }
                    try {
                        p.setPlayerListName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + p.getName());
                        p.setCustomName(ChatColor.RED + "[Runner] " + ChatColor.GOLD + p.getName());
                    } catch (Exception ignored) {
                    }
                }
                for (Player p : ArrayOptions.GetNotNullItems(Spammer)) {
                    try {
                        p.getInventory().setItem(0, InventoryGen.GenItem(10, ChatColor.GOLD + "Zombie Spawn", null, Material.ZOMBIE_SPAWN_EGG));
                        p.getInventory().setItem(1, InventoryGen.GenItem(8, ChatColor.GOLD + "Skeleton Spawn", null, Material.SKELETON_SPAWN_EGG));
                        p.getInventory().setItem(2, InventoryGen.GenItem(3, ChatColor.GOLD + "Creeper Spawn", null, Material.SKELETON_SPAWN_EGG));
                        p.getInventory().setItem(3, InventoryGen.GenItem(5, ChatColor.GOLD + "Spider Spawn", null, Material.SKELETON_SPAWN_EGG));
                        p.getInventory().setItem(4, InventoryGen.GenItem(5, ChatColor.GOLD + "Cave Spider Spawn", null, Material.SKELETON_SPAWN_EGG));
                    } catch (Exception ignored) {
                    }
                    try {
                        p.setPlayerListName(ChatColor.RED + "[Spammer] " + ChatColor.GOLD + p.getName());
                        p.setCustomName(ChatColor.RED + "[Spammer] " + ChatColor.GOLD + p.getName());
                    } catch (Exception ignored) {
                    }
                }
                for (Player p : ArrayOptions.GetNotNullItems(Guardian)) {
                    try {
                        p.getInventory().setItem(0, InventoryGen.GenItem(1, ChatColor.GREEN + "Protector", null, Material.NETHER_STAR));
                    } catch (Exception ignored) {
                    }
                    try {
                        p.getInventory().setItem(8, GenerateTargetingCompass());
                    } catch (Exception ignored) {
                    }
                    try {
                        p.setPlayerListName(ChatColor.RED + "[Guardian] " + ChatColor.GOLD + p.getName());
                        p.setCustomName(ChatColor.RED + "[Guardian] " + ChatColor.GOLD + p.getName());
                    } catch (Exception ignored) {
                    }
                }
            } else{
                for(String Key: Teams.keySet()){
                    int randTeamSpawnLocation = ArrayOptions.Random(-300, 300);
                    for(String PlayersInTeam: Teams.get(Key)){
                        int randPlayerInTeamSpawnLocation = ArrayOptions.Random(-5, 5);
                        double XLoc = SpawnLocation.getX() + randTeamSpawnLocation + randPlayerInTeamSpawnLocation, ZLoc = SpawnLocation.getZ() + randTeamSpawnLocation + randPlayerInTeamSpawnLocation;
                        Objects.requireNonNull(Bukkit.getPlayer(PlayersInTeam)).teleport(new Location(Bukkit.getWorld("world"), XLoc, Objects.requireNonNull(Bukkit.getWorld("world")).getHighestBlockYAt((int) XLoc, (int) ZLoc), ZLoc));
                    }
                }
            }
        }
    }

    public static void StopManHunt(Player sender) {
        if (GameStats) {
            // disable timers
            if (VariableManager.HuntMode == Modes.MANHUNT)
                Bukkit.getScheduler().cancelTask(ManHuntTimerTaskID);
            Bukkit.getScheduler().cancelTask(PlayerHPShowTaskID);

            GameStats = false;
            for (Player p : GetPlugin().getServer().getOnlinePlayers()) {
                p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Man Hunt Stopped!");
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.getWorld().setGameRule(GameRule.FALL_DAMAGE, false);
                p.setPlayerListName(ChatColor.RED + "[Player] " + ChatColor.GOLD + PlayersName.get(p));
                p.setCustomName(ChatColor.RED + "[Player] " + ChatColor.GOLD + PlayersName.get(p));
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.teleport(SpawnLocation);
            }
            Objects.requireNonNull(GetPlugin().getServer().getWorld("world")).setGameRule(GameRule.FALL_DAMAGE, true);
        } else {
            sender.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "ManHunt is currently not started yet");
        }
    }
    public static void reloadConfig (){
        VariableManager.ColorableChat = VariableManager.file.getBoolean("colorable-chat");
        VariableManager.SmelterPickaxe = VariableManager.file.getBoolean("smelter-pickaxe");
        VariableManager.TrackingCompass = VariableManager.file.getBoolean("tracking-compass");
        VariableManager.ChatEnabled = VariableManager.file.getBoolean("chat-enabled");
        VariableManager.MaxServerPlayers = (Objects.equals(VariableManager.file.getString("max-server-players"), "server.MaxPlayers"))?ManHunt.GetPlugin().getServer().getMaxPlayers():VariableManager.file.getInt("max-server-players");
        VariableManager.ShowPlayersHealth = VariableManager.file.getBoolean("show-players-hp-on-tab-and-screen");
        VariableManager.WoodenAttack = VariableManager.file.getBoolean("give-wooden-sword-to-hunter");
        for(World w: ManHunt.GetPlugin().getServer().getWorlds())
            w.setGameRule(GameRule.KEEP_INVENTORY, VariableManager.file.getBoolean("keep-inventory-on-dead"));
    }
    public static void RestartManHunt (){
        if(!VariableManager.GameStats){
            return;
        }
        if(VariableManager.HuntMode == Modes.MANHUNT)
            Bukkit.getScheduler().cancelTask(ManHuntTimerTaskID);
        Bukkit.getScheduler().cancelTask(PlayerHPShowTaskID);

        GameStats = false;
        for (Player p : GetPlugin().getServer().getOnlinePlayers()) {
            try {
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.getWorld().setGameRule(GameRule.FALL_DAMAGE, false);
                p.setPlayerListName(ChatColor.GOLD + "[Player] " + PlayersName.get(p));
                p.setCustomName(ChatColor.GOLD + "[Player] " + PlayersName.get(p));
            } catch (Exception ignored) {
            }
        }
        StartManHunt();
        for(Player p: Bukkit.getOnlinePlayers())
            p.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "]" + ChatColor.GREEN + "ManHunt Restarted!");
    }
    public static HashMap<Player, Roles> GenerateRandomizedRanks (Player[] players, Roles[] ranks, int[] maximumPlayerPerRank){
        HashMap<Player, Roles> PlayerRoles = new HashMap<>();
        HashMap<Roles, Integer> maxUses = new HashMap<>();
        for (Roles remainedRank : ranks) maxUses.put(remainedRank, 0);
        Random random = new Random();
        for(Player player: players){
            Roles playerRank = FAIWNASNKSA(random, ranks, maximumPlayerPerRank, ranks, maxUses);
            PlayerRoles.put(player, playerRank);
        }
        return PlayerRoles;
    }
    private static int searchForIndex(Roles[] roles, Roles role){
        for(int i = 0; i < roles.length; i++){
            if(roles[i]==role){
                return i;
            }
        }
        return -1;
    }
    private static Roles FAIWNASNKSA (Random random, Roles[] remainedRanks, int[] maximumPlayerPerRank, Roles[] ranks, HashMap<Roles, Integer> maxUses){
        int index = random.nextInt(remainedRanks.length);
        Roles playerRank = remainedRanks[index];
        if(maxUses.get(playerRank)>maximumPlayerPerRank[searchForIndex(ranks, playerRank)]){
            remainedRanks[searchForIndex(ranks, playerRank)] = null;
            remainedRanks = ArrayOptions.GetNotNullItems(remainedRanks);
            FAIWNASNKSA(random, remainedRanks, maximumPlayerPerRank, ranks, maxUses);
        }
        return playerRank;
    }
    public static Roles getPlayerRank(Player player){
        Player[][] search = {Hunters, Runners, Spectators, Guardian, Spammer};
        Roles[] roles = {Roles.HUNTER, Roles.RUNNER, Roles.SPECTATOR, Roles.GUARDIAN, Roles.SPAMMER};
        for(int i = 0; i < search.length; i++){
            if(ArrayOptions.ContainsPlayer(search[i], player)!=-1){
                return roles[i];
            }
        }
        return Roles.NULL;
    }
    public static int RandomNum(int length){
        Random random = new Random();
        int num = random.nextInt(length);
        int negative = random.nextInt(2);
        if(negative == 1){
            num = Integer.parseInt("-" + num);
        }
        return num;
    }
}
