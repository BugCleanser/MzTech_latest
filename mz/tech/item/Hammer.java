/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.recipe.HammerRecipe;
import mz.tech.recipe.MzTechRecipe;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public interface Hammer {
    public static void init() {
        new HammerRecipe(new MaterialData(Material.COBBLESTONE), new ItemStack(Material.GRAVEL)).reg("\u6c99\u783e");
        new HammerRecipe(new MaterialData(Material.GRAVEL), new ItemStack(Material.SAND)).reg("\u6c99\u5b50");
    }

    default public boolean onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer() == null || event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            MzTechRecipe.forEach(HammerRecipe.class, recipe -> {
                if (event.getBlock().getType().equals((Object)recipe.raw.getItemType()) && event.getBlock().getData() == recipe.raw.getData()) {
                    event.setDropItems(false);
                    MzTech.dropItemStack(event.getBlock(), recipe.drop);
                }
            });
        }
        return true;
    }

    public static List<List<ItemStack>> getRecipeExtras() {
        return Lists.newArrayList((Object[])new List[]{Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.COBBLESTONE), new ItemStack(Material.GRAVEL)}), Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.GRAVEL), new ItemStack(Material.SAND)})});
    }
}

