/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
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
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import mz.tech.MzTech;
import mz.tech.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ShowInventoryEvent
extends Event
implements Cancellable,
Listener {
    private static final HandlerList handlers;
    private boolean cancelled = false;
    public InventoryView rawInv;
    public Inventory invToShow;

    static {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.WINDOW_ITEMS}){

            public void onPacketSending(PacketEvent event) {
                ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                if (event.getPacket().getItemListModifier().size() > 0) {
                    list.addAll((Collection)event.getPacket().getItemListModifier().read(0));
                } else {
                    list.addAll(Arrays.asList((ItemStack[])event.getPacket().getItemArrayModifier().read(0)));
                }
                ShowInventoryEvent e = new ShowInventoryEvent(event.getPlayer().getOpenInventory(), list);
                Bukkit.getPluginManager().callEvent((Event)e);
                if (e.isCancelled()) {
                    event.setCancelled(true);
                } else if (event.getPacket().getItemListModifier().size() > 0) {
                    event.getPacket().getItemListModifier().write(0, list);
                } else {
                    event.getPacket().getItemArrayModifier().write(0, (Object)list.toArray(new ItemStack[list.size()]));
                }
            }
        });
        handlers = new HandlerList();
    }

    public ShowInventoryEvent() {
    }

    public ShowInventoryEvent(final InventoryView inventoryView, final List<ItemStack> iss) {
        this.rawInv = inventoryView;
        this.invToShow = new InventoryUtil(null){

            public void setMaxStackSize(int max) {
            }

            public void setItem(int slot, ItemStack is) {
                iss.set(slot, is);
            }

            public void setContents(ItemStack[] t) throws IllegalArgumentException {
                iss.clear();
                iss.addAll(Lists.newArrayList((Object[])t));
            }

            public ListIterator<ItemStack> iterator(int first) {
                return iss.listIterator(first);
            }

            public ListIterator<ItemStack> iterator() {
                return iss.listIterator();
            }

            public String getTitle() {
                return inventoryView.getTitle();
            }

            public int getSize() {
                return inventoryView.countSlots();
            }

            public String getName() {
                return null;
            }

            public Location getLocation() {
                return null;
            }

            public ItemStack getItem(int slot) {
                return (ItemStack)iss.get(slot);
            }

            public ItemStack[] getContents() {
                return iss.toArray(new ItemStack[iss.size()]);
            }
        };
    }

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
}

