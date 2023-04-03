/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ArrowCarryingDrop;
import mz.tech.luckyEffect.ArrowPortalEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ArrowCarryingEnderPearl
extends LuckyBowHitEffect
implements ArrowPortalEffect,
ArrowCarryingDrop {
    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.ENDER_PEARL);
    }

    @Override
    public void onTick(Arrow arrow) {
        ArrowPortalEffect.super.onTick(arrow);
    }

    @Override
    public void toggle(Arrow arrow, Location location) {
        super.toggle(arrow, location);
        location.setDirection(((LivingEntity)arrow.getShooter()).getEyeLocation().getDirection());
        ((Entity)arrow.getShooter()).teleport(location);
    }
}

