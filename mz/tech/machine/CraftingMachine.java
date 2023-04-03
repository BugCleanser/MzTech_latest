/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.plugin.Plugin;

public interface CraftingMachine {
    public static final Map<CraftingMachine, Integer> craftingMachines = new HashMap<CraftingMachine, Integer>(){
        private static final long serialVersionUID = 173945101012257670L;
        {
            TaskUtil.runTaskTimer((Plugin)MzTech.instance, 1L, 1L, () -> {
                if (!craftingMachines.isEmpty()) {
                    Maps.newHashMap(craftingMachines).forEach((m, t) -> {
                        craftingMachines.put((CraftingMachine)m, t - 1);
                        if (t == 1) {
                            craftingMachines.remove(m);
                            m.onCraftingFinish();
                        }
                        m.onScheduleUpdate(t - 1);
                    });
                }
            });
        }
    };

    default public void remove() {
        this.breakCrafting();
    }

    default public void startCrafting(int timeTotal) {
        craftingMachines.put(this, timeTotal);
        this.onScheduleUpdate(timeTotal);
    }

    default public void breakCrafting() {
        if (craftingMachines.containsKey(this)) {
            craftingMachines.remove(this);
        }
        this.onScheduleUpdate(0);
    }

    default public int getSchedule() {
        return craftingMachines.getOrDefault(this, 0);
    }

    public void onCraftingFinish();

    public void onScheduleUpdate(int var1);

    @Nullable
    default public MzTechMachine load(NBT nbt) {
        if (nbt.hasKey("remainingTime")) {
            craftingMachines.put(this, nbt.getInt("remainingTime"));
        }
        return null;
    }

    @Nullable
    default public NBT save(NBT nbt) {
        if (craftingMachines.containsKey(this)) {
            nbt.set("remainingTime", this.getSchedule());
        }
        return null;
    }
}

