/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.block.BlockState
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.StructureGrowEvent
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class MachineGrowEvent
extends Event
implements Cancellable,
Listener {
    static HandlerList handlerList = new HandlerList();
    public MzTechMachine machine;
    public StructureGrowEvent e;

    public MachineGrowEvent() {
    }

    public MachineGrowEvent(MzTechMachine machine, StructureGrowEvent e) {
        this.machine = machine;
        this.e = e;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onStructureGrow(StructureGrowEvent event) {
        if (event.isCancelled() || event.getBlocks().size() == 1 && ((BlockState)event.getBlocks().get(0)).getLocation().equals((Object)event.getLocation())) {
            return;
        }
        MzTechMachine mzTechCopy = MzTechMachine.asMzTechCopy(event.getLocation().getBlock());
        if (mzTechCopy != null) {
            MachineGrowEvent e = new MachineGrowEvent(mzTechCopy, event);
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled() || !mzTechCopy.onGrow(event.getBlocks(), event.isFromBonemeal(), event.getPlayer())) {
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

