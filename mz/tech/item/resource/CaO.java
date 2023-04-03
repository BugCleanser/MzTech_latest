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

public class CaO
extends MzTechItem {
    public CaO() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a77\u6c27\u5316\u9499").setLoreList("\u00a77CaO").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6c27\u5316\u9499";
    }
}

