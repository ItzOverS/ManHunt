package me.overlight.Events;

import me.overlight.Commands.Commands.ManHunt;
import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static me.overlight.Managers.MethodManager.*;

public class OnInventoryItemSelected implements Listener {

    @EventHandler
    public void InventorySelectedItem(InventoryClickEvent e) {
        if (!VariableManager.PluginStats) {
            return;
        }
            Player target = (Player) e.getWhoClicked();
            if(e.getCurrentItem() == null){
                return;
            }
            for (int XXZY = 0; XXZY < 1; XXZY++) {
                if (e.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Track")) {
                    e.setCancelled(true);
                    if(e.getCurrentItem().getType() == Material.GLOWSTONE_DUST){
                        VariableManager.ParticleTrack.put(e.getView().getPlayer().getName(), !VariableManager.ParticleTrack.get(e.getView().getPlayer().getName()));
                        {
                            HashMap<Integer, ItemStack> LifePlayers = new HashMap<>();
                            int index = 0;
                            for(Player p: ArrayOptions.GetNotNullItems(VariableManager.Runners)){
                                if(!p.isDead()) {
                                    LifePlayers.put(index, InventoryGen.GenItem(1, ChatColor.YELLOW + p.getName(), null, Material.PLAYER_HEAD));
                                    index++;
                                }
                            }
                            LifePlayers.put(53, InventoryGen.GenItem(1, ChatColor.GOLD + "Particle track: " + ((VariableManager.ParticleTrack.get(e.getView().getPlayer().getName()))?ChatColor.GREEN + "ON":ChatColor.RED + "OFF"), null, Material.GLOWSTONE_DUST));
                            e.getView().getPlayer().openInventory(InventoryGen.GenInv(54, LifePlayers, ChatColor.RED + "Track"));
                        }
                        break;
                    }
                    VariableManager.TargetCompass.remove(target);
                    VariableManager.TargetCompass.put((Player) target, Bukkit.getPlayer(Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName().substring(2)));
                    target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Now tracking " + ChatColor.RED + Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName());
                } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Man" + ChatColor.RED + "Hunt")) {
                    String DisPlay = Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName();
                    if (DisPlay.equalsIgnoreCase(ChatColor.GOLD + "Change Ranks")) {
                        ShowPlayersGUI((Player)e.getWhoClicked());
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.GOLD + "Spawn")) {
                        ShowSpawnSetGUI(target);
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.GREEN + "Start")) {
                        StartManHunt();
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.RED + "Stop")) {
                        StopManHunt(target);
                    }
                    e.setCancelled(true);
                } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Change Roles")) {
                    e.setCancelled(true);
                    ShowPlayersRankData(target, e);
                } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Select Role")) {
                    e.setCancelled(true);
                    ItemStack selectedItem = e.getCurrentItem();
                    assert selectedItem != null;
                    if (selectedItem.getType() == Material.BARRIER) {
                        target.closeInventory();
                        ShowPlayersGUI((Player)e.getWhoClicked());
                        break;
                    }
                    Player TargetRanker = Bukkit.getPlayer(Objects.requireNonNull(Objects.requireNonNull(e.getInventory().getItem(40)).getItemMeta()).getDisplayName().substring(2));
                    String DisplayName = Objects.requireNonNull(selectedItem.getItemMeta()).getDisplayName();
                    assert TargetRanker != null;
                    String tag;
                    try {
                        VariableManager.Roles role = VariableManager.MapRoles.get(TargetRanker);
                        tag = ArrayOptions.ToBiggerChar(VariableManager.RolesToStringConvert(role), 0);
                    } catch (Exception ignored) {
                        tag = "None";
                    }
                    if (DisplayName.equalsIgnoreCase(ChatColor.GOLD + "Hunter")) {
                        if (tag.equalsIgnoreCase("None"))
                            ManHunt.HunterAppend(target, new String[]{"", "", TargetRanker.getName()});
                        else if (tag.equalsIgnoreCase("Hunter"))
                            target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + TargetRanker.getName() + " is already a hunter!");
                        else {
                            try {
                                switch (tag) {
                                    case "Runner":
                                        VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, TargetRanker)] = null;
                                        break;
                                    case "Spammer":
                                        VariableManager.Spammer[ArrayOptions.ContainsPlayer(VariableManager.Spammer, TargetRanker)] = null;
                                        break;
                                    case "Guardian":
                                        VariableManager.Guardian[ArrayOptions.ContainsPlayer(VariableManager.Guardian, TargetRanker)] = null;
                                        break;
                                    case "Spectator":
                                        VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, TargetRanker)] = null;
                                        break;
                                }
                            } catch (Exception ignored) {
                            }
                            ManHunt.HunterAppend(target, new String[]{"", "", TargetRanker.getName()});
                        }
                    } else if (DisplayName.equalsIgnoreCase(ChatColor.GOLD + "Runner")) {
                        if (tag.equalsIgnoreCase("None"))
                            ManHunt.RunnerAppend(target, new String[]{"", "", TargetRanker.getName()});
                        else if (tag.equalsIgnoreCase("Runner"))
                            target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + TargetRanker.getName() + " is already a runner!");
                        else {
                            try {
                                switch (tag) {
                                    case "Hunter":
                                        VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, TargetRanker)] = null;
                                        break;
                                    case "Spammer":
                                        VariableManager.Spammer[ArrayOptions.ContainsPlayer(VariableManager.Spammer, TargetRanker)] = null;
                                        break;
                                    case "Guardian":
                                        VariableManager.Guardian[ArrayOptions.ContainsPlayer(VariableManager.Guardian, TargetRanker)] = null;
                                        break;
                                    case "Spectator":
                                        VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, TargetRanker)] = null;
                                        break;
                                }
                            } catch (Exception ignored) {
                            }
                            ManHunt.RunnerAppend(target, new String[]{"", "", TargetRanker.getName()});
                        }
                    } else if (DisplayName.equalsIgnoreCase(ChatColor.GOLD + "Spectator")) {
                        if (tag.equalsIgnoreCase("None"))
                            ManHunt.SpectatorAppend(target, new String[]{"", "", TargetRanker.getName()});
                        else if (tag.equalsIgnoreCase("Spectator"))
                            target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + TargetRanker.getName() + " is already a spectator!");
                        else {
                            try {
                                switch (tag) {
                                    case "Hunter":
                                        VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, TargetRanker)] = null;
                                        break;
                                    case "Spammer":
                                        VariableManager.Spammer[ArrayOptions.ContainsPlayer(VariableManager.Spammer, TargetRanker)] = null;
                                        break;
                                    case "Guardian":
                                        VariableManager.Guardian[ArrayOptions.ContainsPlayer(VariableManager.Guardian, TargetRanker)] = null;
                                        break;
                                    case "Runner":
                                        VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, TargetRanker)] = null;
                                        break;
                                }
                            } catch (Exception ignored) {
                            }
                            ManHunt.SpectatorAppend(target, new String[]{"", "", TargetRanker.getName()});
                        }
                    } else if (DisplayName.equalsIgnoreCase(ChatColor.GOLD + "Spammer")) {
                        if (tag.equalsIgnoreCase("None"))
                            ManHunt.SpammerAppend(target, new String[]{"", "", TargetRanker.getName()});
                        else if (tag.equalsIgnoreCase("Spammer"))
                            target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + TargetRanker.getName() + " is already a spammer!");
                        else {
                            try {
                                switch (tag) {
                                    case "Hunter":
                                        VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, TargetRanker)] = null;
                                        break;
                                    case "Spectator":
                                        VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, TargetRanker)] = null;
                                        break;
                                    case "Guardian":
                                        VariableManager.Guardian[ArrayOptions.ContainsPlayer(VariableManager.Guardian, TargetRanker)] = null;
                                        break;
                                    case "Runner":
                                        VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, TargetRanker)] = null;
                                        break;
                                }
                            } catch (Exception ignored) {
                            }
                            ManHunt.SpammerAppend(target, new String[]{"", "", TargetRanker.getName()});
                        }
                    } else if (DisplayName.equalsIgnoreCase(ChatColor.GOLD + "Guardian")) {
                        if (tag.equalsIgnoreCase("None"))
                            ManHunt.GuardianAppend(target, new String[]{"", "", TargetRanker.getName()});
                        else if (tag.equalsIgnoreCase("Guardian"))
                            target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + TargetRanker.getName() + " is already a guardian!");
                        else {
                            try {
                                switch (tag) {
                                    case "Hunter":
                                        VariableManager.Hunters[ArrayOptions.ContainsPlayer(VariableManager.Hunters, TargetRanker)] = null;
                                        break;
                                    case "Spectator":
                                        VariableManager.Spectators[ArrayOptions.ContainsPlayer(VariableManager.Spectators, TargetRanker)] = null;
                                        break;
                                    case "Spammer":
                                        VariableManager.Spammer[ArrayOptions.ContainsPlayer(VariableManager.Spammer, TargetRanker)] = null;
                                        break;
                                    case "Runner":
                                        VariableManager.Runners[ArrayOptions.ContainsPlayer(VariableManager.Runners, TargetRanker)] = null;
                                        break;
                                }
                            } catch (Exception ignored) {
                            }
                            ManHunt.GuardianAppend(target, new String[]{"", "", TargetRanker.getName()});
                        }
                    }
                } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Spawn Set")) {
                    e.setCancelled(true);
                    String DisPlay = Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName();
                    if (DisPlay.equalsIgnoreCase(ChatColor.GOLD + "Hunter Spawn")) {
                        target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Hunter's spawn point set to " + target.getLocation().getX() + " " + target.getLocation().getY() + " " + target.getLocation().getZ());
                        VariableManager.HuntersLocation = target.getLocation();
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.GOLD + "Runner Spawn")) {
                        target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "Runner's spawn point set to " + target.getLocation().getX() + " " + target.getLocation().getY() + " " + target.getLocation().getZ());
                        VariableManager.RunnersLocation = target.getLocation();
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.GOLD + "Public Spawn")) {
                        target.sendMessage(ChatColor.RED + "[" + VariableManager.PluginAttributes.get("name") + "] " + ChatColor.GREEN + "All player's spawn point set to " + target.getLocation().getX() + " " + target.getLocation().getY() + " " + target.getLocation().getZ());
                        VariableManager.SpawnLocation = target.getLocation();
                    } else if (DisPlay.equalsIgnoreCase(ChatColor.RED + "Close")) {
                        target.closeInventory();
                        target.openInventory(ShowMainGUI());
                    }
                }
            }
    }

    public static Inventory ShowMainGUI() {
        String[] Texts = {ChatColor.GOLD + "Change Ranks", ChatColor.GOLD + "Reload", ChatColor.GOLD + "Spawn", ((VariableManager.GameStats) ? ChatColor.RED + "Stop" : ChatColor.GREEN + "Start")};
        int[] Location = {11, 13, 15, 40};
        ItemStack[] Items = {new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.GOLD_INGOT, 1), new ItemStack(Material.SPAWNER, 1), new ItemStack(((VariableManager.GameStats) ? Material.RED_WOOL : Material.GREEN_WOOL), 1)};
        HashMap<Integer, ItemStack> InvLayout = new HashMap<>();
        for (int i = 0; i < Texts.length; i++) {
            ItemStack item = Items[i];
            ItemMeta Meta = item.getItemMeta();
            assert Meta != null;
            Meta.setDisplayName(Texts[i]);
            item.setItemMeta(Meta);
            InvLayout.put(Location[i], item);
        }
        return InventoryGen.GenInv(54, InvLayout, ChatColor.GREEN + "Man" + ChatColor.RED + "Hunt");
    }

    private void ShowSpawnSetGUI(Player target) {
        int[] Location = {20, 22, 24, 49};
        ItemStack[] Items = {InventoryGen.GenItem(1, ChatColor.GOLD + "Hunter Spawn", null, Material.DIAMOND_SWORD), InventoryGen.GenItem(1, ChatColor.GOLD + "Runner Spawn", null, Material.DRAGON_EGG), InventoryGen.GenItem(1, ChatColor.GOLD + "Public Spawn", null, Material.DIAMOND), InventoryGen.GenItem(1, ChatColor.RED + "Close", null, Material.BARRIER)};
        HashMap<Integer, ItemStack> Item = new HashMap<>();
        for (int i = 0; i < Location.length; i++) {
            Item.put(Location[i], Items[i]);
        }
        target.openInventory(InventoryGen.GenInv(54, Item, ChatColor.GOLD + "Spawn Set"));
    }

    public static void ShowPlayersGUI(Player sender){
        HashMap<Integer, ItemStack> Items = new HashMap<>();
        int i = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack Stack = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta Meta = Stack.getItemMeta();
            assert Meta != null;
            Meta.setDisplayName(ChatColor.RED + p.getName());
            Stack.setItemMeta(Meta);
            Items.put(i, Stack);
            i++;
        }
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta Meta = item.getItemMeta();
        assert Meta != null;
        Meta.setDisplayName(ChatColor.RED + "Close");
        item.setItemMeta(Meta);
        Items.put(49, item);
        sender.openInventory(InventoryGen.GenInv(54, Items, ChatColor.GOLD + "Change Roles"));
    }

    private static void ShowPlayersRankData(Player target, InventoryClickEvent e) {
        if(e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().getType() == Material.BARRIER) {
            target.closeInventory();
            target.openInventory(ShowMainGUI());
            return;
        }
        /*
        if (e.getCurrentItem().getType() != Material.PLAYER_HEAD) {
            target.closeInventory();
            ShowPlayersGUI((Player) e.getWhoClicked());
        }

         */
        Player Ranker = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
        int SearchHunters = ArrayOptions.ContainsPlayer(VariableManager.Hunters, Ranker);
        int SearchRunners = ArrayOptions.ContainsPlayer(VariableManager.Runners, Ranker);
        int SearchSpectators = ArrayOptions.ContainsPlayer(VariableManager.Spectators, Ranker);
        int SearchSpammer = ArrayOptions.ContainsPlayer(VariableManager.Spammer, Ranker);
        int SearchGuardian = ArrayOptions.ContainsPlayer(VariableManager.Guardian, Ranker);
        String tag = "None";
        try {
            if(VariableManager.MapRoles.containsKey(target)){
            VariableManager.Roles role = VariableManager.MapRoles.get(target);
            tag = ArrayOptions.ToBiggerChar(VariableManager.RolesToStringConvert(role), 0);
            }
        } catch (Exception ignored) {
            tag = "None";
        }
        if (SearchHunters == -1 && SearchRunners == -1 && SearchSpectators == -1 && SearchGuardian == -1 && SearchSpammer == -1) {
            HashMap<Integer, ItemStack> Item = new HashMap<>();
            assert Ranker != null;
            String[] Texts = {
                    ChatColor.RED + Ranker.getName() + "\n" + ChatColor.GOLD + "Rank: " + tag,
                    ChatColor.GOLD + "Hunter",
                    ChatColor.GOLD + "Runner",
                    ChatColor.GOLD + "Spectator",
                    ChatColor.GOLD + "Spammer",
                    ChatColor.GOLD + "Guardian",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ChatColor.RED + "Close",
                    "",
                    "",
                    "",
                    "",
                    ((VariableManager.Votes.containsKey(Ranker.getName()))? ChatColor.RED + "Voted for " + ChatColor.GOLD + VariableManager.Votes.get(Ranker.getName()).toString()
                            : ChatColor.RED + "Not Voted")
            };
            int[] Locations = {
                    40,
                    21,
                    22,
                    23,
                    30,
                    31,
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    17,
                    18,
                    26,
                    27,
                    35,
                    36,
                    44,
                    45,
                    46,
                    47,
                    48,
                    49,
                    50,
                    51,
                    52,
                    53,
                    13
            };
            ItemStack[] stacks = {
                    new ItemStack(Material.PLAYER_HEAD, 1),
                    new ItemStack(Material.DIAMOND_SWORD, 1),
                    new ItemStack(Material.DRAGON_EGG, 1),
                    new ItemStack(Material.SPECTRAL_ARROW, 1),
                    new ItemStack(Material.SPAWNER, 1),
                    new ItemStack(Material.SHIELD, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.BARRIER, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.SHIELD, 1)
            };
            for (int i = 0; i < stacks.length; i++) {
                ArrayList<String> list = new ArrayList<>();
                if (Texts[i].split("\n").length == 2) {
                    list.add(Texts[i].split("\n")[1]);
                }
                ItemStack item = stacks[i];
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(Texts[i]);
                meta.setLore(list);
                item.setItemMeta(meta);
                Item.put(Locations[i], item);
            }

            Inventory Inv = InventoryGen.GenInv(54, Item, ChatColor.GOLD + "Select Role");
            Ranker.closeInventory();
            target.openInventory(Inv);
        } else {
            HashMap<Integer, ItemStack> Item = new HashMap<>();
            assert Ranker != null;
            String[] Texts = {
                    ChatColor.RED + Ranker.getName() + "\n" + ChatColor.GOLD + "Rank: " + tag,
                    ChatColor.RED + "Remove",
                    ChatColor.GOLD + "Hunter",
                    ChatColor.GOLD + "Runner",
                    ChatColor.GOLD + "Spectator",
                    ChatColor.GOLD + "Spammer",
                    ChatColor.GOLD + "Guardian",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ChatColor.RED + "Close",
                    "",
                    "",
                    "",
                    ""
            };
            int[] Locations = {
                    40,
                    19,
                    23,
                    24,
                    25,
                    32,
                    33,
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    17,
                    18,
                    26,
                    27,
                    35,
                    36,
                    44,
                    45,
                    46,
                    47,
                    48,
                    49,
                    50,
                    51,
                    52,
                    53,
            };
            ItemStack[] stacks = {
                    new ItemStack(Material.PLAYER_HEAD, 1),
                    new ItemStack(Material.RED_STAINED_GLASS, 1),
                    new ItemStack(Material.DIAMOND_SWORD, 1),
                    new ItemStack(Material.DRAGON_EGG, 1),
                    new ItemStack(Material.SPECTRAL_ARROW, 1),
                    new ItemStack(Material.SPAWNER, 1),
                    new ItemStack(Material.SHIELD, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.BARRIER, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
                    new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1),
            };
            for (int i = 0; i < stacks.length; i++) {
                ArrayList<String> list = new ArrayList<>();
                if (Texts[i].split("\n").length > 1) {
                    list.add(Texts[i].split("\n")[1]);
                }
                ItemStack item = stacks[i];
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(Texts[i]);
                meta.setLore(list);
                item.setItemMeta(meta);
                Item.put(Locations[i], item);
            }

            Inventory Inv = InventoryGen.GenInv(54, Item, ChatColor.GOLD + "Select Role");
            target.closeInventory();
            target.openInventory(Inv);
        }
    }
}