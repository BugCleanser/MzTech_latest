/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.food;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.SpawnEgg;
import mz.tech.recipe.UseItemForEntityRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SheepMilk
extends MzTechItem {
    static {
        new UseItemForEntityRecipe(new ItemStack(Material.BUCKET), true, new ItemStackBuilder(new SpawnEgg("sheep")).setLocName("\u7f8a").build(), new SheepMilk()).reg("\u7f8a\u5976");
    }

    public SheepMilk() {
        super(new ItemStackBuilder(Material.MILK_BUCKET).setLocName("\u7f8a\u5976").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u7f8a\u5976";
    }
}

