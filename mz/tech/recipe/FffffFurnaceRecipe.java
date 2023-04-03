/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.item.baseMachine.FffffFurnace;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.inventory.ItemStack;

public class FffffFurnaceRecipe
extends MzTechRecipe {
    public RawItem raw;
    public List<ItemStack> results;
    public static List<ItemStack> machines = Lists.newArrayList((Object[])new ItemStack[]{new FffffFurnace()});

    public FffffFurnaceRecipe(RawItem raw, List<ItemStack> results) {
        this.raw = raw;
        this.results = results;
    }

    public FffffFurnaceRecipe(RawItem raw, ItemStack ... results) {
        this(raw, Lists.newArrayList((Object[])results));
    }

    @Override
    public List<RawItem> getRaws() {
        return Lists.newArrayList((Object[])new RawItem[]{this.raw});
    }

    @Override
    public List<ItemStack> getMachines() {
        return machines;
    }

    @Override
    public List<ItemStack> getResults() {
        return this.results;
    }

    public static FffffFurnaceRecipe getRecipe(ItemStack raw) {
        FffffFurnaceRecipe[] rr = new FffffFurnaceRecipe[1];
        MzTechRecipe.forEach(FffffFurnaceRecipe.class, r -> {
            if (r.raw.matching(raw) != null) {
                fffffFurnaceRecipeArray[0] = r;
            }
        });
        return rr[0];
    }
}

