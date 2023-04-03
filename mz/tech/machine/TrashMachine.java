/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Hopper
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import mz.tech.MzTech;
import mz.tech.machine.InventoryMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.TaskUtil;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class TrashMachine
extends MzTechMachine
implements InventoryMachine,
Trigger {
    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 100L, 100L, () -> TrashMachine.toggleAll());
    }

    @Override
    public Inventory getInventory() {
        BlockState dropper = this.getBlock().getState();
        if (dropper instanceof Hopper) {
            return ((Hopper)dropper).getInventory();
        }
        return null;
    }

    @Override
    public void setInventory(Inventory inv) {
        Hopper dropper = (Hopper)this.getBlock().getState();
        dropper.getInventory().setContents(inv.getContents());
    }

    @Override
    public String getType() {
        return "\u5783\u573e\u6876";
    }

    public static void toggleAll() {
        MzTechMachine.forEach(TrashMachine.class, trash -> trash.toggle());
    }

    @Override
    public boolean toggle() {
        this.getInventory().clear();
        return true;
    }
}

