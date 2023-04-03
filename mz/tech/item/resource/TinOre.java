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
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.TinOreBlock;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;

public class TinOre
extends MzTechItem {
    static {
        new OreGenerator(30, 10, new TinOre()).reg();
    }

    public TinOre() {
        super(new ItemStackBuilder(ItemStackBuilder.andesite).setLocName("\u00a7f\u9521\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u9521\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new TinOreBlock().setBlock(block);
    }
}

