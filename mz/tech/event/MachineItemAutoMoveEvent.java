/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.block.BlockState
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryMoveItemEvent
 *  org.bukkit.inventory.Inventory
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class MachineItemAutoMoveEvent
extends Event
implements Listener {
    private static final HandlerList handlers = new HandlerList();
    public boolean cancelled;
    public MzTechMachine machine;
    public boolean isToMachine;
    public Inventory otherInv;

    public MachineItemAutoMoveEvent() {
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
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        MzTechMachine mz;
        if (event.isCancelled()) {
            return;
        }
        if (event.getDestination().getHolder() instanceof BlockState && (mz = MzTechMachine.asMzTechCopy(((BlockState)event.getDestination().getHolder()).getBlock())) != null) {
            MachineItemAutoMoveEvent e = new MachineItemAutoMoveEvent(mz, true, event.getSource());
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled() || !mz.onItemAutoMove(e)) {
                event.setCancelled(true);
            }
            return;
        }
        if (event.getSource().getHolder() instanceof BlockState && (mz = MzTechMachine.asMzTechCopy(((BlockState)event.getSource().getHolder()).getBlock())) != null) {
            MachineItemAutoMoveEvent e = new MachineItemAutoMoveEvent(mz, false, event.getDestination());
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled() || !mz.onItemAutoMove(e)) {
                event.setCancelled(true);
            }
        }
    }

    public MachineItemAutoMoveEvent(MzTechMachine machine, boolean isToMachine, Inventory otherInv) {
        this.machine = machine;
        this.isToMachine = isToMachine;
        this.otherInv = otherInv;
    }
}

