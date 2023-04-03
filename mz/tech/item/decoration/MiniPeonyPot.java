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

public class MiniPeonyPot
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniPeonyPot(), new Object[]{new ItemStackBuilder("DOUBLE_PLANT", 5, "peony", 1), null, 1, new ItemStackBuilder("FLOWER_POT_item", 0, "FLOWER_POT", 1)}).reg("\u8ff7\u4f60\u7261\u4e39\u76c6\u683d");
    }

    public MiniPeonyPot() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(826746114813L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM1MjU3Yjc5OWQzOTQ2OTI3ZjJiMzI1ZDM2NmViNTEwNGE1YzM1MjE5ZWU0ZTRkMzU3MjFiZjI4YTIxMCJ9fX0").setLocName("\u00a7a\u8ff7\u4f60\u7261\u4e39\u76c6\u683d").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u7261\u4e39\u76c6\u683d";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

