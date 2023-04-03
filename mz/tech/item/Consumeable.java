/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.EquipmentSlot
 */
package mz.tech.item;

import mz.tech.event.ConsumeItemEvent;
import mz.tech.item.MzTechItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;

public interface Consumeable {
    default public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        if (this.canConsume(player)) {
            ConsumeItemEvent event = new ConsumeItemEvent(player, (MzTechItem)((Object)this));
            Bukkit.getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                if (player.getGameMode() != GameMode.CREATIVE) {
                    ((MzTechItem)((Object)this)).setAmount(((MzTechItem)((Object)this)).getAmount() - 1);
                }
                player.setFoodLevel(player.getFoodLevel() + this.getFoodLevel() > 20 ? 20 : player.getFoodLevel() + this.getFoodLevel());
                player.setSaturation(player.getSaturation() + this.getSaturation() > 20.0f ? 20.0f : player.getSaturation() + this.getSaturation());
                player.setHealth(player.getHealth() + this.getHealth() > player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + this.getHealth());
                if (this.getSound() != null) {
                    player.getWorld().playSound(player.getLocation(), this.getSound(), 1.0f, 1.0f);
                }
                this.onConsume(player);
            }
        }
        return false;
    }

    default public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        return this.onRightClickAir(player, hand);
    }

    default public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block entity) {
        return this.onRightClickAir(player, hand);
    }

    default public void onConsume(Player player) {
    }

    default public boolean canConsume(Player player) {
        return player.getGameMode() == GameMode.CREATIVE || player.getFoodLevel() < 20;
    }

    default public int getFoodLevel() {
        return 2;
    }

    default public float getSaturation() {
        return this.getFoodLevel() * 2;
    }

    default public double getHealth() {
        return 0.0;
    }

    default public Sound getSound() {
        return Sound.ENTITY_GENERIC_EAT;
    }
}

