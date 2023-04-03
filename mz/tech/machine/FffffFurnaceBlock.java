/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Furnace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mz.tech.NBT;
import mz.tech.event.MachineItemAutoMoveEvent;
import mz.tech.machine.CraftingMachine;
import mz.tech.machine.Industrial;
import mz.tech.machine.MzTechMachine;
import mz.tech.recipe.FffffFurnaceRecipe;
import mz.tech.util.FurnaceUtil;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FffffFurnaceBlock
extends MzTechMachine
implements Industrial,
CraftingMachine {
    public ItemStack[] rawItems;
    public ItemStack[] finishedItems;
    public List<Inventory> viewers;
    public List<Integer> lock;
    public static Map<Integer, Integer> guiToRaw = new HashMap<Integer, Integer>();
    public static Map<Integer, Integer> guiToFinished;

    static {
        guiToRaw.put(10, 0);
        guiToRaw.put(11, 1);
        guiToRaw.put(12, 2);
        guiToRaw.put(19, 3);
        guiToRaw.put(20, 4);
        guiToRaw.put(21, 5);
        guiToRaw.put(28, 6);
        guiToRaw.put(29, 7);
        guiToRaw.put(30, 8);
        guiToFinished = new HashMap<Integer, Integer>();
        guiToFinished.put(14, 0);
        guiToFinished.put(15, 1);
        guiToFinished.put(16, 2);
        guiToFinished.put(23, 3);
        guiToFinished.put(24, 4);
        guiToFinished.put(25, 5);
        guiToFinished.put(32, 6);
        guiToFinished.put(33, 7);
        guiToFinished.put(34, 8);
    }

    @Override
    public String getType() {
        return "rrrrr\u7194\u7089";
    }

    @Override
    public MzTechMachine setBlock(Block block) {
        this.rawItems = new ItemStack[9];
        this.finishedItems = new ItemStack[9];
        this.viewers = new ArrayList<Inventory>();
        return super.setBlock(block);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        Industrial.super.load(nbt);
        CraftingMachine.super.load(nbt);
        return super.load(nbt);
    }

    @Override
    public NBT save(NBT nbt) {
        Industrial.super.save(nbt);
        CraftingMachine.super.save(nbt);
        return super.save(nbt);
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        return Industrial.super.onRightClick(event);
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void initInventory(Inventory inv) {
        int i;
        int j = 0;
        while (j < 2) {
            i = 0;
            while (i < 9) {
                inv.setItem(i + 36 * j, new ItemStackBuilder(ItemStackBuilder.redGlassPane).setDisplayName("\u00a71").build());
                ++i;
            }
            ++j;
        }
        j = 0;
        while (j < 3) {
            i = 0;
            while (i < 5) {
                inv.setItem(i * 9 + j * 4, new ItemStackBuilder(ItemStackBuilder.redGlassPane).setDisplayName("\u00a71").build());
                ++i;
            }
            ++j;
        }
        int i2 = 0;
        while (i2 < 9) {
            if (i2 != 4) {
                inv.setItem(i2 + 45, new ItemStackBuilder(ItemStackBuilder.yellowGlassPane).setDisplayName("\u00a7c\u4e0b\u9762\u653e\u71c3\u6599").build());
            }
            ++i2;
        }
        inv.setItem(49, this.getState().getInventory().getFuel());
        Industrial.super.initInventory(inv);
    }

    @Override
    public List<Integer> getLock() {
        if (this.lock == null) {
            this.lock = new ArrayList<Integer>();
        }
        return this.lock;
    }

    @Override
    public Runnable onClick(Inventory inv, boolean toMachine, int slot) {
        if (slot == 49) {
            Furnace f = this.getState();
            inv.setItem(49, f.getInventory().getFuel());
            return () -> {
                f.getInventory().setFuel(inv.getItem(49));
                this.onRawUpdate(49);
            };
        }
        return Industrial.super.onClick(inv, toMachine, slot);
    }

    @Override
    public void remove() {
        Industrial.super.remove();
        CraftingMachine.super.remove();
        super.remove();
    }

    @Override
    public List<Inventory> getViewers() {
        return this.viewers;
    }

    @Override
    public boolean onItemAutoMove(MachineItemAutoMoveEvent e) {
        return Industrial.super.onItemAutoMove(e);
    }

    @Override
    public ItemStack[] getRawItems() {
        return this.rawItems;
    }

    @Override
    public ItemStack[] getFinishedItems() {
        return this.finishedItems;
    }

    @Override
    public Map<Integer, Integer> getGuiToRaw() {
        return guiToRaw;
    }

    @Override
    public Map<Integer, Integer> getGuiToFinished() {
        return guiToFinished;
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        Industrial.super.onBreak(player, silkTouch, drop);
    }

    @Override
    public void onScheduleUpdate(int remainingTime) {
        if (remainingTime > 0) {
            this.ignite();
            if (!this.isIgniting()) {
                this.breakCrafting();
                return;
            }
        }
        Industrial.super.onScheduleUpdate(remainingTime);
    }

    @Override
    public void onScheduleUpdate(Inventory v, int remainingTime) {
        if (remainingTime == 0) {
            v.setItem(40, new ItemStackBuilder(ItemStackBuilder.blackGlassPane).setDisplayName("\u00a7b\u6478\u9c7c\u4e2d").setLoreList("\u00a77\u7b49\u5f85\u539f\u6599\u6216\u71c3\u6599").build());
        } else {
            v.setItem(40, new ItemStackBuilder(Material.BLAZE_POWDER).setDisplayName("\u00a7e\u6b63\u5728\u7194\u5316").setLoreList("\u00a77\u6652\u8db3" + (180 - remainingTime + 1) + "\u5929").build());
        }
    }

    @Override
    public void onRawUpdate(int slot) {
        this.breakCrafting();
        if (this.canCraft()) {
            this.ignite();
            if (this.isIgniting()) {
                this.startCrafting(180);
            }
        }
    }

    @Override
    public void onFinishedUpdate(int slot) {
        this.onRawUpdate(slot);
    }

    public boolean canCraft() {
        ItemStack[] itemStackArray = this.rawItems;
        int n = this.rawItems.length;
        int n2 = 0;
        while (n2 < n) {
            FffffFurnaceRecipe r;
            ItemStack i = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(i) && (r = FffffFurnaceRecipe.getRecipe(i)) != null && ItemStackBuilder.canHold(this.getFinishedItems(), r.getResults().toArray(new ItemStack[0]))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public Furnace getState() {
        return (Furnace)this.getBlock().getState();
    }

    public void ignite() {
        FurnaceUtil.ignite(this.getState());
        this.getViewers().forEach(i -> i.setItem(49, this.getState().getInventory().getFuel()));
    }

    public boolean isIgniting() {
        return this.getState().getBurnTime() > 0;
    }

    @Override
    public void onCraftingFinish() {
        int i = 0;
        while (i < 9) {
            FffffFurnaceRecipe r;
            if (!ItemStackBuilder.isEmpty(this.getRawItems()[i]) && (r = FffffFurnaceRecipe.getRecipe(this.getRawItems()[i])) != null) {
                this.getRawItems()[i].setAmount(this.getRawItems()[i].getAmount() - r.raw.matching(this.getRawItems()[i]).getAmount());
                this.finishedItems = ItemStackBuilder.addItems(this.finishedItems, r.results.toArray(new ItemStack[0]));
                break;
            }
            ++i;
        }
        this.updateRaw();
        this.updateFinished();
        this.onRawUpdate(0);
    }
}

