/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.item.baseMachine;

import mz.tech.category.MzTechCategory;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;

@Deprecated
public class ChemicalFurnace
extends MzTechItem {
    public ChemicalFurnace() {
        super(new ItemStackBuilder("CAULDRON_ITEM", 0, "CAULDRON", 1).setLocName("\u00a74\u9ad8\u6e29\u5316\u5408\u9505").setLoreList("\u00a77\u4e5f\u53ef\u4ee5\u7528\u6765\u5236\u952d").build());
    }

    @Override
    public String getTypeName() {
        return "\u9ad8\u6e29\u5316\u5408\u9505";
    }

    @Override
    public MzTechCategory getCategory() {
        return null;
    }
}

