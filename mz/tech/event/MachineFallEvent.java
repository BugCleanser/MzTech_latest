/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.block.Block
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockEvent
 *  org.bukkit.event.block.BlockPhysicsEvent
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

public class MachineFallEvent
extends BlockEvent
implements Listener,
Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public MzTechMachine machine;
    private boolean cancelled = false;

    public MachineFallEvent() {
        super(null);
    }

    public MachineFallEvent(MzTechMachine machine) {
        super(machine.getBlock());
        this.machine = machine;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @EventHandler
    void onBlockPhysics(BlockPhysicsEvent event) {
        MzTechMachine machine;
        if (event.isCancelled()) {
            return;
        }
        Block b = event.getBlock();
        if (b.getLocation().subtract(0.0, 1.0, 0.0).getBlock().isEmpty() && (machine = MzTechMachine.asMzTechCopy(b)) != null) {
            MachineFallEvent e = new MachineFallEvent(machine);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

