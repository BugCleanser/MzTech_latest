/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.DragType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.event.inventory.InventoryInteractEvent
 *  org.bukkit.inventory.Inventory
 */
package mz.tech.event;

import mz.tech.MzTech;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

public class MoveItemEvent
extends Event
implements Cancellable,
Listener {
    public InventoryInteractEvent cause;
    private Slot from;
    private Slot to;
    private Integer num;
    private static HandlerList handlers = new HandlerList();

    @EventHandler(priority=EventPriority.HIGH)
    static void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        block0 : switch (event.getAction()) {
            case NOTHING: {
                break;
            }
            case MOVE_TO_OTHER_INVENTORY: {
                Slot from = new Slot(event.getClickedInventory(), event.getSlot());
                int num = from.getItemStack().getAmount();
                int max = from.getItemStack().getType().getMaxStackSize();
                if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                    Slot to;
                    int i = event.getView().countSlots() - 1;
                    while (i >= event.getView().getTopInventory().getSize()) {
                        to = new Slot(event.getView().getBottomInventory(), event.getView().convertSlot(i));
                        if (from.getItemStack().isSimilar(to.getItemStack()) && to.getItemStack().getAmount() < max) {
                            if (to.getItemStack().getAmount() + num <= max) {
                                Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, num));
                                num = 0;
                                break;
                            }
                            Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, max - to.getItemStack().getAmount()));
                            num -= max - to.getItemStack().getAmount();
                        }
                        --i;
                    }
                    if (num <= 0) break;
                    i = event.getView().countSlots() - 1;
                    while (i >= event.getView().getTopInventory().getSize()) {
                        to = new Slot(event.getView().getBottomInventory(), event.getView().convertSlot(i));
                        if (ItemStackBuilder.isEmpty(to.getItemStack())) {
                            Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, num));
                            break block0;
                        }
                        --i;
                    }
                } else {
                    Slot to;
                    int i = 0;
                    while (i < event.getView().getTopInventory().getSize()) {
                        to = new Slot(event.getView().getTopInventory(), event.getView().convertSlot(i));
                        if (from.getItemStack().isSimilar(to.getItemStack()) && to.getItemStack().getAmount() < max) {
                            if (to.getItemStack().getAmount() + num <= max) {
                                Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, num));
                                num = 0;
                                break;
                            }
                            Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, max - to.getItemStack().getAmount()));
                            num -= max - to.getItemStack().getAmount();
                        }
                        ++i;
                    }
                    if (num <= 0) break;
                    i = 0;
                    while (i < event.getView().getTopInventory().getSize()) {
                        to = new Slot(event.getView().getTopInventory(), event.getView().convertSlot(i));
                        if (ItemStackBuilder.isEmpty(to.getItemStack())) {
                            Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, num));
                            break block0;
                        }
                        ++i;
                    }
                }
                break;
            }
            case COLLECT_TO_CURSOR: {
                Slot to = new Slot((Player)event.getView().getPlayer());
                int max = to.getItemStack().getType().getMaxStackSize();
                int num = to.getItemStack().getAmount();
                int i = 0;
                while (i < event.getView().countSlots()) {
                    Slot from = new Slot(i < event.getView().getTopInventory().getSize() ? event.getView().getTopInventory() : event.getView().getBottomInventory(), event.getView().convertSlot(i));
                    if (to.getItemStack().isSimilar(from.getItemStack())) {
                        int n = from.getItemStack().getAmount();
                        if (n + num > max) {
                            n = max - num;
                        }
                        Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, n));
                        if ((num += n) == max) break block0;
                    }
                    ++i;
                }
                break;
            }
            default: {
                Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    static void onInventoryDrag(InventoryDragEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Slot from = new Slot((Player)event.getWhoClicked());
        Integer[] rawSlots = event.getRawSlots().toArray(new Integer[0]);
        Integer[] slots = event.getInventorySlots().toArray(new Integer[0]);
        if (!ItemStackBuilder.isEmpty(event.getCursor()) && event.getCursor().equals((Object)event.getOldCursor())) {
            int max = event.getOldCursor().getType().getMaxStackSize();
            int i = 0;
            while (i < rawSlots.length) {
                Slot to = rawSlots[i] < event.getView().getTopInventory().getSize() ? new Slot(event.getView().getTopInventory(), slots[i]) : new Slot(event.getView().getBottomInventory(), slots[i]);
                int n = max - to.getItemStack().getAmount();
                if (n > 0) {
                    Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, new Slot(), to, n));
                }
                ++i;
            }
        } else {
            int max = event.getOldCursor().getType().getMaxStackSize();
            int n = event.getType() == DragType.SINGLE ? 1 : event.getOldCursor().getAmount() / rawSlots.length;
            int i = 0;
            while (i < rawSlots.length) {
                Slot to = rawSlots[i] < event.getView().getTopInventory().getSize() ? new Slot(event.getView().getTopInventory(), slots[i]) : new Slot(event.getView().getBottomInventory(), slots[i]);
                int m = max - (to.getItemStack() == null ? 0 : to.getItemStack().getAmount());
                if (m > n) {
                    m = n;
                }
                if (m > 0) {
                    Bukkit.getPluginManager().callEvent((Event)new MoveItemEvent((InventoryInteractEvent)event, from, to, m));
                }
                ++i;
            }
        }
    }

    public Slot getFrom() {
        if (this.from == null) {
            this.count();
        }
        return this.from;
    }

    public Slot getTo() {
        if (this.to == null) {
            this.count();
        }
        return this.to;
    }

    public int getNum() {
        if (this.num == null) {
            this.count();
        }
        return this.num;
    }

    public void count() {
        if (this.cause instanceof InventoryClickEvent) {
            InventoryClickEvent event = (InventoryClickEvent)this.cause;
            switch (event.getAction()) {
                case CLONE_STACK: {
                    this.from = new Slot();
                    this.to = new Slot((Player)event.getWhoClicked());
                    this.num = this.to.getItemStack().getType().getMaxStackSize() - this.to.getItemStack().getAmount();
                    break;
                }
                case DROP_ALL_CURSOR: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot();
                    this.num = this.from.getItemStack().getAmount();
                    break;
                }
                case HOTBAR_SWAP: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Inventory)event.getView().getPlayer().getInventory(), event.getHotbarButton() == -1 ? -106 : event.getHotbarButton());
                    if (ItemStackBuilder.isEmpty(this.from.getItemStack())) {
                        Slot t = this.from;
                        this.from = this.to;
                        this.to = t;
                    }
                    this.num = this.from.getItemStack().getAmount();
                    break;
                }
                case DROP_ALL_SLOT: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot();
                    this.num = this.from.getItemStack().getAmount();
                    break;
                }
                case DROP_ONE_SLOT: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot();
                    this.num = 1;
                    break;
                }
                case DROP_ONE_CURSOR: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot();
                    this.num = 1;
                    break;
                }
                case HOTBAR_MOVE_AND_READD: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Inventory)event.getView().getPlayer().getInventory(), event.getHotbarButton() == -1 ? -106 : event.getHotbarButton());
                    this.num = 0;
                    break;
                }
                case PICKUP_ALL: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Player)event.getWhoClicked());
                    this.num = this.from.getItemStack().getAmount();
                    break;
                }
                case PICKUP_HALF: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Player)event.getWhoClicked());
                    this.num = (this.from.getItemStack().getAmount() + 1) / 2;
                    break;
                }
                case PICKUP_ONE: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Player)event.getWhoClicked());
                    this.num = 1;
                    break;
                }
                case PICKUP_SOME: {
                    this.from = new Slot(event.getClickedInventory(), event.getSlot());
                    this.to = new Slot((Player)event.getWhoClicked());
                    this.num = this.to.getItemStack().getType().getMaxStackSize() - this.to.getItemStack().getAmount();
                    break;
                }
                case PLACE_ALL: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot(event.getClickedInventory(), event.getSlot());
                    this.num = this.from.getItemStack().getAmount();
                    break;
                }
                case PLACE_ONE: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot(event.getClickedInventory(), event.getSlot());
                    this.num = 1;
                    break;
                }
                case PLACE_SOME: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot(event.getClickedInventory(), event.getSlot());
                    this.num = this.to.getItemStack().getType().getMaxStackSize() - this.to.getItemStack().getAmount();
                    break;
                }
                case SWAP_WITH_CURSOR: {
                    this.from = new Slot((Player)event.getWhoClicked());
                    this.to = new Slot(event.getClickedInventory(), event.getSlot());
                    this.num = 0;
                    break;
                }
                case UNKNOWN: {
                    MzTech.sendMessage((CommandSender)this.cause.getView().getPlayer(), "\u00a7e\u672a\u77e5\u7684\u5bb9\u5668\u64cd\u4f5c\uff0c\u4e3a\u5b89\u5168\u8d77\u89c1\u5df2\u88ab\u62e6\u622a");
                    this.setCancelled(true);
                    break;
                }
                case NOTHING: 
                case MOVE_TO_OTHER_INVENTORY: 
                case COLLECT_TO_CURSOR: {
                    break;
                }
                default: {
                    event.getWhoClicked().sendMessage("\u672a\u5904\u7406\u7684\u5bb9\u5668\u64cd\u4f5c\uff1a " + event.getAction().name());
                }
            }
        }
    }

    public MoveItemEvent() {
    }

    public MoveItemEvent(InventoryInteractEvent cause) {
        this.cause = cause;
    }

    public MoveItemEvent(InventoryInteractEvent cause, Slot from, Slot to, Integer num) {
        this.cause = cause;
        this.from = from;
        this.to = to;
        this.num = num;
    }

    public boolean isCancelled() {
        return this.cause.isCancelled();
    }

    public void setCancelled(boolean cancelled) {
        this.cause.setCancelled(cancelled);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isSwap() {
        return this.getNum() == 0;
    }
}

