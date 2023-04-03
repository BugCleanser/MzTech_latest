/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 */
package mz.tech.item;

import java.util.List;
import mz.tech.item.Button;
import mz.tech.item.CraftGuide;
import mz.tech.item.GuideView;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

class ReturnButton
extends Button {
    @Override
    public void set(GuideView view, int slot) {
        view.getInventory().setItem(slot, new ItemStackBuilder(Material.MAGENTA_GLAZED_TERRACOTTA).setLocName("\u00a75\u8fd4\u56de").build());
        super.set(view, slot);
    }

    @Override
    public void onClick(HumanEntity humanEntity) {
        List<GuideView> views = CraftGuide.views.get(humanEntity);
        views.remove(views.size() - 1);
        if (views.isEmpty()) {
            CraftGuide.views.remove(humanEntity);
            humanEntity.closeInventory();
        } else {
            views.get(views.size() - 1).open(humanEntity);
        }
        super.onClick(humanEntity);
    }
}

