package me.overlight.Libraries;

import me.overlight.Managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class ArrayOptions {
    public static boolean AllArrayLike (boolean[] list, boolean match){
        for(boolean item : list){
            if(item != match){
                return false;
            }
        }
        return true;
    }
    public static boolean AND (Block[] list, Block match){
        for(Block item : list){
            if(item != match){
                return false;
            }
        }
        return true;
    }
    public static boolean AND (Block[] list, Material match){
        for(Block item : list){
            if(item.getType() != match){
                return false;
            }
        }
        return true;
    }
    public static boolean OR (String[] list, String or){
        for (String i: list){
            if(Objects.equals(i, or))
                return true;
        }
        return false;
    }
    public static String subFloat(double num, int floatLength){
        String output = String.valueOf(num).substring(0, String.valueOf(num).indexOf("."));
        String floats = String.valueOf(num).substring(String.valueOf(num).indexOf(".")+1);
        if(floatLength > floats.length()){
            for(int i = 0; i < floatLength - floats.length(); i++){
                floats += "0";
            }
        } else {
            floats = floats.substring(0, floatLength);
        }
        return output + "." + floats;
    }
    public static String ToBiggerChar(String text, int index){
        String output = "";
        for(int i = 0; i < text.length(); i++){
            if(i == index){
                output += text.substring(i, i+1).toUpperCase();
                continue;
            }
            output += text.substring(i, i+1);
        }
        return output;
    }
    public static int ContainsPlayer (Player[] list, Player Runner){
        for(int i = 0; i < list.length; i++){
            if(list[i] == Runner){
                return i;
            }
        }
        return -1;
    }
    public static int ContainsString (String[] list, String Runner){
        for(int i = 0; i < list.length; i++){
            if(Objects.equals(list[i], Runner)){
                return i;
            }
        }
        return -1;
    }
    public static int ContainsBoolean (boolean[] list, boolean bool){
        for(int i = 0; i < list.length; i++){
            if(list[i] == bool){
                return i;
            }
        }
        return -1;
    }
    public static int GetNull (Player[] list){
        for(int i = 0; i < list.length; i++){
            if(list[i] == null){
                return i;
            }
        }
        return -1;
    }
    public static int GetNull (Block[] list){
        for(int i = 0; i < list.length; i++){
            if(list[i] == null){
                return i;
            }
        }
        return -1;
    }
    public static int GetNull (Location[] list){
        for(int i = 0; i < list.length; i++){
            if(list[i] == null){
                return i;
            }
        }
        return -1;
    }
    public static int ArrayLength (Object[] x){
        int length = 0;
        for(Object z : x){
            if(z != null){
                length++;
            }
        }
        return length;
    }
    public static Player[] ToPlayerObject(Object[] x){
        Player[] s = new Player[x.length];
        for(int i = 0; i < s.length; i++){
            s[i] = (Player)x[i];
        }
        return s;
    }
    public static Player[] ToPlayerObject3(Object[] x){
        Player[] s = new Player[x.length];
        for(int i = 0; i < s.length; i++){
            s[i] = Bukkit.getPlayer((String) x[i]);
        }
        return s;
    }
    public static String GetGroupName(String p, Map<String, List<String>> list){
        for(String str: list.keySet()){
            if(list.get(str).contains(p)){
                return str;
            }
        }
        return "-";
    }
    public static Player[] ToPlayerObject2(String[] x){
        Player[] s = new Player[x.length];
        for(int i = 0; i < s.length; i++){
            s[i] = Bukkit.getPlayer(x[i]);
        }
        return s;
    }
    public static boolean Index(String[] list, String text){
        for(String s: list){
            if(Objects.equals(s, text)){
                return true;
            }
        }
        return false;
    }
    public static String[] append (String[] array, String str){
        String[] output = new String[array.length + 1];
        System.arraycopy(array, 0, output, 0, array.length);
        output[array.length] = str;
        return output;
    }
    public static Location[] append (Location[] array, Location loc){
        Location[] output = new Location[array.length + 1];
        System.arraycopy(array, 0, output, 0, array.length);
        output[array.length] = loc;
        return output;
    }
    public static Player[] RemoveArray(Player[] main, Player[] arrayRemove){
        Player[] output;
        int index = 0;
        if(GetNotNullItems(arrayRemove).length == 0){
            return main;
        }
        Player[] addedPlayers = new Player[Math.max(main.length, arrayRemove.length)];
        for (Player value : main) {
            for (Player player : arrayRemove) {
                if (value != player) {
                    if(ContainsPlayer(addedPlayers, player) == -1) {
                        addedPlayers[index] = player;
                        index++;
                    }
                }
            }
        }
        output = GetNotNullItems(addedPlayers);
        return output;
    }
    public static String[] RemoveArray(String[] main, String[] arrayRemove){
        String[] output;
        int index = 0;
        if(GetNotNullItems(arrayRemove).length == 0){
            return main;
        }
        String[] addedPlayers = new String[Math.max(main.length, arrayRemove.length)];
        for (String value : main) {
            for (String player : arrayRemove) {
                if (value != player) {
                    if(ContainsString(addedPlayers, player) == -1) {
                        addedPlayers[index] = player;
                        index++;
                    }
                }
            }
        }
        output = GetNotNullItems(addedPlayers);
        return output;
    }
    public static List<String> PlayerToListObject(Player[] p){
        List<String> x = new ArrayList<>();
        for (Player player : p) {
            x.add(player.getName());
        }
        return x;
    }
    public static String[] PlayerToStringObject(Player[] p){
        String[] x = new String[p.length];
        int index = 0;
        for (Player player : p) {
            x[index] = player.getName();
            index++;
        }
        return x;
    }
    public static Player[] GetNotNullItems (Player[] p){
        Player[] xx;
        int index = 0;
        for(Player player : p){
            if(player != null){
                index++;
            }
        }
        xx = new Player[index];
        index = 0;
        for(Player player : p){
            if(player != null){
                xx[index] = player;
                index++;
            }
        }
        return xx;
    }
    public static VariableManager.Roles[] GetNotNullItems (VariableManager.Roles[] p){
        VariableManager.Roles[] xx;
        int index = 0;
        for(VariableManager.Roles player : p){
            if(player != null){
                index++;
            }
        }
        xx = new VariableManager.Roles[index];
        index = 0;
        for(VariableManager.Roles player : p){
            if(player != null){
                xx[index] = player;
                index++;
            }
        }
        return xx;
    }
    public static Location[] GetNotNullItems (Location[] p){
        Location[] xx;
        int index = 0;
        for(Location player : p){
            if(player != null){
                index++;
            }
        }
        xx = new Location[index];
        index = 0;
        for(Location player : p){
            if(player != null){
                xx[index] = player;
                index++;
            }
        }
        return xx;
    }
    public static String[] GetNotNullItems (String[] p){
        String[] xx;
        int index = 0;
        for(String player : p){
            if(player != null){
                index++;
            }
        }
        xx = new String[index];
        index = 0;
        for(String player : p){
            if(player != null){
                xx[index] = player;
                index++;
            }
        }
        return xx;
    }
    public static Player[] ConvertNamesToPlayer (String[] Names){
        Player[] p = new Player[Names.length];
        int index = 0;
        for(String name : Names){
            p[index] = Bukkit.getPlayer(name);
            index++;
        }
        return p;
    }
    public static int Random(int start, int end){
        String str_start = String.valueOf(start), str_end = String.valueOf(end);
        int startDup, endDup;
        Random rand = new Random();
        startDup = Integer.parseInt(str_end.replace("-", ""));
        endDup = Integer.parseInt(str_start.replace("-", ""));
        int randNum = rand.nextInt(endDup - startDup) + startDup;
        if(rand.nextInt(2) == 0){
            randNum = -randNum;
        }
        return randNum;
    }
    public static String[] ObjectToStringConvert(Object[] objs){
        String[] s = new String[objs.length];
        for(int i = 0; i < s.length; i++){
            s[i] = String.valueOf(objs[i]);
        }
        return s;
    }
}
