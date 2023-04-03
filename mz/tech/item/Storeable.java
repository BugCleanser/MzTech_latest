/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.event.MoveItemEvent;
import mz.tech.item.MzTechItem;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class Storeable
extends MzTechItem {
    private static Map<Player, Storeable> playersOpenStoreables = new HashMap<Player, Storeable>();
    private Inventory inv;

    static {
        try {
            new File(String.valueOf(MzTech.instance.getDataFolder().getAbsolutePath()) + "/bags").mkdirs();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler(priority=EventPriority.MONITOR)
            public void onInventoryClose(InventoryCloseEvent event) {
                if (playersOpenStoreables.containsKey(event.getPlayer())) {
                    event.getPlayer().getWorld().playSound(event.getPlayer().getEyeLocation(), ((Storeable)((Object)playersOpenStoreables.get(event.getPlayer()))).getCloseSound(), 1.0f, 1.0f);
                    ((Storeable)((Object)playersOpenStoreables.get(event.getPlayer()))).setInventory(((Storeable)((Object)playersOpenStoreables.get(event.getPlayer()))).inv);
                    playersOpenStoreables.remove(event.getPlayer());
                }
            }

            @EventHandler(priority=EventPriority.HIGHEST)
            public void onMoveItem(MoveItemEvent event) {
                playersOpenStoreables.forEach((p, s) -> {
                    if (moveItemEvent.cause.getView().getTopInventory().equals(((Storeable)s).inv) && !s.onMoveItem(event)) {
                        event.setCancelled(true);
                    }
                });
            }

            @EventHandler(priority=EventPriority.LOWEST)
            public void onPlayerDropItem(PlayerDropItemEvent event) {
                if (playersOpenStoreables.containsKey(event.getPlayer())) {
                    event.setCancelled(true);
                }
            }
        }, (Plugin)MzTech.instance);
    }

    public boolean onMoveItem(MoveItemEvent event) {
        boolean[] rb = new boolean[]{true};
        (event.getNum() > 0 ? Lists.newArrayList((Object[])new ItemStack[]{event.getFrom().getItemStack()}) : Lists.newArrayList((Object[])new ItemStack[]{event.getFrom().getItemStack(), event.getTo().getItemStack()})).forEach(is -> {
            MzTechItem copy = MzTechItem.asMzTechCopy(is);
            if (copy != null && copy instanceof Storeable && ((Storeable)copy).getId().equals(this.getId())) {
                blArray[0] = false;
            }
        });
        return rb[0];
    }

    public Storeable(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract int getSize();

    public abstract String getTitle();

    public void open(Player player) {
        if (this.getAmount() != 1) {
            MzTech.sendMessage((CommandSender)player, "\u00a7e\u4e00\u6b21\u53ea\u80fd\u6253\u5f00\u4e00\u4e2a\u7269\u54c1\u680f");
            return;
        }
        player.openInventory(this.getInventory());
        player.getWorld().playSound(player.getEyeLocation(), this.getOpenSound(), 1.0f, 1.0f);
        playersOpenStoreables.put(player, this);
    }

    @Override
    public boolean onRightClickAir(Player player, EquipmentSlot hand) {
        this.open(player);
        return false;
    }

    @Override
    public boolean onRightClickEntity(Player player, EquipmentSlot hand, Entity entity) {
        this.open(player);
        return false;
    }

    @Override
    public boolean onRightClickBlock(Player player, EquipmentSlot hand, Block block) {
        TaskUtil.runTask((Plugin)MzTech.instance, () -> {
            MzTechItem mzTechCopy;
            if (player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING && (mzTechCopy = MzTechItem.asMzTechCopy(hand == EquipmentSlot.HAND ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand())) instanceof Storeable) {
                ((Storeable)mzTechCopy).open(player);
                new PlayerUtil(player).setSlot(hand, mzTechCopy);
            }
        });
        return true;
    }

    @Override
    public void onDisable() {
        playersOpenStoreables.forEach((player, inv) -> {
            player.closeInventory();
            inv.setInventory(inv.inv);
        });
    }

    public static File getFile(String id) {
        return new File(String.valueOf(MzTech.instance.getDataFolder().getAbsolutePath()) + "/bags/" + id + ".inv");
    }

    public String getId() {
        String[] id = new String[1];
        if (this.hasItemMeta() && this.getItemMeta().hasLore()) {
            this.getItemMeta().getLore().forEach(lore -> {
                if (lore.startsWith("\u00a70ID: ")) {
                    stringArray[0] = lore.substring("\u00a70ID: ".length());
                }
            });
        }
        if (id[0] == null) {
            int i = 0;
            while (true) {
                File f;
                if (!(f = Storeable.getFile("" + i)).exists()) {
                    id[0] = "" + i;
                    ItemMeta im = this.getItemMeta();
                    if (!im.hasLore()) {
                        im.setLore((List)Lists.newArrayList((Object[])new String[]{"\u00a70ID: " + id[0]}));
                    } else {
                        List lore2 = im.getLore();
                        lore2.add("\u00a70ID: " + id[0]);
                        im.setLore(lore2);
                    }
                    this.setItemMeta(im);
                    TaskUtil.throwRuntime(() -> {
                        f.createNewFile();
                        Throwable throwable = null;
                        Object var3_4 = null;
                        try (DataOutputStream fos = new DataOutputStream(new FileOutputStream(f));){
                            DataFile.writeInventory(fos, Bukkit.createInventory(null, (int)this.getSize(), (String)this.getTitle()));
                        }
                        catch (Throwable throwable2) {
                            if (throwable == null) {
                                throwable = throwable2;
                            } else if (throwable != throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                            throw throwable;
                        }
                    });
                    break;
                }
                ++i;
            }
        }
        return id[0];
    }

    public void setId(String id) {
        this.setItemMeta(new ItemStackBuilder(this).setInfoToLore("\u00a70ID: ", id).build().getItemMeta());
    }

    public Inventory getInventory() {
        if (this.inv == null) {
            TaskUtil.throwRuntime(() -> {
                Throwable throwable = null;
                Object var2_3 = null;
                try (DataInputStream fis = new DataInputStream(new FileInputStream(Storeable.getFile(this.getId())));){
                    this.inv = DataFile.readInventory(fis, this.getTitle());
                }
                catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            });
        }
        return this.inv;
    }

    public void setInventory(Inventory inv) {
        this.inv = inv;
        TaskUtil.throwRuntime(() -> {
            Throwable throwable = null;
            Object var2_3 = null;
            try (DataOutputStream fos = new DataOutputStream(new FileOutputStream(Storeable.getFile(this.getId())));){
                DataFile.writeInventory(fos, this.inv);
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        });
    }

    public Sound getOpenSound() {
        return Sound.ENTITY_BAT_TAKEOFF;
    }

    public Sound getCloseSound() {
        return this.getOpenSound();
    }

    @Override
    public boolean giveCommand(String[] args) {
        switch (args.length) {
            case 0: {
                return true;
            }
            case 1: {
                this.setId(args[0]);
                return true;
            }
        }
        return false;
    }

    @Override
    public String giveCommandArgs() {
        return "[\u7269\u54c1\u680fID]";
    }
}

