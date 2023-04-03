/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.ShapelessRecipe
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
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WorkBenchShapelessRecipe
extends MzTechRecipe
implements Listener {
    ShapelessRecipe recipe;
    public static ItemStack machine;

    static {
        try {
            machine = new ItemStack(Material.WORKBENCH);
        }
        catch (Error e) {
            machine = new ItemStack(Enum.valueOf(Material.class, "CRAFTING_TABLE"));
        }
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u5de5\u4f5c\u53f0");
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u5728\u5de5\u4f5c\u53f0\u4e2d\u5408\u6210", "\u00a76\u65e0\u5e8f\u5408\u6210"}));
        machine.setItemMeta(im);
    }

    public WorkBenchShapelessRecipe(ShapelessRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void reg(String name) {
        super.reg(name);
        try {
            Bukkit.addRecipe((Recipe)this.recipe);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    @Override
    public List<RawItem> getRaws() {
        ArrayList<RawItem> rl = new ArrayList<RawItem>();
        this.recipe.getIngredientList().forEach(i -> {
            if (i != null) {
                if (i.getDurability() == Short.MAX_VALUE) {
                    i.setDurability((short)0);
                }
                rl.add(new RawItem((ItemStack)i));
            }
        });
        return rl;
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.recipe.getResult()});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

