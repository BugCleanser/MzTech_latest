/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.item;

import java.util.List;
import mz.tech.item.Button;
import mz.tech.item.CraftGuide;
import mz.tech.item.GlassPane;
import mz.tech.item.GuideView;
import mz.tech.item.ReturnButton;
import mz.tech.item.lastPageButton;
import mz.tech.item.nextPageButton;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.recipe.RawItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

class RecipeView
extends GuideView {
    public ItemStack result;
    private List<MzTechRecipe> recipes;
    private int loop = 0;

    public RecipeView(final ItemStack result, List<MzTechRecipe> recipes, int page) {
        super(Bukkit.createInventory(null, (int)54, (String)recipes.get((int)page).name), page);
        this.result = result;
        new ReturnButton().set(this, 0);
        this.recipes = recipes;
        int i = 0;
        while (i < 9) {
            new GlassPane("yellow", 4).set(this, 27 + i);
            ++i;
        }
        new lastPageButton().set(this, 29);
        new nextPageButton().set(this, 33);
        this.getInventory().setItem(16, result);
        i = 0;
        while (i < 3) {
            int j = 0;
            while (j < 3) {
                RawItem t;
                if (recipes.get(page).getRaws().size() > i * 3 + j && (t = recipes.get(page).getRaws().get(i * 3 + j)) != null) {
                    new Button(){

                        @Override
                        public void onClick(HumanEntity humanEntity) {
                            List<MzTechRecipe> rs = CraftGuide.getRecipesForItem(t.keySet().toArray(new ItemStack[t.size()])[RecipeView.this.loop % t.size()]);
                            if (rs != null && !rs.isEmpty()) {
                                new RecipeView(result, rs, 0).open(humanEntity);
                            }
                            super.onClick(humanEntity);
                        }
                    }.set(this, i * 9 + j + 3);
                }
                ++j;
            }
            ++i;
        }
        int[] i2 = new int[]{4};
        this.recipes.get(page).getExtras().forEach(extras -> {
            int[] j = new int[1];
            extras.forEach(extra -> {
                this.getInventory().setItem(i2[0] * 9 + j[0], extra);
                nArray2[0] = j[0] + 1;
            });
            nArray[0] = i2[0] + 1;
        });
    }

    @Override
    public void update(HumanEntity player) {
        ++this.loop;
        this.getInventory().setItem(10, this.recipes.get(this.page).getMachines().get(this.loop % this.recipes.get(this.page).getMachines().size()));
        int[] i = new int[1];
        this.recipes.get(this.page).getDisplayRaws().forEach(raw -> {
            if (raw != null && !raw.isEmpty()) {
                this.getInventory().setItem(i[0] / 3 * 9 + i[0] % 3 + 3, raw.keySet().toArray(new ItemStack[raw.size()])[this.loop % raw.size()]);
            }
            nArray[0] = i[0] + 1;
        });
    }

    @Override
    public int getMaxPage() {
        return this.recipes.size() - 1;
    }

    @Override
    public RecipeView anotherPage(int page) {
        return new RecipeView(this.result, this.recipes, page);
    }
}

