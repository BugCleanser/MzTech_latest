/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package mz.tech;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import mz.tech.ReflectionWrapper;
import mz.tech.util.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NBT
implements Cloneable {
    public Object nmsNBT;
    private static Class<?> nbtClass = ReflectionWrapper.getNMSClass("NBTTagCompound");
    protected static Method nbtWrite = ReflectionWrapper.getMethod(nbtClass, "write", DataOutput.class);
    protected static Class<?> nbtReadLimiterClass = ReflectionWrapper.getNMSClass("NBTReadLimiter");
    protected static Constructor<?> nbtReadLimiterConstructor = ReflectionWrapper.getConstructor(nbtReadLimiterClass, Long.TYPE);
    protected static Method nbtLoad;
    protected static Object nbtB;
    protected static Method nbtBB;
    protected static Constructor<?> nbtConstructor;
    private static Method nbtHasKey;
    private static Method nbtRemove;
    private static Class<?> craftItemStackClass;
    public static Method craftAsBukkitCopy;
    private static Method craftItemStackAsNMSCopy;
    public static Constructor<?> itemStackConstructor;
    private static Method itemStackSave;
    private static Class<?> craftEntityClass;
    private static Method craftEntityGetHandle;
    private static Class<?> nbtByteClass;
    private static Constructor<?> nbtByteConstructor;
    private static Field nbtByteData;
    private static Class<?> nbtDoubleClass;
    private static Constructor<?> nbtDoubleConstructor;
    private static Field nbtDoubleData;
    private static Class<?> nbtFloatClass;
    private static Constructor<?> nbtFloatConstructor;
    private static Field nbtFloatData;
    private static Class<?> nbtIntClass;
    private static Constructor<?> nbtIntConstructor;
    private static Field nbtIntData;
    private static Class<?> nbtLongClass;
    private static Constructor<?> nbtLongConstructor;
    private static Field nbtLongData;
    private static Class<?> nbtShortClass;
    private static Constructor<?> nbtShortConstructor;
    private static Field nbtShortData;
    private static Class<?> nbtStringClass;
    private static Constructor<?> nbtStringConstructor;
    private static Field nbtStringData;
    private static Class<?> nbtListClass;
    private static Constructor<?> nbtListConstructor;
    private static Field nbtListList;
    private static Method nbtGet;
    private static Method nbtSet;
    private static Class<?> entityClass;
    private static Method entityLoad;
    protected static Method entitySave;
    protected static Class<?> commandAbstractClass;
    protected static Class<?> commandDataAccessorEntityClass;
    private static Method nbtGetKeys;
    static final Class<?> itemStackClass;
    static final Method itemStackHasTag;
    static final Method itemStackGetTag;
    static final Method itemStackSetTag;
    static final Class<?> mojangsonParserClass;
    static final Method mojangsonParserParse;

    static {
        nbtConstructor = ReflectionWrapper.getConstructor(nbtClass, new Class[0]);
        nbtHasKey = ReflectionWrapper.getMethod(nbtClass, "hasKey", String.class);
        nbtRemove = ReflectionWrapper.getMethod(nbtClass, "remove", String.class);
        craftItemStackClass = ReflectionWrapper.getCraftBukkitClass("inventory.CraftItemStack");
        craftAsBukkitCopy = ReflectionWrapper.getMethod(craftItemStackClass, "asBukkitCopy", ItemStackBuilder.itemStackClass);
        craftItemStackAsNMSCopy = ReflectionWrapper.getMethod(craftItemStackClass, "asNMSCopy", ItemStack.class);
        itemStackConstructor = ReflectionWrapper.getConstructor(ItemStackBuilder.itemStackClass, nbtClass);
        itemStackSave = ReflectionWrapper.getMethod(ItemStackBuilder.itemStackClass, "save", nbtClass);
        craftEntityClass = ReflectionWrapper.getCraftBukkitClass("entity.CraftEntity");
        craftEntityGetHandle = ReflectionWrapper.getMethod(craftEntityClass, "getHandle", new Class[0]);
        nbtByteClass = ReflectionWrapper.getNMSClass("NBTTagByte");
        nbtByteConstructor = ReflectionWrapper.getConstructor(nbtByteClass, Byte.TYPE);
        nbtByteData = ReflectionWrapper.getField(nbtByteClass, "data");
        nbtDoubleClass = ReflectionWrapper.getNMSClass("NBTTagDouble");
        nbtDoubleConstructor = ReflectionWrapper.getConstructor(nbtDoubleClass, Double.TYPE);
        nbtDoubleData = ReflectionWrapper.getField(nbtDoubleClass, "data");
        nbtFloatClass = ReflectionWrapper.getNMSClass("NBTTagFloat");
        nbtFloatConstructor = ReflectionWrapper.getConstructor(nbtFloatClass, Float.TYPE);
        nbtFloatData = ReflectionWrapper.getField(nbtFloatClass, "data");
        nbtIntClass = ReflectionWrapper.getNMSClass("NBTTagInt");
        nbtIntConstructor = ReflectionWrapper.getConstructor(nbtIntClass, Integer.TYPE);
        nbtIntData = ReflectionWrapper.getField(nbtIntClass, "data");
        nbtLongClass = ReflectionWrapper.getNMSClass("NBTTagLong");
        nbtLongConstructor = ReflectionWrapper.getConstructor(nbtLongClass, Long.TYPE);
        nbtLongData = ReflectionWrapper.getField(nbtLongClass, "data");
        nbtShortClass = ReflectionWrapper.getNMSClass("NBTTagShort");
        nbtShortConstructor = ReflectionWrapper.getConstructor(nbtShortClass, Short.TYPE);
        nbtShortData = ReflectionWrapper.getField(nbtShortClass, "data");
        nbtStringClass = ReflectionWrapper.getNMSClass("NBTTagString");
        nbtStringConstructor = ReflectionWrapper.getConstructor(nbtStringClass, String.class);
        nbtStringData = ReflectionWrapper.getField(nbtStringClass, "data");
        nbtListClass = ReflectionWrapper.getNMSClass("NBTTagList");
        nbtListConstructor = ReflectionWrapper.getConstructor(nbtListClass, new Class[0]);
        nbtListList = ReflectionWrapper.getField(nbtListClass, "list");
        nbtGet = ReflectionWrapper.getMethod(nbtClass, "get", String.class);
        nbtSet = ReflectionWrapper.getMethod(nbtClass, "set", String.class, ReflectionWrapper.getNMSClass("NBTBase"));
        entityClass = ReflectionWrapper.getNMSClass("Entity");
        entitySave = ReflectionWrapper.getMethod(entityClass, "save", nbtClass);
        try {
            commandAbstractClass = ReflectionWrapper.getNMSClass("CommandAbstract");
        }
        catch (Throwable e) {
            commandDataAccessorEntityClass = ReflectionWrapper.getNMSClass("CommandDataAccessorEntity");
        }
        try {
            entityLoad = ReflectionWrapper.getMethod(entityClass, "f", nbtClass);
        }
        catch (Throwable e) {
            entityLoad = ReflectionWrapper.getMethod(entityClass, "load", nbtClass);
        }
        try {
            nbtGetKeys = ReflectionWrapper.getMethod(nbtClass, "getKeys", new Class[0]);
        }
        catch (Throwable e) {
            nbtGetKeys = ReflectionWrapper.getMethod(nbtClass, "c", new Class[0]);
        }
        itemStackClass = ReflectionWrapper.getNMSClass("ItemStack");
        itemStackHasTag = ReflectionWrapper.getMethod(itemStackClass, "hasTag", new Class[0]);
        itemStackGetTag = ReflectionWrapper.getMethod(itemStackClass, "getTag", new Class[0]);
        itemStackSetTag = ReflectionWrapper.getMethod(itemStackClass, "setTag", nbtClass);
        mojangsonParserClass = ReflectionWrapper.getNMSClass("MojangsonParser");
        mojangsonParserParse = ReflectionWrapper.getMethod(mojangsonParserClass, "parse", String.class);
    }

    public static Object getNmsEntity(Entity entity) {
        return ReflectionWrapper.invokeMethod(craftEntityGetHandle, entity, new Object[0]);
    }

    public static Boolean hasNmsItemStackTag(Object nmsItem) {
        return (Boolean)ReflectionWrapper.invokeMethod(itemStackHasTag, nmsItem, new Object[0]);
    }

    public static Object getNmsItemStackTag(Object nmsItem) {
        return ReflectionWrapper.invokeMethod(itemStackGetTag, nmsItem, new Object[0]);
    }

    public void setNmsItemStackTag(Object nmsItem) {
        ReflectionWrapper.invokeMethod(itemStackSetTag, nmsItem, this.nmsNBT);
    }

    public NBT(String s) {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        this.nmsNBT = ReflectionWrapper.invokeStaticMethod(mojangsonParserParse, s);
    }

    public NBT(Entity entity) {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        this.nmsNBT = ReflectionWrapper.invokeMethod(entitySave, NBT.getNmsEntity(entity), new NBT().nmsNBT);
    }

    public void set(Entity entity) {
        ReflectionWrapper.invokeMethod(entityLoad, NBT.getNmsEntity(entity), this.nmsNBT);
    }

    public NBT(ItemStack is) {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        Object nmsItem = NBT.asNMSCopy(is);
        this.nmsNBT = ReflectionWrapper.invokeMethod(itemStackSave, nmsItem, new NBT().nmsNBT);
    }

    public NBT(Block block) {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        Object tileEntity = ItemStackBuilder.getTileEntity(block);
        if (tileEntity != null) {
            this.nmsNBT = ReflectionWrapper.invokeMethod(ReflectionWrapper.getMethodParent(tileEntity.getClass(), "save", nbtClass), tileEntity, new NBT().nmsNBT);
        }
    }

    public NBT() {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        this.nmsNBT = ReflectionWrapper.newInstance(nbtConstructor, new Object[0]);
    }

    public NBT(Object nmsNbt) {
        try {
            nbtLoad = ReflectionWrapper.getMethod(nbtClass, "load", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        catch (Throwable e) {
            nbtB = ReflectionWrapper.getStaticFieldValue(ReflectionWrapper.getField(nbtClass, "b"));
            nbtBB = ReflectionWrapper.getMethod(nbtB.getClass(), "b", DataInput.class, Integer.TYPE, nbtReadLimiterClass);
        }
        this.nmsNBT = nmsNbt;
    }

    public NBT(Inventory inv) {
        this();
        this.set("size", inv.getSize());
        ItemStack[] con = inv.getContents();
        ArrayList<NBT> items = new ArrayList<NBT>();
        byte i = 0;
        while (i < inv.getSize()) {
            if (con[i] != null && con[i].getType() != Material.AIR && con[i].getAmount() > 0) {
                items.add(new NBT(con[i]).set("Slot", i));
            }
            i = (byte)(i + 1);
        }
        this.set("Items", items.toArray());
    }

    public Inventory toInventory(String title) {
        Inventory ri = Bukkit.createInventory(null, (int)this.getInt("size"), (String)title);
        this.getChildList("Items").forEach(i -> ri.setItem((int)i.getByte("Slot").byteValue(), i.remove("Slot").toItemStack()));
        return ri;
    }

    public NBT(Location loc) {
        this();
        this.set("world", loc.getWorld().getName());
        this.set("x", loc.getBlockX());
        this.set("y", loc.getBlockY());
        this.set("z", loc.getBlockZ());
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld((String)this.getString("world")), (double)this.getInt("x").intValue(), (double)this.getInt("y").intValue(), (double)this.getInt("z").intValue());
    }

    public NBT(UUID uuid) {
        this();
        this.set("Least", uuid.getLeastSignificantBits());
        this.set("Most", uuid.getMostSignificantBits());
    }

    public UUID toUUID() {
        return new UUID(this.getLong("Most"), this.getLong("Least"));
    }

    public void set(Block block) {
        Object tileEntity = ItemStackBuilder.getTileEntity(block);
        if (tileEntity != null) {
            try {
                Method tileEntityLoad = ReflectionWrapper.getMethodParent(tileEntity.getClass(), "load", ItemStackBuilder.iBlockDataClass, nbtClass);
                ReflectionWrapper.invokeMethod(tileEntityLoad, tileEntity, ItemStackBuilder.getNmsBlockTypeIfLoaded(block), this.nmsNBT);
            }
            catch (Throwable e) {
                Method tileEntityLoad;
                try {
                    tileEntityLoad = ReflectionWrapper.getMethodParent(tileEntity.getClass(), "load", nbtClass);
                }
                catch (Throwable e2) {
                    tileEntityLoad = ReflectionWrapper.getMethodParent(tileEntity.getClass(), "a", nbtClass);
                }
                ReflectionWrapper.invokeMethod(tileEntityLoad, tileEntity, this.nmsNBT);
            }
        }
    }

    public DataOutput save(DataOutput s) {
        ReflectionWrapper.invokeMethod(nbtWrite, this.nmsNBT, s);
        return s;
    }

    public NBT(DataInput s) {
        this();
        if (nbtLoad != null) {
            ReflectionWrapper.invokeMethod(nbtLoad, this.nmsNBT, s, 0, ReflectionWrapper.newInstance(nbtReadLimiterConstructor, Long.MAX_VALUE));
        } else {
            this.nmsNBT = ReflectionWrapper.invokeMethod(nbtBB, nbtB, s, 0, ReflectionWrapper.newInstance(nbtReadLimiterConstructor, Long.MAX_VALUE));
        }
    }

    public ItemStack toItemStack() {
        return (ItemStack)ReflectionWrapper.invokeStaticMethod(craftAsBukkitCopy, ReflectionWrapper.newInstance(itemStackConstructor, this.nmsNBT));
    }

    public void set(ItemStack is) {
        ItemStack newIS = this.toItemStack();
        is.setData(newIS.getData());
        is.setAmount(newIS.getAmount());
        is.setItemMeta(newIS.getItemMeta());
    }

    public static Object asNMSCopy(ItemStack is) {
        return ReflectionWrapper.invokeStaticMethod(craftItemStackAsNMSCopy, is);
    }

    public NBT set(String key, NBT value) {
        if (value == null) {
            return this;
        }
        this.setTag(key, value.nmsNBT);
        return this;
    }

    public NBT set(String key, byte value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, double value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, float value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, int value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, long value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, short value) {
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, String value) {
        if (value == null) {
            return this;
        }
        this.setTag(key, this.newTag(value));
        return this;
    }

    public NBT set(String key, boolean value) {
        return this.set(key, value ? (byte)1 : 0);
    }

    public NBT set(String key, UUID value) {
        if (value == null) {
            return this;
        }
        this.set(key, new NBT(value));
        return this;
    }

    public boolean hasKey(String key) {
        return (Boolean)ReflectionWrapper.invokeMethod(nbtHasKey, this.nmsNBT, key);
    }

    public NBT remove(String key) {
        ReflectionWrapper.invokeMethod(nbtRemove, this.nmsNBT, key);
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NBT)) {
            return false;
        }
        return this.nmsNBT.equals(((NBT)obj).nmsNBT);
    }

    public <T> NBT set(String key, T ... values) {
        ArrayList<Object> list = new ArrayList<Object>();
        Lists.newArrayList((Object[])values).forEach(tag -> {
            if (tag instanceof Byte) {
                list.add(this.newTag((Byte)tag));
            } else if (tag instanceof Double) {
                list.add(this.newTag((Double)tag));
            } else if (tag instanceof Float) {
                list.add(this.newTag(((Float)tag).floatValue()));
            } else if (tag instanceof Integer) {
                list.add(this.newTag((Integer)tag));
            } else if (tag instanceof Long) {
                list.add(this.newTag((Long)tag));
            } else if (tag instanceof Short) {
                list.add(this.newTag((Short)tag));
            } else if (tag instanceof String) {
                if (tag != null) {
                    list.add(this.newTag((String)tag));
                }
            } else if (tag instanceof Boolean) {
                list.add(this.newTag((Boolean)tag != false ? (byte)1 : (byte)0));
            } else if (tag instanceof UUID) {
                list.add(new NBT((UUID)((UUID)tag)).nmsNBT);
            } else if (tag instanceof NBT) {
                list.add(((NBT)tag).nmsNBT);
            }
        });
        this.setTag(key, NBT.newNBTList(list));
        return this;
    }

    public static Object newNBTList(List<Object> list) {
        Object ro = ReflectionWrapper.newInstance(nbtListConstructor, new Object[0]);
        ReflectionWrapper.setFieldValue(nbtListList, ro, list);
        return ro;
    }

    private void setTag(String key, Object value) {
        ReflectionWrapper.invokeMethod(nbtSet, this.nmsNBT, key, value);
    }

    private Object newTag(byte data) {
        return ReflectionWrapper.newInstance(nbtByteConstructor, data);
    }

    private Object newTag(double data) {
        return ReflectionWrapper.newInstance(nbtDoubleConstructor, data);
    }

    private Object newTag(float data) {
        return ReflectionWrapper.newInstance(nbtFloatConstructor, Float.valueOf(data));
    }

    private Object newTag(int data) {
        return ReflectionWrapper.newInstance(nbtIntConstructor, data);
    }

    private Object newTag(long data) {
        return ReflectionWrapper.newInstance(nbtLongConstructor, data);
    }

    private Object newTag(short data) {
        return ReflectionWrapper.newInstance(nbtShortConstructor, data);
    }

    private Object newTag(String data) {
        return ReflectionWrapper.newInstance(nbtStringConstructor, data);
    }

    public NBT getChild(String key) {
        return new NBT(this.getTag(key));
    }

    public Byte getByte(String key) {
        return NBT.getTagByte(this.getTag(key));
    }

    public Double getDouble(String key) {
        return NBT.getTagDouble(this.getTag(key));
    }

    public Float getFloat(String key) {
        return NBT.getTagFloat(this.getTag(key));
    }

    public Integer getInt(String key) {
        return NBT.getTagInt(this.getTag(key));
    }

    public Long getLong(String key) {
        return NBT.getTagLong(this.getTag(key));
    }

    public Short getShort(String key) {
        return NBT.getTagShort(this.getTag(key));
    }

    public String getString(String key) {
        return NBT.getTagString(this.getTag(key));
    }

    public Boolean getBoolean(String key) {
        Byte b = this.getByte(key);
        if (b == null) {
            return null;
        }
        if (b != 0) {
            return true;
        }
        return false;
    }

    public UUID getUUID(String key) {
        return this.getChild(key).toUUID();
    }

    public List<NBT> getChildList(String key) {
        return Lists.newArrayList((Object[])this.getList(key).stream().map(n -> new NBT(n)).toArray());
    }

    public List<Byte> getByteList(String key) {
        ArrayList<Byte> rl = new ArrayList<Byte>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagByte(tag)));
        return rl;
    }

    public List<Double> getDoubleList(String key) {
        ArrayList<Double> rl = new ArrayList<Double>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagDouble(tag)));
        return rl;
    }

    public List<Float> getFloatList(String key) {
        ArrayList<Float> rl = new ArrayList<Float>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagFloat(tag)));
        return rl;
    }

    public List<Integer> getIntList(String key) {
        ArrayList<Integer> rl = new ArrayList<Integer>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagInt(tag)));
        return rl;
    }

    public List<Long> getLongList(String key) {
        ArrayList<Long> rl = new ArrayList<Long>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagLong(tag)));
        return rl;
    }

    public List<Short> getShortList(String key) {
        ArrayList<Short> rl = new ArrayList<Short>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagShort(tag)));
        return rl;
    }

    public List<String> getStringList(String key) {
        ArrayList<String> rl = new ArrayList<String>();
        this.getList(key).forEach(tag -> rl.add(NBT.getTagString(tag)));
        return rl;
    }

    public List<UUID> getUUIDList(String key) {
        ArrayList<UUID> rl = new ArrayList<UUID>();
        this.getList(key).forEach(tag -> rl.add(new NBT(tag).toUUID()));
        return rl;
    }

    private List<?> getList(String key) {
        return (List)ReflectionWrapper.getFieldValue(nbtListList, this.getTag(key));
    }

    private Object getTag(String key) {
        return ReflectionWrapper.invokeMethod(nbtGet, this.nmsNBT, key);
    }

    private static Byte getTagByte(Object tag) {
        return tag == null ? null : (Byte)ReflectionWrapper.getFieldValue(nbtByteData, tag);
    }

    private static Double getTagDouble(Object tag) {
        return tag == null ? null : (Double)ReflectionWrapper.getFieldValue(nbtDoubleData, tag);
    }

    private static Float getTagFloat(Object tag) {
        return tag == null ? null : (Float)ReflectionWrapper.getFieldValue(nbtFloatData, tag);
    }

    private static Integer getTagInt(Object tag) {
        return tag == null ? null : (Integer)ReflectionWrapper.getFieldValue(nbtIntData, tag);
    }

    private static Long getTagLong(Object tag) {
        return tag == null ? null : (Long)ReflectionWrapper.getFieldValue(nbtLongData, tag);
    }

    private static Short getTagShort(Object tag) {
        return tag == null ? null : (Short)ReflectionWrapper.getFieldValue(nbtShortData, tag);
    }

    private static String getTagString(Object tag) {
        return tag == null ? null : (String)ReflectionWrapper.getFieldValue(nbtStringData, tag);
    }

    public List<String> getKeyList() {
        return new ArrayList<String>((Collection)ReflectionWrapper.invokeMethod(nbtGetKeys, this.nmsNBT, new Object[0]));
    }

    public String toString() {
        return "" + this.nmsNBT;
    }

    public NBT clone() {
        return new NBT((DataInput)ByteStreams.newDataInput((byte[])((ByteArrayDataOutput)this.save((DataOutput)ByteStreams.newDataOutput())).toByteArray()));
    }
}

