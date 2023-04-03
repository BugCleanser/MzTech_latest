/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.item.pvz.MelonPult;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class MelonPultBlock
extends MzTechMachine {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 20L, 20L, () -> MzTechMachine.forEach(MelonPultBlock.class, m -> {
            Entity e = MzTech.getNearest(m.getBlock(), (List<Entity>)Lists.newArrayList(MzTech.getEntities(m.getBlock(), 10.0, 3.0, 10.0).stream().filter(en -> en instanceof Zombie).iterator()));
            if (e != null) {
                FallingBlock f = m.getBlock().getWorld().spawnFallingBlock(MzTech.getBlockCentre(m.getBlock()), new MaterialData(new MelonPult().getType()));
                f.setHurtEntities(true);
                f.setFallDistance(3.0f);
                f.setDropItem(false);
                f.setVelocity(e.getLocation().subtract(MzTech.getBlockCentre(m.getBlock())).multiply(0.045).add(new Vector(0.0, MzTech.getDistance(MzTech.getBlockCentre(m.getBlock()), e.getLocation()) * 0.03 + 0.2, 0.0)).toVector());
            }
        }));
    }

    @Override
    public String getType() {
        return "\u897f\u74dc\u6295\u624b";
    }

    @Override
    public boolean onPlace(BlockPlaceEvent event) {
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            if (this.isLogged()) {
                this.getBlock().setType(Material.MELON_STEM);
                BlockState s = this.getBlock().getState();
                s.setRawData((byte)7);
                s.update();
            }
        });
        return true;
    }

    @Override
    public boolean onPhysics(BlockPhysicsEvent event) {
        return this.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().isEmpty();
    }
}

