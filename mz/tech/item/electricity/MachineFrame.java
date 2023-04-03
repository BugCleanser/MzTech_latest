/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.electricity;

import mz.tech.category.ElectricityCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class MachineFrame
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new MachineFrame(), new Object[]{Material.IRON_BLOCK, ItemStackBuilder.lightGrayGlass, 0, 1, null, 1, 0, 1, 0}).reg("\u673a\u5668\u6846\u67b6");
    }

    public MachineFrame() {
        super(new ItemStackBuilder(ItemStackBuilder.lightGrayGlass).setLocName("\u00a77\u673a\u5668\u6846\u67b6").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ElectricityCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u673a\u5668\u6846\u67b6";
    }
}

