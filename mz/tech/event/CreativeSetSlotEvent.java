/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import mz.tech.MzTech;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CreativeSetSlotEvent
extends Event
implements Cancellable,
Listener {
    public int slot;
    public ItemStack is;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public CreativeSetSlotEvent() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.LOW, new PacketType[]{PacketType.Play.Client.SET_CREATIVE_SLOT}){

            public void onPacketReceiving(PacketEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                ItemStack copy = new ItemStack((ItemStack)event.getPacket().getItemModifier().read(0));
                CreativeSetSlotEvent e = new CreativeSetSlotEvent((Integer)event.getPacket().getIntegers().read(0), copy);
                try {
                    Bukkit.getPluginManager().callEvent((Event)e);
                }
                catch (Throwable ex) {
                    e = new CreativeSetSlotEvent(true, e.slot, e.is);
                    Bukkit.getPluginManager().callEvent((Event)e);
                }
                copy = e.is;
                if (e.isCancelled()) {
                    event.setCancelled(true);
                }
                event.getPacket().getIntegers().write(0, (Object)e.slot);
                event.getPacket().getItemModifier().write(0, (Object)copy);
            }
        });
    }

    public CreativeSetSlotEvent(boolean isAsync, Integer slot, ItemStack is) {
        super(isAsync);
        this.slot = slot;
        this.is = is;
    }

    public CreativeSetSlotEvent(Integer slot, ItemStack is) {
        this(false, slot, is);
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
}

