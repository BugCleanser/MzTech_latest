/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.command.BlockCommandSender
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.util.Vector
 */
package mz.tech;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import mz.tech.AutoWelcome;
import mz.tech.Baubles;
import mz.tech.BucketDoorZombie;
import mz.tech.CuteCreeper;
import mz.tech.DispenserBugFix;
import mz.tech.DropsName;
import mz.tech.EntityStack;
import mz.tech.MzTechData;
import mz.tech.NoExplode;
import mz.tech.ReflectionWrapper;
import mz.tech.command.MzTechCommand;
import mz.tech.event.BlockDamageStopEvent;
import mz.tech.event.ClickItemEvent;
import mz.tech.event.ClickMachineEvent;
import mz.tech.event.ConsumeItemEvent;
import mz.tech.event.CreativeSetSlotEvent;
import mz.tech.event.EntityPickupMzTechItemEvent;
import mz.tech.event.ItemFillEvent;
import mz.tech.event.LingeringPotionBreakEvent;
import mz.tech.event.MachineBreakEvent;
import mz.tech.event.MachineFallEvent;
import mz.tech.event.MachineGrowEvent;
import mz.tech.event.MachineItemAutoMoveEvent;
import mz.tech.event.MachineMoveEvent;
import mz.tech.event.MachinePhysicsEvent;
import mz.tech.event.MoveItemEvent;
import mz.tech.event.PlaceMachineEvent;
import mz.tech.event.ShowBlockEvent;
import mz.tech.event.ShowBlockNbtEvent;
import mz.tech.event.ShowInventoryEvent;
import mz.tech.event.ShowItemEvent;
import mz.tech.event.UseItemForBrew;
import mz.tech.item.MzTechItem;
import mz.tech.item.RedPacketInventory;
import mz.tech.machine.MzTechMachine;
import mz.tech.mail.Attachments;
import mz.tech.mail.MailboxGuide;
import mz.tech.recipe.UseItemForEntityRecipe;
import mz.tech.util.EnchantUtil;
import mz.tech.util.MidiUtil;
import mz.tech.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.objectweb.asm.ClassReader;
import sun.misc.Unsafe;

public class MzTech
extends JavaPlugin
implements Listener {
    public static Unsafe unsafe = (Unsafe)ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(Unsafe.class, "theUnsafe"));
    public static String MCVersion = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.", "");
    public static MzTech instance;
    public static Charset UTF8;
    public static String MzTechPrefix;
    public static Random rand;

    static {
        UTF8 = Charset.forName("UTF8");
        MzTechPrefix = "";
        rand = new Random();
    }

    public MzTech() {
        instance = this;
        MachineBreakEvent.init();
        MzTech.loadClassUsingNMS("mz.tech.util.FurnaceUtil");
        MzTech.loadClassUsingNMS("mz.tech.util.message.Message");
        MzTech.loadClassUsingNMS("mz.tech.CuteCreeper");
        MzTech.loadClassUsingNMS("mz.tech.CuteCreeper$1");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockEvent");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockEvent$1");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockEvent$2");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockEvent$3");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockNbtEvent");
        MzTech.loadClassUsingNMS("mz.tech.event.ShowBlockNbtEvent$1");
    }

    public static byte[] toPrimitive(Byte[] original) {
        int length = original.length;
        byte[] dest = new byte[length];
        int i = 0;
        while (i < length) {
            dest[i] = original[i];
            ++i;
        }
        return dest;
    }

    public static Byte[] toWrap(byte[] original) {
        int length = original.length;
        Byte[] dest = new Byte[length];
        int i = 0;
        while (i < length) {
            dest[i] = original[i];
            ++i;
        }
        return dest;
    }

    public static void loadClassUsingNMS(String className) {
        MzTech.loadClassUsingNMS(MzTech.class.getClassLoader(), className);
    }

    public static void loadClassUsingNMS(String className, String ... compliteVer) {
        MzTech.loadClassUsingNMS(MzTech.class.getClassLoader(), className, compliteVer);
    }

    public static void loadClassUsingNMS(ClassLoader classLoader, String className) {
        MzTech.loadClassUsingNMS(classLoader, className, "v1_12_R1", "v1_16_R3");
    }

    public static void loadClassUsingNMS(ClassLoader classLoader, String className, String ... compliteVer) {
        MzTech.loadClassUsingNMS(classLoader, classLoader, className, compliteVer);
    }

    public static void loadClassUsingNMS(ClassLoader classLoader, ClassLoader cl, String className, String ... compliteVers) {
        TaskUtil.throwRuntime(() -> {
            InputStream c = classLoader.getResourceAsStream(String.valueOf(className.replace(".", "/")) + ".class");
            byte[] bs = new ClassReader((InputStream)c).b;
            c.close();
            String[] stringArray2 = compliteVers;
            int n = compliteVers.length;
            int n2 = 0;
            while (n2 < n) {
                String compliteVer = stringArray2[n2];
                bs = MzTech.toPrimitive(MzTech.replaceBytes(MzTech.toWrap(bs), MzTech.toWrap(compliteVer.getBytes(UTF8)), MzTech.toWrap(MCVersion.getBytes(UTF8))));
                ++n2;
            }
            MzTech.loadClass(className, bs, classLoader);
        });
    }

    public static byte[] getClassBytesUsingNMS(ClassLoader classLoader, String className) {
        return MzTech.getClassBytesUsingNMS(classLoader, className, "v1_12_R1");
    }

    public static byte[] getClassBytesUsingNMS(ClassLoader classLoader, String className, String compliteVer) {
        try {
            InputStream c = classLoader.getResourceAsStream(String.valueOf(className.replace(".", "/")) + ".class");
            byte[] bs = new ClassReader((InputStream)c).b;
            c.close();
            bs = MzTech.toPrimitive(MzTech.replaceBytes(MzTech.toWrap(bs), MzTech.toWrap(compliteVer.getBytes(UTF8)), MzTech.toWrap(MCVersion.getBytes(UTF8))));
            return bs;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getClassBytes(ClassLoader classLoader, String className) {
        try {
            InputStream c = classLoader.getResourceAsStream(String.valueOf(className.replace(".", "/")) + ".class");
            byte[] bs = new ClassReader((InputStream)c).b;
            c.close();
            return bs;
        }
        catch (Exception e) {
            return (byte[])MzTech.throwException(e);
        }
    }

    public static void loadClass(String className, byte[] bs, ClassLoader classLoader) {
        try {
            unsafe.defineClass(className, bs, 0, bs.length, classLoader, null);
        }
        catch (NoSuchMethodError e) {
            ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(ClassLoader.class, "defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE), classLoader, className, bs, 0, bs.length);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static Byte[] replaceBytes(Byte[] src, Byte[] oldBytes, Byte[] newBytes) {
        rbs = new ArrayList<Byte>();
        i = 0;
        while (i < src.length) {
            if (src.length - i < oldBytes.length) ** GOTO lbl-1000
            j = 0;
            while (j < oldBytes.length) {
                if (!src[i + j].equals(oldBytes[j])) {
                    j = -1;
                    break;
                }
                ++j;
            }
            if (j != -1) {
                rbs.addAll(Lists.newArrayList((Object[])newBytes));
                i += oldBytes.length - 1;
            } else lbl-1000:
            // 2 sources

            {
                rbs.add(src[i]);
            }
            ++i;
        }
        return rbs.toArray(new Byte[0]);
    }

    public static byte[] replaceBytes(byte[] src, byte[] oldBytes, byte[] newBytes) {
        return MzTech.toPrimitive(MzTech.replaceBytes(MzTech.toWrap(src), MzTech.toWrap(oldBytes), MzTech.toWrap(newBytes)));
    }

    public static Byte[] replaceLengthAndBytes(Byte[] src, Byte[] oldBytes, Byte[] newBytes) {
        ArrayList o = Lists.newArrayList((Object[])oldBytes);
        o.add(0, (byte)oldBytes.length);
        ArrayList n = Lists.newArrayList((Object[])newBytes);
        n.add(0, (byte)newBytes.length);
        return MzTech.replaceBytes(src, o.toArray(oldBytes), n.toArray(newBytes));
    }

    public static byte[] replaceString(byte[] src, String oldString, String newString) {
        return MzTech.replaceLengthAndBytes(src, oldString.getBytes(StandardCharsets.UTF_8), oldString.getBytes(StandardCharsets.UTF_8));
    }

    public static Byte[] replaceString(Byte[] src, String oldString, String newString) {
        return MzTech.replaceLengthAndBytes(src, MzTech.toWrap(oldString.getBytes(StandardCharsets.UTF_8)), MzTech.toWrap(oldString.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] replaceLengthAndBytes(byte[] src, byte[] oldBytes, byte[] newBytes) {
        return MzTech.toPrimitive(MzTech.replaceLengthAndBytes(MzTech.toWrap(src), MzTech.toWrap(oldBytes), MzTech.toWrap(newBytes)));
    }

    public File getFile() {
        return super.getFile();
    }

    public void onEnable() {
        try {
            this.enable();
        }
        catch (Throwable e) {
            Bukkit.broadcastMessage((String)(String.valueOf(MzTechPrefix) + "\u00a74\u00a7l\u79d1\u6280\u52a0\u8f7d\u5931\u8d25\uff0c\u8bf7\u6682\u65f6\u4e0d\u8981\u4f7f\u7528\u79d1\u6280"));
            e.printStackTrace();
        }
    }

    public void enable() {
        MzTechCommand.cint();
        Bukkit.getPluginCommand((String)"mztech").setExecutor((sender, command, alias, args) -> MzTechCommand.execute(sender, command, alias, args));
        Bukkit.getPluginCommand((String)"mztech").setTabCompleter((sender, command, alias, args) -> MzTechCommand.onTabCompleter(sender, command, alias, args));
        Bukkit.getPluginCommand((String)"sunday").setExecutor((sender, command, alias, args) -> {
            if (sender instanceof Entity) {
                if (sender.isOp()) {
                    ((Entity)sender).getWorld().setTime(0L);
                    ((Entity)sender).getWorld().setThundering(false);
                    ((Entity)sender).getWorld().setStorm(false);
                    sender.sendMessage(String.valueOf(MzTechPrefix) + "\u00a7a\u5df2\u5c06\u4e16\u754c" + ((Entity)sender).getWorld().getName() + "\u66f4\u6539\u4e3a\u00a7m\u661f\u671f\u65e5\u00a7a\u6674\u5929\u3001\u767d\u5929");
                } else {
                    MzTechCommand.sendNoOp(sender);
                }
            } else {
                Bukkit.getWorlds().forEach(world -> {
                    world.setTime(0L);
                    world.setThundering(false);
                    world.setStorm(false);
                });
                sender.sendMessage(String.valueOf(MzTechPrefix) + "\u00a7a\u5df2\u5c06\u6240\u6709\u4e16\u754c\u66f4\u6539\u4e3a\u00a7m\u661f\u671f\u65e5\u00a7a\u6674\u5929\u3001\u767d\u5929");
            }
            return true;
        });
        MzTechData.reload();
        MzTechMachine.regAll();
        MzTechData.loadAll();
        MzTechItem.regAll();
        DispenserBugFix.start();
        if (this.getConfig().getBoolean("entityStack")) {
            Bukkit.getPluginManager().registerEvents((Listener)new EntityStack(), (Plugin)this);
        }
        Bukkit.getPluginManager().registerEvents((Listener)new CuteCreeper(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new AutoWelcome(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new NoExplode(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new EnchantUtil(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new UseItemForEntityRecipe(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BlockDamageStopEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachineBreakEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ItemFillEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new UseItemForBrew(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new EntityPickupMzTechItemEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ShowInventoryEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachineGrowEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachineMoveEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachineItemAutoMoveEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachinePhysicsEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MachineFallEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ClickMachineEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ClickItemEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new LingeringPotionBreakEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ConsumeItemEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DropsName(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MoveItemEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlaceMachineEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ShowItemEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new CreativeSetSlotEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ShowBlockEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ShowBlockNbtEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BucketDoorZombie(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Baubles(), (Plugin)this);
    }

    public void onDisable() {
        TaskUtil.runAll();
        MzTechData.saveAll();
        try {
            MailboxGuide.closeAll();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        MzTechItem.disable();
        MidiUtil.stopAll();
        try {
            Attachments.closeAll();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            RedPacketInventory.closeAll();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static <R> R throwException(Throwable e) {
        return MzTech.throwException0(e);
    }

    public static <R, E extends Throwable> R throwException0(Throwable e) throws E {
        throw e;
    }

    public static <T, O> T castBypassCheck(O o) {
        return (T)o;
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(String.valueOf(MzTechPrefix) + message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage((String)(String.valueOf(MzTechPrefix) + message));
    }

    public static UUID newUUID(long num) {
        return new UUID(2323346933L, num);
    }

    public static <T> T randKey(Map<T, ?> map) {
        return (T)Lists.newArrayList(map.keySet()).get(rand.nextInt(map.size()));
    }

    public static <T> T randValue(Map<?, T> map) {
        return (T)Lists.newArrayList(map.values()).get(rand.nextInt(map.size()));
    }

    public static <T> T randValue(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(rand.nextInt(list.size()));
    }

    @SafeVarargs
    public static <T> T randValue(T ... list) {
        return MzTech.randValue(Lists.newArrayList((Object[])list));
    }

    public static String[] MergeStrings(int mergeIndex, int stringsNum, String ... strings) {
        if (strings.length < stringsNum) {
            return null;
        }
        if (strings.length == stringsNum) {
            return strings;
        }
        ArrayList<String> rl = new ArrayList<String>();
        int i = 0;
        while (i < mergeIndex) {
            rl.add(strings[i]);
            ++i;
        }
        StringBuilder merge = new StringBuilder(strings[mergeIndex]);
        int i2 = mergeIndex + 1;
        while (i2 <= mergeIndex + strings.length - stringsNum) {
            merge.append(" " + strings[i2]);
            ++i2;
        }
        rl.add(merge.toString());
        i2 = mergeIndex + strings.length - stringsNum + 1;
        while (i2 < strings.length) {
            rl.add(strings[i2]);
            ++i2;
        }
        return rl.toArray(new String[rl.size()]);
    }

    public static Location getBlockCentre(Block block) {
        return new Location(block.getWorld(), (double)block.getX() + 0.5, (double)block.getY() + 0.5, (double)block.getZ() + 0.5);
    }

    public static Color randColor() {
        return Color.fromRGB((int)rand.nextInt(256), (int)rand.nextInt(256), (int)rand.nextInt(256));
    }

    public static Item dropItemStack(Location loc, ItemStack is) {
        double xs = (double)(rand.nextFloat() * 0.7f) - 0.35;
        double ys = (double)(rand.nextFloat() * 0.7f) - 0.35;
        double zs = (double)(rand.nextFloat() * 0.7f) - 0.35;
        loc = loc.clone();
        MzTech.randLocWithinBlock(loc, xs, ys, zs);
        try {
            return (Item)loc.getWorld().spawn(loc, Item.class, e -> e.setItemStack(is));
        }
        catch (Throwable e2) {
            Item ri = loc.getWorld().dropItem(loc, is);
            ri.teleport(loc);
            return ri;
        }
    }

    public static Item dropItemStack(Block loc, ItemStack is) {
        return MzTech.dropItemStack(MzTech.getBlockCentre(loc), is);
    }

    public static void randLocWithinBlock(Location loc, double xs, double ys, double zs) {
        double prevX = loc.getX();
        double prevY = loc.getY();
        double prevZ = loc.getZ();
        loc.add(xs, ys, zs);
        if (loc.getX() < Math.floor(prevX)) {
            loc.setX(Math.floor(prevX));
        }
        if (loc.getX() >= Math.ceil(prevX)) {
            loc.setX(Math.ceil(prevX - 0.01));
        }
        if (loc.getY() < Math.floor(prevY)) {
            loc.setY(Math.floor(prevY));
        }
        if (loc.getY() >= Math.ceil(prevY)) {
            loc.setY(Math.ceil(prevY - 0.01));
        }
        if (loc.getZ() < Math.floor(prevZ)) {
            loc.setZ(Math.floor(prevZ));
        }
        if (loc.getZ() >= Math.ceil(prevZ)) {
            loc.setZ(Math.ceil(prevZ - 0.01));
        }
    }

    public static <T> boolean isAll(List<T> list, T o) {
        for (T i : list) {
            if (Objects.equal(i, o)) continue;
            return false;
        }
        return true;
    }

    public static String throwableToString(Throwable e) {
        ByteArrayOutputStream m = new ByteArrayOutputStream();
        PrintStream p = null;
        try {
            p = new PrintStream((OutputStream)m, true, "UTF8");
        }
        catch (Throwable e2) {
            MzTech.throwException(e2);
        }
        e.printStackTrace(p);
        return new String(m.toByteArray(), StandardCharsets.UTF_8);
    }

    public static void sendThrowable(CommandSender sender, Throwable e) {
        MzTech.sendMessage(sender, String.valueOf(e instanceof Error ? "\u00a74" : "\u00a7e") + MzTech.throwableToString(e).replace("\r", "").replace("\t", "  "));
    }

    public static Object fromAddress(long address) {
        Object[] objects = new Object[1];
        long baseOffset = unsafe.arrayBaseOffset(objects.getClass());
        unsafe.putLong(objects, baseOffset, address);
        return objects[0];
    }

    public static long toAddress(Object obj) {
        Object[] objects = new Object[]{obj};
        long baseOffset = unsafe.arrayBaseOffset(objects.getClass());
        return MzTech.normalize(unsafe.getInt(objects, baseOffset));
    }

    public static long normalize(int value) {
        if (value > 0) {
            return value;
        }
        return 0xFFFFFFFFL & (long)value;
    }

    public static List<String> tabComplement(List<String> all, String input) {
        ArrayList<String> r = new ArrayList<String>();
        int spaceNum = ("awa" + input + "awa").split(" ").length - 1;
        all.forEach(t -> {
            if (t.toLowerCase().startsWith(input.toLowerCase())) {
                r.add(t.split(" ")[spaceNum]);
            }
        });
        return r;
    }

    public static List<Entity> getEntities(Location centre, double x, double y, double z) {
        return new ArrayList<Entity>(centre.getWorld().getNearbyEntities(centre, x, y, z));
    }

    public static List<Entity> getEntities(Location centre, Vector r) {
        return MzTech.getEntities(centre, r.getX(), r.getY(), r.getZ());
    }

    public static List<Entity> getEntities(Location centre, double r) {
        return MzTech.getEntities(centre, r, r, r);
    }

    public static List<Entity> getEntities(Block centre, double x, double y, double z) {
        return new ArrayList<Entity>(centre.getWorld().getNearbyEntities(MzTech.getBlockCentre(centre), x, y, z));
    }

    public static List<Entity> getEntities(Block centre, Vector r) {
        return MzTech.getEntities(MzTech.getBlockCentre(centre), r.getX(), r.getY(), r.getZ());
    }

    public static List<Entity> getEntities(Block centre, double r) {
        return MzTech.getEntities(MzTech.getBlockCentre(centre), r, r, r);
    }

    public static List<Entity> getEntities(Entity centre, double x, double y, double z) {
        return new ArrayList<Entity>(centre.getWorld().getNearbyEntities(centre.getLocation(), x, y, z));
    }

    public static List<Entity> getEntities(Entity centre, Vector r) {
        return MzTech.getEntities(centre, r.getX(), r.getY(), r.getZ());
    }

    public static List<Entity> getEntities(Entity centre, double r) {
        return MzTech.getEntities(centre, r, r, r);
    }

    public static double getDistance(Vector v1, Vector v2) {
        return Math.sqrt((v1.getX() - v2.getX()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v1.getY() - v2.getY()) + (v1.getZ() - v2.getZ()) * (v1.getZ() - v2.getZ()));
    }

    public static double getDistance(Location l1, Location l2) {
        if (l1.getWorld() != l2.getWorld()) {
            return Double.POSITIVE_INFINITY;
        }
        return MzTech.getDistance(l1.toVector(), l2.toVector());
    }

    public static double getDistance(Block b1, Block b2) {
        return MzTech.getDistance(MzTech.getBlockCentre(b1), MzTech.getBlockCentre(b2));
    }

    public static double getDistance(Entity e1, Entity e2) {
        return MzTech.getDistance(e1.getLocation(), e2.getLocation());
    }

    public static double getDistance(Block b, Entity e) {
        return MzTech.getDistance(MzTech.getBlockCentre(b), e.getLocation());
    }

    public static Entity getNearest(Location loc, List<Entity> entities) {
        Entity r = null;
        for (Entity e : entities) {
            if (r != null && !(MzTech.getDistance(loc, e.getLocation()) < MzTech.getDistance(loc, r.getLocation()))) continue;
            r = e;
        }
        return r;
    }

    public static Entity getNearest(Block block, List<Entity> entities) {
        return MzTech.getNearest(MzTech.getBlockCentre(block), entities);
    }

    public static Entity getNearest(Entity e, List<Entity> entities) {
        return MzTech.getNearest(e.getLocation(), entities);
    }

    public static Location getCommandLocation(CommandSender sender, String x, String y, String z) {
        Location loc = sender instanceof ConsoleCommandSender ? ShowBlockEvent.getConsoleCommandSenderLocation() : (sender instanceof BlockCommandSender ? ((BlockCommandSender)sender).getBlock().getLocation() : ((Entity)sender).getLocation());
        if (x.startsWith("~")) {
            loc.setX(loc.getX() + (x.length() == 1 ? 0.0 : Double.parseDouble(x.substring(1))));
        } else {
            loc.setX(Double.parseDouble(x));
        }
        if (y.startsWith("~")) {
            loc.setY(loc.getY() + (y.length() == 1 ? 0.0 : Double.parseDouble(y.substring(1))));
        } else {
            loc.setY(Double.parseDouble(y));
        }
        if (z.startsWith("~")) {
            loc.setZ(loc.getZ() + (z.length() == 1 ? 0.0 : Double.parseDouble(z.substring(1))));
        } else {
            loc.setZ(Double.parseDouble(z));
        }
        return loc;
    }

    public static <T> T[] subArray(T[] array, int i) {
        if (array == null) {
            return null;
        }
        return MzTech.subArray(array, i, array.length);
    }

    public static <T> T[] subArray(T[] array, int i, int length) {
        if (array == null) {
            return null;
        }
        Object[] n = (Object[])Array.newInstance(array.getClass().getComponentType(), length - i);
        int j = 0;
        while (i < length) {
            n[j] = array[i];
            ++i;
            ++j;
        }
        return n;
    }
}

