/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Arrow
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ArrowCarryingDrop;
import mz.tech.luckyEffect.ArrowFireEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

public interface ArrowWithLightning
extends ArrowCarryingDrop,
ArrowFireEffect {
    @Override
    default public ItemStack getItemStack() {
        return new ItemStack(Material.BLAZE_ROD);
    }

    @Override
    default public void onTick(Arrow arrow) {
        ArrowFireEffect.super.onTick(arrow);
    }

    @Override
    default public void onHit(Arrow arrow, Location location) {
        ArrowCarryingDrop.super.onHit(arrow, location);
        location.getWorld().strikeLightning(location);
    }

    public static CArrowWithLightning newInstance() {
        return new CArrowWithLightning();
    }

    public static class CArrowWithLightning
    extends LuckyBowHitEffect
    implements ArrowWithLightning {
    }
}

