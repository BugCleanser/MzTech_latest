/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 */
package mz.tech.luckyEffect;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface ILuckyBowEffect {
    default public void onShoot(LivingEntity shooter, Arrow arrow) {
    }

    default public void onTick(Arrow arrow) {
    }

    default public void onHit(Arrow arrow, Location location) {
    }

    default public void onHit(Arrow arrow, Entity entity) {
        this.onHit(arrow, entity.getLocation());
    }

    default public void onHit(Arrow arrow, Block block) {
        this.onHit(arrow, arrow.getLocation());
    }
}

