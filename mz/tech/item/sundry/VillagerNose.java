/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.DropsName;
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.SpawnEgg;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.VillagerNoseBlock;
import mz.tech.recipe.KillEntityRecipe;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class VillagerNose
extends MzTechItem {
    static {
        new KillEntityRecipe((ItemStack)new VillagerNose(), new ItemStack[]{new SpawnEgg("villager").setLocName("\u6751\u6c11").setLoreList("\u00a77\u51e0\u7387\uff1a 80%").build(), new SpawnEgg("zombie_villager").setLocName("\u50f5\u5c38\u6751\u6c11").setLoreList("\u00a77\u51e0\u7387\uff1a 50%").build()}, new int[]{80, 50}).setExtra(0, 0, new ItemStack(Material.EMERALD)).setExtra(1, 0, new ItemStack(Material.SLIME_BALL)).reg("\u6751\u6c11\u7684\u9f3b\u5b50");
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.EMERALD)});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new VillagerNose())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new VillagerNose().setLoreList("\u00a77\u6309\u4e0b" + new VillagerNose().getName() + "\u00a77\u83b7\u5f97").build()});
            }
        }.reg(DropsName.getShowName(new ItemStack(Material.EMERALD)));
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.SLIME_BALL)});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new VillagerNose())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new VillagerNose().setLoreList("\u00a77\u6309\u4e0b" + new VillagerNose().getName() + "\u00a77\u83b7\u5f97").build()});
            }
        }.reg(DropsName.getShowName(new ItemStack(Material.SLIME_BALL)));
    }

    public VillagerNose() {
        super(new ItemStackBuilder("WOOD_BUTTON", 0, "OAK_BUTTON", 1).setLocName("\u00a76\u6751\u6c11\u7684\u9f3b\u5b50").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6751\u6c11\u7684\u9f3b\u5b50";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new VillagerNoseBlock().setBlock(block);
    }
}

