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
import mz.tech.machine.MagnetiteBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Magnetite
extends MzTechItem {
    static {
        new OreGenerator(70, 10, new Magnetite()).reg();
    }

    public Magnetite() {
        super(new ItemStackBuilder(Material.COAL_ORE).setLocName("\u00a74\u78c1\u94c1\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u78c1\u94c1\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new MagnetiteBlock().setBlock(block);
    }
}

