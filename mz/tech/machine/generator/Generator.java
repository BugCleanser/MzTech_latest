/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkPopulateEvent
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine.generator;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public abstract class Generator {
    public static List<Generator> generators = new ArrayList<Generator>();
    public List<Biome> biomes;
    public List<MaterialData> replaceables;
    int genChance = 100;
    int maxEveryChunk;

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onChunkPopulate(ChunkPopulateEvent event) {
                generators.forEach(g -> {
                    if (MzTech.rand.nextInt(100) < g.genChance) {
                        int i = 0;
                        while (i < g.maxEveryChunk) {
                            Block b = MzTech.randValue(g.getGenableBlocks(event.getChunk(), MzTech.rand.nextInt(16), MzTech.rand.nextInt(16)));
                            if (b != null && MzTechMachine.asMzTechCopy(b) == null) {
                                g.gen(b);
                            }
                            ++i;
                        }
                    }
                });
            }
        }, (Plugin)MzTech.instance);
    }

    public void setBiomes(Biome ... biomes) {
        this.biomes = Lists.newArrayList((Object[])biomes);
    }

    public Generator(int chance, int numEveryChunk, MaterialData ... replaceables) {
        this.genChance = chance;
        this.maxEveryChunk = numEveryChunk;
        if (replaceables.length > 0) {
            this.replaceables = Lists.newArrayList((Object[])replaceables);
        }
    }

    public List<Block> getGenableBlocks(Chunk chunk, int x, int z) {
        ArrayList<Block> rl = new ArrayList<Block>();
        int y = 0;
        while (y < 256) {
            Block block = chunk.getBlock(x, y, z);
            if ((this.biomes == null || this.biomes.contains(block.getBiome())) && (this.replaceables == null || this.replaceables.contains(new MaterialData(block.getType(), block.getState().getRawData())))) {
                rl.add(block);
            }
            ++y;
        }
        return rl;
    }

    public void reg() {
        generators.add(this);
    }

    public void unreg() {
        generators.remove(this);
    }

    public abstract void gen(Block var1);
}

