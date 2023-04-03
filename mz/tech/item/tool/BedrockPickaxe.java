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
import mz.tech.item.tool.BedrockTool;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.ToolUtil;
import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BedrockPickaxe
extends BedrockTool {
    static {
        new SmilingCraftingTableRecipe(new BedrockPickaxe(), Material.BEDROCK, 0, 0, null, Material.STICK, null, null, 4).reg("\u57fa\u5ca9\u9550");
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.BARRIER)});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new BedrockPickaxe())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new BedrockPickaxe().setLoreList("\u00a77\u4f7f\u7528\u57fa\u5ca9\u9550\u6316\u6398\u5f97\u5230").build()});
            }
        }.reg(DropsName.getShowName(new ItemStack(Material.BARRIER)));
        new MzTechRecipe(){

            @Override
            public List<ItemStack> getResults() {
                return Lists.newArrayList((Object[])new ItemStack[]{new ItemStackBuilder("END_PORTAL_FRAME", 0, "ENDER_PORTAL_FRAME", 1)});
            }

            @Override
            public List<RawItem> getRaws() {
                return Lists.newArrayList((Object[])new RawItem[]{new RawItem(new BedrockPickaxe())});
            }

            @Override
            public List<ItemStack> getMachines() {
                return Lists.newArrayList((Object[])new ItemStack[]{new BedrockPickaxe().setLoreList("\u00a77\u4f7f\u7528\u57fa\u5ca9\u9550\u6316\u6398\u5f97\u5230").build()});
            }
        }.reg(DropsName.getShowName(new ItemStackBuilder("END_PORTAL_FRAME", 0, "ENDER_PORTAL_FRAME", 1)));
    }

    public BedrockPickaxe() {
        super(new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDurability(0).setLocName("\u00a78\u00a7l\u57fa\u5ca9\u9550").setLoreList("\u00a77\u7c89\u788e\u4e00\u5207").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u57fa\u5ca9\u9550";
    }

    @Override
    public boolean onStartBreakBlock(BlockDamageEvent event) {
        switch (event.getBlock().getType().name()) {
            case "BEDROCK": {
                ToolUtil.startBreakBlock(event.getPlayer(), event.getBlock(), 6000, Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.BEDROCK)}));
                break;
            }
            case "ENDER_PORTAL_FRAME": 
            case "END_PORTAL_FRAME": 
            case "BARRIER": {
                ToolUtil.startBreakBlock(event.getPlayer(), event.getBlock(), 8000, Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(event.getBlock().getType())}));
            }
        }
        return true;
    }
}

