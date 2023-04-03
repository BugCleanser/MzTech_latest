/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.utility.StreamSerializer
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.yaml.snakeyaml.Yaml
 */
package mz.tech;

import com.comphenix.protocol.utility.StreamSerializer;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import mz.tech.ImageMap;
import mz.tech.MzTech;
import mz.tech.machine.MzTechMachine;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

@Deprecated
public class DataFile {
    static Map<String, Object> config;

    public static void reload() {
        try {
            new File("plugins/MzTech").mkdirs();
            File configFile = new File("plugins/MzTech/config.yml");
            if (!configFile.exists()) {
                InputStream res = MzTech.instance.getResource("mz/tech/config.yml");
                Files.copy(res, configFile.toPath(), new CopyOption[0]);
            }
            Throwable throwable = null;
            Object var2_4 = null;
            try (FileInputStream fis = new FileInputStream(configFile);){
                config = (Map)new Yaml().loadAs((InputStream)fis, Map.class);
                MzTech.MzTechPrefix = (String)config.get("MzTech");
                ImageMap.reload();
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException e) {
            MzTech.throwException(e);
        }
    }

    public static Map<String, Object> getConfig() {
        return config;
    }

    public static File getChunkFile(Chunk c) {
        return new File("plugins\\MzTech\\machines\\" + c.getWorld().getName() + "(" + c.getX() + "," + c.getZ() + ").mc");
    }

    public static void unloadChunk(Chunk chunk) {
        MzTechMachine.getChunks().remove(chunk);
    }

    public static void saveUUID(DataOutputStream dfo, UUID uuid) {
        if (uuid == null) {
            uuid = new UUID(0L, 0L);
        }
        UUID finalUuid = uuid;
        TaskUtil.throwRuntime(() -> {
            dfo.writeLong(finalUuid.getMostSignificantBits());
            dfo.writeLong(finalUuid.getLeastSignificantBits());
        });
    }

    public static void loadAll() {
        DataFile.loadMachines();
    }

    public static void loadMachines() {
        Bukkit.getWorlds().forEach(world -> {
            Chunk[] chunkArray = world.getLoadedChunks();
            int n = chunkArray.length;
            int n2 = 0;
            while (n2 < n) {
                Chunk chunk = chunkArray[n2];
                DataFile.loadChunk(chunk);
                ++n2;
            }
        });
    }

    @Deprecated
    public static void loadChunk(Chunk chunk) {
        if (MzTechMachine.getChunks().containsKey(chunk)) {
            return;
        }
        try {
            File file = DataFile.getChunkFile(chunk);
            if (file.exists()) {
                TaskUtil.throwRuntime(() -> {
                    Throwable throwable = null;
                    Object var2_3 = null;
                    try (DataInputStream dfi = new DataInputStream(new FileInputStream(file));){
                        int size = dfi.readInt();
                        while (size > 0) {
                            byte[] bs = new byte[dfi.readInt()];
                            dfi.read(bs);
                            String type = new String(bs, MzTech.UTF8);
                            bs = new byte[dfi.readInt()];
                            dfi.read(bs);
                            String world = new String(bs, MzTech.UTF8);
                            Block block = new Location(Bukkit.getWorld((String)world), dfi.readDouble(), dfi.readDouble(), dfi.readDouble()).getBlock();
                            dfi.readInt();
                            MzTechMachine.types.forEach(clazz -> {
                                MzTechMachine m = null;
                                try {
                                    m = (MzTechMachine)MzTech.unsafe.allocateInstance((Class<?>)clazz);
                                }
                                catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
                                MzTechMachine machine = m;
                                if (machine != null && machine.getType() != null && machine.getType().equals(type)) {
                                    machine.setBlock(block);
                                    TaskUtil.throwRuntime(() -> machine.loadOld(dfi));
                                    machine.add();
                                }
                            });
                            --size;
                        }
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                });
            }
        }
        catch (Throwable e) {
            Bukkit.getLogger().warning("\u52a0\u8f7d\u533a\u5757" + chunk.getWorld().getName() + ":" + chunk.getX() + "," + chunk.getZ() + "\u65f6\u53d1\u751f\u9519\u8bef\uff1a");
            e.printStackTrace();
        }
    }

    public static UUID loadUUID(DataInputStream dfi) {
        try {
            return new UUID(dfi.readLong(), dfi.readLong());
        }
        catch (Throwable e) {
            MzTech.throwException(e);
            return null;
        }
    }

    public static void writeInventory(DataOutputStream stream, Inventory inv) {
        try {
            if (inv == null) {
                stream.writeInt(0);
            } else {
                stream.writeInt(inv.getSize());
                byte[] title = new byte[]{};
                stream.writeInt(title.length);
                stream.write(title);
                int i = 0;
                while (i < inv.getSize()) {
                    ItemStack is = inv.getItem(i);
                    if (is == null || is.getType() == Material.AIR) {
                        stream.writeBoolean(false);
                    } else {
                        stream.writeBoolean(true);
                        DataFile.writeItemStack(stream, is);
                    }
                    ++i;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Inventory readInventory(DataInputStream stream, String name) {
        Inventory inv = null;
        try {
            int isNum = stream.readInt();
            if (isNum > 0) {
                byte[] title = new byte[stream.readInt()];
                stream.read(title);
                inv = Bukkit.createInventory(null, (int)isNum, (String)(name == null ? new String(title, MzTech.UTF8) : name));
                int i = 0;
                while (i < isNum) {
                    if (stream.readBoolean()) {
                        inv.setItem(i, DataFile.readItemStack(stream));
                    }
                    ++i;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

    public static void writeItemStack(DataOutputStream stream, ItemStack is) {
        StreamSerializer ss = new StreamSerializer();
        try {
            String sItem = ss.serializeItemStack(is);
            byte[] buf = sItem.getBytes(MzTech.UTF8);
            stream.writeInt(buf.length);
            stream.write(buf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack readItemStack(DataInputStream stream) {
        StreamSerializer ss = new StreamSerializer();
        try {
            byte[] buf = new byte[stream.readInt()];
            stream.read(buf);
            return ss.deserializeItemStack(new String(buf, MzTech.UTF8));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void saveString(DataOutputStream stream, String s) {
        byte[] bs = s.getBytes(MzTech.UTF8);
        TaskUtil.throwRuntime(() -> {
            stream.writeInt(bs.length);
            stream.write(bs);
        });
    }

    public static <T> void saveList(DataOutputStream stream, List<T> list, BiConsumer<DataOutputStream, T> saver) {
        TaskUtil.throwRuntime(() -> stream.writeInt(list.size()));
        list.forEach(n -> saver.accept(stream, n));
    }

    public static <T> List<T> loadList(DataInputStream stream, C<T> c) {
        ArrayList rl = new ArrayList();
        TaskUtil.throwRuntime(() -> {
            int size = stream.readInt();
            int i = 0;
            while (i < size) {
                rl.add(c.run(stream));
                ++i;
            }
        });
        return rl;
    }

    public static String loadString(DataInputStream fis) {
        try {
            byte[] bs = new byte[fis.readInt()];
            fis.read(bs);
            return new String(bs, MzTech.UTF8);
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveLocation(DataOutputStream dfo, Location centre) {
        TaskUtil.throwRuntime(() -> {
            DataFile.saveString(dfo, centre.getWorld().getName());
            dfo.writeDouble(centre.getX());
            dfo.writeDouble(centre.getY());
            dfo.writeDouble(centre.getZ());
        });
    }

    public static Location loadLocation(DataInputStream fis) {
        try {
            return new Location(Bukkit.getWorld((String)DataFile.loadString(fis)), fis.readDouble(), fis.readDouble(), fis.readDouble());
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static interface C<T> {
        public T run(DataInputStream var1);
    }
}

