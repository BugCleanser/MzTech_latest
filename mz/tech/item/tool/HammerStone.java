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

public class HammerStone
extends MzTechItem
implements Hammer {
    static {
        new SmilingCraftingTableRecipe(new HammerStone(), Material.COBBLESTONE, 0, 0, 0, Material.STICK, 0, null, 4).setExtras(Hammer.getRecipeExtras()).reg("\u77f3\u9524");
    }

    public HammerStone() {
        super(new ItemStackBuilder(Material.STONE_PICKAXE).setLocName("\u00a77\u77f3\u9524").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u77f3\u9524";
    }

    @Override
    public boolean onBlockBreak(BlockBreakEvent event) {
        return Hammer.super.onBlockBreak(event);
    }
}

