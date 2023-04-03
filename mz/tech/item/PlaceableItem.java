/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item;

import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.PlacedItem;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public abstract class PlaceableItem
extends MzTechItem {
    public PlaceableItem(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new PlacedItem(block, this.getTypeName());
    }
}

