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

public class KNO3
extends MzTechItem {
    static {
        new GriddleRecipe((ItemStack)new ItemStackBuilder("SULPHUR", 0, "gunpowder", 3), (ItemStack)new KNO3()).reg("\u785d\u77f3");
    }

    public KNO3() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u785d\u77f3").setLoreList("\u00a77KNO\u2083").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u785d\u77f3";
    }
}

