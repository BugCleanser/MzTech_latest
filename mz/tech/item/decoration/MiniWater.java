/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiniWater
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniWater(), new RawItem(new ItemStack[0]).add(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BUCKET))).reg("\u8ff7\u4f60\u6c34");
    }

    public MiniWater() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(514137816L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3ZWNiZmQ2ZDMzZTg3M2ExY2Y5YTkyZjU3ZjE0NjE1MmI1MmQ5ZDczMTE2OTQ2MDI2NzExMTFhMzAyZiJ9fX0").setLocName("\u00a7b\u8ff7\u4f60\u6c34").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u6c34";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

