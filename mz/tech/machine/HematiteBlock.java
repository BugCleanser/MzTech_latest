/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.Plugin;

public class HematiteBlock
extends MzTechMachine {
    @Override
    public String getType() {
        return "\u8d64\u94c1\u77ff";
    }

    @Override
    public boolean onPhysics(BlockPhysicsEvent event) {
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            if (this.getBlock().getType() == Material.REDSTONE_ORE && this.isLogged()) {
                this.getBlock().setType(Material.REDSTONE_ORE);
            }
        });
        return false;
    }
}

