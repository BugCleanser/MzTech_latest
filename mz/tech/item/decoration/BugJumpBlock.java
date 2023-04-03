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

public class BugJumpBlock
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new BugJumpBlock(), new Object[]{ItemStackBuilder.magentaConcrete, ItemStackBuilder.blackConcrete, null, 1, 0}).reg("BugJump\u65b9\u5757");
        new SmilingCraftingTableRecipe(new BugJumpBlock(), new Object[]{ItemStackBuilder.blackConcrete, ItemStackBuilder.magentaConcrete, null, 1, 0}).reg("BugJump\u65b9\u5757");
    }

    public BugJumpBlock() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(925134061406013L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTllYjlkYTI2Y2YyZDMzNDEzOTdhN2Y0OTEzYmEzZDM3ZDFhZDEwZWFlMzBhYjI1ZmEzOWNlYjg0YmMifX19").setLocName("\u00a74BugJump\u65b9\u5757").build());
    }

    @Override
    public String getTypeName() {
        return "BugJump\u65b9\u5757";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

