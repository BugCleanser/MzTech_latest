/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.ArrowCarryingDrop;
import mz.tech.luckyEffect.LuckyBowHitEffect;
import mz.tech.util.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HitToSetIce
extends ArrowCarryingDrop {
    public static CHitToSetIce newInstance() {
        return new CHitToSetIce();
    }

    @Override
    default public void onHit(Arrow arrow, Location location) {
        ArrowCarryingDrop.super.onHit(arrow, location);
        int x = (int)(location.getX() - 1.5);
        while (x < (int)(location.getX() + 2.5)) {
            int y = location.getBlockY();
            while (y >= 0 && y < 256 && y <= location.getBlockY() + 3) {
                int z = (int)(location.getZ() - 1.5);
                while (z < (int)(location.getZ() + 2.5)) {
                    Block b = new Location(location.getWorld(), (double)x, (double)y, (double)z).getBlock();
                    if (b.isEmpty() && new PlayerUtil((Player)arrow.getShooter()).canBuildIgnoreAntiCheat(b)) {
                        b.setType(Material.ICE);
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
    }

    @Override
    default public ItemStack getItemStack() {
        return new ItemStack(Material.ICE);
    }

    public static class CHitToSetIce
    extends LuckyBowHitEffect
    implements HitToSetIce {
    }
}

