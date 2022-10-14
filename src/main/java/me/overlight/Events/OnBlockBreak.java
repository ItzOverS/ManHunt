package me.overlight.Events;

import me.overlight.Managers.VariableManager;
import me.overlight.Libraries.ArrayOptions;
import me.overlight.Libraries.InventoryGen;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class OnBlockBreak implements Listener {
    @EventHandler
    public void OnBlockBroken(BlockBreakEvent e){
        if(!VariableManager.PluginStats){
            e.setCancelled(false);
            return;
        }
        if(!VariableManager.GameStats){
            e.setCancelled(true);
            return;
        }
        if(!VariableManager.AllowMovement){
            if(VariableManager.MapRoles.get(e.getPlayer()) == VariableManager.Roles.HUNTER){
                e.setCancelled(true);
                return;
            }
        }
        Player breaker = e.getPlayer();
        Block brokenBlock = e.getBlock();
        Collection<ItemStack> dropsFromBlock = brokenBlock.getDrops();
        if(VariableManager.SmelterPickaxe){
            if(brokenBlock.getType() == Material.IRON_ORE)
                dropsFromBlock = Collections.singletonList(InventoryGen.GenItem(1, "Iron Ingot", null, Material.IRON_INGOT));
            else if(brokenBlock.getType() == Material.GOLD_ORE)
                dropsFromBlock = Collections.singletonList(InventoryGen.GenItem(1, "Gold Ingot", null, Material.GOLD_INGOT));
        }
        ItemStack[] dropsObject = ToItemStack(dropsFromBlock.toArray());
        Material[] NotAllowedBlocks = {
                Material.BLACK_BED,
                Material.BLUE_BED,
                Material.BROWN_BED,
                Material.CYAN_BED,
                Material.GRAY_BED,
                Material.GREEN_BED,
                Material.LIGHT_BLUE_BED,
                Material.LIME_BED,
                Material.MAGENTA_BED,
                Material.WHITE_BED,
                Material.ORANGE_BED,
                Material.RED_BED,
                Material.YELLOW_BED,
                Material.PISTON,
                Material.MOVING_PISTON,
                Material.PISTON_HEAD,
                Material.ACACIA_DOOR,
                Material.BIRCH_DOOR,
                Material.DARK_OAK_DOOR,
                Material.CRIMSON_DOOR,
                Material.IRON_DOOR,
                Material.JUNGLE_DOOR,
                Material.OAK_DOOR,
                Material.SPRUCE_DOOR,
                Material.WARPED_DOOR,
                Material.LILAC,
                Material.ROSE_BUSH,
                Material.PEONY
        };
        boolean ACCEPTDROP = false;
        e.getPlayer().giveExp(e.getExpToDrop());
        e.setExpToDrop(0);
        for(Material x: NotAllowedBlocks){
            if(brokenBlock.getType() == x){
                ACCEPTDROP = true;
            }
        }
        if(isInvFull(e.getPlayer().getInventory()))
            ACCEPTDROP = true;
        e.setDropItems(ACCEPTDROP);
        if(!ACCEPTDROP) {
            if (brokenBlock.isPreferredTool(Objects.requireNonNull(breaker.getItemInHand()))) {
                for (ItemStack x : dropsObject) {
                    e.getPlayer().getInventory().addItem(x);
                }
            }
        }
    }
    private ItemStack[] ToItemStack (Object[] list){
        ItemStack[] s = new ItemStack[list.length];
        for(int i = 0; i < list.length; i++){
            s[i] = (ItemStack) list[i];
        }
        return s;
    }
    private boolean isInvFull (Inventory inv){
        ItemStack[] InvItems = new ItemStack[36];
        for(int i = 0; i < 36; i++){
            InvItems[i] = inv.getItem(i);
        }
        for (ItemStack invItem : InvItems) {
            if (invItem == null) {
                return false;
            }
        }
        return true;
    }
}
