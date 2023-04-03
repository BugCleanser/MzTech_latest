/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 */
package mz.tech.machine.generator;

import mz.tech.machine.generator.Generator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class BlockGenerator
extends Generator {
    ItemStack is;

    public BlockGenerator(int chance, int numEveryChunk, ItemStack is, MaterialData ... replaceables) {
        super(chance, numEveryChunk, replaceables);
        this.is = is;
    }

    @Override
    public void gen(Block b) {
        b.setType(Material.AIR);
        ItemStackBuilder.toBlock(this.is, b);
    }
}

