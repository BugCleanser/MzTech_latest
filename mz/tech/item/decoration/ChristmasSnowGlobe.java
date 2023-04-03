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
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class ChristmasSnowGlobe
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasSnowGlobe(), new Object[]{Material.STICK, Material.SNOW, 0, ItemStackBuilder.glassPane, new RawItem(ItemStackBuilder.saplings), 3, 0, 1, 0}).reg("\u5723\u8bde\u6c34\u6676\u7403");
    }

    public ChristmasSnowGlobe() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(5216432871234L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiZThmZDFhYTM5ZjE1MDc2ZTg4NGRmZTZkZGI5YTNmMzc2MWRiMzFlMmIxZjk5NDBiNWRmZDM0ZDFjNGQifX19").setLocName("\u00a7b\u5723\u8bde\u6c34\u6676\u7403").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u6c34\u6676\u7403";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

