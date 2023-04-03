/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

class Breaking {
    public Block block;
    public double when;
    public double total;
    public List<ItemStack> drops;

    public Breaking(Block block, int total, List<ItemStack> drops) {
        this.block = block;
        this.when = 0.0;
        this.total = total;
        this.drops = drops;
    }
}

