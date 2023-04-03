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

public class H2
extends MzTechItem {
    public H2() {
        super(new ItemStackBuilder(Material.GLASS_BOTTLE).setLocName("\u00a7f\u6c22\u6c14").setLoreList("\u00a77H\u2082").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6c22\u6c14";
    }
}

