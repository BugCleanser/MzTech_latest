/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.HoverEvent$Action
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.minecraft.server.v1_12_R1.ChatComponentText
 *  net.minecraft.server.v1_12_R1.ChatHoverable
 *  net.minecraft.server.v1_12_R1.ChatHoverable$EnumHoverAction
 *  net.minecraft.server.v1_12_R1.ChatMessage
 *  net.minecraft.server.v1_12_R1.ChatModifier
 *  net.minecraft.server.v1_12_R1.IChatBaseComponent
 *  net.minecraft.server.v1_16_R3.ChatHoverable
 *  net.minecraft.server.v1_16_R3.ChatHoverable$c
 *  net.minecraft.server.v1_16_R3.ChatMessage
 *  net.minecraft.server.v1_16_R3.ChatModifier
 *  net.minecraft.server.v1_16_R3.IChatBaseComponent
 *  net.minecraft.server.v1_16_R3.IMaterial
 *  net.minecraft.server.v1_16_R3.ItemStack
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import java.lang.reflect.Field;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.event.ShowItemEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.ChatHoverable;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.ChatHoverable;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.ChatModifier;
import net.minecraft.server.v1_16_R3.IMaterial;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class ShowChatItem {
    public static Class<?> chatHoverableC;
    public static Field chatHoverableCA;
    public static Field chatHoverableCB;

    static {
        try {
            chatHoverableC = ReflectionWrapper.getNMSClass("ChatHoverable$c");
            chatHoverableCA = ReflectionWrapper.getField(chatHoverableC, "a");
            chatHoverableCB = ReflectionWrapper.getField(chatHoverableC, "b");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private ShowChatItem() {
    }

    public static void start() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.MONITOR, new PacketType[]{PacketType.Play.Server.CHAT}){

            public void onPacketSending(PacketEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                BaseComponent[] md5 = (BaseComponent[])event.getPacket().getSpecificModifier(BaseComponent[].class).read(0);
                if (md5 != null) {
                    int i = 0;
                    while (i < md5.length) {
                        if (md5[i].getHoverEvent() != null && md5[i].getHoverEvent().getAction() == HoverEvent.Action.SHOW_ITEM) {
                            ItemStack is = new NBT(((TextComponent)md5[i].getHoverEvent().getValue()[0]).getText()).toItemStack();
                            ShowItemEvent e = new ShowItemEvent(null, false, -1, is);
                            Bukkit.getPluginManager().callEvent((Event)e);
                            if (e.isCancelled()) {
                                event.setCancelled(true);
                            }
                            md5[i].getHoverEvent().getValue()[0] = new TextComponent(new NBT(e.itemStack).toString());
                        }
                        ++i;
                    }
                } else {
                    ShowChatItem.forChatBaseComponent((IChatBaseComponent)event.getPacket().getSpecificModifier(IChatBaseComponent.class).read(0));
                }
            }
        });
    }

    public static void forChatBaseComponent(IChatBaseComponent c2) {
        ChatHoverable h;
        net.minecraft.server.v1_12_R1.ChatModifier m;
        List l;
        try {
            l = c2.a();
        }
        catch (NoSuchMethodError e) {
            l = (List)MzTech.castBypassCheck(((net.minecraft.server.v1_16_R3.IChatBaseComponent)MzTech.castBypassCheck(c2)).getSiblings());
        }
        if (l != null) {
            l.forEach(i -> ShowChatItem.forChatBaseComponent(i));
        }
        if (c2 instanceof net.minecraft.server.v1_12_R1.ChatMessage) {
            Object[] os;
            try {
                os = ((net.minecraft.server.v1_12_R1.ChatMessage)c2).j();
            }
            catch (NoSuchMethodError e) {
                os = ((ChatMessage)MzTech.castBypassCheck(c2)).getArgs();
            }
            Object[] objectArray = os;
            int n = os.length;
            int n2 = 0;
            while (n2 < n) {
                Object o = objectArray[n2];
                if (o instanceof IChatBaseComponent) {
                    ShowChatItem.forChatBaseComponent((IChatBaseComponent)o);
                }
                ++n2;
            }
        }
        if ((m = c2.getChatModifier()) == null) {
            return;
        }
        try {
            h = m.i();
        }
        catch (NoSuchMethodError e) {
            h = (ChatHoverable)MzTech.castBypassCheck(((ChatModifier)MzTech.castBypassCheck(m)).getHoverEvent());
        }
        if (h != null && h.a() == ChatHoverable.EnumHoverAction.SHOW_ITEM) {
            ChatHoverable.c chc;
            ShowItemEvent e;
            try {
                e = new ShowItemEvent(null, false, -1, new NBT(h.b().getText()).toItemStack());
            }
            catch (NoSuchMethodError e1) {
                chc = (ChatHoverable.c)((net.minecraft.server.v1_16_R3.ChatHoverable)MzTech.castBypassCheck(h)).a(((net.minecraft.server.v1_16_R3.ChatHoverable)MzTech.castBypassCheck(h)).a());
                if (chc == null) {
                    return;
                }
                e = new ShowItemEvent(null, false, -1, CraftItemStack.asBukkitCopy((net.minecraft.server.v1_16_R3.ItemStack)new net.minecraft.server.v1_16_R3.ItemStack((IMaterial)ReflectionWrapper.getFieldValue(chatHoverableCA, chc), ((Integer)ReflectionWrapper.getFieldValue(chatHoverableCB, chc)).intValue())));
            }
            Bukkit.getPluginManager().callEvent((Event)e);
            if (e.isCancelled()) {
                m.setChatHoverable(null);
            } else {
                try {
                    m.setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ITEM, (IChatBaseComponent)new ChatComponentText(new NBT(e.itemStack).toString())));
                }
                catch (NoSuchMethodError e1) {
                    chc = (ChatHoverable.c)((net.minecraft.server.v1_16_R3.ChatHoverable)MzTech.castBypassCheck(h)).a(((net.minecraft.server.v1_16_R3.ChatHoverable)MzTech.castBypassCheck(h)).a());
                    net.minecraft.server.v1_16_R3.ItemStack nmsi = CraftItemStack.asNMSCopy((ItemStack)e.itemStack);
                    ReflectionWrapper.setFieldValue(chatHoverableCA, chc, MzTech.castBypassCheck(nmsi.getItem()));
                    ReflectionWrapper.setFieldValue(chatHoverableCB, chc, nmsi.getCount());
                }
            }
        }
    }
}

