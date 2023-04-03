/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.BlockShowBoxBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockShowBox
extends MzTechItem {
    public BlockShowBox() {
        super(new ItemStackBuilder(Material.GLASS).setLocName("\u00a76\u65b9\u5757\u5c55\u793a\u6846").build());
    }

    @Override
    public String getTypeName() {
        return "\u65b9\u5757\u5c55\u793a\u6846";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new BlockShowBoxBlock().setBlock(block);
    }
}

