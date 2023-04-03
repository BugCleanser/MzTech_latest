/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.AbstractArrow
 *  org.bukkit.entity.AbstractArrow$PickupStatus
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Arrow$PickupStatus
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import mz.tech.MzTech;
import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public interface ShootConsecutively
extends ILuckyBowEffect {
    public static CShootConsecutively newInstance() {
        return new CShootConsecutively();
    }

    @Override
    default public void onShoot(final LivingEntity shooter, Arrow arrow) {
        ILuckyBowEffect.super.onShoot(shooter, arrow);
        final Vector offset = arrow.getLocation().subtract(shooter.getLocation()).toVector();
        final Vector velocity = arrow.getVelocity();
        final int[] i = new int[1];
        new BukkitRunnable(){

            public void run() {
                if (shooter.isDead()) {
                    this.cancel();
                    return;
                }
                shooter.getWorld().spawn(shooter.getLocation().add(offset), Arrow.class, a -> {
                    a.setShooter((ProjectileSource)shooter);
                    a.setKnockbackStrength(1);
                    a.setVelocity(velocity);
                    a.setCritical(true);
                    a.setBounce(true);
                    try {
                        a.setPickupStatus(Arrow.PickupStatus.CREATIVE_ONLY);
                    }
                    catch (Throwable e) {
                        ((AbstractArrow)a).setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                    }
                });
                shooter.getWorld().playSound(shooter.getEyeLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
                i[0] = i[0] + 1;
                if (i[0] >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MzTech.instance, 2L, 2L);
    }

    public static class CShootConsecutively
    extends LuckyBowHitEffect
    implements ShootConsecutively {
    }
}

