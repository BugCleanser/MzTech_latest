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
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package mz.tech.item.food;

import mz.tech.category.MzTechCategory;
import mz.tech.item.Candy;
import mz.tech.item.MzTechItem;
import mz.tech.item.resource.Au;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CandyGold
extends MzTechItem
implements Candy {
    static {
        new SmilingCraftingTableRecipe(false, (ItemStack)new CandyGold(), new Object[]{Material.SUGAR, Material.PAPER, new Au(), Material.STRING}).reg("\u91d1\u8272\u7cd6\u679c");
    }

    public CandyGold() {
        super(new ItemStackBuilder(Material.GOLDEN_CARROT).setLocName("\u00a7e\u91d1\u8272\u7cd6\u679c").build());
    }

    @Override
    public String getTypeName() {
        return "\u91d1\u8272\u7cd6\u679c";
    }

    @Override
    public int getFoodLevel() {
        return 10;
    }

    @Override
    public void onConsume(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 480, 0));
        player.removePotionEffect(PotionEffectType.ABSORPTION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 240, 2));
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0));
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

    @Override
    public MzTechCategory getCategory() {
        return Candy.super.getCategory();
    }
}

