/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.item.decoration.BugJumpBlock;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;

public class IllusionBlock
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new IllusionBlock(), new Object[]{ItemStackBuilder.blackConcrete, ItemStackBuilder.whiteConcrete, 0, 1, new BugJumpBlock(), 1, 0, 1, 0}).reg("\u9519\u89c9\u65b9\u5757");
    }

    public IllusionBlock() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(71571368256154L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MyYzU5ZmNkOTI2MjVlYzRkNTc4MTU5YTVmZDViZDQyNDdlMzgyZDQ5NDcyODRjZjUwZjk5OWM4NDExNmMwIn19fQ==").setLocName("\u00a75\u9519\u89c9\u65b9\u5757").build());
    }

    @Override
    public String getTypeName() {
        return "\u9519\u89c9\u65b9\u5757";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

