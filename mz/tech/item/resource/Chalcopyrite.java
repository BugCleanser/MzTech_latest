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
import mz.tech.machine.ChalcopyriteBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Chalcopyrite
extends MzTechItem {
    static {
        new OreGenerator(70, 20, new Chalcopyrite()).reg();
    }

    public Chalcopyrite() {
        super(new ItemStackBuilder(Material.GOLD_ORE).setLocName("\u00a7e\u9ec4\u94dc\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u9ec4\u94dc\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new ChalcopyriteBlock().setBlock(block);
    }
}

