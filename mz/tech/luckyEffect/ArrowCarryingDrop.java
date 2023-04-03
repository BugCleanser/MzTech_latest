/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import mz.tech.MzTech;
import mz.tech.luckyEffect.ArrowCarryingEntity;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public interface ArrowCarryingDrop
extends ArrowCarryingEntity {
    @Override
    default public Entity spawnEntity(Location location) {
        Item drop = MzTech.dropItemStack(location, this.getItemStack());
        drop.setPickupDelay(Short.MAX_VALUE);
        drop.setCustomNameVisible(false);
        return drop;
    }

    public ItemStack getItemStack();

    @Override
    default public void onHit(Arrow arrow, Location location) {
        arrow.getPassengers().forEach(e -> {
            if (e instanceof Item) {
                e.remove();
            }
        });
    }
}

