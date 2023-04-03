/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.World
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.plugin.Plugin
 */
package mz.tech.machine;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mz.tech.DataFile;
import mz.tech.MzTech;
import mz.tech.MzTechData;
import mz.tech.NBT;
import mz.tech.machine.MzTechMachine;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

public class MachineChunk
extends ArrayList<MzTechMachine> {
    private static final long serialVersionUID = -8013156337126102551L;
    public static Map<Chunk, MachineChunk> chunks;
    public World world;

    static {
        Bukkit.getPluginManager().registerEvents(new Listener(){

            @EventHandler
            public void onChunkLoad(ChunkLoadEvent event) {
                MachineChunk.load(event.getChunk());
            }

            @EventHandler(ignoreCancelled=false)
            public void onChunkUnload(ChunkUnloadEvent event) {
                MachineChunk.save(event.getChunk());
                chunks.remove(event.getChunk());
            }
        }, (Plugin)MzTech.instance);
        chunks = new HashMap<Chunk, MachineChunk>(){
            private static final long serialVersionUID = 6977193041641269027L;

            @Override
            public MachineChunk get(Object key) {
                if (!(key instanceof Chunk)) {
                    return null;
                }
                MachineChunk[] rm = new MachineChunk[1];
                this.forEach((c2, mc) -> {
                    if (c2.getWorld().getUID().equals(((Chunk)key).getWorld().getUID()) && ((Chunk)key).getX() == c2.getX() && ((Chunk)key).getZ() == c2.getZ()) {
                        machineChunkArray[0] = mc;
                    }
                });
                return rm[0];
            }

            @Override
            public boolean containsKey(Object key) {
                return this.get(key) != null;
            }
        };
    }

    public MachineChunk(NBT nbt) {
        this.world = Bukkit.getWorld((String)nbt.getString("world"));
        nbt.getChildList("machines").forEach((? super T m) -> {
            try {
                this.add(MzTechMachine.load(this.world, m));
            }
            catch (Throwable e) {
                MzTech.instance.getLogger().warning("\u52a0\u8f7d\u4e16\u754c" + this.world.getName() + "\u65f6\u53d1\u751f\u9519\u8bef\uff1a ");
                e.printStackTrace();
            }
        });
    }

    public MachineChunk(World world) {
        this.world = world;
    }

    public MachineChunk(File f) throws FileNotFoundException {
        this(new NBT(new DataInputStream(new FileInputStream(f))));
    }

    public NBT save(NBT nbt) {
        nbt.set("world", this.world.getName());
        ArrayList machines = new ArrayList();
        this.forEach((? super E m) -> machines.add(m.save()));
        nbt.set("machines", machines.toArray());
        return nbt;
    }

    public void save(File file) throws IOException {
        MzTechData.save(file, dos -> this.save(new NBT()).save((DataOutput)dos));
    }

    public static File getFile(Chunk c2) {
        return new File(c2.getWorld().getWorldFolder(), "MzTech/" + c2.getX() + "," + c2.getZ() + ".chunk");
    }

    public static void load(Chunk chunk) {
        try {
            File f = MachineChunk.getFile(chunk);
            if (f.isFile()) {
                chunks.put(chunk, new MachineChunk(f));
            }
        }
        catch (Throwable e) {
            throw new RuntimeException("\u52a0\u8f7d\u533a\u5757\uff08" + chunk.getWorld() + "\uff0c" + chunk.getX() + "\uff0c" + chunk.getZ() + "\uff09\u65f6\u51fa\u73b0\u5f02\u5e38", e);
        }
    }

    public static void save(Chunk chunk) {
        try {
            if (chunks.containsKey(chunk)) {
                chunks.get(chunk).save(MachineChunk.getFile(chunk));
            }
        }
        catch (Throwable e) {
            throw new RuntimeException("\u4fdd\u5b58\u533a\u5757\uff08" + chunk.getWorld() + "\uff0c" + chunk.getX() + "\uff0c" + chunk.getZ() + "\uff09\u65f6\u5f02\u5e38", e);
        }
    }

    public static void remove(Chunk c2) {
        if (chunks.containsKey(c2)) {
            chunks.remove(c2);
            MachineChunk.getFile(c2).deleteOnExit();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (super.remove(o)) {
            if (this.isEmpty()) {
                MachineChunk.remove(((MzTechMachine)o).getBlock().getChunk());
                File f = MachineChunk.getFile(((MzTechMachine)o).getBlock().getChunk());
                if (f.exists()) {
                    f.delete();
                }
            }
            return true;
        }
        return false;
    }

    public static File getOldDataFolder() {
        return new File(MzTech.instance.getDataFolder(), "machines");
    }

    public static void loadAll() {
        if (MachineChunk.getOldDataFolder().isDirectory()) {
            MzTech.instance.getLogger().info("\u68c0\u6d4b\u5230\u65e7\u7248\u6570\u636e\uff0c\u6b63\u5728\u66f4\u65b0...");
            try {
                MachineChunk.updateOld(MachineChunk.getOldDataFolder());
            }
            catch (Throwable e) {
                MzTech.instance.getLogger().warning("\u65e7\u7248\u6570\u636e\u66f4\u65b0\u5931\u8d25");
                e.printStackTrace();
            }
        }
        MzTech.instance.getLogger().info("\u6b63\u5728\u8bfb\u53d6\u5df2\u52a0\u8f7d\u533a\u5757...");
        ArrayList ts = new ArrayList();
        Bukkit.getWorlds().forEach((? super T w) -> {
            Chunk[] chunkArray = w.getLoadedChunks();
            int n = chunkArray.length;
            int n2 = 0;
            while (n2 < n) {
                Chunk c2 = chunkArray[n2];
                try {
                    MachineChunk.load(c2);
                }
                catch (Throwable e) {
                    ts.add(e);
                }
                ++n2;
            }
        });
        if (ts.size() > 0) {
            ts.forEach((? super T t) -> t.printStackTrace());
        } else {
            MzTech.instance.getLogger().info("\u6210\u529f\u8bfb\u53d6\u5df2\u52a0\u8f7d\u533a\u5757\u3002");
        }
    }

    public Chunk getChunk() {
        if (this.size() > 0) {
            return ((MzTechMachine)this.get(0)).getBlock().getChunk();
        }
        Chunk[] rc = new Chunk[1];
        chunks.forEach((? super K c2, ? super V mc) -> {
            if (mc == this) {
                chunkArray[0] = c2;
            }
        });
        return rc[0];
    }

    public static void updateOld(File dir2) throws IOException {
        File[] fileArray = dir2.listFiles();
        int n = fileArray.length;
        int n2 = 0;
        while (n2 < n) {
            File f = fileArray[n2];
            String[] ss = f.getName().split("[\\(\\,\\)]");
            Chunk chunk = Bukkit.getWorld((String)ss[0]).getChunkAt(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
            DataFile.loadChunk(chunk);
            MachineChunk.save(chunk);
            chunks.remove(chunk);
            ++n2;
        }
        File newDir = new File(String.valueOf(dir2.getCanonicalPath()) + "_bak");
        dir2.renameTo(newDir);
        MzTech.instance.getLogger().info("\u6210\u529f\u66f4\u65b0\u65e7\u7248\u6570\u636e\uff0c\u65e7\u7248\u6570\u636e\u5df2\u5907\u4efd\u5230" + newDir.getCanonicalPath());
    }

    public static void saveAll() {
        MzTech.instance.getLogger().info("\u6b63\u5728\u4fdd\u5b58\u5df2\u52a0\u8f7d\u533a\u5757...");
        ArrayList ts = new ArrayList();
        chunks.forEach((? super K c2, ? super V mc) -> {
            try {
                MachineChunk.save(c2);
            }
            catch (Throwable e) {
                ts.add(e);
            }
        });
        if (!ts.isEmpty()) {
            ts.forEach((? super T t) -> {
                System.out.println(t.getMessage());
                t.printStackTrace();
            });
        } else {
            MzTech.instance.getLogger().info("\u6210\u529f\u4fdd\u5b58\u5df2\u52a0\u8f7d\u533a\u5757\u3002");
        }
    }
}

