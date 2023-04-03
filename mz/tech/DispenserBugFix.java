/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.BlockFace
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockDispenseEvent
 *  org.bukkit.material.DirectionalContainer
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import mz.tech.MzTech;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.plugin.Plugin;

public class DispenserBugFix {
    public static void start() {
        if (MzTech.MCVersion.startsWith("v1_11_R") || MzTech.MCVersion.startsWith("v1_12_R")) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler(priority=EventPriority.LOWEST)
                public void onBlockDispense(BlockDispenseEvent event) {
                    BlockFace facing;
                    if (event.getBlock().getType() == Material.DISPENSER && ((facing = ((DirectionalContainer)event.getBlock().getState().getData()).getFacing()) == BlockFace.UP && event.getBlock().getY() == 255 || facing == BlockFace.DOWN && event.getBlock().getY() == 0) && event.getItem().getType().name().endsWith("_SHULKER_BOX")) {
                        event.setCancelled(true);
                    }
                }
            }, (Plugin)MzTech.instance);
        }
    }
}

