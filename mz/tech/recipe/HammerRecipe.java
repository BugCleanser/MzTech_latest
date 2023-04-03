/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.item.Hammer;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class HammerRecipe
extends MzTechRecipe {
    public MaterialData raw;
    public ItemStack drop;

    public HammerRecipe(MaterialData raw, ItemStack drop) {
        this.raw = raw;
        this.drop = drop;
    }

    @Override
    public List<RawItem> getRaws() {
        return Lists.newArrayList((Object[])new RawItem[]{new RawItem(this.raw.toItemStack(1))});
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.drop});
    }

    @Override
    public List<ItemStack> getMachines() {
        ArrayList<ItemStack> ri = new ArrayList<ItemStack>();
        MzTechItem.getTypes().forEach(hammer -> {
            if (hammer instanceof Hammer) {
                ri.add((ItemStack)hammer);
            }
        });
        return ri;
    }
}

