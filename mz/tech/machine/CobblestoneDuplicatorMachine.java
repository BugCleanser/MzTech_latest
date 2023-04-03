/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Dispenser
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.Dispenser
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class CobblestoneDuplicatorMachine
extends MzTechMachine {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 40L, 40L, () -> MzTechMachine.forEach(CobblestoneDuplicatorMachine.class, machine -> {
            BlockState state = machine.getBlock().getState();
            if (state instanceof org.bukkit.block.Dispenser) {
                ((org.bukkit.block.Dispenser)state).getInventory().addItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE)});
                machine.getBlock().getWorld().playSound(machine.getBlock().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f);
            }
        }));
    }

    @Override
    public String getType() {
        return "\u5237\u77f3\u673a";
    }

    @Override
    public boolean onPlace(BlockPlaceEvent event) {
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            BlockState state = this.getBlock().getState();
            if (state instanceof org.bukkit.block.Dispenser) {
                MaterialData data = ((org.bukkit.block.Dispenser)state).getData();
                ((Dispenser)data).setFacingDirection(BlockFace.DOWN);
                state.setData(data);
                state.update();
            }
        });
        return super.onPlace(event);
    }
}

