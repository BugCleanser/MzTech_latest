/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.baseMachine;

import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CobblestoneDuplicator
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new CobblestoneDuplicator(), Material.COBBLESTONE, 0, 0, new RawItem(new ItemStack[0]).add(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BUCKET)), null, new RawItem(new ItemStack[0]).add(new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BUCKET)), 0, Material.HOPPER, 0).reg("\u5237\u77f3\u673a");
        new SmilingCraftingTableRecipe(new CobblestoneDuplicator(), Material.COBBLESTONE, 0, 0, new RawItem(new ItemStack[0]).add(new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BUCKET)), null, new RawItem(new ItemStack[0]).add(new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.BUCKET)), 0, Material.HOPPER, 0).reg("\u5237\u77f3\u673a");
    }

    public CobblestoneDuplicator() {
        super(new ItemStackBuilder(Material.DISPENSER).setLocName("\u00a77\u5237\u77f3\u673a").addLore("\u00a77\u539f\u7248\u7279\u6027").build());
    }

    @Override
    public String getTypeName() {
        return "\u5237\u77f3\u673a";
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }
}

