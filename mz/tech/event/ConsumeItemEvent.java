/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 */
package mz.tech.event;

import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeItemEvent
extends Event
implements Listener {
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public MzTechItem item;
    public boolean cancelled;

    public ConsumeItemEvent() {
    }

    public ConsumeItemEvent(Player player, MzTechItem item) {
        this.player = player;
        this.item = item;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        MzTechItem item;
        if (event.getItem().getItemMeta().hasLocalizedName() && (item = MzTechItem.asMzTechCopy(event.getItem())) != null && item.canConsume(event.getPlayer())) {
            ConsumeItemEvent e = new ConsumeItemEvent(event.getPlayer(), item);
            e.setCancelled(event.isCancelled());
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled()) {
                event.setCancelled(true);
            } else {
                item.onConsume(event.getPlayer());
            }
        }
    }
}

