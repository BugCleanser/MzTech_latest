/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Particle
 *  org.bukkit.entity.Arrow
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;

public interface ArrowPortalEffect
extends ILuckyBowEffect {
    @Override
    default public void onTick(Arrow arrow) {
        arrow.getWorld().spawnParticle(Particle.PORTAL, arrow.getLocation(), 5);
        ILuckyBowEffect.super.onTick(arrow);
    }
}

