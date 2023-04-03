/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import mz.tech.DropsName;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.event.CreativeSetSlotEvent;
import mz.tech.event.ShowChatItem;
import mz.tech.event.ShowMerchantV1_12;
import mz.tech.util.NbtUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ShowItemEvent
extends Event
implements Cancellable,
Listener {
    public Player player;
    public boolean justBag;
    public int slot;
    public ItemStack itemStack;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public ShowItemEvent() {
        try {
            ReflectionWrapper.getNMSClass("PacketPlayOutOpenWindowMerchant").equals(null);
            final Class<?> merchantRecipe = ReflectionWrapper.getNMSClass("MerchantRecipe");
            final Field merchantRecipeBuyingItem1 = ReflectionWrapper.getField(merchantRecipe, "buyingItem1");
            final Field merchantRecipeBuyingItem2 = ReflectionWrapper.getField(merchantRecipe, "buyingItem2");
            final Field merchantRecipeSellingItem = ReflectionWrapper.getField(merchantRecipe, "sellingItem");
            final Field merchantRecipeUses = ReflectionWrapper.getField(merchantRecipe, "uses");
            final Field merchantRecipeMaxUses = ReflectionWrapper.getField(merchantRecipe, "maxUses");
            final Field merchantRecipeRewardExp = ReflectionWrapper.getField(merchantRecipe, "rewardExp");
            ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.LOWEST, new PacketType[]{PacketType.Play.Server.OPEN_WINDOW_MERCHANT}){

                public void onPacketSending(PacketEvent event) {
                    if (event.isCancelled()) {
                        return;
                    }
                    List ols = (List)event.getPacket().getSpecificModifier(List.class).read(0);
                    List ls = (List)ReflectionWrapper.newInstance(ReflectionWrapper.getConstructor(ols.getClass(), new Class[0]), new Object[0]);
                    ls.addAll(ols);
                    int j = 0;
                    while (j < ls.size()) {
                        Object mr = ls.get(j);
                        ArrayList iss = Lists.newArrayList((Object[])new Object[]{ReflectionWrapper.getFieldValue(merchantRecipeBuyingItem1, mr), ReflectionWrapper.getFieldValue(merchantRecipeBuyingItem2, mr), ReflectionWrapper.getFieldValue(merchantRecipeSellingItem, mr)});
                        int i = 0;
                        while (i < iss.size()) {
                            ItemStack is = NbtUtil.asBukkitCopy(iss.get(i));
                            CreativeSetSlotEvent e = new CreativeSetSlotEvent(true, 0, is);
                            try {
                                Bukkit.getPluginManager().callEvent((Event)e);
                            }
                            catch (IllegalStateException e2) {
                                e = new CreativeSetSlotEvent(0, is);
                                Bukkit.getPluginManager().callEvent((Event)e);
                            }
                            iss.set(i, NbtUtil.asNMSCopy(e.is));
                            ++i;
                        }
                        ReflectionWrapper.setFieldValue(merchantRecipeBuyingItem1, mr, iss.get(0));
                        ReflectionWrapper.setFieldValue(merchantRecipeBuyingItem2, mr, iss.get(1));
                        ReflectionWrapper.setFieldValue(merchantRecipeSellingItem, mr, iss.get(2));
                        Object omr = mr;
                        try {
                            mr = MzTech.unsafe.allocateInstance(merchantRecipe);
                        }
                        catch (Throwable e) {
                            MzTech.throwException(e);
                        }
                        ls.set(j, mr);
                        ReflectionWrapper.setFieldValue(merchantRecipeUses, mr, ReflectionWrapper.getFieldValue(merchantRecipeUses, omr));
                        ReflectionWrapper.setFieldValue(merchantRecipeMaxUses, mr, ReflectionWrapper.getFieldValue(merchantRecipeMaxUses, omr));
                        ReflectionWrapper.setFieldValue(merchantRecipeRewardExp, mr, ReflectionWrapper.getFieldValue(merchantRecipeRewardExp, omr));
                        int i2 = 0;
                        while (i2 < iss.size()) {
                            ItemStack is = NbtUtil.asBukkitCopy(iss.get(i2));
                            ShowItemEvent e = new ShowItemEvent(event.getPlayer(), false, i2, is);
                            Bukkit.getPluginManager().callEvent((Event)e);
                            if (e.isCancelled()) {
                                event.setCancelled(true);
                            }
                            iss.set(i2, NbtUtil.asNMSCopy(e.itemStack));
                            ++i2;
                        }
                        ReflectionWrapper.setFieldValue(merchantRecipeBuyingItem1, mr, iss.get(0));
                        ReflectionWrapper.setFieldValue(merchantRecipeBuyingItem2, mr, iss.get(1));
                        ReflectionWrapper.setFieldValue(merchantRecipeSellingItem, mr, iss.get(2));
                        ++j;
                    }
                    event.getPacket().getSpecificModifier(List.class).write(0, (Object)ls);
                }
            });
        }
        catch (Throwable e) {
            MzTech.loadClassUsingNMS("mz.tech.event.ShowMerchantV1_12");
            MzTech.loadClassUsingNMS("mz.tech.event.ShowMerchantV1_12$1");
            ShowMerchantV1_12.start();
        }
        MzTech.loadClassUsingNMS("mz.tech.event.ShowChatItem");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowChatItem$1");
        ShowChatItem.start();
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Server.CHAT}){

            public void onPacketSending(PacketEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                PacketContainer packet = event.getPacket();
                if (event.getPacketType().equals((Object)PacketType.Play.Server.SET_SLOT)) {
                    ItemStack itemStack = (ItemStack)packet.getItemModifier().read(0);
                    ShowItemEvent e = new ShowItemEvent(event.getPlayer(), (Integer)packet.getIntegers().read(0) == 0, (Integer)packet.getIntegers().read(1), itemStack);
                    Bukkit.getPluginManager().callEvent((Event)e);
                    itemStack = e.itemStack;
                    if (e.isCancelled()) {
                        event.setCancelled(true);
                        return;
                    }
                    packet.getItemModifier().write(0, (Object)itemStack);
                } else if (event.getPacketType().equals((Object)PacketType.Play.Server.WINDOW_ITEMS)) {
                    ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                    if (packet.getItemListModifier().size() > 0) {
                        list.addAll((Collection)packet.getItemListModifier().read(0));
                    } else {
                        list.addAll(Arrays.asList((ItemStack[])packet.getItemArrayModifier().read(0)));
                    }
                    int wi = (Integer)packet.getIntegers().read(0);
                    int i = 0;
                    while (i < list.size()) {
                        ShowItemEvent e = new ShowItemEvent(event.getPlayer(), wi == 0, i, (ItemStack)list.get(i));
                        Bukkit.getPluginManager().callEvent((Event)e);
                        list.set(i, e.itemStack);
                        if (e.isCancelled()) {
                            event.setCancelled(true);
                        }
                        ++i;
                    }
                    if (packet.getItemListModifier().size() > 0) {
                        packet.getItemListModifier().write(0, list);
                    } else {
                        packet.getItemArrayModifier().write(0, (Object)list.toArray(new ItemStack[list.size()]));
                    }
                    return;
                }
            }
        });
    }

    public ShowItemEvent(Player player, boolean justBag, int slot, ItemStack itemStack) {
        this.player = player;
        this.justBag = justBag;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @EventHandler
    public void onShowItem(ShowItemEvent event) {
        ItemMeta itemMeta;
        String grassTranslation;
        if (DropsName.growGrassTranslate && (!event.itemStack.hasItemMeta() || !event.itemStack.getItemMeta().hasLocalizedName() && !event.itemStack.getItemMeta().hasDisplayName()) && (grassTranslation = DropsName.getGrowGrassName(event.itemStack)) != null) {
            ItemMeta im = event.itemStack.getItemMeta();
            im.setDisplayName("\u00a7\u751f\u00a7\u8349\u00a7f" + grassTranslation);
            event.itemStack.setItemMeta(im);
        }
        if (event.itemStack.hasItemMeta() && (itemMeta = event.itemStack.getItemMeta()).hasLocalizedName() && !itemMeta.hasDisplayName()) {
            itemMeta.setDisplayName("\u00a7\u65e0\u00a7f" + itemMeta.getLocalizedName());
            event.itemStack.setItemMeta(itemMeta);
        }
    }

    @EventHandler
    void onCreativeSetSlot(CreativeSetSlotEvent event) {
        if (event.is.hasItemMeta()) {
            ItemMeta im = event.is.getItemMeta();
            if (im.hasDisplayName() && im.getDisplayName().startsWith("\u00a7\u751f\u00a7\u8349")) {
                im.setDisplayName(null);
                event.is.setItemMeta(im);
            }
            if (im.hasDisplayName() && im.hasLocalizedName() && im.getDisplayName().startsWith("\u00a7\u65e0")) {
                im.setDisplayName(null);
                event.is.setItemMeta(im);
            }
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

