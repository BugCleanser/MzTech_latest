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

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.sundry.Pigment;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class Hamburger
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(new Hamburger(), new Object[]{null, Material.BREAD, 0, Material.SPIDER_EYE, Material.ROTTEN_FLESH, new Pigment(), 0, 1}).reg("\u8001\u516b\u79d8\u5236\u5c0f\u6c49\u5821");
    }

    public Hamburger() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(41618464341L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjFjMjVmNTE2ZjJlN2Q2Zjc2Njc0MjBlMzNhZGNmM2NkZjkzOGNiMzdmOWE0MWE4YjM1ODY5ZjU2OWIifX19").setLocName("\u00a7e\u8001\u516b\u79d8\u5236\u5c0f\u6c49\u5821").setLoreList("\u00a77\u4e00\u65e5\u4e09\u9910\u6ca1\u70e6\u607c\uff0c\u4eca\u5929\u5c31\u5403\u8001\u516b\u79d8\u5236\u5c0f\u6c49\u5821", "\u00a77\u65e2\u5b9e\u60e0\uff0c\u8fd8\u7ba1\u9971", "\u00a77\u51d1\u8c46\u8150\uff0c\u4fd8\u864f\uff0c\u52a0\u67e0\u6aac", "\u00a77\u4f60\u770b\u8fd9\u6c49\u5821\u505a\u7684\u884c\u4e0d\u884c\uff1f", "\u00a77\u5965\u5229\u7ed9\uff01\u5144\u5f1f\u4eec\uff01\u9020\u5b83\u5c31\u5b8c\u4e86\uff01").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8001\u516b\u79d8\u5236\u5c0f\u6c49\u5821";
    }

    @Override
    public int getFoodLevel() {
        return 20;
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

