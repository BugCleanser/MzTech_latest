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

public class CO2
extends MzTechItem {
    public CO2() {
        super(new ItemStackBuilder(Material.GLASS_BOTTLE).setLocName("\u00a77\u4e8c\u6c27\u5316\u78b3").setLoreList("\u00a77CO\u2082").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u4e8c\u6c27\u5316\u78b3";
    }
}

