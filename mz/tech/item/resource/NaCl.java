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

public class NaCl
extends MzTechItem {
    public NaCl() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a7f\u6c2f\u5316\u94a0").setLoreList("\u00a77NaCl").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u6c2f\u5316\u94a0";
    }
}

