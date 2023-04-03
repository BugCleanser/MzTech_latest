/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface ArrowCarryingEntity
extends ILuckyBowEffect {
    public Entity spawnEntity(Location var1);

    @Override
    default public void onShoot(LivingEntity entity, Arrow arrow) {
        arrow.addPassenger(this.spawnEntity(arrow.getLocation()));
    }
}

