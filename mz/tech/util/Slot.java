/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Slot {
    Inventory inv;
    int slot;

    public Slot(Player player) {
        this.inv = player.getInventory();
        this.slot = -1;
    }

    public Slot() {
        this.inv = null;
        this.slot = 0;
    }

    public Slot(Inventory inv, int slot) {
        this.inv = inv;
        this.slot = slot;
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public int getSlot() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        if (this.inv == null) {
            return null;
        }
        if (this.isCursor()) {
            return this.getCursorPlayer().getItemOnCursor();
        }
        return this.inv.getItem(this.slot);
    }

    public void setItemStack(ItemStack is) {
        if (this.isCursor()) {
            this.getCursorPlayer().setItemOnCursor(is);
        } else {
            this.inv.setItem(this.slot, is);
        }
    }

    public boolean isDrop() {
        return this.inv == null;
    }

    public boolean isCursor() {
        return this.slot < 0;
    }

    public Player getCursorPlayer() {
        return (Player)this.inv.getHolder();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Slot) {
            return this.inv.equals(((Slot)obj).inv) && this.slot == ((Slot)obj).slot;
        }
        return false;
    }

    public static void setSlots(Slot[] array, ItemStack[] array2) {
        int i = 0;
        while (i < array.length) {
            array[i].setItemStack(array2[i]);
            ++i;
        }
    }
}

