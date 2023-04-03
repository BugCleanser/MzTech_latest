/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.PlaceableItem;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class Test
extends PlaceableItem {
    public Test() {
        super(new ItemStackBuilder(Material.SAND).setLocName("test").build());
    }

    @Override
    public String getTypeName() {
        return "test";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }
}

