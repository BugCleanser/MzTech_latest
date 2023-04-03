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

public class SO3
extends MzTechItem {
    public SO3() {
        super(new ItemStackBuilder(Material.SUGAR).setLocName("\u00a77\u4e09\u6c27\u5316\u786b").setLoreList("\u00a77SO\u2083").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u4e09\u6c27\u5316\u786b";
    }
}

