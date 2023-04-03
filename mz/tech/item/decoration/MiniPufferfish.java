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

public class MiniPufferfish
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniPufferfish(), new Object[]{new ItemStackBuilder("raw_fish", 3, "PUFFERFISH", 1)}).reg("\u8ff7\u4f60\u6cb3\u8c5a");
    }

    public MiniPufferfish() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(9591276287682L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk1NTkzODg5OTNmZTc4MmY2N2JkNThkOThjNGRmNTZiY2Q0MzBlZGNiMmY2NmVmNTc3N2E3M2MyN2RlMyJ9fX0").setLocName("\u00a7e\u8ff7\u4f60\u6cb3\u8c5a").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u6cb3\u8c5a";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

