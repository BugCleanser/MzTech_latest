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

public final class ToolCategory
extends MzTechCategory {
    public static ToolCategory instance = new ToolCategory();

    private ToolCategory() {
        super("\u5de5\u5177", new ItemStackBuilder(Material.DIAMOND_AXE).setLocName("\u00a76\u5de5\u5177").build());
    }
}

