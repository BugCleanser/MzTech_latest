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
import mz.tech.machine.ChalcociteBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.generator.OreGenerator;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Chalcocite
extends MzTechItem {
    static {
        new OreGenerator(70, 20, new Chalcocite()).reg();
    }

    public Chalcocite() {
        super(new ItemStackBuilder(Material.LAPIS_ORE).setLocName("\u00a71\u8f89\u94dc\u77ff").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u8f89\u94dc\u77ff";
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new ChalcociteBlock().setBlock(block);
    }
}

