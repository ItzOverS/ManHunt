package me.overlight.Managers;

import me.overlight.ManHunt;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class VariableManager {
    //Variables

    public static ManHunt GetPlugin() {
        return ManHunt.GetPlugin();
    }
    public static Player[] Hunters = new Player[100];
    public static Player[] Runners = new Player[100];
    public static Player[] Spectators = new Player[100];
    public static Player[] Spammer = new Player[100];
    public static Player[] Guardian = new Player[100];
    public static HashMap<Player, Integer> kills = new HashMap<>();
    public static Dictionary<Player, Roles> DicRoles = new Dictionary<Player, Roles>() {
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
        public Enumeration<Roles> elements() {
            return null;
        }

        @Override
        public Roles get(Object key) {
            return null;
        }

        @Override
        public Roles put(Player key, Roles value) {
            return null;
        }

        @Override
        public Roles remove(Object key) {
            return null;
        }
    };
    public static HashMap<Player, Roles> MapRoles = new HashMap<>();
    public static boolean GameStats = false;
    public static boolean PluginStats = true;
    public static Location HuntersLocation = null;
    public static Location RunnersLocation = null;
    public static Location SpawnLocation = null;
    public static HashMap<Player, Player> TargetCompass = new HashMap<>();
    public static HashMap<String, String> PluginAttributes = new HashMap<>();
    public static HashMap<Player, String> PlayersName = new HashMap<>();
    public static HashMap<String, Roles> NameRules = new HashMap<>();
    public static boolean
            ColorableChat = true,
            SmelterPickaxe = false,
            TrackingCompass = true,
            ChatEnabled = true,
            ShowPlayersHealth = false,
            WoodenAttack = false;
    public static HashMap<String, String> KilledPlayers = new HashMap<>();
    //public static HashMap<String, Integer> IndexOfLastLocation = new HashMap<>();
    public static HashMap<String, Integer> BukkitTaskIDs = new HashMap<>();
    public static HashMap<Roles, Integer> SpawnRadians = new HashMap<>();
    public static int
            PlayerHPShowTaskID = -1,
            HuntersWaitTimerTaskID = -1,
            GameStartTimerTaskID = -1,
            HuntersMoveTime = 10,
            GameStartTime = 3,
            GamePowerManagerID = -1;
    public static boolean AllowMovement = false, GameStarted = true;
    public static int NPCSpawnTaskID = -1, NPCSpawnTimer = 120;
    public static int ManHuntTimerTaskID = -1;
    public static FileConfiguration file = null;
    //public static String[] BannedPlayers = new String[100];
    public static int[] time = new int[5];
    public static String[] MutedPlayers = new String[100];
    public static Map<String, List<String>> Teams = new HashMap<>(); // HashMap<TeamName, TeamMates>
    public static Modes HuntMode = Modes.MANHUNT;
    public static int MaxServerPlayers = 5;
    public static boolean KickedPlayer = false;
    public static HashMap<String, Roles> Votes = new HashMap<>(); // HashMap<Player, TargetRole>
    public static Challenges challenge = Challenges.NULL;
    public static boolean RanksVoteToggle = false;
    public static Collection<Player> OnlinePlayers = new ArrayList<>();
    public static HashMap<String, Boolean> ParticleTrack = new HashMap<>();
    //public static HashMap<String, List<String>> AttackApply = new HashMap<>();
    public enum Roles {
        HUNTER,
        RUNNER,
        SPECTATOR,
        GUARDIAN,
        SPAMMER,
        NULL
    }
    public enum Modes {
        MANHUNT,
        FACTIONS
    }
    public enum Challenges {
        VAMPIERRUNNER,
        SHARPSHIFT,
        TANKERHUNTER,
        NULL
        }


    // ManHunt Main Methods

    public static String RolesToStringConvert(Roles role){
        if(role == Roles.HUNTER)
            return "hunter";
        else if(role == Roles.RUNNER)
            return "runner";
        else if(role == Roles.SPECTATOR)
            return "spectate";
        else if(role == Roles.SPAMMER)
            return "spammer";
        else if (role == Roles.GUARDIAN)
            return "guardian";

        return "";
    }
    public static Player[] RolesToVariable(Roles role){
        if(role == Roles.HUNTER)
            return Hunters;
        else if(role == Roles.RUNNER)
            return Runners;
        else if(role == Roles.SPECTATOR)
            return Spectators;
        else if(role == Roles.SPAMMER)
            return Spammer;
        else if(role == Roles.GUARDIAN)
            return Guardian;

        return null;
    }
}