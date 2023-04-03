/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.util.ItemStackBuilder;

public final class ElectricityCategory
extends MzTechCategory {
    public static ElectricityCategory instance = new ElectricityCategory();

    private ElectricityCategory() {
        super("\u7535\u529b\u4e0e\u673a\u5668", new ItemStackBuilder(ItemStackBuilder.lightGrayGlass).setLocName("\u00a77\u7535\u529b\u4e0e\u673a\u5668").build());
    }
}

