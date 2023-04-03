/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package mz.tech.item.baseMachine;

import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.FffffFurnaceBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class FffffFurnace
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new FffffFurnace(), Material.FURNACE, 0, 0, 0, 0, 0, 0, 0, 0).reg("rrrrr\u7194\u7089");
    }

    public FffffFurnace() {
        super(new ItemStackBuilder(Material.FURNACE).setLocName("\u00a7crrrrr\u7194\u7089").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "rrrrr\u7194\u7089";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new FffffFurnaceBlock().setBlock(block);
    }
}

