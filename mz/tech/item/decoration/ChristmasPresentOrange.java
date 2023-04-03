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

public class ChristmasPresentOrange
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasPresentOrange(), new Object[]{ItemStackBuilder.orangeDye, ItemStackBuilder.lightBlueDye, 0, 1, Material.CHEST, 1, 0, 1, 0}).reg("\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u6a59\uff09");
    }

    public ChristmasPresentOrange() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87941268526912956L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI4ZTY5MmQ4NmUyMjQ0OTc5MTVhMzk1ODNkYmUzOGVkZmZkMzljYmJhNDU3Y2M5NWE3YWMzZWEyNWQ0NDUifX19").setLocName("\u00a76\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u6a59\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u6a59\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

