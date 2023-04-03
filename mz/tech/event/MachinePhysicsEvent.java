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
 *  org.bukkit.event.block.BlockPhysicsEvent
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class MachinePhysicsEvent
extends Event
implements Cancellable,
Listener {
    static HandlerList handlerList = new HandlerList();
    public MzTechMachine machine;
    public BlockPhysicsEvent e;

    public MachinePhysicsEvent() {
    }

    public MachinePhysicsEvent(MzTechMachine machine, BlockPhysicsEvent e) {
        this.machine = machine;
        this.e = e;
    }

    @EventHandler
    void onBlockPhysics(BlockPhysicsEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MzTechMachine mzTechCopy = MzTechMachine.asMzTechCopy(event.getBlock());
        if (mzTechCopy != null) {
            MachinePhysicsEvent e = new MachinePhysicsEvent(mzTechCopy, event);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled() || !mzTechCopy.onPhysics(event)) {
                event.setCancelled(true);
            }
        }
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
}

