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
 *  net.minecraft.server.v1_12_R1.BlockPosition
 *  net.minecraft.server.v1_12_R1.NBTTagCompound
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
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
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import mz.tech.MzTech;
import mz.tech.NBT;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ShowBlockNbtEvent
extends Event
implements Cancellable,
Listener {
    public final Player player;
    public final Block block;
    public int type;
    public NBT nbt;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public ShowBlockNbtEvent() {
        this.player = null;
        this.block = null;
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.TILE_ENTITY_DATA}){

            public void onPacketSending(PacketEvent event) {
                BlockPosition p = (BlockPosition)event.getPacket().getSpecificModifier(BlockPosition.class).read(0);
                ShowBlockNbtEvent e = new ShowBlockNbtEvent(event.getPlayer(), new Location(event.getPlayer().getWorld(), (double)p.getX(), (double)p.getY(), (double)p.getZ()).getBlock(), (Integer)event.getPacket().getIntegers().read(0), new NBT(event.getPacket().getSpecificModifier(NBTTagCompound.class).read(0)));
                e.setCancelled(event.isCancelled());
                Bukkit.getPluginManager().callEvent((Event)e);
                event.setCancelled(e.isCancelled());
                event.getPacket().getIntegers().write(0, (Object)e.type);
                event.getPacket().getSpecificModifier(NBTTagCompound.class).write(0, (Object)((NBTTagCompound)e.nbt.nmsNBT));
            }
        });
    }

    public ShowBlockNbtEvent(Player player, Block block, int type, NBT nbt) {
        this.player = player;
        this.block = block;
        this.type = type;
        this.nbt = nbt;
    }

    public void send() {
        PacketContainer pc = new PacketContainer(PacketType.Play.Server.TILE_ENTITY_DATA);
        pc.getSpecificModifier(BlockPosition.class).write(0, (Object)new BlockPosition(this.block.getX(), this.block.getY(), this.block.getZ()));
        pc.getIntegers().write(0, (Object)this.type);
        pc.getSpecificModifier(NBTTagCompound.class).write(0, (Object)((NBTTagCompound)this.nbt.nmsNBT));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, pc);
        }
        catch (Throwable e) {
            MzTech.throwException(e);
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

