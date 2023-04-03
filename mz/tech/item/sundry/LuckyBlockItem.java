/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package mz.tech.item.sundry;

import mz.tech.MzTech;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.LuckyBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class LuckyBlockItem
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new LuckyBlockItem(), Material.GOLD_INGOT, 0, 0, 0, Material.DROPPER, 0, 0, 0, 0).reg("\u5e78\u8fd0\u65b9\u5757");
    }

    public LuckyBlockItem() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(1794521915745922L), "ewogICJ0aW1lc3RhbXAiIDogMTYwODgyMzY1OTYzNywKICAicHJvZmlsZUlkIiA6ICI0NWY3YTJlNjE3ODE0YjJjODAwODM5MmRmN2IzNWY0ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJfSnVzdERvSXQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk4NGIxZjc2YTc3NGVjNzVlZGUwYzU0YTk2MDk3NzA0MTEwNDE2MTAyYTNiNTdiZDNkNzlkNzVkYjkyMmM0MiIKICAgIH0KICB9Cn0=").setLocName("\u00a7e\u5e78\u8fd0\u65b9\u5757").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u5e78\u8fd0\u65b9\u5757";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new LuckyBlock().setBlock(block);
    }
}

