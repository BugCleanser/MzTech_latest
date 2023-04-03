/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockExplodeEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package mz.tech;

import java.util.List;
import mz.tech.MzTech;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NoExplode
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    void onEntityExplode(EntityExplodeEvent event) {
        if (((List)MzTech.instance.getConfig().get("noExplodeWorlds")).contains(event.getLocation().getWorld().getName())) {
            Block[] blockArray = event.blockList().toArray(new Block[0]);
            int n = blockArray.length;
            int n2 = 0;
            while (n2 < n) {
                Block b = blockArray[n2];
                if (b.getType() != Material.TNT) {
                    event.blockList().remove(b);
                }
                ++n2;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    void onBlockExplode(BlockExplodeEvent event) {
        if (((List)MzTech.instance.getConfig().get("noExplodeWorlds")).contains(event.getBlock().getWorld().getName())) {
            Block[] blockArray = event.blockList().toArray(new Block[0]);
            int n = blockArray.length;
            int n2 = 0;
            while (n2 < n) {
                Block b = blockArray[n2];
                if (b.getType() != Material.TNT) {
                    event.blockList().remove(b);
                }
                ++n2;
            }
        }
    }
}

