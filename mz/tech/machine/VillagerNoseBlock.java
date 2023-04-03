/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Item
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class VillagerNoseBlock
extends MzTechMachine {
    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            this.remove();
            Lists.newArrayList((Object[])new ItemStack[]{new ItemStack(Material.EMERALD), new ItemStack(Material.SLIME_BALL)}).forEach(drop -> {
                Item item = MzTech.dropItemStack(this.getBlock(), drop);
            });
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.getBlock().setType(Material.AIR));
            return false;
        }
        return super.onRightClick(event);
    }

    @Override
    public String getType() {
        return "\u6751\u6c11\u7684\u9f3b\u5b50";
    }
}

