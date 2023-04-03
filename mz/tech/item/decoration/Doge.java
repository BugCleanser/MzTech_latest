/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.item.decoration.Funny;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class Doge
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new Doge(), new Object[]{new Funny()}).reg("doge");
    }

    public Doge() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(516455554451335L), "ewogICJ0aW1lc3RhbXAiIDogMTYwNDc0OTIxMDU1NSwKICAicHJvZmlsZUlkIiA6ICI3MzgyZGRmYmU0ODU0NTVjODI1ZjkwMGY4OGZkMzJmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCdUlJZXQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFjYjRjMDY1OTMzYTllYTdiMjAwOWM3OWFhYTdhYzYyYjJjYTM4NTU0NGY0MGVjMWJhODE3MTgwMGE5OTU4MyIKICAgIH0KICB9Cn0===").setLocName("\u00a7edoge").build());
    }

    @Override
    public String getTypeName() {
        return "doge";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

