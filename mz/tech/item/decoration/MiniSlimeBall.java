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

public class MiniSlimeBall
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniSlimeBall(), Material.SLIME_BALL).reg("\u8ff7\u4f60\u7c98\u6db2\u7403");
    }

    public MiniSlimeBall() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(516813538L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDkzNGE5ZjVhYjE3ODlhN2Q4ZGQ5NmQzMjQ5M2NkYWNmZjU3N2Q4YzgxZTdiMjM5MTdkZmYyZTMyYmQwYmMxMCJ9fX0").setLocName("\u00a7a\u8ff7\u4f60\u7c98\u6db2\u7403").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u7c98\u6db2\u7403";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

