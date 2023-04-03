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

public interface ShootToGoBack
extends ILuckyBowEffect {
    public static CShootToGoBack newInstance() {
        return new CShootToGoBack();
    }

    @Override
    default public void onShoot(LivingEntity shooter, Arrow arrow) {
        ILuckyBowEffect.super.onShoot(shooter, arrow);
        shooter.setVelocity(shooter.getVelocity().subtract(shooter.getEyeLocation().getDirection().setY(0).multiply(2)).add(new Vector(0, 1, 0)));
    }

    public static class CShootToGoBack
    extends LuckyBowShootEffect
    implements ShootToGoBack {
    }
}

