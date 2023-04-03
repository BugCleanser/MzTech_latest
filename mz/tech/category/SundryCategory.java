/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class SundryCategory
extends MzTechCategory {
    public static SundryCategory instance = new SundryCategory();

    private SundryCategory() {
        super("\u6742\u9879", new ItemStackBuilder(Material.LEATHER).setLocName("\u00a76\u6742\u9879").build());
        ItemStack enchanted_golden_apple;
        ItemStackBuilder cobweb = new ItemStackBuilder("web", 0, "cobweb", 1);
        new SmilingCraftingTableRecipe(cobweb, Material.STRING, 0, 0, 0, 0, 0, 0, 0, 0).reg("\u8718\u86db\u7f51");
        this.addItem(cobweb);
        ItemStackBuilder spawner = new ItemStackBuilder("mob_spawner", 0, "spawner", 1);
        ItemStackBuilder totem = new ItemStackBuilder("totem", 0, "totem_of_undying", 1);
        new SmilingCraftingTableRecipe(spawner, new Object[]{new ItemStackBuilder("iron_fence", 0, "iron_bars", 1), 0, 0, 0, totem, 0, 0, 0, 0}).reg("\u5237\u602a\u7b3c");
        this.addItem(spawner);
        try {
            enchanted_golden_apple = new ItemStack(Enum.valueOf(Material.class, "ENCHANTED_GOLDEN_APPLE"));
        }
        catch (Throwable e) {
            enchanted_golden_apple = new ItemStack(Material.GOLDEN_APPLE, 1, 1);
        }
        new SmilingCraftingTableRecipe(totem, null, enchanted_golden_apple, 0, Material.GOLDEN_APPLE, 3, 3, 0, 3).reg("\u4e0d\u6b7b\u56fe\u817e");
        this.addItem(totem);
    }
}

