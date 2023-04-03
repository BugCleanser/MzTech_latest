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
 *  org.bukkit.map.MapPalette
 *  org.bukkit.plugin.Plugin
 */
package mz.tech;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.collect.Lists;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import mz.tech.MzTech;
import mz.tech.util.map.MapUtil;
import org.bukkit.map.MapPalette;
import org.bukkit.plugin.Plugin;

public class ImageMap {
    static Map<Integer, byte[]> imageMaps;

    static {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)MzTech.instance, ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.MAP}){

            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Integer id = (Integer)packet.getIntegers().read(0);
                if (imageMaps.containsKey(id)) {
                    event.setPacket(MapUtil.toPacket(packet, imageMaps.get(id)));
                }
            }
        });
    }

    public static void reload() {
        File f = new File(String.valueOf(MzTech.instance.getDataFolder().getPath()) + "/imageMaps");
        f.mkdirs();
        imageMaps = new HashMap<Integer, byte[]>();
        Lists.newArrayList((Object[])f.listFiles()).forEach(file -> {
            String name = file.getName();
            if (name.contains(".")) {
                name = name.split("\\.")[0];
            }
            int id = Integer.parseInt(name);
            byte[] rbs = ImageMap.loadImage(file);
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(MapUtil.toPacket(id, rbs));
            imageMaps.put(id, rbs);
        });
    }

    public static byte[] loadImage(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            return MapPalette.imageToBytes((Image)image.getScaledInstance(128, 128, 4));
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

