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

public class MiniMelon
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniMelon(), new Object[]{new ItemStackBuilder("melon_block", 0, "melon", 1)}).reg("\u8ff7\u4f60\u897f\u74dc");
    }

    public MiniMelon() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(272587614659L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQxNDEyYjhjNmZkNTdlNGUxNjIxNjZkZGZkNzRiMTQ4YTU5NmY5ZWIxZDE1OTNjMDQ2OTYzOGM4ZDcxNCJ9fX0===").setLocName("\u00a7a\u8ff7\u4f60\u897f\u74dc").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u897f\u74dc";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

