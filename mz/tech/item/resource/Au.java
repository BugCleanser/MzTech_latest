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
import mz.tech.recipe.GriddleRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Au
extends MzTechItem {
    static {
        new GriddleRecipe(new ItemStack(Material.GRAVEL), (ItemStack)new Au(), 5).reg("\u91d1\u7c89");
    }

    public Au() {
        super(new ItemStackBuilder(Material.GLOWSTONE_DUST).setLocName("\u00a7e\u91d1\u7c89").setLoreList("\u00a77Au").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u91d1\u7c89";
    }
}

