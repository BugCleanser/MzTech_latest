/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.util.ItemStackBuilder;

public final class PvzCategory
extends MzTechCategory {
    public static PvzCategory instance = new PvzCategory();

    private PvzCategory() {
        super("\u7578\u5f62\u79cd", new ItemStackBuilder("SEEDS", 0, "wheat_seeds", 1).setLocName("\u00a7a\u7578\u5f62\u79cd").setLoreList("\u00a77\u81f4\u656cPVZ").build());
    }
}

