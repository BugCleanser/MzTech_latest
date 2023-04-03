/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 */
package mz.tech.item;

import java.util.List;
import mz.tech.category.MzTechCategory;
import mz.tech.item.Button;
import mz.tech.item.CraftGuide;
import mz.tech.item.GlassPane;
import mz.tech.item.GuideView;
import mz.tech.item.RecipeView;
import mz.tech.item.ReturnButton;
import mz.tech.item.lastPageButton;
import mz.tech.item.nextPageButton;
import mz.tech.recipe.MzTechRecipe;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

class CategoryView
extends GuideView {
    MzTechCategory category;

    public CategoryView(String title, final MzTechCategory category, final int page) {
        super(Bukkit.createInventory(null, (int)54, (String)title), page);
        this.category = category;
        int i = 0;
        while (i < 9) {
            new GlassPane("lime", 5).set(this, i);
            new GlassPane("lime", 5).set(this, 45 + i);
            new lastPageButton().set(this, 47);
            new nextPageButton().set(this, 51);
            ++i;
        }
        new ReturnButton().set(this, 0);
        i = 0;
        while (i < 36 && category.items.size() > page * 36 + i) {
            final int j = i;
            new Button(){

                @Override
                public void set(GuideView view, int slot) {
                    view.getInventory().setItem(slot, category.items.get(page * 36 + j));
                    super.set(view, slot);
                }

                @Override
                public void onClick(HumanEntity player) {
                    List<MzTechRecipe> recipes = CraftGuide.getRecipesForItem(category.items.get(page * 36 + j));
                    if (recipes != null && !recipes.isEmpty()) {
                        new RecipeView(category.items.get(page * 36 + j), recipes, 0).open(player);
                    }
                    super.onClick(player);
                }
            }.set(this, 9 + i);
            ++i;
        }
    }

    @Override
    public int getMaxPage() {
        return this.category.items.size() / 36;
    }

    @Override
    public CategoryView anotherPage(int page) {
        return new CategoryView(this.getInventory().getTitle(), this.category, page);
    }
}

