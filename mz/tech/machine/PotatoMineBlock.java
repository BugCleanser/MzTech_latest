/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.entity.Zombie
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;

public class PotatoMineBlock
extends MzTechMachine {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 20L, 20L, () -> MzTechMachine.forEach(PotatoMineBlock.class, m -> {
            if (m.getBlock().getState().getRawData() < 7) {
                return;
            }
            Entity e = MzTech.getNearest(m.getBlock(), (List<Entity>)Lists.newArrayList(MzTech.getEntities(m.getBlock(), 1.0, 1.0, 1.0).stream().filter(en -> en instanceof Zombie).iterator()));
            if (e != null) {
                m.getBlock().setType(Material.AIR);
                m.remove();
                m.getBlock().getWorld().spawn(MzTech.getBlockCentre(m.getBlock()), TNTPrimed.class, t -> t.setFuseTicks(0));
            }
        }));
    }

    @Override
    public String getType() {
        return "\u571f\u8c46\u5730\u96f7";
    }
}

