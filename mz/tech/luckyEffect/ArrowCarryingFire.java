/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockIgniteEvent
 *  org.bukkit.event.block.BlockIgniteEvent$IgniteCause
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ArrowCarryingDrop;
import mz.tech.luckyEffect.ArrowFireEffect;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

public interface ArrowCarryingFire
extends ArrowCarryingDrop,
ArrowFireEffect {
    @Override
    default public ItemStack getItemStack() {
        return new ItemStackBuilder("fireball", 0, "fire_charge", 1).build();
    }

    @Override
    default public void onTick(Arrow arrow) {
        ArrowFireEffect.super.onTick(arrow);
    }

    @Override
    default public void onHit(Arrow arrow, Location location) {
        Block b;
        if (location.getBlockY() >= 0 && (b = location.getBlock()).isEmpty()) {
            PlayerBucketEmptyEvent event = new PlayerBucketEmptyEvent((Player)arrow.getShooter(), location.clone().subtract(0.0, 1.0, 0.0).getBlock(), BlockFace.UP, Material.LAVA_BUCKET, new ItemStack(Material.LAVA_BUCKET));
            Bukkit.getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                b.setType(Material.LAVA);
            }
        }
        int x = location.getBlockX() - 2;
        while (x <= location.getBlockX() + 2) {
            int y = location.getBlockY() - 2;
            while (y > 0 && y <= location.getBlockY() + 2) {
                int z = location.getBlockZ() - 2;
                while (z <= location.getBlockZ() + 2) {
                    Block b2 = new Location(location.getWorld(), (double)x, (double)y, (double)z).getBlock();
                    Block d = new Location(location.getWorld(), (double)x, (double)(y - 1), (double)z).getBlock();
                    if (b2.getType() == Material.AIR && !d.isEmpty()) {
                        BlockIgniteEvent event = new BlockIgniteEvent(b2, BlockIgniteEvent.IgniteCause.FIREBALL, (Entity)arrow.getShooter());
                        Bukkit.getPluginManager().callEvent((Event)event);
                        if (!event.isCancelled()) {
                            b2.setType(Material.FIRE);
                        }
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        ArrowCarryingDrop.super.onHit(arrow, location);
    }

    public static ArrowCarryingFireEvent newInstance() {
        return new ArrowCarryingFireEvent();
    }

    public static class ArrowCarryingFireEvent
    extends LuckyBowHitEffect
    implements ArrowCarryingFire {
        @Override
        public void toggle(Arrow arrow, Location location) {
        }
    }
}

