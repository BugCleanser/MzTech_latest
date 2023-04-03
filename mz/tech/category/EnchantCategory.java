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

public final class EnchantCategory
extends MzTechCategory {
    public static EnchantCategory instance = new EnchantCategory();

    private EnchantCategory() {
        super("\u9644\u9b54", new ItemStackBuilder(Material.ENCHANTED_BOOK).setLocName("\u00a75\u9644\u9b54").build());
    }
}

