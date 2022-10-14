package me.overlight.Libraries;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InventoryGen {
    public static Inventory GenInv (int size, Player[] items, ItemStack item, String title){
        Inventory Inv = Bukkit.createInventory(null, size, title);
        for(int i = 0; i < items.length; i++){
            if(items[i] != null) {
                ItemMeta Meta = item.getItemMeta();
                try {
                    assert Meta != null;
                    Meta.setDisplayName(items[i].getName());
                } catch (Exception ignored) {
                }
                item.setItemMeta(Meta);
                Inv.setItem(i, item);
            }
        }
        return Inv;
    }
    public static int GetIndexItemAt (Inventory inv, ItemStack item){
        ItemStack[] Content = inv.getContents();
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) != null){
                if(
                        Objects.requireNonNull(Objects.requireNonNull(inv.getItem(i)).getItemMeta()).getDisplayName().equals(Objects.requireNonNull(item.getItemMeta()).getDisplayName())
                        &&
                                Objects.requireNonNull(inv.getItem(i)).getType() == item.getType()
                ){
                    return i;
                }
            }
        }
        return -1;
    }
    public static Inventory GenInv(int size, HashMap<Integer, ItemStack> Items, String title){
        Inventory Inv = Bukkit.createInventory(null, size, title);
        for(int i = 0; i < size; i++){
            if(Items.containsKey(i)){
                ItemMeta Meta = Items.get(i).getItemMeta();
                Inv.setItem(i, Items.get(i));
            }
        }
        return Inv;
    }
    public static ItemStack GenItem(int size, String DisplayName, ArrayList<String> lore, Material type){
        ItemStack returnBack = new ItemStack(type, size);
        ItemMeta Meta = returnBack.getItemMeta();
        assert Meta != null;
        Meta.setDisplayName(DisplayName);
        Meta.setLore(lore);
        returnBack.setItemMeta(Meta);
        return returnBack;
    }
    public static Player[] getPlayersWithOutRoles (Player[][] Roles, Player[] Players){
        Player[] WithoutRoles = new Player[Players.length];
        int index = 0;
        for(int TPlayer = 0; TPlayer < Players.length; TPlayer++){
            Player CTargetPlayer = Players[TPlayer];
            boolean ChecksInRoles;
            for(int CheckPlayer = 0; CheckPlayer < Roles.length; CheckPlayer++){
                Player[] playersOnRoles = Roles[CheckPlayer];
                ChecksInRoles = ArrayOptions.ContainsPlayer(playersOnRoles, CTargetPlayer) != -1; // if != -1: it found on that role
                if(ChecksInRoles){
                    break;
                }
            }
            WithoutRoles[index] = CTargetPlayer;
            index += 1;
        }
        return WithoutRoles;
    }
}
