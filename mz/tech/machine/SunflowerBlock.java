/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.item.pvz.Sunshine;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SunflowerBlock
extends MzTechMachine {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 500L, 500L, () -> MzTechMachine.forEach(SunflowerBlock.class, m -> MzTech.dropItemStack(m.getBlock(), (ItemStack)new Sunshine().setCount(25))));
    }

    @Override
    public String getType() {
        return "\u5411\u65e5\u8475";
    }
}

