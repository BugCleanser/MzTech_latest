/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerGameModeChangeEvent
 *  org.bukkit.inventory.CraftingInventory
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import mz.tech.MzTech;
import mz.tech.event.ShowItemEvent;
import mz.tech.item.CraftGuide;
import mz.tech.util.ItemStackBuilder;
import mz.tech.util.TaskUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Baubles
implements Listener {
    public static boolean isButtonShowed(Player player) {
        Inventory crafting = player.getOpenInventory().getTopInventory();
        return ItemStackBuilder.isEmpty(crafting.getItem(0)) && ItemStackBuilder.isEmpty(crafting.getItem(1)) && ItemStackBuilder.isEmpty(crafting.getItem(2)) && ItemStackBuilder.isEmpty(crafting.getItem(3)) && ItemStackBuilder.isEmpty(crafting.getItem(4));
    }

    public static ItemStack getBauble(int slot) {
        switch (slot) {
            case 1: {
                return new ItemStackBuilder(new CraftGuide().getType()).setDisplayName("\u00a7bMZ\u79d1\u6280\u5408\u6210\u6307\u5357").build();
            }
            case 2: {
                return new ItemStackBuilder(Material.BOOK).setDisplayName("\u00a7e\u9970\u54c1\u680f").addLore("\u00a74\u5f00\u53d1\u4e2d", "\u00a77\u6253\u4e0d\u5f00\u7684\u54df").build();
            }
            case 3: {
                return new ItemStackBuilder(ItemStackBuilder.lightGrayGlassPane).setDisplayName("\u00a77\u5f85\u5f00\u53d1\u7684\u529f\u80fd\u952e").build();
            }
            case 4: {
                return new ItemStackBuilder(ItemStackBuilder.lightGrayGlassPane).setDisplayName("\u00a77\u5f85\u5f00\u53d1\u7684\u529f\u80fd\u952e").build();
            }
        }
        return null;
    }

    @EventHandler
    void onShowItem(ShowItemEvent event) {
        if (event.justBag && event.player.getGameMode() != GameMode.CREATIVE && Baubles.isButtonShowed(event.player) && event.slot > 0 && event.slot < 5) {
            event.itemStack = Baubles.getBauble(event.slot);
        }
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory() instanceof CraftingInventory && event.getRawSlot() > 0 && event.getRawSlot() < 5) {
            if (ItemStackBuilder.isEmpty(event.getCursor()) && Baubles.isButtonShowed((Player)event.getWhoClicked())) {
                PacketContainer pc = new PacketContainer(PacketType.Play.Server.SET_SLOT);
                pc.getIntegers().write(0, (Object)0);
                pc.getIntegers().write(1, (Object)event.getRawSlot());
                pc.getItemModifier().write(0, (Object)event.getView().getTopInventory().getItem(event.getRawSlot()));
                TaskUtil.throwRuntime(() -> ProtocolLibrary.getProtocolManager().sendServerPacket((Player)event.getWhoClicked(), pc));
                switch (event.getRawSlot()) {
                    case 1: {
                        CraftGuide.open((Player)event.getWhoClicked());
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                }
            } else {
                int i = 1;
                while (i < 5) {
                    PacketContainer pc = new PacketContainer(PacketType.Play.Server.SET_SLOT);
                    pc.getIntegers().write(0, (Object)0);
                    pc.getIntegers().write(1, (Object)i);
                    pc.getItemModifier().write(0, (Object)event.getView().getTopInventory().getItem(i));
                    TaskUtil.runTask((Plugin)MzTech.instance, () -> TaskUtil.throwRuntime(() -> ProtocolLibrary.getProtocolManager().sendServerPacket((Player)event.getWhoClicked(), pc)));
                    ++i;
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        switch (event.getPlayer().getGameMode()) {
            case CREATIVE: {
                if (event.getNewGameMode() == GameMode.CREATIVE) break;
                int i = 1;
                while (i < 5) {
                    PacketContainer pc = new PacketContainer(PacketType.Play.Server.SET_SLOT);
                    pc.getIntegers().write(0, (Object)0);
                    pc.getIntegers().write(1, (Object)i);
                    pc.getItemModifier().write(0, (Object)Baubles.getBauble(i));
                    TaskUtil.runTask((Plugin)MzTech.instance, () -> TaskUtil.throwRuntime(() -> ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), pc)));
                    ++i;
                }
                break;
            }
            default: {
                if (event.getNewGameMode() != GameMode.CREATIVE) break;
                int i = 1;
                while (i < 5) {
                    PacketContainer pc = new PacketContainer(PacketType.Play.Server.SET_SLOT);
                    pc.getIntegers().write(0, (Object)0);
                    pc.getIntegers().write(1, (Object)i);
                    pc.getItemModifier().write(0, (Object)ItemStackBuilder.air);
                    TaskUtil.runTask((Plugin)MzTech.instance, () -> TaskUtil.throwRuntime(() -> ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), pc)));
                    ++i;
                }
                break block0;
            }
        }
    }
}

