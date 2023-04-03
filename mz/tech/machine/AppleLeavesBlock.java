/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.event.MachineBreakEvent;
import mz.tech.item.sundry.AppleSapling;
import mz.tech.machine.MzTechMachine;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AppleLeavesBlock
extends MzTechMachine {
    @Override
    public String getType() {
        return "\u82f9\u679c\u6811\u53f6";
    }

    @Override
    public List<ItemStack> getDropNaturally(MachineBreakEvent event) {
        ArrayList rl = Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.APPLE, MzTech.rand.nextInt(3) + 1)});
        if (MzTech.rand.nextInt(100) < 5) {
            rl.add(new AppleSapling());
        }
        return rl;
    }

    @Override
    public List<ItemStack> getDrop(ItemStack tool) {
        if (tool != null && tool.getType() == Material.SHEARS) {
            return this.getDropAccurate(null);
        }
        return super.getDrop(tool);
    }
}

