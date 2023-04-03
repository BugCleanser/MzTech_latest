/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MzTechFurnaceRecipe
extends MzTechRecipe {
    FurnaceRecipe minecraftRecipe;
    public static ItemStack machine = new ItemStack(Material.FURNACE);

    static {
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u7194\u7089");
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u5728\u7194\u7089\u4e2d\u70e7\u70bc"}));
        machine.setItemMeta(im);
    }

    public MzTechFurnaceRecipe(FurnaceRecipe minecraftRecipe) {
        this.minecraftRecipe = minecraftRecipe;
    }

    @Override
    public void reg(String name) {
        Bukkit.addRecipe((Recipe)this.minecraftRecipe);
        super.reg(name);
    }

    @Override
    public List<RawItem> getRaws() {
        ArrayList<RawItem> rl = new ArrayList<RawItem>();
        ItemStack t = this.minecraftRecipe.getInput();
        if (t != null) {
            if (t.getDurability() == Short.MAX_VALUE) {
                t.setDurability((short)0);
            }
            rl.add(new RawItem(t));
        }
        return rl;
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.minecraftRecipe.getResult()});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

