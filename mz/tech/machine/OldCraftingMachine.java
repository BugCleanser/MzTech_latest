/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.event.MoveItemEvent;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.StoreableMachine;
import mz.tech.machine.Trigger;
import mz.tech.machine.WithProgressBar;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.Slot;
import mz.tech.util.TaskUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Deprecated
public abstract class OldCraftingMachine
extends StoreableMachine
implements WithProgressBar,
Trigger {
    private MzTechRecipe recipe;

    static {
        CraftingFor10s.start();
    }

    public abstract List<Slot> getRawSlots();

    public void updateRecipe() {
    }

    public MzTechRecipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(MzTechRecipe recipe) {
        if (this.recipe != recipe) {
            this.setProgress(0);
        }
        this.recipe = recipe;
    }

    @Override
    public void onMoveItem(MoveItemEvent event) {
        if (event.getFrom().getInventory() != event.getTo().getInventory()) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.updateRecipe());
        }
    }

    public List<ItemStack> getRaws() {
        ArrayList<ItemStack> iss = new ArrayList<ItemStack>();
        this.getRawSlots().forEach(slot -> {
            ItemStack is = slot.getItemStack();
            if (!ItemStackBuilder.isEmpty(is)) {
                iss.add(is.clone());
            }
        });
        return iss;
    }

    @Override
    public boolean toggle() {
        return false;
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.updateRecipe();
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        super.loadOld(dfi);
        this.updateRecipe();
    }

    public static interface CraftingFor10s {
        public static void start() {
            TaskUtil.runTaskTimer((Plugin)MzTech.instance, 20L, 20L, () -> MzTechMachine.forEach(CraftingFor10s.class, machine -> {
                if (((OldCraftingMachine)((Object)machine)).recipe != null) {
                    int progress = ((OldCraftingMachine)((Object)machine)).getProgress();
                    if (progress < 9) {
                        ((OldCraftingMachine)((Object)machine)).setProgress(progress + 1);
                    } else {
                        ((OldCraftingMachine)((Object)machine)).toggle();
                        ((OldCraftingMachine)((Object)machine)).setProgress(0);
                        ((OldCraftingMachine)((Object)machine)).recipe = null;
                        ((OldCraftingMachine)((Object)machine)).updateRecipe();
                    }
                }
            }));
        }
    }
}

