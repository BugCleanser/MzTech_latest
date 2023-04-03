/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class LuckyBowShootEffect {
    public void toggle(LivingEntity entity, Arrow arrow) {
        if (this instanceof ILuckyBowEffect) {
            ((ILuckyBowEffect)((Object)this)).onShoot(entity, arrow);
        }
    }

    public LuckyBowShootEffect fromLuckyEffect(final LuckyEffect raw) {
        return new LuckyBowShootEffect(){

            @Override
            public void toggle(LivingEntity player, Arrow arrow) {
                if (player instanceof Player) {
                    raw.toggle((Player)player, arrow.getLocation());
                }
            }
        };
    }
}

