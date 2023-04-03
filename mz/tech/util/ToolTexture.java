/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import mz.tech.item.MzTechItem;
import mz.tech.item.tool.BedrockTool;
import mz.tech.item.tool.ObsidianTool;
import org.bukkit.inventory.ItemStack;

enum ToolTexture {
    WOODEN,
    STONE,
    IRON,
    DIAMOND,
    NETHERITE,
    OBSIDIAN,
    BEDROCK,
    GOLDEN,
    NONE;


    static ToolTexture getToolTexture(ItemStack tool) {
        if (tool == null) {
            return NONE;
        }
        MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(tool);
        if (mzTechCopy instanceof ObsidianTool) {
            return OBSIDIAN;
        }
        if (mzTechCopy instanceof BedrockTool) {
            return BEDROCK;
        }
        switch (tool.getType().toString()) {
            case "WOOD_SHOVEL": 
            case "WOOD_SWORD": 
            case "WOODEN_PICKAXE": 
            case "WOOD_PICKAXE": 
            case "WOODEN_SHOVEL": 
            case "WOODEN_SWORD": 
            case "WOOD_AXE": 
            case "WOOD_HOE": 
            case "WOODEN_AXE": 
            case "WOODEN_HOE": {
                return WOODEN;
            }
            case "STONE_SWORD": 
            case "STONE_PICKAXE": 
            case "STONE_AXE": 
            case "STONE_HOE": 
            case "STONE_SHOVEL": {
                return STONE;
            }
            case " IRON_PICKAXE": 
            case "IRON_SWORD": 
            case "IRON_AXE": 
            case "IRON_HOE": 
            case "IRON_SHOVEL": {
                return IRON;
            }
            case "DIAMOND_SWORD": 
            case "DIAMOND_AXE": 
            case "DIAMOND_HOE": 
            case "DIAMOND_SHOVEL": 
            case "DIAMOND_PICKAXE": {
                return DIAMOND;
            }
            case "NETHERITE_AXE": 
            case "NETHERITE_HOE": 
            case "NETHERITE_PICKAXE": 
            case "NETHERITE_SWORD": 
            case "NETHERITE_SHOVEL": {
                return NETHERITE;
            }
            case "GOLD_PICKAXE": 
            case "GOLD_AXE": 
            case "GOLD_HOE": 
            case "GOLDEN_PICKAXE": 
            case "GOLDEN_SWORD": 
            case "GOLDEN_AXE": 
            case "GOLDEN_HOE": 
            case "GOLD_SWORD": 
            case "GOLDEN_SHOVEL": 
            case "GOLD_SHOVEL": {
                return GOLDEN;
            }
        }
        return NONE;
    }
}

