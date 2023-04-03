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

public class Metronome
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new Metronome(), Material.STONE, Material.REDSTONE, 0, new ItemStackBuilder("redstone_torch_on", 0, "redstone_torch", 1).build(), 0, 3).reg("\u8282\u62cd\u5668");
    }

    public Metronome() {
        super(new ItemStackBuilder(Material.LEVER).setLocName("\u8282\u62cd\u5668").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8282\u62cd\u5668";
    }
}

