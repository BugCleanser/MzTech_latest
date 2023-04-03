/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_12_R1.ItemStack
 *  net.minecraft.server.v1_12_R1.TileEntityFurnace
 *  net.minecraft.server.v1_16_R3.Container
 *  net.minecraft.server.v1_16_R3.IChatBaseComponent
 *  net.minecraft.server.v1_16_R3.ItemStack
 *  net.minecraft.server.v1_16_R3.PlayerInventory
 *  net.minecraft.server.v1_16_R3.TileEntityFurnace
 *  org.bukkit.block.Furnace
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.util.ItemStackBuilder;
import net.minecraft.server.v1_12_R1.TileEntityFurnace;
import net.minecraft.server.v1_16_R3.Container;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PlayerInventory;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

public class FurnaceUtil {
    public static int getFuelTime(ItemStack fuel) {
        try {
            return TileEntityFurnace.fuelTime((net.minecraft.server.v1_12_R1.ItemStack)((net.minecraft.server.v1_12_R1.ItemStack)MzTech.castBypassCheck(NBT.asNMSCopy(fuel))));
        }
        catch (Throwable e) {
            return new net.minecraft.server.v1_16_R3.TileEntityFurnace(null, null){

                protected IChatBaseComponent getContainerName() {
                    return null;
                }

                protected Container createContainer(int var1, PlayerInventory var2) {
                    return null;
                }

                public int fuelTime(net.minecraft.server.v1_16_R3.ItemStack itemstack) {
                    return super.fuelTime(itemstack);
                }
            }.fuelTime((net.minecraft.server.v1_16_R3.ItemStack)MzTech.castBypassCheck(NBT.asNMSCopy(fuel)));
        }
    }

    public static void ignite(Furnace f) {
        int time;
        ItemStack fuel;
        if (f.getBurnTime() <= 0 && !ItemStackBuilder.isEmpty(fuel = f.getInventory().getFuel()) && (time = FurnaceUtil.getFuelTime(fuel)) > 0) {
            f.setBurnTime((short)time);
            fuel.setAmount(fuel.getAmount() - 1);
            f.update();
            f.getInventory().setFuel(fuel);
        }
    }
}

