/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.StringReader
 *  net.minecraft.server.v1_12_R1.EntityPlayer
 *  net.minecraft.server.v1_12_R1.IChatBaseComponent
 *  net.minecraft.server.v1_12_R1.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_12_R1.MinecraftServer
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package mz.tech.util.message;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import mz.tech.util.TaskUtil;
import mz.tech.util.message.MessageComponent;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Message
extends ArrayList<MessageComponent> {
    private static final long serialVersionUID = -5268371894679282201L;
    public static String minecraftVersion = MinecraftServer.getServer().getVersion();
    static Class<?> argumentChatComponentClass;
    static Object argumentChatComponent;
    static Method argumentChatComponentParse;
    static Method playerSendMessageUUID;

    static {
        try {
            argumentChatComponentClass = ReflectionWrapper.getNMSClass("ArgumentChatComponent");
            argumentChatComponent = MzTech.unsafe.allocateInstance(argumentChatComponentClass);
            argumentChatComponentParse = ReflectionWrapper.getMethod(argumentChatComponentClass, "parse", StringReader.class);
            playerSendMessageUUID = ReflectionWrapper.getMethod(EntityPlayer.class, "sendMessage", UUID.class, IChatBaseComponent[].class);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public Message(MessageComponent ... coms) {
        super(Lists.newArrayList((Object[])coms));
    }

    public Message(Collection<? extends MessageComponent> coms) {
        super(coms);
    }

    public void send(Player ... players) {
        if (this.size() == 0 || players.length == 0) {
            return;
        }
        TaskUtil.throwRuntime(() -> {
            IChatBaseComponent nms = this.toNms0();
            Player[] playerArray2 = players;
            int n = players.length;
            int n2 = 0;
            while (n2 < n) {
                Player player = playerArray2[n2];
                if (playerSendMessageUUID == null) {
                    ((CraftPlayer)player).getHandle().sendMessage(nms);
                } else {
                    ReflectionWrapper.invokeMethod(playerSendMessageUUID, ((CraftPlayer)player).getHandle(), null, new IChatBaseComponent[]{nms});
                }
                ++n2;
            }
        });
    }

    @Deprecated
    public IChatBaseComponent toNms0() {
        if (argumentChatComponentParse == null) {
            return IChatBaseComponent.ChatSerializer.a((String)this.toString());
        }
        return (IChatBaseComponent)ReflectionWrapper.invokeMethod(argumentChatComponentParse, argumentChatComponent, new StringReader(this.toString()));
    }

    public Object toNms() {
        return this.toNms0();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        this.forEach((? super E mc) -> {
            sb.append(mc.toString());
            sb.append(',');
        });
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }
}

