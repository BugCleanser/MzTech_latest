/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.baseMachine;

import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.GriddleMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Griddle
extends MzTechItem {
    static {
        RawItem itemPlanks;
        try {
            itemPlanks = new RawItem(new ItemStack(Material.WOOD, 1, 0), new ItemStack(Material.WOOD, 1, 1), new ItemStack(Material.WOOD, 1, 2), new ItemStack(Material.WOOD, 1, 3), new ItemStack(Material.WOOD, 1, 4), new ItemStack(Material.WOOD, 1, 5));
        }
        catch (Throwable e) {
            itemPlanks = new RawItem(new ItemStack(Enum.valueOf(Material.class, "ACACIA_PLANKS")), new ItemStack(Enum.valueOf(Material.class, "BIRCH_PLANKS")), new ItemStack(Enum.valueOf(Material.class, "DARK_OAK_PLANKS")), new ItemStack(Enum.valueOf(Material.class, "JUNGLE_PLANKS")), new ItemStack(Enum.valueOf(Material.class, "OAK_PLANKS")), new ItemStack(Enum.valueOf(Material.class, "SPRUCE_PLANKS")));
            try {
                itemPlanks.add(new ItemStack(Enum.valueOf(Material.class, "CRIMSON_PLANKS")), null);
                itemPlanks.add(new ItemStack(Enum.valueOf(Material.class, "WARPED_PLANKS")), null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        new SmilingCraftingTableRecipe(new Griddle(), itemPlanks, null, 0, 0, new ItemStackBuilder("web", 0, "cobweb", 1).build(), 0, Material.STICK, null, 6).setExtra(0, 0, new ItemStackBuilder("SULPHUR", 0, "gunpowder", 3)).setExtra(0, 1, new ItemStackBuilder(Material.COAL).setLoreList("\u00a77\u51e0\u7387\uff1a 100%").build()).reg("\u7b5b\u5b50");
    }

    public Griddle() {
        super(new ItemStackBuilder("WEB", 0, "COBWEB", 1).setLocName("\u7b5b\u5b50").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u7b5b\u5b50";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new GriddleMachine().setBlock(block);
    }
}

