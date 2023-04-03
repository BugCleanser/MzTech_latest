/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ArrowCarryingDrop;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class LuckyBowFlyEffect
extends LuckyBowHitEffect
implements ArrowCarryingDrop {
    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.FEATHER);
    }

    @Override
    public void toggle(Arrow arrow, Location location) {
        location.getWorld().getNearbyEntities(location, 2.0, 2.0, 2.0).forEach(entity -> {
            if (entity instanceof LivingEntity) {
                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent((Entity)arrow.getShooter(), entity, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, 1.0);
                Bukkit.getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    entity.setVelocity(entity.getVelocity().add(new Vector(0, 2, 0)));
                }
            }
        });
        super.toggle(arrow, location);
    }

    @Override
    public void toggle(Arrow arrow, Entity entity) {
        entity.setVelocity(entity.getVelocity().add(new Vector(0, 3, 0)));
        super.toggle(arrow, entity.getLocation());
    }
}

