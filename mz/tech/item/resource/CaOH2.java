/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.resource.CaO;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class CaOH2
extends MzTechItem {
    static {
        RawItem water = new RawItem(new ItemStack[0]);
        water.put(new ItemStackBuilder(Material.POTION).setPotionType(PotionType.WATER).build(), new ItemStack(Material.GLASS_BOTTLE));
        new SmilingCraftingTableRecipe(false, (ItemStack)new CaOH2(), new Object[]{new CaO(), water}).reg("\u6c22\u6c27\u5316\u9499  CaO + H\u2082O == Ca(OH)\u2082");
    }

    public CaOH2() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a77\u6c22\u6c27\u5316\u9499").setLoreList("\u00a77Ca(OH)\u2082").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6c22\u6c27\u5316\u9499";
    }
}

