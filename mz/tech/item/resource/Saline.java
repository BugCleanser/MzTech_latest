/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionType
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.resource.NaCl;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class Saline
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new Saline(), new RawItem(new ItemStack(Material.SAND), new NaCl()), new ItemStackBuilder(Material.POTION).setPotionType(PotionType.WATER).build()).reg("\u76d0\u6c34");
    }

    public Saline() {
        super(new ItemStackBuilder(Material.POTION).setPotionType(PotionType.WATER).setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).setLocName("\u00a77\u76d0\u6c34").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u76d0\u6c34";
    }
}

