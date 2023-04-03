/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 */
package mz.tech.item.sundry;

import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.GunpowderBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;

public class GunpowderBlockItem
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new GunpowderBlockItem(), new Object[]{new ItemStackBuilder("SULPHUR", 0, "gunpowder", 1), 0, 0, 0, 0, 0, 0, 0, 0}).reg("\u706b\u836f\u5757");
    }

    public GunpowderBlockItem() {
        super(new ItemStackBuilder("wool", 7, "light_gray_wool", 1).setLocName("\u00a77\u706b\u836f\u5757").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u706b\u836f\u5757";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new GunpowderBlock().setBlock(block);
    }
}

