/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.mail;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mz.tech.DropsName;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.mail.Mailbox;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import mz.tech.util.message.Message;
import mz.tech.util.message.MessageComponent;
import mz.tech.util.message.ShowItemOnMouse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Attachments {
    static Map<Player, Attachments> playersOpen = new HashMap<Player, Attachments>();
    public Inventory inv = Bukkit.createInventory(null, (int)27, (String)"\u9644\u4ef6\u7f16\u8f91");
    public boolean gotten = false;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler
                public void onInventoryClose(InventoryCloseEvent event) {
                    if (playersOpen.containsKey(event.getPlayer())) {
                        Mailbox.get((OfflinePlayer)((OfflinePlayer)event.getPlayer())).draft.show((Player)event.getPlayer());
                        playersOpen.remove(event.getPlayer());
                    }
                }
            }, (Plugin)MzTech.instance);
        }
    }

    public Attachments() {
    }

    public Attachments(NBT nbt) {
        this();
        if (nbt.hasKey("gotten")) {
            this.gotten = nbt.getBoolean("gotten");
        }
        nbt.getChildList("Items").forEach(n -> this.inv.addItem(new ItemStack[]{n.toItemStack()}));
    }

    public NBT save(NBT nbt) {
        nbt.set("gotten", this.gotten);
        ArrayList issNbts = Lists.newArrayList();
        ItemStack[] itemStackArray = this.inv.getStorageContents();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack i = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(i)) {
                issNbts.add(new NBT(i));
            }
            ++n2;
        }
        nbt.set("Items", issNbts.toArray());
        return nbt;
    }

    public NBT save() {
        return this.save(new NBT());
    }

    public void open(Player player) {
        player.openInventory(this.inv);
        playersOpen.put(player, this);
    }

    public boolean isEmpty() {
        ItemStack[] itemStackArray = this.inv.getStorageContents();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(is)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public void show(Player player) {
        Message msg = new Message(new MessageComponent("\u2502 \u00a7e\u00a7l\u9644\u4ef6"));
        this.inv.forEach(i -> {
            if (!ItemStackBuilder.isEmpty(i)) {
                msg.add(new MessageComponent("\n\u2502         "));
                msg.add(new MessageComponent(DropsName.getDropName(i)).setShowOnMouse(new ShowItemOnMouse((ItemStack)i)).setBold(true));
            }
        });
        msg.send(player);
    }

    public void give(Player player) {
        if (this.gotten) {
            throw new RuntimeException("\u4f60\u5df2\u7ecf\u9886\u53d6\u8fc7\u8be5\u9644\u4ef6\u4e86");
        }
        this.gotten = true;
        PlayerUtil util = new PlayerUtil(player);
        ItemStack[] itemStackArray = this.inv.getStorageContents();
        int n = itemStackArray.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack is = itemStackArray[n2];
            if (!ItemStackBuilder.isEmpty(is)) {
                util.give(is);
            }
            ++n2;
        }
    }

    public void close() {
        this.inv.getViewers().forEach(HumanEntity::closeInventory);
    }

    public static void closeAll() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getOpenInventory().getTopInventory() instanceof Attachments) {
                p.closeInventory();
            }
        });
    }

    public static boolean isEmpty(Attachments attachments) {
        return attachments == null || attachments.isEmpty();
    }
}

