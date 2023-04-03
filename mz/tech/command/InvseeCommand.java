/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.command;

import java.util.HashMap;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.command.MzTechCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class InvseeCommand
extends MzTechCommand {
    static Map<Inventory, Player> invsees = new HashMap<Inventory, Player>();

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player && invsees.containsKey(event.getView().getTopInventory())) {
                    invsees.remove(event.getView().getTopInventory());
                }
            }

            @EventHandler(priority=EventPriority.MONITOR)
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (invsees.containsKey(event.getView().getTopInventory())) {
                    InvseeCommand.updateInv(event.getView().getTopInventory());
                }
                this.onPlayerDropItem(new PlayerDropItemEvent((Player)event.getWhoClicked(), null));
            }

            @EventHandler(priority=EventPriority.MONITOR)
            public void onPlayerDropItem(PlayerDropItemEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (invsees.containsValue(event.getPlayer())) {
                    invsees.forEach((inv, player) -> {
                        if (player == event.getPlayer()) {
                            InvseeCommand.updateView(inv);
                        }
                    });
                }
            }
        }, (Plugin)MzTech.instance);
    }

    protected InvseeCommand() {
        super(true);
    }

    @Override
    public String usage() {
        return "invsee";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                OfflinePlayer player = Bukkit.getOfflinePlayer((String)args[0]);
                if (player == null) {
                    return false;
                }
                Inventory inv = Bukkit.createInventory(null, (int)45, (String)(String.valueOf(player.getName()) + "\u7684\u7269\u54c1\u680f"));
                ((HumanEntity)sender).openInventory(inv);
                invsees.put(inv, player.getPlayer());
                InvseeCommand.updateView(inv);
            } else {
                MzTech.sendMessage(sender, "\u00a74\u53ea\u6709\u6e38\u620f\u4e2d\u7684\u73a9\u5bb6\u624d\u80fd\u4f7f\u7528\u8be5\u547d\u4ee4\uff01");
            }
            return true;
        }
        return false;
    }

    static void updateView(Inventory inv) {
        int i = 0;
        while (i < 36) {
            inv.setItem(i, invsees.get(inv).getInventory().getItem(i));
            ++i;
        }
    }

    static void updateInv(Inventory inv) {
    }
}

