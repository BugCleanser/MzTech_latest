/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.luckyEffect;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.luckyEffect.ILuckyBowEffect;
import mz.tech.luckyEffect.LuckyEffect;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;

public abstract class LuckyBowHitEffect {
    static Map<Arrow, LuckyBowHitEffect> arrows = new HashMap<Arrow, LuckyBowHitEffect>();

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.HIGHEST)
            public void onProjectileHit(ProjectileHitEvent event) {
                if (arrows.containsKey(event.getEntity())) {
                    if (event.getHitEntity() != null) {
                        if (arrows.get(event.getEntity()) instanceof ILuckyBowEffect) {
                            ((ILuckyBowEffect)((Object)arrows.get(event.getEntity()))).onHit((Arrow)event.getEntity(), event.getHitEntity());
                        }
                        arrows.get(event.getEntity()).toggle((Arrow)event.getEntity(), event.getHitEntity());
                    }
                    if (event.getHitBlock() != null) {
                        if (arrows.get(event.getEntity()) instanceof ILuckyBowEffect) {
                            ((ILuckyBowEffect)((Object)arrows.get(event.getEntity()))).onHit((Arrow)event.getEntity(), event.getHitBlock());
                        }
                        arrows.get(event.getEntity()).toggle((Arrow)event.getEntity(), event.getHitBlock());
                    }
                }
            }
        }, (Plugin)MzTech.instance);
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 1L, 1L, () -> {
            if (!arrows.isEmpty()) {
                Maps.newHashMap(arrows).forEach((arrow, effect) -> {
                    if (arrow.isDead() || arrow.isOnGround()) {
                        arrows.remove(arrow);
                    } else {
                        effect.tick((Arrow)arrow);
                        if (effect instanceof ILuckyBowEffect) {
                            ((ILuckyBowEffect)((Object)effect)).onTick((Arrow)arrow);
                        }
                    }
                });
            }
        });
    }

    public void tick(Arrow arrow) {
    }

    public void toggle(Arrow arrow, Entity entity) {
        this.toggle(arrow, entity.getLocation());
    }

    protected void toggle(Arrow arrow, Block block) {
        this.toggle(arrow, arrow.getLocation());
    }

    public void toggle(Arrow arrow, Location location) {
        arrow.remove();
    }

    public static LuckyBowHitEffect fromRawEffect(final LuckyEffect raw) {
        return new LuckyBowHitEffect(){

            @Override
            public void toggle(Arrow arrow, Location location) {
                raw.toggle((Player)arrow.getShooter(), location);
            }
        };
    }

    public void bind(Arrow arrow) {
        arrows.put(arrow, this);
        if (this instanceof ILuckyBowEffect) {
            ((ILuckyBowEffect)((Object)this)).onShoot((LivingEntity)arrow.getShooter(), arrow);
        }
    }
}

