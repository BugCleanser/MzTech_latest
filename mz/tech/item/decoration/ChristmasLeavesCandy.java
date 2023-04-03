/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Candy;
import mz.tech.item.MzTechItem;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

public class ChristmasLeavesCandy
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new ChristmasLeavesCandy(), new RawItem(ItemStackBuilder.oakLeaves, ItemStackBuilder.spruceLeaves, ItemStackBuilder.birchLeaves, ItemStackBuilder.jungleLeaves, ItemStackBuilder.acaciaLeaves, ItemStackBuilder.darkOakLeaves), new RawItem(MzTechItem.get(Candy.class).toArray(new MzTechItem[0]))).reg("\u5723\u8bde\u6811\u53f6\uff08\u7cd6\u679c\uff09");
    }

    public ChristmasLeavesCandy() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87154259565L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiMzJiNmViYWE3MzczZWY3NDQ3Mzg1ZTBiOTYxNmI2N2VmOTgyZjY5MzQxMzcxNDI5MWZmMzVlYjAyM2MifX19").setLocName("\u00a7a\u5723\u8bde\u6811\u53f6\uff08\u7cd6\u679c\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u6811\u53f6\uff08\u7cd6\u679c\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

