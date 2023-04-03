/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityPickupItemEvent
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item.sundry;

import mz.tech.MzTech;
import mz.tech.category.SundryCategory;
import mz.tech.item.Consumeable;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Baseball
extends MzTechItem
implements Consumeable {
    static {
        new SmilingCraftingTableRecipe(new Baseball(), new Object[]{Material.LEATHER, 0, 0, Material.STRING, new ItemStackBuilder("wool", 0, "white_wool", 1), 3, 0, 0, 0}).reg("\u68d2\u7403");
    }

    public Baseball() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(445165823458L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVhNzNhNmMxM2FmZjY2YTkyMjg2NTViNDkwYTA1ODQyOTE2OTYzNTEwNzU2Yjk2ZmFjMjZiNjUxOWVlZjJjNyJ9fX0=").setLocName("\u00a7a\u68d2\u7403").build());
    }

    @Override
    public SundryCategory getCategory() {
        return SundryCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u68d2\u7403";
    }

    @Override
    public boolean canConsume(Player player) {
        return true;
    }

    @Override
    public void onConsume(Player player) {
        player.getWorld().dropItem(player.getEyeLocation(), this.clone().setCount(1).build()).setVelocity(player.getVelocity().add(player.getEyeLocation().getDirection()));
    }

    @Override
    public Sound getSound() {
        return Sound.ENTITY_SNOWBALL_THROW;
    }

    @Override
    public boolean onEntityPickup(EntityPickupItemEvent event) {
        super.onEntityPickup(event);
        return event.getItem().isOnGround();
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

