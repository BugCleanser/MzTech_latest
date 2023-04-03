/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.event;

import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFillEvent
extends Event
implements Cancellable,
Listener {
    static HandlerList handlerList = new HandlerList();
    public MzTechItem item;
    public PlayerBucketFillEvent e;

    public ItemFillEvent() {
    }

    public ItemFillEvent(MzTechItem item, PlayerBucketFillEvent e) {
        this.item = item;
        this.e = e;
    }

    public boolean isCancelled() {
        return this.e.isCancelled();
    }

    public void setCancelled(boolean cancelled) {
        this.e.setCancelled(cancelled);
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onPlayerBucketFill(PlayerBucketFillEvent event) {
        MzTechItem mzTechCopy;
        if (event.isCancelled()) {
            return;
        }
        ItemStack is = event.getPlayer().getInventory().getItemInMainHand();
        if (is.getType() != Material.BUCKET) {
            is = event.getPlayer().getInventory().getItemInOffHand();
        }
        if ((mzTechCopy = MzTechItem.asMzTechCopy(is)) != null) {
            ItemFillEvent e = new ItemFillEvent(mzTechCopy, event);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (!e.isCancelled() && !mzTechCopy.onFill(event)) {
                event.setCancelled(true);
            }
        }
    }
}

