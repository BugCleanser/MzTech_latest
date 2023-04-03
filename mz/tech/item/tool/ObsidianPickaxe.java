/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.tool;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.DropsName;
import mz.tech.category.ToolCategory;
import mz.tech.item.tool.BedrockPickaxe;
import mz.tech.item.tool.ObsidianTool;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.ToolUtil;
import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ObsidianPickaxe
extends ObsidianTool {
    static {
        try {
            new SmilingCraftingTableRecipe(new ObsidianPickaxe(), Material.OBSIDIAN, 0, 0, null, Material.STICK, null, null, 4).reg("\u9ed1\u66dc\u77f3\u9550");
            new MzTechRecipe(){

                @Override
                public List<ItemStack> getResults() {
                    return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.BEDROCK)});
                }

                @Override
                public List<RawItem> getRaws() {
                    return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new ObsidianPickaxe(), new BedrockPickaxe())});
                }

                @Override
                public List<ItemStack> getMachines() {
                    return Lists.newArrayList((Object[])new ItemStack[]{new ObsidianPickaxe().setLoreList("\u00a77\u4f7f\u7528\u9ed1\u66dc\u77f3\u9550\u6316\u6398\u5f97\u5230").build(), new BedrockPickaxe().setLoreList("\u00a77\u4f7f\u7528\u57fa\u5ca9\u9550\u6316\u6398\u5f97\u5230").build()});
                }
            }.reg(DropsName.getShowName(new ItemStack(Material.BEDROCK)));
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public ObsidianPickaxe() {
        super(new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDurability(0).setLocName("\u00a75\u9ed1\u66dc\u77f3\u9550").setLoreList("\u00a77\u7c89\u788e\u57fa\u5ca9").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u9ed1\u66dc\u77f3\u9550";
    }

    @Override
    public boolean onStartBreakBlock(BlockDamageEvent event) {
        switch (event.getBlock().getType().toString()) {
            case "BEDROCK": {
                ToolUtil.startBreakBlock(event.getPlayer(), event.getBlock(), 6000, Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.BEDROCK)}));
            }
        }
        return true;
    }
}

