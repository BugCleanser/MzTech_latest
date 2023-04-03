/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.block.BlockBreakEvent
 */
package mz.tech.item.tool;

import mz.tech.category.ToolCategory;
import mz.tech.item.Hammer;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

public class HammerGold
extends MzTechItem
implements Hammer {
    static {
        new SmilingCraftingTableRecipe(new HammerGold(), Material.GOLD_INGOT, 0, 0, 0, Material.STICK, 0, null, 4).setExtras(Hammer.getRecipeExtras()).reg("\u91d1\u9524");
    }

    public HammerGold() {
        super(new ItemStackBuilder("gold_pickaxe", 0, "golden_pickaxe", 1).setLocName("\u00a7e\u91d1\u9524").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u91d1\u9524";
    }

    @Override
    public boolean onBlockBreak(BlockBreakEvent event) {
        return Hammer.super.onBlockBreak(event);
    }
}

