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

public class MiniPumpkin
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniPumpkin(), new Object[]{new ItemStackBuilder(Material.PUMPKIN)}).reg("\u8ff7\u4f60\u5357\u74dc");
    }

    public MiniPumpkin() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(5821618715237L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdkN2FkMmRjYjU3ZGZhOWYwMjNkYmI5OWI2OThmYzUzMDc1YzNlOWQ2NTQ1MDYxMzlhNjQ3YWM5MDdmZGRjNSJ9fX0===").setLocName("\u00a76\u8ff7\u4f60\u5357\u74dc").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u5357\u74dc";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

