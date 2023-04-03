/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyBowShootEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public interface ShootUp
extends ILuckyBowEffect {
    @Override
    default public void onShoot(LivingEntity shooter, Arrow arrow) {
        ILuckyBowEffect.super.onShoot(shooter, arrow);
        Vector v = arrow.getVelocity();
        arrow.setVelocity(new Vector(0.0, Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ()), 0.0));
    }

    public static CShootUp newInstance() {
        return new CShootUp();
    }

    public static class CShootUp
    extends LuckyBowShootEffect
    implements ShootUp {
    }
}

