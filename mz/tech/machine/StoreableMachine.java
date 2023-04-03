/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryInteractEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.IOException;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.event.MoveItemEvent;
import mz.tech.machine.InventoryMachine;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class StoreableMachine
extends MzTechMachine
implements InventoryMachine {
    private Inventory inv;

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.HIGHEST)
            public void onMoveItem(MoveItemEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                MzTechMachine.forEach(StoreableMachine.class, machine -> {
                    if (machine.getInventory().getViewers().contains(moveItemEvent.cause.getView().getPlayer())) {
                        machine.onMoveItem(event);
                    }
                });
            }

            @EventHandler(priority=EventPriority.HIGHEST)
            public void onInventoryClose(InventoryCloseEvent event) {
                MzTechMachine.forEach(StoreableMachine.class, machine -> {
                    Sound sound;
                    if (machine.getInventory().equals(event.getInventory()) && (sound = machine.getCloseSound()) != null) {
                        machine.getBlock().getWorld().playSound(machine.getBlock().getLocation().add(0.5, 0.5, 0.5), sound, 1.0f, 1.0f);
                    }
                });
            }
        }, (Plugin)MzTech.instance);
    }

    @Override
    public MzTechMachine setBlock(Block block) {
        if (this.inv == null) {
            this.inv = Bukkit.createInventory(null, (int)this.getSize(), (String)this.getTitle());
        }
        return super.setBlock(block);
    }

    public static boolean isInventory(Class<? extends StoreableMachine> clazz, Inventory inv) {
        boolean[] rb = new boolean[1];
        MzTechMachine.forEach(StoreableMachine.class, machine -> {
            if (machine.getInventory() == inv) {
                blArray[0] = true;
            }
        });
        return rb[0];
    }

    public void onMoveItem(MoveItemEvent event) {
    }

    @Override
    public Inventory getInventory() {
        return this.inv;
    }

    @Override
    public void setInventory(Inventory inv) {
        this.inv = inv;
    }

    public abstract int getSize();

    public abstract String getTitle();

    @Override
    public NBT save(NBT nbt) {
        Lists.newArrayList((Iterable)this.inv.getViewers()).forEach(e -> e.closeInventory());
        nbt.set("inventory", new NBT(this.inv));
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        this.inv = nbt.getChild("inventory").toInventory(this.getTitle());
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        this.inv = DataFile.readInventory(dfi, this.getTitle());
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        super.onRightClick(event);
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            this.openInventory((HumanEntity)event.getPlayer());
            return false;
        }
        return true;
    }

    public void openInventory(HumanEntity player) {
        player.openInventory(this.inv);
        if (this.getOpenSound() != null) {
            this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), this.getOpenSound(), 1.0f, 1.0f);
        }
    }

    public Sound getOpenSound() {
        return Sound.BLOCK_CHEST_OPEN;
    }

    public Sound getCloseSound() {
        return Sound.BLOCK_CHEST_CLOSE;
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        Lists.newArrayList((Iterable)this.inv.getViewers()).forEach(e -> e.closeInventory());
        int i = 0;
        while (i < this.getSize()) {
            ItemStack is = this.inv.getItem(i);
            if (!ItemStackBuilder.isEmpty(is)) {
                InventoryClickEvent ie = null;
                try {
                    ie = (InventoryClickEvent)MzTech.unsafe.allocateInstance(InventoryClickEvent.class);
                    ie.setCancelled(false);
                }
                catch (Throwable e2) {
                    throw new RuntimeException(e2);
                }
                MoveItemEvent e3 = new MoveItemEvent((InventoryInteractEvent)ie, new Slot(this.inv, i), new Slot(){

                    @Override
                    public Inventory getInventory() {
                        return null;
                    }

                    @Override
                    public ItemStack getItemStack() {
                        return new ItemStack(Material.AIR);
                    }

                    @Override
                    public void setItemStack(ItemStack is) {
                    }
                }, is.getAmount());
                this.onMoveItem(e3);
                if (!e3.isCancelled() && !ItemStackBuilder.isEmpty(is)) {
                    MzTech.dropItemStack(this.getBlock(), is);
                }
            }
            ++i;
        }
    }

    public void addItems(ItemStack ... iss) {
        int size = this.getInventory().getSize();
        for (ItemStack is : Lists.newArrayList((Object[])iss)) {
            if (ItemStackBuilder.isEmpty(is)) continue;
            int i = 0;
            while (i < size) {
                if (is.isSimilar(this.getInventory().getItem(i))) {
                    if (is.getAmount() + this.getInventory().getItem(i).getAmount() > is.getType().getMaxStackSize()) {
                        this.getInventory().getItem(i).setAmount(is.getType().getMaxStackSize());
                        is.setAmount(is.getAmount() - is.getType().getMaxStackSize() + this.getInventory().getItem(i).getAmount());
                    } else {
                        this.getInventory().getItem(i).setAmount(this.getInventory().getItem(i).getAmount() + is.getAmount());
                        is.setAmount(0);
                    }
                }
                ++i;
            }
            if (ItemStackBuilder.isEmpty(is)) continue;
            if (this.getInventory().firstEmpty() < 0) {
                MzTech.dropItemStack(this.getBlock(), is);
                continue;
            }
            this.getInventory().setItem(this.getInventory().firstEmpty(), is);
        }
    }
}

