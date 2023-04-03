/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.item;

import java.util.HashMap;
import java.util.Map;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.item.baseMachine.ConversionTable;
import mz.tech.item.sundry.RedPacket;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class RedPacketInventory {
    public static Map<Player, RedPacketInventory> playersOpen = new HashMap<Player, RedPacketInventory>();
    Inventory inv = Bukkit.createInventory(null, (int)9, (String)"\u7ea2\u5305\u91d1\u989d\u7f16\u8f91\uff08\u5173\u95ed\u8be5\u754c\u9762\u540e\u7acb\u5373\u6253\u5305\u4e0d\u53ef\u66f4\u6539\uff09");
    RedPacket redpacket;
    float value = 0.0f;

    static {
        if (MzTech.instance.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(new Listener(){

                @EventHandler(priority=EventPriority.HIGHEST)
                public void onInventoryClose(InventoryCloseEvent event) {
                    if (playersOpen.containsKey(event.getPlayer())) {
                        RedPacket redpacket = RedPacketInventory.playersOpen.get((Object)event.getPlayer()).redpacket;
                        redpacket.setLoreList(new String[0]);
                        NBT nbt = new NBT(redpacket.build());
                        NBT tag = nbt.getChild("tag");
                        tag.set("value", ((RedPacketInventory)event.getInventory()).value);
                        nbt.set("tag", tag);
                        nbt.set(redpacket);
                        new PlayerUtil((Player)event.getPlayer()).give(redpacket.build());
                        playersOpen.remove(event.getPlayer());
                    }
                }

                @EventHandler(priority=EventPriority.HIGHEST)
                public void onInventoryClick(InventoryClickEvent event) {
                    if (event.isCancelled()) {
                        return;
                    }
                    if (playersOpen.containsKey(event.getWhoClicked())) {
                        switch (event.getSlot()) {
                            case 0: {
                                playersOpen.get(event.getWhoClicked()).subtract(event.getWhoClicked(), 10000.0f);
                                break;
                            }
                            case 1: {
                                playersOpen.get(event.getWhoClicked()).subtract(event.getWhoClicked(), 500.0f);
                                break;
                            }
                            case 2: {
                                playersOpen.get(event.getWhoClicked()).subtract(event.getWhoClicked(), 10.0f);
                                break;
                            }
                            case 3: {
                                playersOpen.get(event.getWhoClicked()).subtract(event.getWhoClicked(), 0.2f);
                                break;
                            }
                            case 5: {
                                playersOpen.get(event.getWhoClicked()).add(event.getWhoClicked(), 0.2f);
                                break;
                            }
                            case 6: {
                                playersOpen.get(event.getWhoClicked()).add(event.getWhoClicked(), 10.0f);
                                break;
                            }
                            case 7: {
                                playersOpen.get(event.getWhoClicked()).add(event.getWhoClicked(), 500.0f);
                                break;
                            }
                            case 8: {
                                playersOpen.get(event.getWhoClicked()).add(event.getWhoClicked(), 10000.0f);
                            }
                        }
                        event.setCancelled(true);
                    }
                }
            }, (Plugin)MzTech.instance);
        }
    }

    public RedPacketInventory(RedPacket redpacket) {
        this.redpacket = redpacket;
        this.inv.setItem(0, ItemStackBuilder.redGlassPane.clone().setDisplayName("\u00a74\u51cf\u5c11\uffe510000").build());
        this.inv.setItem(1, ItemStackBuilder.redGlassPane.clone().setDisplayName("\u00a74\u51cf\u5c11\uffe5500").build());
        this.inv.setItem(2, ItemStackBuilder.redGlassPane.clone().setDisplayName("\u00a74\u51cf\u5c11\uffe510").build());
        this.inv.setItem(3, ItemStackBuilder.redGlassPane.clone().setDisplayName("\u00a74\u51cf\u5c11\uffe50.2").build());
        this.inv.setItem(4, redpacket.build());
        this.inv.setItem(5, ItemStackBuilder.limeGlassPane.clone().setDisplayName("\u00a7a\u589e\u52a0\uffe50.2").build());
        this.inv.setItem(6, ItemStackBuilder.limeGlassPane.clone().setDisplayName("\u00a7a\u589e\u52a0\uffe510").build());
        this.inv.setItem(7, ItemStackBuilder.limeGlassPane.clone().setDisplayName("\u00a7a\u589e\u52a0\uffe5500").build());
        this.inv.setItem(8, ItemStackBuilder.limeGlassPane.clone().setDisplayName("\u00a7a\u589e\u52a0\uffe510000").build());
    }

    public static void closeAll() {
        playersOpen.forEach((p, i) -> p.closeInventory());
    }

    public void open(Player player) {
        player.openInventory(this.inv);
        playersOpen.put(player, this);
    }

    public void add(HumanEntity player, float v) {
        if (ConversionTable.econ.has((OfflinePlayer)((Player)player), (double)v)) {
            this.value += v;
            ConversionTable.econ.withdrawPlayer((OfflinePlayer)((Player)player), (double)v);
            this.redpacket.setMoney(this.value);
            this.inv.setItem(4, this.redpacket.build());
        } else {
            MzTech.sendMessage((CommandSender)player, "\u00a74\u4f59\u989d\u4e0d\u8db3");
        }
    }

    public void subtract(HumanEntity player, float v) {
        if (this.value >= v) {
            ConversionTable.econ.depositPlayer((OfflinePlayer)((Player)player), (double)v);
            this.value -= v;
            this.redpacket.setMoney(this.value);
            this.inv.setItem(4, this.redpacket.build());
        }
    }
}

