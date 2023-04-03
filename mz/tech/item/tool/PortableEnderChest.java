/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.tool;

import mz.tech.MzTech;
import mz.tech.category.ToolCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PortableEnderChest
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new PortableEnderChest(), Material.ENDER_CHEST).reg("\u4fbf\u643a\u5f0f\u672b\u5f71\u7bb1");
    }

    public PortableEnderChest() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(9417371004681L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZjYzQ4NmMyYmUxY2I5ZGZjYjJlNTNkZDlhM2U5YTg4M2JmYWRiMjdjYjk1NmYxODk2ZDYwMmI0MDY3In19fQ").setLocName("\u00a75\u4fbf\u643a\u5f0f\u672b\u5f71\u7bb1").setLoreList("\u00a74\u53f3\u952e \u00a77\u6253\u5f00").setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).build());
    }

    @Override
    public ToolCategory getCategory() {
        return ToolCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u4fbf\u643a\u5f0f\u672b\u5f71\u7bb1";
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        player.openInventory(player.getEnderChest());
        try {
            player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 1.0f, 1.0f);
        }
        catch (Throwable e) {
            player.getWorld().playSound(player.getEyeLocation(), Enum.valueOf(Sound.class, "BLOCK_ENDER_CHEST_OPEN"), 1.0f, 1.0f);
        }
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

