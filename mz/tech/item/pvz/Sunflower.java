/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 */
package mz.tech.item.pvz;

import mz.tech.category.MzTechCategory;
import mz.tech.category.PvzCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.SunflowerBlock;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;

public class Sunflower
extends MzTechItem {
    public Sunflower() {
        super(new ItemStackBuilder("double_plant", 0, "sunflower", 1).setLocName("\u00a7e\u5411\u65e5\u8475").build());
    }

    @Override
    public String getTypeName() {
        return "\u5411\u65e5\u8475";
    }

    @Override
    public MzTechCategory getCategory() {
        return PvzCategory.instance;
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new SunflowerBlock().setBlock(block);
    }
}

