/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.LingeringPotionSplashEvent
 */
package mz.tech.event;

import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;

public class LingeringPotionBreakEvent
extends Event
implements Listener,
Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public boolean cancelled;
    public ThrownPotion entity;
    public MzTechItem item;
    public LingeringPotionSplashEvent rawEvent;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
        ThrownPotion potionEntity = (ThrownPotion)event.getEntity();
        MzTechItem mz = MzTechItem.asMzTechCopy(potionEntity.getItem());
        if (mz != null) {
            LingeringPotionBreakEvent e = new LingeringPotionBreakEvent(potionEntity, mz, event);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled() || !mz.onLingeringPotionBreak(e)) {
                event.setCancelled(true);
            }
        }
    }

    public LingeringPotionBreakEvent() {
    }

    public LingeringPotionBreakEvent(ThrownPotion entity, MzTechItem item, LingeringPotionSplashEvent rawEvent) {
        this.entity = entity;
        this.item = item;
        this.rawEvent = rawEvent;
    }
}

