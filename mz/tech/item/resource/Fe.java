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

public class Fe
extends MzTechItem {
    static {
        new GriddleRecipe(new ItemStack(Material.GRAVEL), (ItemStack)new Fe(), 20).reg("\u94c1\u7c89");
    }

    public Fe() {
        super(new ItemStackBuilder("SULPHUR", 0, "GUNPOWDER", 1).setLocName("\u94c1\u7c89").setLoreList("\u00a77Fe").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u94c1\u7c89";
    }
}

