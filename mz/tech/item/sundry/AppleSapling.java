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
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.AppleLeaves;
import mz.tech.machine.AppleSaplingBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class AppleSapling
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new AppleSapling(), Material.APPLE).reg("\u82f9\u679c\u6811\u82d7");
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new AppleSapling()});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new AppleLeaves())});
            }

            @Override
            public List<RawItem> getDisplayRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new AppleLeaves().setLoreList("\u00a77\u51e0\u7387\uff1a 5%").build())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new AppleLeaves().setLoreList("\u00a77\u7834\u574f\u82f9\u679c\u6811\u53f6\u6709\u673a\u4f1a\u6389\u843d").build()});
            }
        }.reg("\u82f9\u679c\u6811\u82d7");
    }

    public AppleSapling() {
        super(new ItemStackBuilder("SAPLING", 0, "OAK_SAPLING", 1).setLocName("\u00a7c\u82f9\u679c\u6811\u82d7").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u82f9\u679c\u6811\u82d7";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new AppleSaplingBlock().setBlock(block);
    }
}

