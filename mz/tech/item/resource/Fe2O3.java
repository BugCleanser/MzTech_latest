/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.resource.Hematite;
import mz.tech.recipe.FffffFurnaceRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Fe2O3
extends MzTechItem {
    static {
        new FffffFurnaceRecipe(new RawItem(new Hematite()), new Fe2O3(), new ItemStack(Material.COBBLESTONE)).reg("\u6c27\u5316\u94c1\u63d0\u7eaf");
    }

    public Fe2O3() {
        super(new ItemStackBuilder(ItemStackBuilder.redDye).setLocName("\u00a7c\u6c27\u5316\u94c1").setLoreList("\u00a77Fe\u2082O\u2083").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6c27\u5316\u94c1";
    }
}

