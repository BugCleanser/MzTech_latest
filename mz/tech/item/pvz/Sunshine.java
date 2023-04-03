/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.pvz;

import mz.tech.category.MzTechCategory;
import mz.tech.category.PvzCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class Sunshine
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new Sunshine(), new Object[]{new ItemStackBuilder("double_plant", 0, "sunflower", 1)}).reg("\u9633\u5149");
    }

    public Sunshine() {
        super(new ItemStackBuilder("double_plant", 0, "sunflower", 1).setLocName("\u00a7e\u9633\u5149").build());
    }

    @Override
    public String getTypeName() {
        return "\u9633\u5149";
    }

    @Override
    public MzTechCategory getCategory() {
        return PvzCategory.instance;
    }
}

