/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class Na2CO3
extends MzTechItem {
    public Na2CO3() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a7f\u78b3\u9178\u94a0").setLoreList("\u00a77Na\u2082CO\u2083").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u78b3\u9178\u94a0";
    }
}

