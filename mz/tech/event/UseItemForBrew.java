/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.BrewEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.event;

import com.google.common.collect.Lists;
import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public class UseItemForBrew
extends Event
implements Listener,
Cancellable {
    static HandlerList handlerList = new HandlerList();
    MzTechItem item;
    BrewEvent e;

    public UseItemForBrew() {
    }

    public UseItemForBrew(MzTechItem item, BrewEvent e) {
        this.item = item;
        this.e = e;
    }

    @EventHandler
    void onBrew(BrewEvent event) {
        Lists.newArrayList((Object[])new ItemStack[]{event.getContents().getIngredient(), event.getContents().getItem(0), event.getContents().getItem(1), event.getContents().getItem(2)}).forEach(is -> {
            MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(is);
            if (mzTechCopy != null) {
                UseItemForBrew e = new UseItemForBrew(mzTechCopy, event);
                Bukkit.getPluginManager().callEvent((Event)e);
                if (!e.isCancelled() && !mzTechCopy.onUseForBrew(event)) {
                    event.setCancelled(true);
                }
            }
        });
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

