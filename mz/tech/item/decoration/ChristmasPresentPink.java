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

public class ChristmasPresentPink
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasPresentPink(), new Object[]{ItemStackBuilder.pinkDye, ItemStackBuilder.lightBlueDye, 0, 1, Material.CHEST, 1, 0, 1, 0}).reg("\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7c89\u7ea2\uff09");
    }

    public ChristmasPresentPink() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87941268526912957L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==").setLocName("\u00a7d\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7c89\u7ea2\uff09").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u793c\u76d2\u6302\u9970\uff08\u7c89\u7ea2\uff09";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

