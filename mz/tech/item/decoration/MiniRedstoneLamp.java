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

public class MiniRedstoneLamp
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniRedstoneLamp(), new Object[]{new ItemStackBuilder("REDSTONE_LAMP_OFF", 0, "redstone_lamp", 1)}).reg("\u8ff7\u4f60\u7ea2\u77f3\u706f");
    }

    public MiniRedstoneLamp() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(2641638411L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFmZjkzZWJlY2MxZjhmYmQxM2JhNzgzOWVjN2JkY2RlY2FiN2MwN2ZkOGJhNzhlZTc4YWQwYmQzYWNjYmUifX19").setLocName("\u00a7e\u8ff7\u4f60\u7ea2\u77f3\u706f").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u7ea2\u77f3\u706f";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

