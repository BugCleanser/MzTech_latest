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
import mz.tech.category.SundryCategory;
import mz.tech.item.MzTechItem;
import mz.tech.item.baseMachine.MouseTrap;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MouseTail
extends MzTechItem {
    static {
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new MouseTail()});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new MouseTrap())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new MouseTrap().setLoreList("\u00a77\u4f7f\u7528\u8001\u9f20\u5939\u83b7\u5f97").build()});
            }
        }.reg("\u8017\u5b50\u5c3e");
    }

    public MouseTail() {
        super(new ItemStackBuilder(Material.STICK).setLocName("\u00a77\u8017\u5b50\u5c3e").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8017\u5b50\u5c3e";
    }
}

