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

public class MiniCocoa
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniCocoa(), new Object[]{new ItemStackBuilder("ink_sack", 3, "cocoa_beans", 3)}).reg("\u8ff7\u4f60\u53ef\u53ef\u679c");
    }

    public MiniCocoa() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(8196148414L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTA4M2VjMmIwMWRjMGZlZTc5YWEzMjE4OGQ5NDI5YWNjNjhlY2Y3MTQwOGRjYTA0YWFhYjUzYWQ4YmVhMCJ9fX0").setLocName("\u00a76\u8ff7\u4f60\u53ef\u53ef\u679c").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u53ef\u53ef\u679c";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

