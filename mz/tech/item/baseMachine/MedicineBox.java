/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package mz.tech.item.baseMachine;

import mz.tech.MzTech;
import mz.tech.category.BaseMachineCategory;
import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.SmilingCraftingTableRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;

public class MedicineBox
extends MzTechItem {
    static {
        new SmilingCraftingTableRecipe(new MedicineBox(), new Object[]{Material.IRON_INGOT, new ItemStackBuilder("totem", 0, "totem_of_undying", 1), 0, 1, 1, 1, 0, 1, 0}).reg("\u836f\u54c1\u7bb1");
    }

    public MedicineBox() {
        super(ItemStackBuilder.newSkull(null, MzTech.newUUID(7486614357L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdjN2RmNTJiNWU1MGJhZGI2MWZlZDcyMTJkOTc5ZTYzZmU5NGYxYmRlMDJiMjk2OGM2YjE1NmE3NzAxMjZjIn19fQ").setLocName("\u00a7c\u836f\u54c1\u7bb1").build());
    }

    @Override
    public String getTypeName() {
        return "\u836f\u54c1\u7bb1";
    }

    @Override
    public MzTechCategory getCategory() {
        return BaseMachineCategory.instance;
    }
}

