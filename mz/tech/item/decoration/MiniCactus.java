/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class MiniCactus
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniCactus(), Material.CACTUS).reg("\u8ff7\u4f60\u4ed9\u4eba\u638c");
    }

    public MiniCactus() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(684184734L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhjOWE3MzAyNjljZTFkZTNlOWZhMDY0YWZiMzcwY2JjZDA3NjZkNzI5ZjNlMjllNGYzMjBhNDMzYjA5OGI1In19fQ").setLocName("\u00a72\u8ff7\u4f60\u4ed9\u4eba\u638c").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u4ed9\u4eba\u638c";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

