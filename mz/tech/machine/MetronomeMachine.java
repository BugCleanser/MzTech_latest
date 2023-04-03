/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.material.Lever
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.Lever;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class MetronomeMachine
extends MzTechMachine
implements Trigger {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 20L, 20L, () -> MetronomeMachine.toggleAll());
    }

    public static void toggleAll() {
        MzTechMachine.forEach(MetronomeMachine.class, metronome -> metronome.toggle());
    }

    @Override
    public boolean toggle() {
        if (this.getBlock().getType() == Material.LEVER) {
            try {
                Object blockData = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(Block.class, "getBlockData", new Class[0]), this.getBlock(), new Object[0]);
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(Class.forName("org.bukkit.block.data.Powerable"), " setPowered", Boolean.TYPE), blockData, (Boolean)ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(Class.forName("org.bukkit.block.data.Powerable"), " isPowered", new Class[0]), blockData, new Object[0]) == false);
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(Block.class, "setBlockData", Class.forName("org.bukkit.block.data.BlockData"), Boolean.TYPE), this.getBlock(), blockData, true);
            }
            catch (Throwable e) {
                BlockState state = this.getBlock().getState();
                Lever data = (Lever)state.getData();
                data.setPowered(!data.isPowered());
                state.setData((MaterialData)data);
                state.update();
            }
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "\u8282\u62cd\u5668";
    }
}

