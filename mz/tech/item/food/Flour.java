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
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class Flour
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new Flour(), Material.WHEAT).reg("\u9762\u7c89");
    }

    public Flour() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a7f\u9762\u7c89").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u9762\u7c89";
    }
}

