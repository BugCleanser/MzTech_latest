/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.BauxiteBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;

public class Bauxite
extends MzTechItem {
    static {
        new OreGenerator(100, 50, new Bauxite()).reg();
    }

    public Bauxite() {
        super(new ItemStackBuilder("coarse_dirt", "dirt", 1, 1).setLocName("\u00a76\u94dd\u571f\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u94dd\u571f\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new BauxiteBlock().setBlock(block);
    }
}

