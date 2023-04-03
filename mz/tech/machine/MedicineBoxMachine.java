/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import mz.tech.MzTech;
import mz.tech.event.MoveItemEvent;
import mz.tech.item.sundry.Medicine;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.StoreableMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.Slot;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MedicineBoxMachine
extends StoreableMachine
implements Trigger {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 200L, 200L, () -> MzTechMachine.forEach(MedicineBoxMachine.class, machine -> machine.toggle()));
    }

    @Override
    public MzTechMachine setBlock(Block block) {
        super.setBlock(block);
        int i = 0;
        while (i < 27) {
            if (i != 13) {
                this.getInventory().setItem(i, new ItemStackBuilder("stained_glass_pane", 6, "pink_stained_glass_pane", 1).setLocName("\u00a70").build());
            }
            ++i;
        }
        return this;
    }

    @Override
    public String getType() {
        return "\u836f\u54c1\u7bb1";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public String getTitle() {
        return "\u00a7c\u836f\u54c1\u7bb1";
    }

    @Override
    public void onMoveItem(MoveItemEvent event) {
        Lists.newArrayList((Object[])new Slot[]{event.getFrom(), event.getTo()}).forEach(slot -> {
            if (this.getInventory().equals(slot.getInventory()) && (slot.getSlot() != 13 || slot == event.getTo())) {
                event.setCancelled(true);
            }
        });
        super.onMoveItem(event);
    }

    @Override
    public boolean toggle() {
        if (this.getInventory().getItem(13) == null || this.getInventory().getItem(13).getType() == Material.AIR) {
            this.getInventory().setItem(13, (ItemStack)new Medicine());
            return true;
        }
        return false;
    }
}

