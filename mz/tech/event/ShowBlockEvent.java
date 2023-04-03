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
 *  com.comphenix.protocol.wrappers.MultiBlockChangeInfo
 *  com.comphenix.protocol.wrappers.WrappedBlockData
 *  net.minecraft.server.v1_12_R1.Block
 *  net.minecraft.server.v1_12_R1.BlockPosition
 *  net.minecraft.server.v1_12_R1.IBlockData
 *  net.minecraft.server.v1_12_R1.MinecraftServer
 *  net.minecraft.server.v1_16_R3.Block
 *  net.minecraft.server.v1_16_R3.IBlockData
 *  net.minecraft.server.v1_16_R3.SectionPosition
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import mz.tech.MzTech;
import mz.tech.util.MoreThrowable;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.SectionPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ShowBlockEvent
extends Event
implements Cancellable,
Listener {
    public final Player player;
    public final org.bukkit.block.Block block;
    public int id;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public static Location getConsoleCommandSenderLocation() {
        BlockPosition p = MinecraftServer.getServer().getChunkCoordinates();
        return new Location((World)MinecraftServer.getServer().getWorld().getWorld(), (double)p.getX(), (double)p.getY(), (double)p.getZ());
    }

    public ShowBlockEvent() {
        this.player = null;
        this.block = null;
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.BLOCK_CHANGE}){

            public void onPacketSending(PacketEvent event) {
                BlockPosition p = (BlockPosition)event.getPacket().getSpecificModifier(BlockPosition.class).read(0);
                ShowBlockEvent e = new ShowBlockEvent(event.getPlayer(), new Location(event.getPlayer().getWorld(), (double)p.getX(), (double)p.getY(), (double)p.getZ()).getBlock(), net.minecraft.server.v1_12_R1.Block.getCombinedId((IBlockData)((IBlockData)event.getPacket().getSpecificModifier(IBlockData.class).read(0))));
                e.setCancelled(event.isCancelled());
                Bukkit.getPluginManager().callEvent((Event)e);
                event.setCancelled(e.isCancelled());
                event.getPacket().getSpecificModifier(IBlockData.class).write(0, (Object)net.minecraft.server.v1_12_R1.Block.getByCombinedId((int)e.id));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.MAP_CHUNK}){

            public void onPacketSending(PacketEvent event) {
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.MULTI_BLOCK_CHANGE}){

            public void onPacketSending(PacketEvent event) {
                try {
                    MultiBlockChangeInfo[] multiBlockChangeInfoArray = (MultiBlockChangeInfo[])event.getPacket().getMultiBlockChangeInfoArrays().read(0);
                    int n = multiBlockChangeInfoArray.length;
                    int n2 = 0;
                    while (n2 < n) {
                        MultiBlockChangeInfo mbci = multiBlockChangeInfoArray[n2];
                        ShowBlockEvent e1 = new ShowBlockEvent(event.getPlayer(), mbci.getLocation(event.getPlayer().getWorld()).getBlock(), net.minecraft.server.v1_12_R1.Block.getCombinedId((IBlockData)((IBlockData)mbci.getData().getHandle())));
                        e1.setCancelled(event.isCancelled());
                        Bukkit.getPluginManager().callEvent((Event)e1);
                        event.setCancelled(e1.isCancelled());
                        mbci.setData(WrappedBlockData.fromHandle((Object)net.minecraft.server.v1_12_R1.Block.getByCombinedId((int)e1.id)));
                        ++n2;
                    }
                }
                catch (Throwable e) {
                    try {
                        SectionPosition sp = (SectionPosition)event.getPacket().getSpecificModifier(SectionPosition.class).read(0);
                        short[] ss = (short[])event.getPacket().getSpecificModifier(short[].class).read(0);
                        net.minecraft.server.v1_16_R3.IBlockData[] bds = (net.minecraft.server.v1_16_R3.IBlockData[])event.getPacket().getSpecificModifier(net.minecraft.server.v1_16_R3.IBlockData[].class).read(0);
                        int i = 0;
                        while (i < bds.length) {
                            ShowBlockEvent ev = new ShowBlockEvent(event.getPlayer(), new Location(event.getPlayer().getWorld(), (double)sp.d(ss[i]), (double)sp.e(ss[i]), (double)sp.f(ss[i])).getBlock(), Block.getCombinedId((net.minecraft.server.v1_16_R3.IBlockData)bds[i]));
                            ev.setCancelled(event.isCancelled());
                            Bukkit.getPluginManager().callEvent((Event)ev);
                            event.setCancelled(ev.isCancelled());
                            bds[i] = Block.getByCombinedId((int)ev.id);
                            ++i;
                        }
                    }
                    catch (Throwable e1) {
                        MzTech.throwException(new MoreThrowable(e, e1));
                    }
                }
            }
        });
    }

    public ShowBlockEvent(Player player, org.bukkit.block.Block block, int id) {
        this.player = player;
        this.block = block;
        this.id = id;
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

