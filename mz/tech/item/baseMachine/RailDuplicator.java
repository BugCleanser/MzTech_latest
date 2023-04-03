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
import mz.tech.item.baseMachine.Metronome;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.RailDuplicatorMachine;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class RailDuplicator
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new RailDuplicator(), new Object[]{Material.AIR, Material.SLIME_BLOCK, new ItemStackBuilder("PISTON_STICKY_BASE", 0, "STICKY_PISTON", 1).build(), new ItemStack(Material.SLIME_BLOCK, 2), 1, new Metronome(), null, Material.HOPPER}).reg("\u94c1\u8f68\u590d\u5236\u673a");
    }

    public RailDuplicator() {
        super(new ItemStackBuilder(Material.HOPPER).setLocName("\u94c1\u8f68\u590d\u5236\u673a").setLoreList("\u00a77\u590d\u5236\u5176\u4e0a\u65b9\u7684\u5730\u6bef\u6216\u94c1\u8f68", "\u00a77\u539f\u7248\u7279\u6027").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u94c1\u8f68\u590d\u5236\u673a";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new RailDuplicatorMachine().setBlock(block);
    }
}

