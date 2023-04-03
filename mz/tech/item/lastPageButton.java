/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 */
package mz.tech.item;

import java.util.List;
import mz.tech.item.CraftGuide;
import mz.tech.item.GlassPane;
import mz.tech.item.GuideView;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.entity.HumanEntity;

class lastPageButton
extends GlassPane {
    private GuideView view;

    public lastPageButton() {
        super("red", 14);
    }

    @Override
    public void set(GuideView view, int slot) {
        super.set(view, slot);
        this.view = view;
        view.getInventory().setItem(slot, new ItemStackBuilder(view.getInventory().getItem(slot)).setLocName("\u00a7a\u4e0a\u4e00\u9875").setCount(view.page + 1).build());
    }

    @Override
    public void onClick(HumanEntity humanEntity) {
        if (this.view.page > 0) {
            List<GuideView> vs = CraftGuide.views.get(humanEntity);
            vs.remove(vs.size() - 1);
            this.view.anotherPage(this.view.page - 1).open(humanEntity);
        }
        super.onClick(humanEntity);
    }
}

