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
import mz.tech.item.decoration.MiniPumpkin;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class MiniPumpkinCarved
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniPumpkinCarved(), new Object[]{new MiniPumpkin()}).reg("\u8ff7\u4f60\u96d5\u523b\u5357\u74dc");
        try {
            new SmilingCraftingTableRecipe(new MiniPumpkinCarved(), Enum.valueOf(Material.class, "CARVED_PUMPKIN")).reg("\u8ff7\u4f60\u96d5\u523b\u5357\u74dc");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public MiniPumpkinCarved() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(8176188888L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVjNDE1ZDcwMmYzMjkyYTgyZjE0NzFjODc5NGNmNjMxMjJkNDQ5ZDI4YWI4ODZkNGRjNThmYWZkNjYifX19===").setLocName("\u00a76\u8ff7\u4f60\u96d5\u523b\u5357\u74dc").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u96d5\u523b\u5357\u74dc";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

