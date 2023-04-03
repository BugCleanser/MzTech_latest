/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.decoration;

import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ChristmasHat
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new ChristmasHat(), new Object[]{ItemStackBuilder.redDye, Material.LEATHER_HELMET}).reg("\u5723\u8bde\u5e3d");
    }

    public ChristmasHat() {
        super(new ItemStackBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.fromRGB((int)255, (int)0, (int)0)).setHideFlags(ItemFlag.values()).removeHideFlags(ItemFlag.HIDE_ENCHANTS).setLocName("\u00a7c\u5723\u8bde\u5e3d").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u5e3d";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

