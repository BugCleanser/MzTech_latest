/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public interface Candy
extends Consumeable {
    @Override
    default public int getFoodLevel() {
        return 2;
    }

    default public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    default public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return Consumeable.super.onRightClickAir(player, hand);
    }

    @Override
    default public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block entity) {
        return Consumeable.super.onRightClickBlock(player, hand, entity);
    }

    @Override
    default public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return Consumeable.super.onRightClickEntity(player, hand, entity);
    }
}

