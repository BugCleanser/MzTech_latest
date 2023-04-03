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

public class ChristmasPresentGreen
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasPresentGreen(), new Object[]{ItemStackBuilder.greenDye, ItemStackBuilder.magentaDye, 0, 1, Material.CHEST, 1, 0, 1, 0}).reg("\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7eff\uff09");
    }

    public ChristmasPresentGreen() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87941268526912953L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ5N2Y0ZjQ0ZTc5NmY3OWNhNDMwOTdmYWE3YjRmZTkxYzQ0NWM3NmU1YzI2YTVhZDc5NGY1ZTQ3OTgzNyJ9fX0=").setLocName("\u00a72\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7eff\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7eff\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

