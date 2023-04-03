/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.event.MoveItemEvent;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.OldCraftingMachine;
import mz.tech.recipe.ChemicalFurnaceRecipe;
import mz.tech.recipe.MzTechRecipe;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.Slot;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Deprecated
public class ChemicalFurnaceMachine
extends OldCraftingMachine
implements OldCraftingMachine.CraftingFor10s {
    @Override
    public MzTechMachine setBlock(Block block) {
        super.setBlock(block);
        int i = 0;
        while (i < 9) {
            this.getInventory().setItem(i, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
            ++i;
        }
        this.getInventory().setItem(9, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
        this.getInventory().setItem(17, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
        this.getInventory().setItem(18, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
        this.getInventory().setItem(26, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
        i = 27;
        while (i < 36) {
            this.getInventory().setItem(i, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
            ++i;
        }
        this.setProgress(0);
        i = 45;
        while (i < 54) {
            if (i != 49) {
                this.getInventory().setItem(i, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setLocName("\u00a70").build());
            }
            ++i;
        }
        return this;
    }

    @Override
    public String getType() {
        return "\u9ad8\u6e29\u5316\u5408\u9505";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public String getTitle() {
        return "\u00a74\u9ad8\u6e29\u5316\u5408\u9505";
    }

    public void clearCoal() {
        this.getInventory().setItem(49, new ItemStackBuilder(Material.COAL).setLocName("\u00a7\u69fd\u7164\u70ad\u69fd").build());
    }

    public boolean hasCoal() {
        return !ItemStackBuilder.isEmpty(this.getInventory().getItem(49)) && this.getInventory().getItem(49).isSimilar(new ItemStack(Material.COAL));
    }

    @Override
    public List<Slot> getRawSlots() {
        ArrayList<Slot> rl = new ArrayList<Slot>();
        int i = 10;
        while (i < 17) {
            rl.add(new Slot(this.getInventory(), i));
            ++i;
        }
        i = 19;
        while (i < 26) {
            rl.add(new Slot(this.getInventory(), i));
            ++i;
        }
        return rl;
    }

    @Override
    public void onMoveItem(MoveItemEvent event) {
        Lists.newArrayList((Object[])new Slot[]{event.getFrom(), event.getTo()}).forEach(slot -> {
            if (slot.getInventory() != null && slot.getInventory().equals(this.getInventory())) {
                int rawSlot = slot.getSlot();
                if (rawSlot > 9 && rawSlot < 17) {
                    return;
                }
                if (rawSlot > 18 && rawSlot < 26) {
                    return;
                }
                if (rawSlot == 49) {
                    if (slot == event.getTo() && !new ItemStack(Material.COAL).isSimilar(event.getFrom().getItemStack())) {
                        event.setCancelled(true);
                    }
                    return;
                }
                event.setCancelled(true);
            }
        });
        if (!event.isCancelled() && (this.getRawSlots().contains(event.getFrom()) || this.getRawSlots().contains(event.getTo()) || event.getFrom().equals(this.getCoalSlot()) || event.getTo().equals(this.getCoalSlot()))) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.updateRecipe());
        }
    }

    @Override
    public Sound getCloseSound() {
        return null;
    }

    @Override
    public Sound getOpenSound() {
        return null;
    }

    @Override
    public int getProgressLine() {
        return 4;
    }

    @Override
    public ItemStack getProgressItem() {
        return new ItemStackBuilder(ItemStackBuilder.redGlassPane).setLocName("\u00a70").build();
    }

    @Override
    public boolean toggle() {
        if (this.getRecipe() == null) {
            return false;
        }
        List<ItemStack> raws = this.getRaws();
        List<ItemStack> matching = ((ChemicalFurnaceRecipe)this.getRecipe()).matching(raws);
        Slot.setSlots(this.getRawSlots().toArray(new Slot[0]), raws.toArray(new ItemStack[raws.size()]));
        this.addItems(matching.toArray(new ItemStack[0]));
        if (((ChemicalFurnaceRecipe)this.getRecipe()).needCoal) {
            this.removeACoal();
        }
        return true;
    }

    public Slot getCoalSlot() {
        return new Slot(this.getInventory(), 49);
    }

    public void removeACoal() {
        this.getInventory().getItem(49).setAmount(this.getInventory().getItem(49).getAmount() - 1);
    }

    @Override
    public void updateRecipe() {
        boolean[] tb = new boolean[]{true};
        List<ItemStack> rawSlots = this.getRaws();
        MzTechRecipe.forEach(ChemicalFurnaceRecipe.class, recipe -> {
            if (recipe.needCoal && !this.hasCoal()) {
                return;
            }
            if (recipe.matching(rawSlots) != null) {
                this.setRecipe((MzTechRecipe)recipe);
                blArray[0] = false;
            }
        });
        if (tb[0]) {
            this.setRecipe(null);
        }
    }
}

