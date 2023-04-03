/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Nameable
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.event;

import mz.tech.MzTech;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Nameable;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class PlaceMachineEvent
extends Event
implements Listener {
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public MzTechMachine machine;
    public BlockPlaceEvent blockPlaceEvent;
    public boolean cancelled;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PlaceMachineEvent() {
    }

    public PlaceMachineEvent(Player player, MzTechMachine machine, BlockPlaceEvent blockPlaceEvent) {
        this.player = player;
        this.machine = machine;
        this.blockPlaceEvent = blockPlaceEvent;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onBlockPlace(BlockPlaceEvent event) {
        MzTechItem mzTechCopy;
        if (!event.isCancelled() && (mzTechCopy = MzTechItem.asMzTechCopy(event.getItemInHand())) != null && mzTechCopy.onPlace(event.getPlayer(), event.getBlock())) {
            PlaceMachineEvent e = new PlaceMachineEvent(event.getPlayer(), mzTechCopy.toMachine(event.getBlock()), event);
            if (e.machine != null) {
                Bukkit.getPluginManager().callEvent((Event)e);
                if (e.isCancelled()) {
                    event.setCancelled(true);
                }
                if (!event.isCancelled() && e.machine.onPlace(event)) {
                    e.machine.add();
                    if (!mzTechCopy.getItemMeta().hasDisplayName()) {
                        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                            BlockState state = event.getBlock().getState();
                            if (state instanceof Nameable) {
                                ((Nameable)state).setCustomName(mzTechCopy.getName());
                                state.update();
                            }
                        });
                    }
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }
}

