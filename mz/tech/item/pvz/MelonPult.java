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
import mz.tech.machine.MelonPultBlock;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.block.Block;

public class MelonPult
extends MzTechItem {
    public MelonPult() {
        super(new ItemStackBuilder("MELON_BLOCK", 0, "melon", 1).setLocName("\u00a7a\u897f\u74dc\u6295\u624b").build());
    }

    @Override
    public String getTypeName() {
        return "\u897f\u74dc\u6295\u624b";
    }

    @Override
    public MzTechCategory getCategory() {
        return PvzCategory.instance;
    }

    @Override
    public MzTechMachine toMachine(Block block) {
        return new MelonPultBlock().setBlock(block);
    }
}

