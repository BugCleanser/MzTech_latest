/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package mz.tech.item.food;

import mz.tech.category.FoodCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.item.food.Chocolate;
import mz.tech.item.resource.Au;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChocolateGold
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(new ChocolateGold(), new Object[]{new Au(), 0, 0, 0, new Chocolate(), 0, 0, 0, 0}).reg("\u91d1\u7b94\u5de7\u514b\u529b");
    }

    public ChocolateGold() {
        super(new ItemStackBuilder(Material.GOLD_INGOT).setLocName("\u00a7e\u91d1\u7b94\u5de7\u514b\u529b").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return FoodCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u91d1\u7b94\u5de7\u514b\u529b";
    }

    @Override
    public int getFoodLevel() {
        return 20;
    }

    @Override
    public double getHealth() {
        return 20.0;
    }

    @Override
    public void onConsume(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4800, 0));
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 2));
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 0));
    }

    @Override
    public boolean canConsume(Player player) {
        return true;
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

