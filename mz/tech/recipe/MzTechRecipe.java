/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import mz.tech.DropsName;
import mz.tech.item.MzTechItem;
import mz.tech.recipe.RawItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class MzTechRecipe {
    private static List<MzTechRecipe> recipes = new ArrayList<MzTechRecipe>();
    public String name;
    public List<List<ItemStack>> extras = new ArrayList<List<ItemStack>>();

    public static void forEach(Consumer<MzTechRecipe> consumer) {
        recipes.forEach(consumer);
    }

    public static <T> void forEach(Class<T> clazz, Consumer<T> consumer) {
        recipes.forEach(recipe -> {
            if (clazz.isAssignableFrom(recipe.getClass())) {
                consumer.accept(recipe);
            }
        });
    }

    public List<RawItem> getDisplayRaws() {
        return this.getRaws();
    }

    public abstract List<RawItem> getRaws();

    public abstract List<ItemStack> getMachines();

    public abstract List<ItemStack> getResults();

    public MzTechRecipe setExtras(List<List<ItemStack>> extras) {
        this.extras = extras;
        return this;
    }

    public MzTechRecipe setExtras(int y, List<ItemStack> extras) {
        this.extras.set(y, extras);
        return this;
    }

    public MzTechRecipe setExtra(int x, int y, ItemStack extra) {
        if (y >= this.extras.size()) {
            while (this.extras.size() <= y) {
                this.extras.add(new ArrayList());
            }
        }
        if (x >= this.extras.get(y).size()) {
            while (this.extras.get(y).size() <= x) {
                this.extras.get(y).add(new ItemStack(Material.AIR));
            }
        }
        this.extras.get(y).set(x, extra);
        return this;
    }

    public List<List<ItemStack>> getExtras() {
        return this.extras;
    }

    public void reg(String name) {
        this.setName(name);
        MzTechRecipe.regRecipe(this);
    }

    public MzTechRecipe setName(String name) {
        this.name = name;
        return this;
    }

    public void reg() {
        this.reg(DropsName.getShowName(this.getResults().get(0)));
    }

    public static List<MzTechRecipe> getRecipes() {
        return recipes;
    }

    public static void setRecipes(List<MzTechRecipe> recipes) {
        MzTechRecipe.recipes = recipes;
    }

    public static void regRecipe(MzTechRecipe recipe) {
        recipe.getResults().forEach(r -> {
            if (r instanceof MzTechItem) {
                MzTechItem mz = (MzTechItem)((Object)r);
                if ((mz = mz.copy(mz).setNum(1)).getCategory() != null) {
                    mz.getCategory().addItem(mz);
                }
            }
        });
        recipes.add(recipe);
    }
}

