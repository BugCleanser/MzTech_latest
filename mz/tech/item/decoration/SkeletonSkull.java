/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class SkeletonSkull
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new SkeletonSkull(), new Object[]{new ItemStackBuilder("skull_item", 0, "SKELETON_SKULL", 1)}).reg("\u9ab7\u9ac5\u5934");
    }

    public SkeletonSkull() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(975237985241L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFlMzg1NWY5NTJjZDRhMDNjMTQ4YTk0NmUzZjgxMmE1OTU1YWQzNWNiY2I1MjYyN2VhNGFjZDQ3ZDMwODEifX19==").setLocName("\u00a77\u9ab7\u9ac5\u5934").build());
    }

    @Override
    public String getTypeName() {
        return "\u9ab7\u9ac5\u5934";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

