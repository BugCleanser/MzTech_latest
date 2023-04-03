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

public class ChristmasPresentPurple
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasPresentPurple(), new Object[]{ItemStackBuilder.purpleDye, ItemStackBuilder.greenDye, 0, 1, Material.CHEST, 1, 0, 1, 0}).reg("\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7d2b\uff09");
    }

    public ChristmasPresentPurple() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87941268526912958L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU3YjJlZTY1NmQ3Yjg2NWMzZmFkZDViMTQyOGMzNThkNDc2M2Y0MTc4YWM1OTlkNjA0ODY5YTE5ZDcifX19").setLocName("\u00a75\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7d2b\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7d2b\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

