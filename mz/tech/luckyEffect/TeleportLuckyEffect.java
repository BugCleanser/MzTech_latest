/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package mz.tech.luckyEffect;

import mz.tech.luckyEffect.LuckyEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TeleportLuckyEffect
extends LuckyEffect {
    public Vector loc;

    public TeleportLuckyEffect(Vector loc) {
        this.loc = loc;
    }

    public TeleportLuckyEffect(int x, int y, int z) {
        this.loc = new Vector((double)x + 0.5, (double)y, (double)z + 0.5);
    }

    @Override
    public void toggle(Player player, Location loc) {
        player.teleport(player.getLocation().getBlock().getLocation().setDirection(player.getEyeLocation().getDirection()).add(this.loc));
    }

    public static void regAll() {
    }
}

