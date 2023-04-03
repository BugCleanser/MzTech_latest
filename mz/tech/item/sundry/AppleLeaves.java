/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.sundry;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.DropsName;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.AppleSapling;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AppleLeaves
extends MzTechItem {
    static {
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new AppleLeaves()});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new AppleSapling())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new AppleSapling().setLoreList("\u00a77\u5728\u82f9\u679c\u6811\u4e0a\u751f\u957f").build()});
            }
        }.reg("\u82f9\u679c\u6811\u53f6");
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.APPLE)});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new AppleLeaves())});
            }

            @Override
            public List<RawItem> getDisplayRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new AppleLeaves().setLoreList("\u00a77\u673a\u4f1a\uff1a100%+50%\u00d72").build())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new AppleLeaves().setLoreList("\u00a77\u7834\u574f\u82f9\u679c\u6811\u53f6\u6709\u673a\u4f1a\u6389\u843d").build()});
            }
        }.reg(DropsName.getShowName(new ItemStack(Material.APPLE)));
    }

    public AppleLeaves() {
        super(new ItemStackBuilder("leaves", 0, "oak_leaves", 1).setLocName("\u00a7a\u82f9\u679c\u6811\u53f6").build());
    }

    @Override
    public String getTypeName() {
        return "\u82f9\u679c\u6811\u53f6";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }
}

