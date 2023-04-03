/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package mz.tech.item.food;

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.PlacedItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoopBlock
extends MzTechItem
implements Consumeable {
    public PoopBlock() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(1614224205826174264L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWIzYjFmNzg1ZjAxNzUzYzQ1ZWY5N2ZjZmZmZmIzZjUyNjU4ZmZjZWIxN2FkM2Y3YjU5Mjk0NWM2ZGYyZmEifX19").setLocName("\u00a76\u00a7l\u5965\u5229\u7ed9\u65b9\u5757").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u5965\u5229\u7ed9\u65b9\u5757";
    }

    @Override
    public int getFoodLevel() {
        return 6;
    }

    @Override
    public void onConsume(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 2));
        player.chat("\u5965\u5229\u7ed9\u5e72\u4e86\u5144\u5f1f\u4eec");
        super.onConsume(player);
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new PlacedItem(block, this.getTypeName());
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        return Consumeable.super.onRightClickAir(player, hand);
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        return true;
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return Consumeable.super.onRightClickEntity(player, hand, entity);
    }
}

