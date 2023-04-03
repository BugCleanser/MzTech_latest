/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkPopulateEvent
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import mz.tech.MzTech;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public final class WorldGenUtil
implements Listener {
    public static List<RandBlock> randBlocks = new ArrayList<RandBlock>();

    static {
        Bukkit.getPluginManager().registerEvents((Listener)new WorldGenUtil(), (Plugin)MzTech.instance);
    }

    private WorldGenUtil() {
    }

    @EventHandler
    private void onChunkPopulate(ChunkPopulateEvent event) {
    }

    protected static abstract class RandBlock {
        public ItemStackBuilder is;
        public int countPerChunk;
        public MaterialData[] replacedBlocks;

        public RandBlock(ItemStackBuilder is, int countPerChunk, MaterialData[] replacedBlocks) {
            this.is = is;
            this.countPerChunk = countPerChunk;
            this.replacedBlocks = replacedBlocks;
        }

        public static void forEach(Consumer<? super RandBlock> action) {
            randBlocks.forEach(action);
        }

        public static void forEach(Class<RandBlock> t, Consumer<RandBlock> action) {
            randBlocks.forEach(b -> {
                if (t.isAssignableFrom(b.getClass())) {
                    action.accept((RandBlock)b);
                }
            });
        }
    }

    public static class RandOre
    extends RandBlock {
        public RandOre(ItemStackBuilder is, int countPerChunk) {
            super(is, countPerChunk, new MaterialData[0]);
        }
    }
}

