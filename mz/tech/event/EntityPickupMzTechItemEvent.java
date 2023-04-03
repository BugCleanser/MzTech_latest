/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.event;

import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class EntityPickupMzTechItemEvent
extends Event
implements Listener,
Cancellable {
    private static HandlerList handlerList = new HandlerList();
    public MzTechItem item;
    public EntityPickupItemEvent e;

    public EntityPickupMzTechItemEvent() {
    }

    public EntityPickupMzTechItemEvent(MzTechItem item, EntityPickupItemEvent e) {
        this.item = item;
        this.e = e;
    }

    @EventHandler
    void onEntityPickupItem(EntityPickupItemEvent event) {
        MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(event.getItem().getItemStack());
        if (mzTechCopy != null) {
            EntityPickupMzTechItemEvent e = new EntityPickupMzTechItemEvent(mzTechCopy, event);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (!e.isCancelled()) {
                if (mzTechCopy.onEntityPickup(event)) {
                    event.getItem().setItemStack((ItemStack)mzTechCopy);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public boolean isCancelled() {
        return this.e.isCancelled();
    }

    public void setCancelled(boolean cancelled) {
        this.e.setCancelled(cancelled);
    }
}

