/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.mail;

import java.util.HashMap;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.mail.Mail;
import mz.tech.mail.Mailbox;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MailboxGuide {
    public static Map<Player, MailboxGuide> playersOpen = new HashMap<Player, MailboxGuide>();
    static ItemStack oneKeyGetIcon = ItemStackBuilder.newSkull(null, MzTech.newUUID(1624010392499L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTllOTM4MTgxZDhjOTZiNGY1OGY2MzMyZDNkZDIzM2VjNWZiODUxYjVhODQwNDM4ZWFjZGJiNjE5YTNmNWYifX19").setDisplayName("\u00a7b\u4e00\u952e\u9886\u53d6").setLoreList("\u00a7b\u9886\u53d6\u6240\u6709\u9644\u4ef6").build();
    static ItemStack sendIcon = ItemStackBuilder.newSkull(null, MzTech.newUUID(486124241291666L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY5MmRkYTNlYjNkNmZmNGNhYjcyMWNjNWMwZWY4ZjI5MWFjMGY5ZjFkYTlkZmRhODczY2IwOGM2ZTE5ODVkMiJ9fX0=").setDisplayName("\u00a75\u8349\u7a3f").setLoreList("\u00a77\u53d1\u9001\u90ae\u4ef6").build();
    static ItemStack oneKeyDeleteIcon = ItemStackBuilder.newSkull(null, MzTech.newUUID(624565593644L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJkNDEwNDJjZTk5MTQ3Y2MzOGNhYzllNDY3NDE1NzZlN2VlNzkxMjgzZTZmYWM4ZDMyOTJjYWUyOTM1ZjFmIn19fQ==").setDisplayName("\u00a74\u4e00\u952e\u5220\u9664").setLoreList("\u00a77\u5220\u9664\u6240\u6709\u5df2\u8bfb\u90ae\u4ef6").build();
    static ItemStackBuilder unreadMailIcon = ItemStackBuilder.newSkull(null, MzTech.newUUID(84214311245552L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDIzZjY1MmNkNTNhNjIxZjY4ODAxZmFhN2FkNzIyNjFlNjBlY2I0N2IyNTUxN2ZjMTdlODg0YzI0YjhlNzlmOSJ9fX0=");
    static ItemStackBuilder readMailIcon = ItemStackBuilder.newSkull(null, MzTech.newUUID(842145852546652L), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzI4Y2I4YTU3MmUzY2U5OTNjY2VjNGY2OWU2ZjM3YTg1OWNkNWNlMGQ0NjZiODBhMTIzYzA5NDkzNWVkNGMyMiJ9fX0=");
    public Inventory inv = Bukkit.createInventory(null, (int)36, (String)"\u90ae\u7bb1");
    public Mailbox mb;
    public int thisPageNum;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
                public void onInventoryClose(InventoryCloseEvent event) {
                    if (playersOpen.containsKey(event.getPlayer())) {
                        playersOpen.remove(event.getPlayer());
                    }
                }

                @EventHandler(priority=EventPriority.LOWEST)
                public void onInventoryClick(InventoryClickEvent event) {
                    if (event.isCancelled()) {
                        return;
                    }
                    if (playersOpen.containsKey(event.getWhoClicked())) {
                        event.setCancelled(true);
                        if (event.getClickedInventory().getType() == InventoryType.CHEST) {
                            MailboxGuide inventory = playersOpen.get(event.getWhoClicked());
                            Mailbox mb = Mailbox.get((OfflinePlayer)event.getWhoClicked());
                            if (event.getSlot() < 27) {
                                if (inventory.hasSlotMail(event.getSlot())) {
                                    if (event.isLeftClick()) {
                                        event.getWhoClicked().closeInventory();
                                        inventory.getMail(event.getSlot()).show((Player)event.getWhoClicked());
                                    } else if (event.isRightClick()) {
                                        try {
                                            mb.delete(inventory.getMail(event.getSlot()));
                                            inventory.update();
                                        }
                                        catch (RuntimeException e) {
                                            MzTech.sendMessage((CommandSender)event.getWhoClicked(), "\u00a74" + e.getMessage());
                                        }
                                    }
                                }
                            } else {
                                switch (event.getSlot()) {
                                    case 28: {
                                        if (inventory.thisPageNum <= 0) break;
                                        --inventory.thisPageNum;
                                        inventory.update();
                                        break;
                                    }
                                    case 30: {
                                        int[] n = new int[1];
                                        mb.inbox.forEach(m -> {
                                            if (m.attachments != null && !m.attachments.isEmpty() && !m.attachments.gotten) {
                                                m.read = true;
                                                m.attachments.give((Player)event.getWhoClicked());
                                                nArray[0] = n[0] + 1;
                                            }
                                        });
                                        MzTech.sendMessage((CommandSender)event.getWhoClicked(), "\u00a7a\u6210\u529f\u9886\u53d6\u4e86" + n[0] + "\u4e2a\u9644\u4ef6");
                                        inventory.update();
                                        break;
                                    }
                                    case 31: {
                                        event.getWhoClicked().closeInventory();
                                        mb.draft.show((Player)event.getWhoClicked());
                                        break;
                                    }
                                    case 32: {
                                        int num = mb.getReadMails().size();
                                        mb.getReadMails().forEach(m -> {
                                            boolean bl = mailbox.inbox.remove(m);
                                        });
                                        MzTech.sendMessage((CommandSender)event.getWhoClicked(), "\u00a7a\u6210\u529f\u5220\u9664" + num + "\u4e2a\u90ae\u4ef6");
                                        inventory.update();
                                        break;
                                    }
                                    case 34: {
                                        if (inventory.thisPageNum >= inventory.getMaxPage()) break;
                                        ++inventory.thisPageNum;
                                        inventory.update();
                                    }
                                }
                            }
                        }
                    }
                }
            }, (Plugin)MzTech.instance);
        }
    }

    public void setItem(int arg0, ItemStack arg1) {
        this.inv.setItem(arg0, arg1);
    }

    public MailboxGuide(Mailbox mb) {
        this.mb = mb;
        this.thisPageNum = 0;
        this.update();
        ItemStack yellowGlassPane = ItemStackBuilder.yellowGlassPane.clone().setDisplayName("\u00a70").build();
        this.setItem(27, yellowGlassPane);
        this.setItem(28, ItemStackBuilder.lightBlueGlassPane.clone().setDisplayName("\u00a7a\u4e0a\u4e00\u9875").build());
        this.setItem(29, yellowGlassPane);
        this.setItem(30, oneKeyGetIcon);
        this.setItem(31, sendIcon);
        this.setItem(32, oneKeyDeleteIcon);
        this.setItem(33, yellowGlassPane);
        this.setItem(34, ItemStackBuilder.lightBlueGlassPane.clone().setDisplayName("\u00a7a\u4e0a\u4e00\u9875").build());
        this.setItem(35, yellowGlassPane);
    }

    public void update() {
        if (this.thisPageNum > this.getMaxPage()) {
            this.thisPageNum = this.getMaxPage();
        }
        int i = 0;
        while (i < 27) {
            if (this.hasSlotMail(i)) {
                Mail m = this.getMail(i);
                this.setItem(i, (m.read ? readMailIcon : unreadMailIcon).clone().setDisplayName("\u00a76" + m.title).setLoreList("\u00a77\u53d1\u4ef6\u4eba\uff1a " + m.from.getName(), "\u00a77\u53d1\u9001\u65f6\u95f4\uff1a " + m.time, m.read ? "\u00a7f\u5df2\u8bfb" : "\u00a7f\u672a\u8bfb").setCount(m.read ? 1 : 2).build());
            } else {
                this.setItem(i, new ItemStack(Material.AIR));
            }
            ++i;
        }
    }

    public void show(Player player) {
        player.openInventory(this.inv);
        playersOpen.put(player, this);
    }

    public int getMaxPage() {
        return (this.mb.inbox.size() - 1) / 27;
    }

    public Mail getMail(int slot) {
        if (slot == 31) {
            return this.mb.draft;
        }
        if (slot < 0 || slot >= 27) {
            return null;
        }
        return this.mb.inbox.get(this.thisPageNum * 27 + slot);
    }

    public boolean hasSlotMail(int slot) {
        if (slot == 31) {
            return true;
        }
        if (slot < 0 || slot >= 27) {
            return false;
        }
        return this.thisPageNum * 27 + slot < this.mb.inbox.size();
    }

    public static void closeAll() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getOpenInventory().getTopInventory() instanceof MailboxGuide) {
                p.closeInventory();
            }
        });
    }
}

