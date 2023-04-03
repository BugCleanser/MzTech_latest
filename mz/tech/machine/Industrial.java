/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Nullable;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.machine.CraftingMachine;
import mz.tech.machine.GuiMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.TaskUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public interface Industrial
extends GuiMachine {
    public static final ItemStack clapboard = new ItemStackBuilder(ItemStackBuilder.lightGrayGlassPane).setDisplayName("\u00a71").build();

    public ItemStack[] getRawItems();

    public ItemStack[] getFinishedItems();

    public Map<Integer, Integer> getGuiToRaw();

    public Map<Integer, Integer> getGuiToFinished();

    @Nullable
    default public MzTechMachine load(NBT nbt) {
        nbt.getChildList("raw").forEach(n -> {
            this.getRawItems()[n.getByte((String)"Slot").byteValue()] = n.remove("Slot").toItemStack();
        });
        nbt.getChildList("finished").forEach(n -> {
            this.getFinishedItems()[n.getByte((String)"Slot").byteValue()] = n.remove("Slot").toItemStack();
        });
        return null;
    }

    @Nullable
    default public NBT save(NBT nbt) {
        ArrayList<NBT> raw = new ArrayList<NBT>();
        byte i = 0;
        while (i < 9) {
            if (!ItemStackBuilder.isEmpty(this.getRawItems()[i])) {
                raw.add(new NBT(this.getRawItems()[i]).set("Slot", i));
            }
            i = (byte)(i + 1);
        }
        nbt.set("raw", raw.toArray());
        ArrayList<NBT> finished = new ArrayList<NBT>();
        byte i2 = 0;
        while (i2 < 9) {
            if (!ItemStackBuilder.isEmpty(this.getFinishedItems()[i2])) {
                finished.add(new NBT(this.getFinishedItems()[i2]).set("Slot", i2));
            }
            i2 = (byte)(i2 + 1);
        }
        nbt.set("finished", finished.toArray());
        return null;
    }

    @Override
    default public void initInventory(Inventory inv) {
        this.updateRaw(inv);
        this.updateFinished(inv);
        if (this instanceof CraftingMachine) {
            this.onScheduleUpdate(inv, ((CraftingMachine)((Object)this)).getSchedule());
        }
    }

    default public void updateRaw(Inventory inv) {
        if (!((GuiMachine.Gui)inv.getHolder()).tasks.isEmpty()) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.updateRaw(inv));
        } else {
            this.getGuiToRaw().forEach((g, r) -> {
                if (!ItemStackBuilder.isEmpty(this.getRawItems()[r])) {
                    inv.setItem(g.intValue(), this.getRawItems()[r]);
                } else {
                    inv.setItem(g.intValue(), new ItemStack(Material.AIR));
                }
            });
        }
    }

    default public void updateRaw() {
        this.getViewers().forEach(inv -> this.updateRaw((Inventory)inv));
    }

    default public void updateFinished(Inventory inv) {
        if (!((GuiMachine.Gui)inv.getHolder()).tasks.isEmpty()) {
            TaskUtil.runTask((Plugin)MzTech.instance, () -> this.updateFinished(inv));
        } else {
            this.getGuiToFinished().forEach((g, f) -> this.waitUnlock((Integer)g, () -> {
                if (ItemStackBuilder.isEmpty(this.getFinishedItems()[f])) {
                    inv.setItem(g.intValue(), clapboard);
                } else {
                    inv.setItem(g.intValue(), this.getFinishedItems()[f]);
                }
            }));
        }
    }

    default public void updateFinished() {
        this.getViewers().forEach(inv -> this.updateFinished((Inventory)inv));
    }

    @Override
    default public Runnable onClick(Inventory inv, boolean toMachine, int slot) {
        if (this.getGuiToRaw().containsKey(slot)) {
            inv.setItem(slot, this.getRawItems()[this.getGuiToRaw().get(slot)]);
            return () -> {
                this.getRawItems()[this.getGuiToRaw().get((Object)Integer.valueOf((int)n)).intValue()] = inv.getItem(slot);
                this.onRawUpdate(this.getGuiToRaw().get(slot));
            };
        }
        if (!toMachine && this.getGuiToFinished().containsKey(slot) && !ItemStackBuilder.isEmpty(this.getFinishedItems()[this.getGuiToFinished().get(slot)])) {
            inv.setItem(slot, this.getFinishedItems()[this.getGuiToFinished().get(slot)]);
            return () -> {
                this.getFinishedItems()[this.getGuiToFinished().get((Object)Integer.valueOf((int)n)).intValue()] = inv.getItem(slot);
                if (ItemStackBuilder.isEmpty(this.getFinishedItems()[this.getGuiToFinished().get(slot)])) {
                    this.waitUnlock(slot, () -> inv.setItem(slot, clapboard));
                }
                this.onFinishedUpdate(this.getGuiToFinished().get(slot));
            };
        }
        return null;
    }

    default public void onRawUpdate(int slot) {
    }

    default public void onFinishedUpdate(int slot) {
    }

    default public void onBreak(Player player, boolean silkTouch, boolean drop) {
        ArrayList drops = Lists.newArrayList((Object[])this.getRawItems());
        drops.addAll(Lists.newArrayList((Object[])this.getFinishedItems()));
        drops.forEach(i -> {
            if (!ItemStackBuilder.isEmpty(i)) {
                MzTech.dropItemStack(((MzTechMachine)((Object)this)).getBlock(), i);
            }
        });
    }

    default public void onScheduleUpdate(int remainingTime) {
        this.getViewers().forEach(v -> this.onScheduleUpdate((Inventory)v, remainingTime));
    }

    default public void onScheduleUpdate(Inventory v, int remainingTime) {
    }
}

