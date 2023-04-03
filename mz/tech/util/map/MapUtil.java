/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.MapMeta
 */
package mz.tech.util.map;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import mz.tech.NBT;
import mz.tech.ReflectionWrapper;
import mz.tech.util.TaskUtil;
import mz.tech.util.map.Window;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class MapUtil
extends Window {
    public static Class<?> mapIconClass = ReflectionWrapper.getNMSClass("MapIcon");
    public static Object mapIconArray = Array.newInstance(mapIconClass, 0);
    public Window controlledWindow = this;
    static Method mapMetaGetMapId;

    static {
        try {
            mapMetaGetMapId = ReflectionWrapper.getMethod(MapMeta.class, "getMapId", new Class[0]);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public MapUtil() {
        super(0, 0, 128, 128);
    }

    @Override
    public void view(Player player, int id) {
        this.view();
        TaskUtil.throwRuntime(() -> ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.toPacket(id)));
    }

    public PacketContainer toPacket(int id) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.MAP);
        packet.getIntegers().write(0, (Object)id);
        this.toPacket(packet);
        return packet;
    }

    public PacketContainer toPacket(PacketContainer packet) {
        byte[] ps = new byte[16384];
        int i = 0;
        while (i < 128) {
            int j = 0;
            while (j < 128) {
                ps[i + j * 128] = (byte)this.DC.getRawPixel(i, j);
                ++j;
            }
            ++i;
        }
        return MapUtil.toPacket(packet, ps);
    }

    public static PacketContainer toPacket(PacketContainer packet, byte[] data) {
        packet.getBytes().write(0, (Object)1);
        packet.getBooleans().write(0, (Object)false);
        packet.getSpecificModifier(mapIconArray.getClass()).write(0, mapIconArray);
        packet.getIntegers().write(1, (Object)0);
        packet.getIntegers().write(2, (Object)0);
        packet.getIntegers().write(3, (Object)128);
        packet.getIntegers().write(4, (Object)128);
        packet.getByteArrays().write(0, (Object)data);
        return packet;
    }

    public static PacketContainer toPacket(int id, byte[] data) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.MAP);
        packet.getIntegers().write(0, (Object)id);
        MapUtil.toPacket(packet, data);
        return packet;
    }

    @Override
    public MapUtil getMap() {
        return this;
    }

    public static ItemStack newItem() {
        NBT nbt = new NBT();
        nbt.set("id", "minecraft:filled_map");
        nbt.set("Count", (byte)1);
        return nbt.toItemStack();
    }

    public static int getMapId(ItemStack map) {
        if (mapMetaGetMapId != null) {
            return (Integer)ReflectionWrapper.invokeMethod(mapMetaGetMapId, map.getItemMeta(), new Object[0]);
        }
        return map.getDurability();
    }

    @Override
    public boolean isControlled() {
        return this == this.controlledWindow;
    }
}

