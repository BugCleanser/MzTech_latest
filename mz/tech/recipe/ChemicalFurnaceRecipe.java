/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.item.baseMachine.ChemicalFurnace;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class ChemicalFurnaceRecipe
extends MzTechRecipe {
    public static List<ItemStack> chemicalFurnaces = Lists.newArrayList((Object[])new ItemStack[]{new ChemicalFurnace().setLoreList("\u00a77\u5728" + new ChemicalFurnace().getName() + "\u00a77\u4e2d\u5236\u4f5c").build()});
    public List<RawItem> raws;
    public ItemStack result;
    public boolean needCoal;

    public ChemicalFurnaceRecipe(List<RawItem> raws, ItemStack result, boolean needCoal) {
        this.raws = raws;
        this.result = result;
        this.needCoal = needCoal;
    }

    public ChemicalFurnaceRecipe(ItemStack result, boolean needCoal, RawItem ... raws) {
        this(Lists.newArrayList((Object[])raws), result, needCoal);
    }

    public ChemicalFurnaceRecipe(List<RawItem> raws, ItemStack result) {
        this(raws, result, true);
    }

    public ChemicalFurnaceRecipe(ItemStack result, RawItem ... raws) {
        this(result, true, raws);
    }

    @Override
    public List<RawItem> getRaws() {
        return this.raws;
    }

    @Override
    public List<ItemStack> getMachines() {
        return chemicalFurnaces;
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.result});
    }

    public List<ItemStack> matching(List<ItemStack> raws) {
        if (raws.size() != this.raws.size()) {
            return null;
        }
        ArrayList<ItemStack> rl = new ArrayList<ItemStack>();
        ArrayList copy = Lists.newArrayList(this.raws);
        raws.forEach(is -> {
            int i = 0;
            while (i < copy.size()) {
                ItemStack matching = ((RawItem)copy.get(i)).matching((ItemStack)is);
                if (matching != null) {
                    is.setAmount(is.getAmount() - matching.getAmount());
                    ItemStack output = ((RawItem)copy.get(i)).get(matching);
                    if (output != null) {
                        rl.add(output.clone());
                    }
                    copy.remove(i);
                    --i;
                }
                ++i;
            }
        });
        if (copy.isEmpty()) {
            rl.addAll(this.getResults());
            return rl;
        }
        return null;
    }
}

