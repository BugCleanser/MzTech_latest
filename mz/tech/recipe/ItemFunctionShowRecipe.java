/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFunctionShowRecipe
extends MzTechRecipe
implements Listener {
    public List<ItemStack> shows = new ArrayList<ItemStack>();
    public static ItemStack machine = new ItemStack(Material.NAME_TAG);

    static {
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u7269\u54c1\u4fe1\u606f");
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u529f\u80fd\u4ecb\u7ecd"}));
        machine.setItemMeta(im);
    }

    @Override
    public List<RawItem> getRaws() {
        ArrayList<RawItem> rl = new ArrayList<RawItem>();
        int i = 1;
        while (i < this.shows.size()) {
            rl.add(new RawItem(this.shows.get(i)));
            ++i;
        }
        return rl;
    }

    @Override
    public List<ItemStack> getResults() {
        return this.shows;
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

