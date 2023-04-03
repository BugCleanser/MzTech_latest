/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.category;

import mz.tech.MzTech;
import mz.tech.category.MzTechCategory;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

public final class DecorationCategory
extends MzTechCategory {
    public static ItemStack cxkHead = ItemStackBuilder.newSkull("cxk", MzTech.newUUID(481678233333L), "ewogICJ0aW1lc3RhbXAiIDogMTYwNDc0MzYyNzA4OCwKICAicHJvZmlsZUlkIiA6ICJkODY1NjliNzg1ODU0OGU3OTJlYmJjNDM2MGYxNjkwNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJPY2FuYW0iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk3NjFjZGYxMjhlMWVkN2RkZTFmY2IxZDU5MjFkYTZjNWQxYmE3OTdhZDMwZjkzMzRlYmI1MTRhMjg2M2FiOCIKICAgIH0KICB9Cn0=");
    public static DecorationCategory instance = new DecorationCategory();

    private DecorationCategory() {
        super("\u88c5\u9970", new ItemStackBuilder("DOUBLE_PLANT", 5, "peony", 1).setLocName("\u00a7d\u88c5\u9970").build());
        new SmilingCraftingTableRecipe(cxkHead, new Object[]{new ItemStackBuilder("raw_chicken", 0, "chicken", 1)}).reg("cxk\u7684\u5934");
        this.addItem(cxkHead);
    }
}

