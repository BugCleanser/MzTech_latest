/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.resource;

import mz.tech.category.MzTechCategory;
import mz.tech.category.ResourceCategory;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;

public class Fe3O4
extends MzTechItem {
    public Fe3O4() {
        super(new ItemStackBuilder("NETHER_BRICK_ITEM", 0, "NETHER_BRICK", 1).setLocName("\u00a77\u56db\u6c27\u5316\u4e09\u94c1").setLoreList("\u00a77Fe\u2083O\u2084").build());
    }

    @Override
    public MzTechCategory getCategory() {
        return ResourceCategory.instance;
    }

    @Override
    public String getTypeName() {
        return "\u56db\u6c27\u5316\u4e09\u94c1";
    }
}

