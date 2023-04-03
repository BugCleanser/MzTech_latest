/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.item.baseMachine.SmilingCraftingTable;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class Funny
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new Funny(), new Object[]{new SmilingCraftingTable()}).reg("\u6ed1\u7a3d");
    }

    public Funny() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(9294157529527491L), "ewogICJ0aW1lc3RhbXAiIDogMTYwNDc0MDU0ODk0OCwKICAicHJvZmlsZUlkIiA6ICJiMGQ0YjI4YmMxZDc0ODg5YWYwZTg2NjFjZWU5NmFhYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNaW5lU2tpbl9vcmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QwNmQwYmYxYzgzNzVlODg0NjMzZGFjNjE1NTFlODYyNWIzOTcxNDVkMzdkZDVjNDk3ZTMxZjgxYTNmM2M3IgogICAgfQogIH0KfQ==").setLocName("\u00a7e\u6ed1\u7a3d").build());
    }

    @Override
    public String getTypeName() {
        return "\u6ed1\u7a3d";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

