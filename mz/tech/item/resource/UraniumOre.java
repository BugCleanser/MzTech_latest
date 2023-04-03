/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.UraniumOreBlock;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class UraniumOre
extends MzTechItem {
    static {
        new OreGenerator(70, 10, new UraniumOre()).reg();
    }

    public UraniumOre() {
        super(new ItemStackBuilder(Material.EMERALD_ORE).setLocName("\u00a72\u94c0\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u94c0\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new UraniumOreBlock().setBlock(block);
    }
}

