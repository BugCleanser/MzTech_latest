/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class FoodCategory
extends MzTechCategory {
    public static FoodCategory instance = new FoodCategory();

    private FoodCategory() {
        super("\u98df\u7269", new ItemStackBuilder(Material.APPLE).setLocName("\u00a7c\u98df\u7269").build());
        ItemStack enchanted_golden_apple;
        try {
            enchanted_golden_apple = new ItemStack(Enum.valueOf(Material.class, "ENCHANTED_GOLDEN_APPLE"));
        }
        catch (Throwable e) {
            enchanted_golden_apple = new ItemStack(Material.GOLDEN_APPLE, 1, 1);
        }
        new SmilingCraftingTableRecipe(enchanted_golden_apple, new Object[]{new ItemStackBuilder("exp_bottle", 0, "experience_bottle", 1), 0, 0, 0, Material.GOLDEN_APPLE, 0, 0, 0, 0}).reg("\u9644\u9b54\u91d1\u82f9\u679c");
        this.addItem(enchanted_golden_apple);
    }
}

