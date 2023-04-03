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

public class MiniBedrock
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniBedrock(), Material.BEDROCK).reg("\u8ff7\u4f60\u57fa\u5ca9");
    }

    public MiniBedrock() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(15864398L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZkMWZhYmRmM2UzNDI2NzFiZDlmOTVmNjg3ZmUyNjNmNDM5ZGRjMmYxYzllYThmZjE1YjEzZjFlN2U0OGI5In19fQ").setLocName("\u00a78\u8ff7\u4f60\u57fa\u5ca9").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u57fa\u5ca9";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

