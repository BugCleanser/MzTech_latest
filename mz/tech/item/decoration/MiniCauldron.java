/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class MiniCauldron
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniCauldron(), new Object[]{new ItemStackBuilder("CAULDRON_ITEM", 0, "cauldron", 1)}).reg("\u8ff7\u4f60\u70bc\u836f\u9505");
    }

    public MiniCauldron() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(58214621788888L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDIyNzY4ZGI2NTczNjUxNjI2NTUyYmIyNjQ0MjYwOWE3Mjc5Yzc4NDNjYjY5YjhlNjAzZDJjMWRiNjQ1ZDAifX19").setLocName("\u00a7f\u8ff7\u4f60\u70bc\u836f\u9505").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u70bc\u836f\u9505";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

