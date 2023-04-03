/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.category;

import mz.tech.category.MzTechCategory;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public final class BaseMachineCategory
extends MzTechCategory {
    public static BaseMachineCategory instance = new BaseMachineCategory();

    private BaseMachineCategory() {
        super("\u57fa\u7840\u673a\u5668", new ItemStackBuilder(Material.DROPPER).setLocName("\u00a76\u57fa\u7840\u673a\u5668").build());
    }
}

