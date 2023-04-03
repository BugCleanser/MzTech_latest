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

import mz.tech.MzTech;
import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.RawItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class IceCream
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(new IceCream(), null, Material.APPLE, 0, new RawItem(new ItemStack[0]).add(Material.MILK_BUCKET, Material.BUCKET), 3, 3, Material.ICE, 6, 6).reg("\u51b0\u6dc7\u6dcb");
    }

    public IceCream() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(1614224201312184L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdlMzhiZTI4ZGZiM2Y3MDNmOTBhNDYwNzExOWZjZWY5NGJmM2UxYzgyNjIxYzI5MzA2ZjA1MzgyMTlkNGUxIn19fQ==").setLocName("\u00a7b\u51b0\u6dc7\u6dcb").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u51b0\u6dc7\u6dcb";
    }

    @Override
    public int getFoodLevel() {
        return 6;
    }

    @Override
    public void onConsume(Player player) {
        player.setFireTicks(-1);
        super.onConsume(player);
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

