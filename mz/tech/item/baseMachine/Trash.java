/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.baseMachine;

import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class Trash
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new Trash(), Material.HOPPER, null, null, Material.LAVA_BUCKET).reg("\u5783\u573e\u6876");
    }

    public Trash() {
        super(new ItemStackBuilder(Material.HOPPER).setLocName("\u00a76\u5783\u573e\u6876").setLoreList("\u00a77\u9500\u6bc1\u4e00\u5207").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u5783\u573e\u6876";
    }
}

