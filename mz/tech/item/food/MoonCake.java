/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.food;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.food.Flour;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class MoonCake
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new MoonCake(), new Object[]{new Flour(), null, null, Material.SUGAR, null, null, 0}).reg("\u6708\u997c");
    }

    public MoonCake() {
        super(new ItemStackBuilder(Material.PUMPKIN_PIE).setLocName("\u00a76\u6708\u997c").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6708\u997c";
    }
}

