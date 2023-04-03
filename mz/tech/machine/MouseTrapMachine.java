/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryPickupItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package mz.tech.machine;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.item.MzTechItem;
import mz.tech.item.food.Cheese;
import mz.tech.item.sundry.MouseTail;
import mz.tech.machine.MzTechMachine;
import mz.tech.machine.Trigger;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class MouseTrapMachine
extends MzTechMachine
implements Trigger {
    public Item cheese;

    static {
        TaskUtil.runTaskTimer((Plugin)MzTech.instance, 100L, 100L, () -> MouseTrapMachine.toggleAll());
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.LOWEST)
            public void onInventoryPickupItem(InventoryPickupItemEvent event) {
                if (event.getItem().getPickupDelay() == Short.MAX_VALUE) {
                    event.setCancelled(true);
                }
            }
        }, (Plugin)MzTech.instance);
    }

    @Override
    public String getType() {
        return "\u8001\u9f20\u5939";
    }

    public static void toggleAll() {
        MzTechMachine.forEach(MouseTrapMachine.class, trash -> trash.toggle());
    }

    @Override
    public boolean onRightClick(PlayerInteractEvent event) {
        if (new PlayerUtil(event.getPlayer()).canOpen()) {
            if (this.cheese == null) {
                EquipmentSlot hand = new PlayerUtil(event.getPlayer()).getUsedHand();
                MzTechItem mzTechCopy = MzTechItem.asMzTechCopy(new PlayerUtil(event.getPlayer()).getSlot(hand));
                if (mzTechCopy instanceof Cheese) {
                    try {
                        this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1.0f, 1.0f);
                    }
                    catch (Throwable e) {
                        this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), Enum.valueOf(Sound.class, "BLOCK_METAL_PRESSURE_PLATE_CLICK_ON"), 1.0f, 1.0f);
                    }
                    this.cheese = MzTech.dropItemStack(this.getBlock(), mzTechCopy.clone().setCount(1).build());
                    this.cheese.setVelocity(new Vector());
                    this.cheese.setGravity(false);
                    this.cheese.setPickupDelay(Short.MAX_VALUE);
                    if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        new PlayerUtil(event.getPlayer()).setSlot(hand, mzTechCopy.setCount(mzTechCopy.getAmount() - 1).build());
                    }
                }
            } else {
                try {
                    this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_OFF, 1.0f, 1.0f);
                }
                catch (Throwable e) {
                    this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), Enum.valueOf(Sound.class, "BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF"), 1.0f, 1.0f);
                }
                this.cheese.remove();
                this.cheese = null;
                new PlayerUtil(event.getPlayer()).give(new Cheese());
            }
            return false;
        }
        return true;
    }

    @Override
    public NBT save(NBT nbt) {
        if (this.cheese != null) {
            nbt.set("cheese", this.cheese.getUniqueId());
        }
        return super.save(nbt);
    }

    @Override
    public MzTechMachine load(NBT nbt) {
        if (nbt.hasKey("cheese")) {
            this.cheese = (Item)Bukkit.getEntity((UUID)nbt.getUUID("cheese"));
        }
        return super.load(nbt);
    }

    @Override
    @Deprecated
    public void loadOld(DataInputStream dfi) throws IOException {
        super.loadOld(dfi);
        this.cheese = (Item)Bukkit.getEntity((UUID)DataFile.loadUUID(dfi));
    }

    @Override
    public void onBreak(Player player, boolean silkTouch, boolean drop) {
        if (this.cheese != null) {
            this.cheese.setPickupDelay(-1);
            this.cheese.setGravity(true);
            this.cheese = null;
        }
    }

    public void catchMouse() {
        if (this.cheese != null) {
            this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), new Cheese().getSound(), 1.0f, 1.0f);
            this.cheese.remove();
            this.cheese = null;
            TaskUtil.runTaskLater((Plugin)MzTech.instance, 5L, () -> {
                this.getBlock().getWorld().playSound(this.getBlock().getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                MzTech.dropItemStack(this.getBlock(), (ItemStack)new MouseTail());
            });
        }
    }

    @Override
    public boolean toggle() {
        if (this.cheese != null && MzTech.rand.nextInt(10) == 0) {
            this.catchMouse();
            return true;
        }
        return false;
    }
}

