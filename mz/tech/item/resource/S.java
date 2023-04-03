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

public class S
extends MzTechItem {
    static {
        new GriddleRecipe((ItemStack)new ItemStackBuilder("SULPHUR", 0, "gunpowder", 3), (ItemStack)new S()).reg("\u786b\u78fa");
    }

    public S() {
        super(new ItemStackBuilder(Material.GLOWSTONE_DUST).setLocName("\u00a7e\u786b\u78fa").setLoreList("\u00a77S").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u786b\u78fa";
    }
}

