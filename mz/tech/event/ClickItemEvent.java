/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.event;

import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ClickItemEvent
extends Event
implements Listener,
Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public MzTechItem itemStack;
    public EquipmentSlot hand;
    public boolean leftClick;
    public boolean clickAir;
    public boolean clickBlock;
    public boolean clickEntity;
    public boolean cancelled;
    public Entity entity;
    public Block block;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onPlayerInteract(PlayerInteractEvent event) {
        MzTechItem item;
        if (event.getAction() != Action.PHYSICAL && event.getItem() != null && event.getItem().getType() != Material.AIR && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName() && (item = MzTechItem.asMzTechCopy(event.getItem())) != null) {
            ClickItemEvent e = new ClickItemEvent(event.getPlayer(), event.getHand(), item, event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK, event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR, event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK, false, event.getClickedBlock(), null);
            e.call();
            if (e.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        MzTechItem mzItem;
        ItemStack item;
        if (event.isCancelled()) {
            return;
        }
        ItemStack itemStack = item = event.getHand() == EquipmentSlot.OFF_HAND ? event.getPlayer().getInventory().getItemInOffHand() : event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasLocalizedName() && (mzItem = MzTechItem.asMzTechCopy(item)) != null) {
            ClickItemEvent e = new ClickItemEvent(event.getPlayer(), event.getHand(), mzItem, false, false, false, true, null, event.getRightClicked());
            e.call();
            if (e.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    public ClickItemEvent(Player player, EquipmentSlot hand, MzTechItem itemStack, boolean leftClick, boolean clickAir, boolean clickBlock, boolean clickEntity, Block block, Entity entity) {
        this.player = player;
        this.hand = hand;
        this.itemStack = itemStack;
        this.leftClick = leftClick;
        this.clickAir = clickAir;
        this.clickBlock = clickBlock;
        this.clickEntity = clickEntity;
        this.entity = entity;
        this.block = block;
    }

    public ClickItemEvent() {
    }

    public void call() {
        ItemStack befor = new ItemStack((ItemStack)this.itemStack);
        if (this.leftClick) {
            if (this.clickAir) {
                this.cancelled = !this.itemStack.onLeftClickAir(this.player, this.hand);
            } else if (this.clickBlock) {
                this.cancelled = !this.itemStack.onLeftClickBlock(this.player, this.hand, this.block);
            } else if (this.clickEntity) {
                this.cancelled = !this.itemStack.onLeftClickEntity(this.player, this.hand, this.entity);
            }
        } else if (this.clickAir) {
            this.cancelled = !this.itemStack.onRightClickAir(this.player, this.hand);
        } else if (this.clickBlock) {
            this.cancelled = !this.itemStack.onRightClickBlock(this.player, this.hand, this.block);
        } else if (this.clickEntity) {
            this.cancelled = !this.itemStack.onRightClickEntity(this.player, this.hand, this.entity);
        }
        Bukkit.getPluginManager().callEvent((Event)this);
        if (befor.getAmount() != this.itemStack.getAmount() || !befor.isSimilar((ItemStack)this.itemStack)) {
            if (this.hand == EquipmentSlot.HAND) {
                this.player.getInventory().setItemInMainHand((ItemStack)this.itemStack);
            } else {
                this.player.getInventory().setItemInOffHand((ItemStack)this.itemStack);
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

