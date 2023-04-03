/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyBowShootEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface ShootToRide
extends ILuckyBowEffect {
    @Override
    default public void onShoot(LivingEntity shooter, Arrow arrow) {
        ILuckyBowEffect.super.onShoot(shooter, arrow);
        arrow.addPassenger((Entity)shooter);
    }

    public static CShootToRide newInstance() {
        return new CShootToRide();
    }

    public static class CShootToRide
    extends LuckyBowShootEffect
    implements ShootToRide {
    }
}

