/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.ShapelessRecipe
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.category;

import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public final class ResourceCategory
extends MzTechCategory {
    public static ResourceCategory instance = new ResourceCategory();

    private ResourceCategory() {
        super("\u8d44\u6e90", new ItemStackBuilder(Material.POTION).setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).setPotionColor(Color.fromRGB((int)255, (int)255, (int)0)).setLocName("\u00a7b\u8d44\u6e90").build());
        try {
            Bukkit.addRecipe((Recipe)new ShapelessRecipe(new NamespacedKey((Plugin)MzTech.instance, "flint"), new ItemStack(Material.FLINT)).addIngredient(Material.GRAVEL).addIngredient(Material.GRAVEL).addIngredient(Material.GRAVEL));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this.addItem(new ItemStack(Material.FLINT));
    }
}

