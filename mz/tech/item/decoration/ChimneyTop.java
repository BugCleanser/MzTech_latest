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

public class ChimneyTop
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new ChimneyTop(), new Object[]{Material.COBBLESTONE, 0, 0, null, new ItemStackBuilder("COBBLE_WALL", 0, "cobblestone_wall", 1)}).reg("\u70df\u56f1\u9876");
    }

    public ChimneyTop() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(156138152L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzA1OGVjNGQzOTIwYWRiZmE4NjU1MGY1ODUyNDIyZTFhZjU1MDU0YTE1YWZjOWMyYzkyMmQ1ODc2NWZhYTViIn19fQ").setLocName("\u00a77\u70df\u56f1\u9876").build());
    }

    @Override
    public String getTypeName() {
        return "\u70df\u56f1\u9876";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

