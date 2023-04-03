/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemFlag
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.inventory.ItemFlag;

public final class RecordCategory
extends MzTechCategory {
    public static RecordCategory instance = new RecordCategory();

    private RecordCategory() {
        super("\u97f3\u4e50\u5531\u7247", new ItemStackBuilder(ItemStackBuilder.record_chirp).setLocName("\u00a7b\u97f3\u4e50\u5531\u7247").setHideFlags(ItemFlag.HIDE_POTION_EFFECTS).build());
    }
}

