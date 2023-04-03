/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.PistonMoveReaction
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class MachineMoveEvent
extends Event
implements Cancellable,
Listener {
    private static final HandlerList handlers = new HandlerList();
    public MzTechMachine machine;
    public Location targetLocation;
    private boolean cancelled = false;

    public MachineMoveEvent(MzTechMachine machine, BlockFace direction) {
        this.machine = machine;
        this.targetLocation = machine.getBlock().getLocation();
        switch (direction) {
            case UP: {
                this.targetLocation.add(0.0, 1.0, 0.0);
                break;
            }
            case DOWN: {
                this.targetLocation.add(0.0, -1.0, 0.0);
                break;
            }
            case EAST: {
                this.targetLocation.add(1.0, 0.0, 0.0);
                break;
            }
            case SOUTH: {
                this.targetLocation.add(0.0, 0.0, 1.0);
                break;
            }
            case WEST: {
                this.targetLocation.add(-1.0, 0.0, 0.0);
                break;
            }
            case NORTH: {
                this.targetLocation.add(0.0, 0.0, -1.0);
                break;
            }
        }
    }

    public MachineMoveEvent() {
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!event.isCancelled()) {
            event.getBlocks().forEach(b -> {
                if (b.getPistonMoveReaction() == PistonMoveReaction.IGNORE || b.getPistonMoveReaction() == PistonMoveReaction.BREAK) {
                    return;
                }
                MzTechMachine machine = MzTechMachine.asMzTechCopy(b);
                if (machine != null) {
                    MachineMoveEvent e = new MachineMoveEvent(machine, event.getDirection());
                    Bukkit.getPluginManager().callEvent((Event)e);
                    if (e.isCancelled() || !machine.onMove(e.targetLocation)) {
                        event.setCancelled(true);
                    }
                }
            });
            if (!event.isCancelled()) {
                event.getBlocks().forEach(b -> {
                    if (b.getPistonMoveReaction() == PistonMoveReaction.IGNORE || b.getPistonMoveReaction() == PistonMoveReaction.BREAK) {
                        return;
                    }
                    MzTechMachine machine = MzTechMachine.asMzTechCopy(b);
                    if (machine != null) {
                        MachineMoveEvent e = new MachineMoveEvent(machine, event.getDirection());
                        machine.move(e.targetLocation);
                    }
                });
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!event.isCancelled()) {
            BlockPistonExtendEvent e = new BlockPistonExtendEvent(event.getBlock(), event.getBlocks(), event.getDirection());
            this.onBlockPistonExtend(e);
            event.setCancelled(e.isCancelled());
        }
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

