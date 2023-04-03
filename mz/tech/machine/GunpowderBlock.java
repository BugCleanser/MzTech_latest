/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine;

import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.event.MachineBreakEvent;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GunpowderBlock
extends MzTechMachine
implements Trigger {
    @Override
    public String getType() {
        return "\u706b\u836f\u5757";
    }

    @Override
    public boolean onPhysics(BlockPhysicsEvent event) {
        if (this.getBlock().getBlockPower() > 0) {
            this.toggle();
        }
        Location l = this.getBlock().getLocation();
        l.add(0.0, 1.0, 0.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        l.add(0.0, -2.0, 0.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        l.add(1.0, 1.0, 0.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        l.add(-2.0, 0.0, 0.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        l.add(1.0, 0.0, 1.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        l.add(0.0, 0.0, -2.0);
        try {
            if (l.getBlock().getType() == Material.FIRE || l.getBlock().getType() == Material.LAVA || l.getBlock().getType() == Material.STATIONARY_LAVA) {
                this.toggle();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return true;
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return true;
        }
        if (event.getItem().getType() == Material.FLINT_AND_STEEL || event.getItem().getType() == ItemStackBuilder.fireCharge.getType()) {
            this.toggle();
        }
        return true;
    }

    @Override
    public boolean toggle() {
        if (!this.isLogged()) {
            return false;
        }
        this.remove();
        this.getBlock().setType(Material.AIR);
        this.getBlock().getWorld().spawn(MzTech.getBlockCentre(this.getBlock()), TNTPrimed.class, t -> t.setFuseTicks(0));
        return true;
    }

    @Override
    public List<ItemStack> getDropNaturally(MachineBreakEvent event) {
        if (event.cause == MachineBreakEvent.Cause.BLOCK_EXPLODE || event.cause == MachineBreakEvent.Cause.ENTITY_EXPLODE) {
            this.toggle();
            this.add();
            return new ArrayList<ItemStack>();
        }
        return super.getDropNaturally(event);
    }
}

