/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class LuckySword
extends MzTechItem {
    public LuckySword() {
        super(new ItemStackBuilder(Material.DIAMOND_SWORD).setLocName("\u00a74\u5e78\u8fd0\u5251").build());
    }

    @Override
    public String getTypeName() {
        return "\u5e78\u8fd0\u5251";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }
}

