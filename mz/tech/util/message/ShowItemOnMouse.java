/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util.message;

import mz.tech.NBT;
import mz.tech.util.message.ShowOnMouse;
import org.bukkit.inventory.ItemStack;

public class ShowItemOnMouse
extends ShowOnMouse {
    private ItemStack i;

    public ShowItemOnMouse(ItemStack i) {
        this.setItem(i);
    }

    @Override
    public String getAction() {
        return "show_item";
    }

    @Override
    public String getValue() {
        return new NBT(this.i).toString();
    }

    public ItemStack getItem() {
        return this.i;
    }

    public ShowItemOnMouse setItem(ItemStack i) {
        this.i = i;
        return this;
    }
}

