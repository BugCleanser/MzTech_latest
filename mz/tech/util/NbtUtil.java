/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import mz.tech.MzTech;
import mz.tech.ReflectionWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class NbtUtil {
    static final Class<?> nmsClass = ReflectionWrapper.getNMSClass("NBTTagCompound");
    static final Constructor<?> nmsConstructor = ReflectionWrapper.getConstructor(nmsClass, new Class[0]);
    static final Class<?> CraftItemStack = ReflectionWrapper.getCraftBukkitClass("inventory.CraftItemStack");
    static final Method asNMSCopy = ReflectionWrapper.getMethod(CraftItemStack, "asNMSCopy", ItemStack.class);
    static final Class<?> nmsItemStack = ReflectionWrapper.getNMSClass("ItemStack");
    static final Method hasTag = ReflectionWrapper.getMethod(nmsItemStack, "hasTag", new Class[0]);
    static final Method getTag = ReflectionWrapper.getMethod(nmsItemStack, "getTag", new Class[0]);
    static final Method setTag = ReflectionWrapper.getMethod(nmsItemStack, "setTag", nmsClass);
    static final Method asBukkitCopy = ReflectionWrapper.getMethod(CraftItemStack, "asBukkitCopy", nmsItemStack);
    Object nmsNBT;

    public static Object asNMSCopy(ItemStack is) {
        return ReflectionWrapper.invokeStaticMethod(asNMSCopy, is);
    }

    public static Boolean hasTag(Object nmsItem) {
        return (Boolean)ReflectionWrapper.invokeMethod(hasTag, nmsItem, new Object[0]);
    }

    public static Object getTag(Object nmsItem) {
        return ReflectionWrapper.invokeMethod(getTag, nmsItem, new Object[0]);
    }

    public void setTag(Object nmsItem) {
        ReflectionWrapper.invokeMethod(getTag, nmsItem, this.nmsNBT);
    }

    public static ItemStack asBukkitCopy(Object nmsItem) {
        return (ItemStack)ReflectionWrapper.invokeStaticMethod(asBukkitCopy, nmsItem);
    }

    public String toString() {
        return this.nmsNBT.toString();
    }

    public NbtUtil() {
        this.nmsNBT = ReflectionWrapper.newInstance(nmsConstructor, new Object[0]);
    }

    public NbtUtil(ItemStack is) {
        Object nmsItem = NbtUtil.asNMSCopy(is);
        this.nmsNBT = NbtUtil.hasTag(nmsItem) != false ? NbtUtil.getTag(nmsItem) : ReflectionWrapper.newInstance(nmsConstructor, new Object[0]);
    }

    public ItemStack setToItemStack(ItemStack is) {
        Object nmsItem = NbtUtil.asNMSCopy(is);
        this.setTag(nmsItem);
        return NbtUtil.asBukkitCopy(nmsItem);
    }

    public static String getEntityNBT(Entity entity) {
        try {
            String CraftBukkitPackage = Bukkit.getServer().getClass().getPackage().getName();
            String NMSPackage = CraftBukkitPackage.replace("org.bukkit.craftbukkit", "net.minecraft.server");
            Class<?> CraftEntityClass = Class.forName(String.valueOf(CraftBukkitPackage) + ".entity.CraftEntity");
            Method getHandle = CraftEntityClass.getDeclaredMethod("getHandle", new Class[0]);
            getHandle.setAccessible(true);
            Object NMSEntity = getHandle.invoke(entity, new Object[0]);
            Class<?> EntityClass = Class.forName(String.valueOf(NMSPackage) + ".Entity");
            try {
                Class<?> CommandAbstract = Class.forName(String.valueOf(NMSPackage) + ".CommandAbstract");
                Method a = CommandAbstract.getDeclaredMethod("a", EntityClass);
                a.setAccessible(true);
                Object NBTComponentObj = a.invoke(null, NMSEntity);
                Class<?> NBTComponentClass = NBTComponentObj.getClass();
                Method toString = NBTComponentClass.getDeclaredMethod("toString", new Class[0]);
                toString.setAccessible(true);
                String nbt = (String)toString.invoke(NBTComponentObj, new Object[0]);
                return nbt;
            }
            catch (Throwable e) {
                Class<?> CommandDataAccessorEntityClass = Class.forName(String.valueOf(NMSPackage) + ".CommandDataAccessorEntity");
                Constructor<?> tc = CommandDataAccessorEntityClass.getDeclaredConstructor(EntityClass);
                tc.setAccessible(true);
                Object DataAccessor = tc.newInstance(NMSEntity);
                Method a = DataAccessor.getClass().getDeclaredMethod("a", new Class[0]);
                a.setAccessible(true);
                Object NBTCom = a.invoke(DataAccessor, new Object[0]);
                Class<?> NBTComponentClass = NBTCom.getClass();
                Method toString = NBTComponentClass.getDeclaredMethod("toString", new Class[0]);
                toString.setAccessible(true);
                String nbt = (String)toString.invoke(NBTCom, new Object[0]);
                return nbt;
            }
        }
        catch (Exception e) {
            MzTech.throwException(e);
            return null;
        }
    }

    public static void setEntityNBT(Entity entity, String key, Object value) {
        Class<?> craftEntityClass = ReflectionWrapper.getCraftBukkitClass("entity.CraftEntity");
        Class<?> nmsEntityClass = ReflectionWrapper.getNMSClass("Entity");
        Object nmsEntity = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(craftEntityClass, "getHandle", new Class[0]), entity, new Object[0]);
        try {
            Object nbt = ReflectionWrapper.invokeStaticMethod(ReflectionWrapper.getMethod(ReflectionWrapper.getNMSClass("CommandAbstract"), "a", nmsEntityClass), nmsEntity);
            ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(nbt.getClass(), "set", String.class, ReflectionWrapper.getNMSClass("NBTBase")), nbt, key, value);
            ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "f", nbt.getClass()), nmsEntity, nbt);
        }
        catch (Throwable e) {
            Object com = ReflectionWrapper.newInstance(ReflectionWrapper.getConstructor(ReflectionWrapper.getNMSClass("CommandDataAccessorEntity"), nmsEntityClass), nmsEntity);
            Object nbt = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(com.getClass(), "a", new Class[0]), com, new Object[0]);
            ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(nbt.getClass(), "set", String.class, ReflectionWrapper.getNMSClass("NBTBase")), nbt, key, value);
            try {
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "f", nbt.getClass()), nmsEntity, nbt);
            }
            catch (Throwable e2) {
                ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethod(nmsEntityClass, "load", nbt.getClass()), nmsEntity, nbt);
            }
        }
    }

    public static void setEntityNBT(Entity entity, String newNBT, boolean combine) {
        try {
            String CraftBukkitPackage = Bukkit.getServer().getClass().getPackage().getName();
            String NMSPackage = CraftBukkitPackage.replace("org.bukkit.craftbukkit", "net.minecraft.server");
            Class<?> CraftEntityClass = Class.forName(String.valueOf(CraftBukkitPackage) + ".entity.CraftEntity");
            Method getHandle = CraftEntityClass.getDeclaredMethod("getHandle", new Class[0]);
            getHandle.setAccessible(true);
            Object NMSEntity = getHandle.invoke(entity, new Object[0]);
            Class<?> EntityClass = Class.forName(String.valueOf(NMSPackage) + ".Entity");
            Class<?> MojangsonParser = Class.forName(String.valueOf(NMSPackage) + ".MojangsonParser");
            Method parse = MojangsonParser.getDeclaredMethod("parse", String.class);
            parse.setAccessible(true);
            Object nNBT = parse.invoke(null, newNBT);
            try {
                Class<?> CommandAbstract = Class.forName(String.valueOf(NMSPackage) + ".CommandAbstract");
                Method a = CommandAbstract.getDeclaredMethod("a", EntityClass);
                a.setAccessible(true);
                Object NBTComponentObj = a.invoke(null, NMSEntity);
                Class<?> NBTComponentClass = NBTComponentObj.getClass();
                Method setNBT = EntityClass.getDeclaredMethod("f", NBTComponentClass);
                setNBT.setAccessible(true);
                if (combine) {
                    Method combineA = NBTComponentClass.getDeclaredMethod("a", NBTComponentClass);
                    combineA.setAccessible(true);
                    combineA.invoke(NBTComponentObj, nNBT);
                }
                setNBT.invoke(NMSEntity, NBTComponentObj);
            }
            catch (Throwable e) {
                Method setNBT;
                Class<?> CommandDataAccessorEntityClass = Class.forName(String.valueOf(NMSPackage) + ".CommandDataAccessorEntity");
                Constructor<?> tc = CommandDataAccessorEntityClass.getDeclaredConstructor(EntityClass);
                tc.setAccessible(true);
                Object DataAccessor = tc.newInstance(NMSEntity);
                Method a = DataAccessor.getClass().getDeclaredMethod("a", new Class[0]);
                a.setAccessible(true);
                Object NBTCom = a.invoke(DataAccessor, new Object[0]);
                Class<?> NBTComponentClass = NBTCom.getClass();
                try {
                    setNBT = EntityClass.getDeclaredMethod("f", NBTComponentClass);
                }
                catch (Throwable e2) {
                    setNBT = EntityClass.getDeclaredMethod("load", NBTComponentClass);
                }
                setNBT.setAccessible(true);
                if (combine) {
                    Method combineA = NBTComponentClass.getDeclaredMethod("a", NBTComponentClass);
                    combineA.setAccessible(true);
                    combineA.invoke(NBTCom, nNBT);
                }
                setNBT.invoke(NMSEntity, NBTCom);
            }
        }
        catch (Exception e) {
            MzTech.throwException(e);
        }
    }
}

