/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.block.Dropper
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import mz.tech.MzTech;
import mz.tech.machine.InventoryMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SmilingCraftingTableMachine
extends MzTechMachine
implements Trigger,
InventoryMachine {
    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            void onInventoryClose(InventoryCloseEvent event) {
                InventoryHolder holder = event.getInventory().getHolder();
                if (holder instanceof Dropper && MzTechMachine.asMzTechCopy(((Dropper)holder).getBlock()) instanceof SmilingCraftingTableMachine) {
                    event.getPlayer().sendMessage(MzTech.instance.getConfig().get("smilingCraftingTable") + "\u00a7eShift+\u53f3\u952e\u4ee5\u5408\u6210\u7269\u54c1");
                }
            }
        }, (Plugin)MzTech.instance);
    }

    @Override
    public String getType() {
        return "\u5fae\u7b11\u7684\u5408\u6210\u53f0";
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (event.getPlayer() != null && event.getPlayer().isSneaking() && new PlayerUtil(event.getPlayer()).canOpen()) {
            this.toggleAndReport(event.getPlayer());
            return false;
        }
        return true;
    }

    @Override
    public Inventory getInventory() {
        Dropper dropper = (Dropper)this.getBlock().getState();
        return dropper == null ? null : dropper.getInventory();
    }

    @Override
    public void setInventory(Inventory inv) {
        Dropper dropper = (Dropper)this.getBlock().getState();
        dropper.getInventory().setContents(inv.getContents());
    }

    public void clearUp() {
        ItemStack[] contents = this.getInventory().getContents();
        int i = 0;
        while (i < 2) {
            if (contents[0] == null && contents[1] == null && contents[2] == null) {
                contents[0] = contents[3];
                contents[1] = contents[4];
                contents[2] = contents[5];
                contents[3] = contents[6];
                contents[4] = contents[7];
                contents[5] = contents[8];
                contents[6] = null;
                contents[7] = null;
                contents[8] = null;
            }
            ++i;
        }
        i = 0;
        while (i < 2) {
            if (contents[0] == null && contents[3] == null && contents[6] == null) {
                contents[0] = contents[1];
                contents[3] = contents[4];
                contents[6] = contents[7];
                contents[1] = contents[2];
                contents[4] = contents[5];
                contents[7] = contents[8];
                contents[2] = null;
                contents[5] = null;
                contents[8] = null;
            }
            ++i;
        }
        this.getInventory().setContents(contents);
    }

    public void toggleAndReport(Player player) {
        if (this.toggle()) {
            switch (MzTech.rand.nextInt(4)) {
                case 0: {
                    player.sendMessage(MzTech.instance.getConfig().get("smilingCraftingTable") + "\u00a7a\u8bf6\u563f\u563f\u563f\u563f\u563f\u563f");
                    break;
                }
                case 1: {
                    player.sendMessage(String.valueOf(((String)MzTech.instance.getConfig().get("smilingCraftingTable")).replace("\u5fae\u7b11", "\u72c2\u7b11")) + "\u00a7a\u8bf6\u563f\u563f\u563f\u563f\u563f\u563f");
                    break;
                }
                case 2: {
                    player.sendMessage(String.valueOf(((String)MzTech.instance.getConfig().get("smilingCraftingTable")).replace("\u5fae\u7b11", "\u5c2c\u7b11")) + "\u00a7a\u8bf6\u563f\u563f\u563f\u563f\u563f\u563f");
                    break;
                }
                case 3: {
                    player.sendMessage(String.valueOf(((String)MzTech.instance.getConfig().get("smilingCraftingTable")).replace("\u5fae\u7b11", "\u5978\u7b11")) + "\u00a7a\u8bf6\u563f\u563f\u563f\u563f\u563f\u563f");
                }
            }
        } else {
            player.sendMessage(MzTech.instance.getConfig().get("smilingCraftingTable") + "\u00a74\u653e\u7684\u4ec0\u4e48\u5783\u573e");
        }
    }

    @Override
    public boolean toggle() {
        this.clearUp();
        boolean[] rb = new boolean[1];
        MzTechRecipe.forEach(SmilingCraftingTableRecipe.class, recipe -> {
            ArrayList<ItemStack> results;
            ArrayList raws = Lists.newArrayList((Object[])this.getInventory().getContents());
            if (recipe.filt(raws, results = new ArrayList<ItemStack>())) {
                blArray[0] = true;
                this.getInventory().setContents(results.toArray(new ItemStack[results.size()]));
                results.forEach(result -> {
                    if (result != null) {
                        int i = 0;
                        while (i < result.getAmount()) {
                            ((Dropper)this.getBlock().getState()).drop();
                            ++i;
                        }
                    }
                });
                this.getInventory().setContents(raws.toArray(new ItemStack[raws.size()]));
            }
        });
        return rb[0];
    }

    public static void toggleAll() {
        MzTechMachine.forEach(SmilingCraftingTableMachine.class, smilingCraftingTable -> smilingCraftingTable.toggle());
    }
}

