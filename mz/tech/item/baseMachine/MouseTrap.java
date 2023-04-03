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
import mz.tech.machine.MouseTrapMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class MouseTrap
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new MouseTrap(), new Object[]{new ItemStackBuilder("iron_plate", 0, "heavy_weighted_pressure_plate", 1), null, null, Material.TRIPWIRE_HOOK, null, null, 0}).reg("\u8001\u9f20\u5939");
    }

    public MouseTrap() {
        super(new ItemStackBuilder(Material.LEVER).setLocName("\u00a76\u8001\u9f20\u5939").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8001\u9f20\u5939";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new MouseTrapMachine().setBlock(block);
    }
}

