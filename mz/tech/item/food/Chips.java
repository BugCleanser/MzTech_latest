/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item.food;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class Chips
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(new Chips().setNum(16), Material.BAKED_POTATO, 0, 0).reg("\u85af\u7247");
    }

    public Chips() {
        super(new ItemStackBuilder(Material.RABBIT_FOOT).setLocName("\u00a7e\u85af\u7247").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u85af\u7247";
    }

    @Override
    public int getFoodLevel() {
        return 1;
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return Consumeable.super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return Consumeable.super.onRightClickBlock(player, hand, block);
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return Consumeable.super.onRightClickEntity(player, hand, entity);
    }
}

