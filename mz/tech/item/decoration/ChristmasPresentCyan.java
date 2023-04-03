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

public class ChristmasPresentCyan
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasPresentCyan(), new Object[]{ItemStackBuilder.cyanDye, ItemStackBuilder.limeDye, 0, 1, Material.CHEST, 1, 0, 1, 0}).reg("\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u9752\uff09");
    }

    public ChristmasPresentCyan() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87941268526912951L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZjZDFjODJlMmZiM2ZhMzY4Y2ZhOWE1MDZhYjZjOTg2NDc1OTVkMjE1ZDY0NzFhZDQ3Y2NlMjk2ODVhZiJ9fX0=").setLocName("\u00a73\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u9752\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u9752\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

