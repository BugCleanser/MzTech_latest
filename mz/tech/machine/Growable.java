/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Player
 *  org.bukkit.material.MaterialData
 */
package mz.tech.machine;

import java.util.List;
import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public abstract class Growable
extends MzTechMachine {
    @Override
    public boolean onGrow(List<BlockState> blocks, boolean byBonemeal, Player player) {
        this.remove();
        blocks.forEach(b -> {
            Class<? extends MzTechMachine> clazz = this.getBlockClass(b.getData());
            if (clazz != null) {
                TaskUtil.throwRuntime(() -> ((MzTechMachine)MzTech.unsafe.allocateInstance(clazz)).setBlock(b.getBlock()).add());
            }
        });
        super.onGrow(blocks, byBonemeal, player);
        return true;
    }

    public abstract Class<? extends MzTechMachine> getBlockClass(MaterialData var1);
}

