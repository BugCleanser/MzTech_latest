/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine.generator;

import mz.tech.machine.generator.BlockGenerator;
import mz.tech.recipe.MineOreRecipe;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

public class OreGenerator
extends BlockGenerator {
    MineOreRecipe recipe;

    public OreGenerator(int chance, int numEveryChunk, ItemStack is) {
        super(chance, numEveryChunk, is, ItemStackBuilder.stone.getData(), ItemStackBuilder.granite.getData(), ItemStackBuilder.diorite.getData(), ItemStackBuilder.andesite.getData());
        this.recipe = new MineOreRecipe(is);
    }

    @Override
    public void reg() {
        super.reg();
        this.recipe.reg();
    }

    @Override
    public void unreg() {
        super.unreg();
        MzTechRecipe.getRecipes().remove(this.recipe);
    }
}

