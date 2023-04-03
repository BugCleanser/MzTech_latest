/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.event.MachineBreakEvent;
import mz.tech.luckyEffect.LuckyEffect;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class LuckyBlock
extends MzTechMachine {
    @Override
    public String getType() {
        return "\u5e78\u8fd0\u65b9\u5757";
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        if (player == null) {
            MzTech.dropItemStack(this.getBlock(), this.getDropAccurate(null).get(0));
        } else if (drop && !silkTouch) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> {
                while (true) {
                    try {
                        MzTech.randKey(LuckyEffect.effects).forEach(effect -> effect.toggle(player, MzTech.getBlockCentre(this.getBlock())));
                    }
                    catch (NullPointerException nullPointerException) {
                        continue;
                    }
                    break;
                }
            });
        }
    }

    @Override
    public List<ItemStack> getDropNaturally(MachineBreakEvent event) {
        return Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.AIR)});
    }
}

