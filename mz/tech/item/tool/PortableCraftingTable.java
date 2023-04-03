/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.tool;

import mz.tech.MzTech;
import mz.tech.category.ToolCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PortableCraftingTable
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new PortableCraftingTable(), new Object[]{new ItemStackBuilder("WORKBENCH", 0, "crafting_table", 1)}).reg("\u4fbf\u643a\u5f0f\u5408\u6210\u53f0");
    }

    public PortableCraftingTable() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(8961782376L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2U3ZDhjMjQyZDJlNGY4MDI4ZjkzMGJlNzZmMzUwMTRiMjFiNTI1NTIwOGIxYzA0MTgxYjI1NzQxMzFiNzVhIn19fQ").setLocName("\u00a7a\u4fbf\u643a\u5f0f\u5408\u6210\u53f0").setLoreList("\u00a74\u53f3\u952e \u00a77\u6253\u5f00").build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u4fbf\u643a\u5f0f\u5408\u6210\u53f0";
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        player.openWorkbench(null, true);
        player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_WOOD_PLACE, 1.0f, 1.0f);
        return false;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return this.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return this.onRightClickAir(player, hand);
    }
}

