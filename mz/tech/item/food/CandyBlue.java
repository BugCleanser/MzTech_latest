/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item.food;

import mz.tech.category.MzTechCategory;
import mz.tech.item.Candy;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CandyBlue
extends MzTechItem
implements Candy {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new CandyBlue(), Material.SUGAR, Material.PAPER, new ItemStackBuilder("ink_sack", 12, "light_blue_dye", 1).build()).reg("\u84dd\u8272\u7cd6\u679c");
    }

    public CandyBlue() {
        super(new ItemStackBuilder(Material.PRISMARINE_CRYSTALS).setLocName("\u00a7b\u84dd\u8272\u7cd6\u679c").build());
    }

    @Override
    public String getTypeName() {
        return "\u84dd\u8272\u7cd6\u679c";
    }

    @Override
    public MzTechCategory getCategory() {
        return Candy.super.getCategory();
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return super.onRightClickBlock(player, hand, block);
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return super.onRightClickEntity(player, hand, entity);
    }
}

