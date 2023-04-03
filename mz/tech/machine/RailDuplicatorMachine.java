/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.Hopper
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.TaskUtil;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class RailDuplicatorMachine
extends MzTechMachine
implements Trigger {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 20L, 20L, () -> RailDuplicatorMachine.toggleAll());
    }

    @Override
    public String getType() {
        return "\u94c1\u8f68\u590d\u5236\u673a";
    }

    @Override
    public boolean toggle() {
        boolean[] rb = new boolean[1];
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            Block rail = this.getBlock().getLocation().add(0.0, 1.0, 0.0).getBlock();
            switch (rail.getType().name()) {
                case "ORANGE_CARPET": 
                case "BLACK_CARPET": 
                case "LIME_CARPET": 
                case "YELLOW_CARPET": 
                case "PINK_CARPET": 
                case "WHITE_CARPET": 
                case "RED_CARPET": 
                case "DETECTOR_RAIL": 
                case "RAIL": 
                case "RAILS": 
                case "LIGHT_BLUE_CARPET": 
                case "BROWN_CARPET": 
                case "LIGHT_GRAY_CARPET": 
                case "PURPLE_CARPET": 
                case "BLUE_CARPET": 
                case "CYAN_CARPET": 
                case "MAGENTA_CARPET": 
                case "POWERED_RAIL": 
                case "GREEN_CARPET": 
                case "GRAY_CARPET": 
                case "ACTIVATOR_RAIL": 
                case "CARPET": {
                    ItemStack is = new ItemStack(rail.getType(), 1, (short)rail.getData());
                    is.setItemMeta(null);
                    ((Hopper)this.getBlock().getState()).getInventory().addItem(new ItemStack[]{is});
                    this.getBlock().getLocation().getWorld().playSound(this.getBlock().getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
                    blArray[0] = true;
                }
            }
        });
        return rb[0];
    }

    public static void toggleAll() {
        MzTechMachine.forEach(RailDuplicatorMachine.class, metronome -> metronome.toggle());
    }
}

