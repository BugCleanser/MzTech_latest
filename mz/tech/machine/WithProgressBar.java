/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine;

import mz.tech.machine.StoreableMachine;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

public interface WithProgressBar {
    default public int getProgressLine() {
        return 0;
    }

    default public ItemStack getProgressItem() {
        return new ItemStackBuilder(ItemStackBuilder.limeGlassPane).setLocName("\u00a70").build();
    }

    default public ItemStack getNullItem() {
        return new ItemStackBuilder(ItemStackBuilder.grayGlassPane).setLocName("\u00a70").build();
    }

    default public void setProgress(int progress) {
        int i = 0;
        while (i < progress) {
            ((StoreableMachine)((Object)this)).getInventory().setItem(this.getProgressLine() * 9 + i, this.getProgressItem());
            ++i;
        }
        while (i < 9) {
            ((StoreableMachine)((Object)this)).getInventory().setItem(this.getProgressLine() * 9 + i, this.getNullItem());
            ++i;
        }
    }

    default public int getProgress() {
        int i = 0;
        while (i < 9 && this.getProgressItem().isSimilar(((StoreableMachine)((Object)this)).getInventory().getItem(this.getProgressLine() * 9 + i))) {
            ++i;
        }
        return i;
    }
}

