/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;

public interface ArrowRebound
extends ILuckyBowEffect {
    public static CArrowRebound newInstance() {
        return new CArrowRebound();
    }

    @Override
    default public void onHit(Arrow arrow, Block block) {
        ILuckyBowEffect.super.onHit(arrow, block);
        if (arrow.getWorld() == ((Entity)arrow.getShooter()).getWorld()) {
            arrow.teleport(arrow.getLocation().add(0.0, 1.0, 0.0));
            arrow.setVelocity(((Entity)arrow.getShooter()).getLocation().subtract(arrow.getLocation()).toVector());
        }
    }

    public static class CArrowRebound
    extends LuckyBowHitEffect
    implements ArrowRebound {
    }
}

