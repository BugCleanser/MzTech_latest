/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package mz.tech.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GriddleRecipe
extends MzTechRecipe {
    public static ItemStack machine;
    public ItemStack raw;
    public ItemStack drop;
    public Integer probabilitie;

    static {
        try {
            machine = new ItemStack(Material.WEB);
        }
        catch (Throwable e) {
            machine = new ItemStack(Enum.valueOf(Material.class, "COBWEB"));
        }
        ItemMeta im = machine.getItemMeta();
        im.setLocalizedName("\u00a7f\u7b5b\u5b50");
        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a77\u4f7f\u7528\u7b5b\u5b50\u7b5b\u5c31\u5b8c\u4e86"}));
        machine.setItemMeta(im);
        new GriddleRecipe((ItemStack)new ItemStackBuilder("SULPHUR", 0, "gunpowder", 3), new ItemStack(Material.COAL)).reg("\u7164\u70ad");
    }

    public GriddleRecipe(ItemStack raw, ItemStack drop) {
        this(raw, drop, 100);
    }

    public GriddleRecipe(ItemStack raw, ItemStack drop, int probabilitie) {
        this.raw = raw;
        this.drop = drop;
        this.probabilitie = probabilitie;
    }

    public GriddleRecipe(ItemStack raw, Material drop) {
        this(raw, drop, 100);
    }

    public GriddleRecipe(ItemStack raw, Material drop, int probabilitie) {
        this.raw = raw;
        this.drop = new ItemStack(drop);
        this.probabilitie = probabilitie;
    }

    public GriddleRecipe(Material raw, ItemStack drop) {
        this(raw, drop, 100);
    }

    public GriddleRecipe(Material raw, ItemStack drop, int probabilitie) {
        this.raw = new ItemStack(raw);
        this.drop = drop;
        this.probabilitie = probabilitie;
    }

    @Override
    public List<RawItem> getDisplayRaws() {
        ArrayList<RawItem> rl = new ArrayList<RawItem>();
        ItemStack is = new ItemStack(this.raw);
        ItemMeta im = is.getItemMeta();
        List lore = im.hasLore() ? im.getLore() : new ArrayList();
        lore.add("\u00a77\u51e0\u7387\uff1a " + this.probabilitie);
        im.setLore(lore);
        is.setItemMeta(im);
        rl.add(new RawItem(is));
        return rl;
    }

    @Override
    public List<RawItem> getRaws() {
        return Lists.newArrayList((Object[])new RawItem[]{new RawItem(this.raw)});
    }

    @Override
    public List<ItemStack> getResults() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.drop});
    }

    @Override
    public List<ItemStack> getMachines() {
        return Lists.newArrayList((Object[])new ItemStack[]{machine});
    }
}

