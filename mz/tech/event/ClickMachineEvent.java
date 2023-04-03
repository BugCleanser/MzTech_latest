/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package mz.tech.event;

import mz.tech.machine.MzTechMachine;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickMachineEvent
extends Event
implements Listener {
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public MzTechMachine machine;
    public boolean leftClick;
    public boolean cancelled;

    public ClickMachineEvent(Player player, MzTechMachine machine, boolean leftClick, Block block) {
        this.player = player;
        this.machine = machine;
        this.leftClick = leftClick;
    }

    public ClickMachineEvent() {
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @EventHandler(priority=EventPriority.HIGH)
    void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MzTechMachine machine = MzTechMachine.asMzTechCopy(event.getClickedBlock());
        if (machine != null) {
            if (!new PlayerUtil(event.getPlayer()).canBuildIgnoreAntiCheat(event.getClickedBlock())) {
                return;
            }
            ClickMachineEvent e = new ClickMachineEvent(event.getPlayer(), machine, event.getAction() == Action.LEFT_CLICK_BLOCK, event.getClickedBlock());
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled()) {
                event.setCancelled(true);
            } else {
                switch (event.getAction()) {
                    case RIGHT_CLICK_BLOCK: {
                        if (!machine.onRightClick(event)) {
                            event.setCancelled(true);
                        }
                    }
                    case LEFT_CLICK_BLOCK: {
                        if (machine.onLeftClick(event)) break;
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

