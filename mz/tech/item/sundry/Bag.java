/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.sundry;

import mz.tech.category.SundryCategory;
import mz.tech.item.Storeable;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class Bag
extends Storeable {
    static {
        new SmilingCraftingTableRecipe(new Bag(), Material.LEATHER, Material.SHULKER_SHELL, 0, Material.STRING, null, 3, 0, 1, 0).reg("\u80cc\u5305");
    }

    public Bag() {
        super(new ItemStackBuilder(Material.BOOK).setLocName("\u00a76\u80cc\u5305").setLoreList("\u00a74\u53f3\u952e \u00a77\u6253\u5f00", "\u00a77\u00a7m\u5141\u8bb8\u5957\u5a03").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getTypeName() {
        return "\u80cc\u5305";
    }

    @Override
    public String getTitle() {
        return "\u00a76\u80cc\u5305";
    }
}

