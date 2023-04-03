/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MineOreRecipe
extends MzTechRecipe {
    ItemStack is;

    public MineOreRecipe(ItemStack is) {
        this.is = is;
    }

    @Override
    public List<RawItem> getRaws() {
        return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new ItemStackBuilder(Material.STONE).setDisplayName("\u00a77\u5730\u4e0b").build())});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDisplayName("\u00a7b\u4ece\u5730\u4e0b\u6316\u53d6").build()});
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.is});
    }
}

