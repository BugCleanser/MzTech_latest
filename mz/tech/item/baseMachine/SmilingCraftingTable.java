/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.ShapelessRecipe
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item.baseMachine;

import mz.tech.MzTech;
import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.SmilingCraftingTableMachine;
import mz.tech.recipe.WorkBenchShapelessRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class SmilingCraftingTable
extends MzTechItem {
    static {
        new WorkBenchShapelessRecipe(new ShapelessRecipe(new NamespacedKey((Plugin)MzTech.instance, "SmilingCraftingTable"), (ItemStack)new SmilingCraftingTable()).addIngredient(Material.DROPPER).addIngredient(new ItemStackBuilder("workbench", 0, "crafting_table", 1).build().getData())).reg("\u5fae\u7b11\u7684\u5408\u6210\u53f0");
        BaseMachineCategory.instance.addItem(new SmilingCraftingTable());
    }

    public SmilingCraftingTable() {
        super(new ItemStackBuilder(Material.DROPPER).setLocName("\u00a76\u5fae\u7b11\u7684\u5408\u6210\u53f0").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u5fae\u7b11\u7684\u5408\u6210\u53f0";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new SmilingCraftingTableMachine().setBlock(block);
    }
}

