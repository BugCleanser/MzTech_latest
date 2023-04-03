/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 */
package mz.tech.item;

import mz.tech.category.MzTechCategory;
import mz.tech.item.Button;
import mz.tech.item.CategoryView;
import mz.tech.item.CraftGuide;
import mz.tech.item.GlassPane;
import mz.tech.item.GuideView;
import mz.tech.item.MzTechItem;
import mz.tech.item.lastPageButton;
import mz.tech.item.nextPageButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

class MainGuideView
extends GuideView {
    public MainGuideView() {
        this(0);
    }

    public MainGuideView(final int page) {
        super(Bukkit.createInventory(null, (int)54, (String)MzTechItem.get("\u5408\u6210\u6307\u5357").getName()), page);
        int i = 0;
        while (i < 9) {
            new GlassPane("blue", 3).set(this, i);
            new GlassPane("blue", 3).set(this, 45 + i);
            ++i;
        }
        new lastPageButton().set(this, 47);
        new nextPageButton().set(this, 51);
        new Button(){

            @Override
            public void set(GuideView view, int slot) {
                view.getInventory().setItem(slot, new CraftGuide().setLoreList(new String[0]).build());
                super.set(view, slot);
            }
        }.set(this, 4);
        i = 0;
        while (i < 36 && page * 36 + i < MzTechCategory.categories.size()) {
            final int j = i;
            new Button(){

                @Override
                public void set(GuideView view, int slot) {
                    view.getInventory().setItem(slot, MzTechCategory.categories.get((int)(page * 36 + j)).icon);
                    super.set(view, slot);
                }

                @Override
                public void onClick(HumanEntity player) {
                    new CategoryView(MzTechCategory.categories.get((int)(page * 36 + j)).name, MzTechCategory.categories.get(page * 36 + j), 0).open(player);
                }
            }.set(this, 9 + i);
            ++i;
        }
    }

    @Override
    public int getMaxPage() {
        return MzTechCategory.categories.size() / 36;
    }

    @Override
    public MainGuideView anotherPage(int page) {
        return new MainGuideView(page);
    }
}

