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

public class ChristmasTreeBell
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChristmasTreeBell(), Material.GOLD_INGOT, 0, 0, 0, null, 0, 0, null, 0).reg("\u5723\u8bde\u6811\u94c3\u94db");
    }

    public ChristmasTreeBell() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(87156721587565L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM1NTNhY2UzMmNmYTEyZjkxOTEzMjMyODQ3NDY3YmViNTZkNjQ1ZjFjOTZjNDE0NWU3OTlkMGZiOTM3YTMwIn19fQ==").setLocName("\u00a7e\u5723\u8bde\u6811\u94c3\u94db").build());
    }

    @Override
    public String getTypeName() {
        return "\u5723\u8bde\u6811\u94c3\u94db";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

