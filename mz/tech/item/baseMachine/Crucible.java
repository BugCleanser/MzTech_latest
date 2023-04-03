/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.baseMachine;

import java.util.ArrayList;
import java.util.List;
import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Crucible
extends MzTechItem {
    private static SmilingCraftingTableRecipe recipe;
    private static List<ItemStack> raws;

    static {
        raws = new ArrayList<ItemStack>();
        recipe = new SmilingCraftingTableRecipe(new Crucible(), Material.OBSIDIAN, null, 0, 0, null, 0, 0, Material.LAVA_BUCKET, 0);
        recipe.reg("\u5769\u57da");
        Crucible.addRaw(new ItemStack(Material.COBBLESTONE, 4));
        Crucible.addRaw(new ItemStack(Material.OBSIDIAN, 1));
    }

    public static void addRaw(ItemStack raw) {
        recipe.setExtra(raws.size(), 0, raw);
        recipe.setExtra(raws.size(), 1, new ItemStack(Material.LAVA_BUCKET));
        raws.add(raw);
    }

    public static List<ItemStack> getRaws() {
        return raws;
    }

    public Crucible() {
        super(new ItemStackBuilder("CAULDRON_ITEM", 0, "CAULDRON", 1).setLocName("\u00a7e\u5769\u57da").build());
    }

    @Override
    public String getTypeName() {
        return "\u5769\u57da";
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }
}

