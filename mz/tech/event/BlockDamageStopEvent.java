/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerOptions
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.injector.GamePhase
 *  com.comphenix.protocol.wrappers.BlockPosition
 *  com.comphenix.protocol.wrappers.EnumWrappers$PlayerDigType
 *  org.bukkit.Bukkit
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package mz.tech.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import mz.tech.MzTech;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockDamageStopEvent
extends Event
implements Listener {
    private static final HandlerList handlers = new HandlerList();
    public Player player;
    public Block block;

    public BlockDamageStopEvent() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(PacketAdapter.params().plugin((Plugin)MzTech.instance).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).options(new ListenerOptions[]{ListenerOptions.SKIP_PLUGIN_VERIFIER}).types(new PacketType[]{PacketType.Play.Client.BLOCK_DIG})){

            public void onPacketReceiving(final PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    new BukkitRunnable(){

                        public void run() {
                            try {
                                if (((EnumWrappers.PlayerDigType)event.getPacket().getPlayerDigTypes().read(0)).equals((Object)EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) || ((EnumWrappers.PlayerDigType)event.getPacket().getPlayerDigTypes().read(0)).equals((Object)EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK)) {
                                    BlockDamageStopEvent e = new BlockDamageStopEvent(event.getPlayer(), ((BlockPosition)event.getPacket().getBlockPositionModifier().read(0)).toLocation(event.getPlayer().getWorld()).getBlock());
                                    Bukkit.getPluginManager().callEvent((Event)e);
                                }
                            }
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                    }.runTaskLater((Plugin)MzTech.instance, 1L);
                }
            }
        });
    }

    public BlockDamageStopEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

