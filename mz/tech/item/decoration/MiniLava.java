/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.decoration;

import mz.tech.MzTech;
import mz.tech.category.DecorationCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiniLava
extends PlaceableItem {
    static {
        new SmilingCraftingTableRecipe(new MiniLava(), new RawItem(new ItemStack[0]).add(new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BUCKET))).reg("\u8ff7\u4f60\u5ca9\u6d46");
    }

    public MiniLava() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(84268443217L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjVlNmE1ODY4NGMyNzdkMTg3MTdjZWM5NTlmMjgzM2E3MmRmYTk1NjYxMDE5ZGJjZGYzZGJmNjZiMDQ4In19fQ").setLocName("\u00a7c\u8ff7\u4f60\u5ca9\u6d46").build());
    }

    @Override
    public String getTypeName() {
        return "\u8ff7\u4f60\u5ca9\u6d46";
    }

    @Override
    public MzTechCategory getCategory() {
        return DecorationCategory.instance;
    }
}

