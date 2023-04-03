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
 *  com.comphenix.protocol.reflect.StructureModifier
 *  io.netty.buffer.Unpooled
 *  net.minecraft.server.v1_12_R1.ItemStack
 *  net.minecraft.server.v1_12_R1.PacketDataSerializer
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
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
import com.comphenix.protocol.reflect.StructureModifier;
import io.netty.buffer.Unpooled;
import mz.tech.MzTech;
import mz.tech.event.ShowItemEvent;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class ShowMerchantV1_12 {
    private ShowMerchantV1_12() {
    }

    public static void start() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.LOWEST, new PacketType[]{PacketType.Play.Server.CUSTOM_PAYLOAD}){

            public void onPacketSending(PacketEvent event) {
                if (event.isCancelled() || !((String)event.getPacket().getStrings().read(0)).equals("MC|TrList")) {
                    return;
                }
                StructureModifier pdss = event.getPacket().getSpecificModifier(PacketDataSerializer.class);
                PacketDataSerializer pds = (PacketDataSerializer)pdss.read(0);
                pds.readerIndex(0);
                int int0 = pds.readInt();
                Object[][] os = new Object[pds.readByte()][6];
                int i = 0;
                while (i < os.length) {
                    os[i][0] = ShowMerchantV1_12.callShowItemEvent(pds.k());
                    if (os[i][0] == null) {
                        event.setCancelled(true);
                    }
                    os[i][1] = ShowMerchantV1_12.callShowItemEvent(pds.k());
                    if (os[i][1] == null) {
                        event.setCancelled(true);
                    }
                    if (pds.readBoolean()) {
                        os[i][2] = ShowMerchantV1_12.callShowItemEvent(pds.k());
                        if (os[i][2] == null) {
                            event.setCancelled(true);
                        }
                    } else {
                        os[i][2] = null;
                    }
                    os[i][3] = pds.readBoolean();
                    os[i][4] = pds.readInt();
                    os[i][5] = pds.readInt();
                    ++i;
                }
                pds = new PacketDataSerializer(Unpooled.buffer());
                pds.writeInt(int0);
                pds.writeByte(os.length);
                i = 0;
                while (i < os.length) {
                    pds.a((net.minecraft.server.v1_12_R1.ItemStack)os[i][0]);
                    pds.a((net.minecraft.server.v1_12_R1.ItemStack)os[i][1]);
                    pds.writeBoolean(os[i][2] != null);
                    if (os[i][2] != null) {
                        pds.a((net.minecraft.server.v1_12_R1.ItemStack)os[i][2]);
                    }
                    pds.writeBoolean(((Boolean)os[i][3]).booleanValue());
                    pds.writeInt(((Integer)os[i][4]).intValue());
                    pds.writeInt(((Integer)os[i][5]).intValue());
                    ++i;
                }
                pdss.write(0, (Object)pds);
            }
        });
    }

    private static net.minecraft.server.v1_12_R1.ItemStack callShowItemEvent(net.minecraft.server.v1_12_R1.ItemStack is) {
        ShowItemEvent event = new ShowItemEvent(null, false, -1, CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack)is));
        Bukkit.getPluginManager().callEvent((Event)event);
        if (event.isCancelled()) {
            return null;
        }
        return CraftItemStack.asNMSCopy((ItemStack)event.itemStack);
    }
}

