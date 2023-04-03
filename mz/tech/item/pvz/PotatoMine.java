/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package mz.tech.item.pvz;

import mz.tech.category.MzTechCategory;
import mz.tech.category.PvzCategory;
import mz.tech.item.MzTechItem;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.PotatoMineBlock;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PotatoMine
extends MzTechItem {
    public PotatoMine() {
        super(new ItemStackBuilder(Material.POTATO).setLocName("\u00a76\u571f\u8c46\u5730\u96f7").build());
    }

    @Override
    public String getTypeName() {
        return "\u571f\u8c46\u5730\u96f7";
    }

    @Override
    public MzTechCategory getCategory() {
        return PvzCategory.instance;
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new PotatoMineBlock().setBlock(block);
    }
}

