/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.category;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public abstract class MzTechCategory {
    public static List<MzTechCategory> categories = new ArrayList<MzTechCategory>();
    public String name;
    public ItemStack icon;
    public List<ItemStack> items = new ArrayList<ItemStack>();

    public void addItem(ItemStack item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
        }
    }

    protected MzTechCategory(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
        categories.add(this);
    }
}

