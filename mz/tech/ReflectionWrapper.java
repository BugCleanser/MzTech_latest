/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package mz.tech;

import io.github.karlatemp.unsafeaccessor.Root;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import mz.tech.MzTech;
import org.bukkit.Bukkit;

public final class ReflectionWrapper {
    private ReflectionWrapper() {
    }

    public static <T> T newInstance(Constructor<T> con, Object ... args) {
        try {
            Root.setAccessible(con, true);
            return con.newInstance(args);
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getClassByName(String n) {
        try {
            return Class.forName(n);
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static Method getMethodParent(Class<?> c, String method, Class<?> ... args) {
        block12: {
            try {
                return ReflectionWrapper.getMethod(c, method, args);
            }
            catch (Throwable e) {
                try {
                    if (c.getSuperclass() != null) {
                        return ReflectionWrapper.getMethodParent(c.getSuperclass(), method, args);
                    }
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException)e;
                    }
                    throw new RuntimeException(e);
                }
                catch (Throwable e) {
                    classes = c.getInterfaces();
                    lastExc = null;
                    if (classes.length == 0) break block12;
                    var9_7 = classes;
                    var8_8 = classes.length;
                    var7_9 = 0;
                    ** while (var7_9 < var8_8)
                }
            }
lbl-1000:
            // 1 sources

            {
                i = var9_7[var7_9];
                exc = null;
                try {
                    return ReflectionWrapper.getMethodParent(i, method, args);
                }
                catch (Throwable e2) {
                    lastExc = exc = e2;
                    ++var7_9;
                }
                continue;
            }
        }
        if (lastExc != null) {
            if (lastExc instanceof RuntimeException) {
                throw (RuntimeException)lastExc;
            }
            throw new RuntimeException(e);
        }
        if (e instanceof RuntimeException) {
            throw (RuntimeException)e;
        }
        throw new RuntimeException(e);
    }

    /*
     * Unable to fully structure code
     */
    public static Field getFieldParent(Class<?> c, String name) {
        block12: {
            try {
                return ReflectionWrapper.getField(c, name);
            }
            catch (Throwable e) {
                try {
                    if (c.getSuperclass() != null) {
                        return ReflectionWrapper.getFieldParent(c.getSuperclass(), name);
                    }
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException)e;
                    }
                    throw new RuntimeException(e);
                }
                catch (Throwable e) {
                    classes = c.getInterfaces();
                    lastExc = null;
                    if (classes.length == 0) break block12;
                    var8_6 = classes;
                    var7_7 = classes.length;
                    var6_8 = 0;
                    ** while (var6_8 < var7_7)
                }
            }
lbl-1000:
            // 1 sources

            {
                i = var8_6[var6_8];
                exc = null;
                try {
                    return ReflectionWrapper.getFieldParent(i, name);
                }
                catch (Throwable e2) {
                    lastExc = exc = e2;
                    ++var6_8;
                }
                continue;
            }
        }
        if (lastExc != null) {
            if (lastExc instanceof RuntimeException) {
                throw (RuntimeException)lastExc;
            }
            throw new RuntimeException(e);
        }
        if (e instanceof RuntimeException) {
            throw (RuntimeException)e;
        }
        throw new RuntimeException(e);
    }

    public static <T> Constructor<T> getInnerConstructor(Class<T> c, Class<?> ... args) {
        try {
            Class[] rargs = new Class[args.length + 1];
            rargs[0] = c.getEnclosingClass();
            int i = 1;
            while (i < rargs.length) {
                rargs[i] = args[i - 1];
                ++i;
            }
            Constructor<T> con = c.getDeclaredConstructor(rargs);
            Root.setAccessible(con, true);
            return con;
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> c, Class<?> ... args) {
        try {
            Constructor<T> con = c.getDeclaredConstructor(args);
            Root.setAccessible(con, true);
            return con;
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getInnerClass(Class<?> parent, String name) {
        Class<?>[] classes;
        Class<?>[] classArray = classes = parent.getDeclaredClasses();
        int n = classes.length;
        int n2 = 0;
        while (n2 < n) {
            Class<?> i = classArray[n2];
            if (i.getSimpleName().equals(name)) {
                return i;
            }
            ++n2;
        }
        return null;
    }

    public static Method getMethod(Class<?> c, String n, Class<?> ... t) {
        try {
            Method m = c.getDeclaredMethod(n, t);
            Root.setAccessible(m, true);
            return m;
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> T invokeMethod(Method m, Object o, Object ... args) {
        try {
            Root.setAccessible(m, true);
            return (T)m.invoke(o, args);
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> T invokeStaticMethod(Method m, Object ... args) {
        Root.setAccessible(m, true);
        return ReflectionWrapper.invokeMethod(m, null, args);
    }

    public static String getNMSClassName(String c) {
        return String.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "net.minecraft.server")) + "." + c;
    }

    public static Class<?> getNMSClass(String c) {
        try {
            return Class.forName(ReflectionWrapper.getNMSClassName(c));
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getCraftBukkitClass(String c) {
        try {
            return Class.forName(String.valueOf(Bukkit.getServer().getClass().getPackage().getName()) + "." + c);
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class<?> c, String n) {
        try {
            Field f = c.getDeclaredField(n);
            Root.setAccessible(f, true);
            return f;
        }
        catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public static <T> T getFieldValue(Field f, Object o) {
        long offset;
        if (MzTech.unsafe == null) {
            Root.setAccessible(f, true);
            try {
                return (T)f.get(o);
            }
            catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        boolean isStatic = Modifier.isStatic(f.getModifiers());
        boolean isVolatile = Modifier.isVolatile(f.getModifiers());
        if (o == null && !isStatic) {
            throw new NullPointerException("\u7ed9\u4e2anull\u4f60\u8111\u762b\u5427");
        }
        long l = offset = isStatic ? MzTech.unsafe.staticFieldOffset(f) : MzTech.unsafe.objectFieldOffset(f);
        if (f.getType().isPrimitive()) {
            if (f.getType() == Integer.TYPE) {
                if (isVolatile) {
                    return (T)Integer.valueOf(MzTech.unsafe.getIntVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Integer.valueOf(MzTech.unsafe.getInt(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Float.TYPE) {
                if (isVolatile) {
                    return (T)Float.valueOf(MzTech.unsafe.getFloatVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Float.valueOf(MzTech.unsafe.getFloat(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Double.TYPE) {
                if (isVolatile) {
                    return (T)Double.valueOf(MzTech.unsafe.getDoubleVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Double.valueOf(MzTech.unsafe.getDouble(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Boolean.TYPE) {
                if (isVolatile) {
                    return (T)Boolean.valueOf(MzTech.unsafe.getBooleanVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Boolean.valueOf(MzTech.unsafe.getBoolean(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Byte.TYPE) {
                if (isVolatile) {
                    return (T)Byte.valueOf(MzTech.unsafe.getByteVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Byte.valueOf(MzTech.unsafe.getByte(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Character.TYPE) {
                if (isVolatile) {
                    return (T)Character.valueOf(MzTech.unsafe.getCharVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Character.valueOf(MzTech.unsafe.getChar(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Long.TYPE) {
                if (isVolatile) {
                    return (T)Long.valueOf(MzTech.unsafe.getLongVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Long.valueOf(MzTech.unsafe.getLong(isStatic ? f.getDeclaringClass() : o, offset));
            }
            if (f.getType() == Short.TYPE) {
                if (isVolatile) {
                    return (T)Short.valueOf(MzTech.unsafe.getShortVolatile(isStatic ? f.getDeclaringClass() : o, offset));
                }
                return (T)Short.valueOf(MzTech.unsafe.getShort(isStatic ? f.getDeclaringClass() : o, offset));
            }
            return null;
        }
        if (isVolatile) {
            return (T)MzTech.unsafe.getObjectVolatile(isStatic ? f.getDeclaringClass() : o, offset);
        }
        return (T)MzTech.unsafe.getObject(isStatic ? f.getDeclaringClass() : o, offset);
    }

    public static <T> T getStaticFieldValue(Field f) {
        return ReflectionWrapper.getFieldValue(f, null);
    }

    public static <T> T setFieldValue(Field f, Object o, T v) {
        long offset;
        boolean isStatic = Modifier.isStatic(f.getModifiers());
        boolean isVolatile = Modifier.isVolatile(f.getModifiers());
        if (o == null && !isStatic) {
            throw new NullPointerException("\u7ed9\u4e2anull\u4f60\u8111\u762b\u5427");
        }
        long l = offset = isStatic ? MzTech.unsafe.staticFieldOffset(f) : MzTech.unsafe.objectFieldOffset(f);
        if (f.getType().isPrimitive()) {
            if (f.getType() == Integer.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putIntVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Integer)v);
                } else {
                    MzTech.unsafe.putInt(isStatic ? f.getDeclaringClass() : o, offset, (Integer)v);
                }
            } else if (f.getType() == Float.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putFloatVolatile(isStatic ? f.getDeclaringClass() : o, offset, ((Float)v).floatValue());
                } else {
                    MzTech.unsafe.putFloat(isStatic ? f.getDeclaringClass() : o, offset, ((Float)v).floatValue());
                }
            } else if (f.getType() == Double.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putDoubleVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Double)v);
                } else {
                    MzTech.unsafe.putDouble(isStatic ? f.getDeclaringClass() : o, offset, (Double)v);
                }
            } else if (f.getType() == Boolean.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putBooleanVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Boolean)v);
                } else {
                    MzTech.unsafe.putBoolean(isStatic ? f.getDeclaringClass() : o, offset, (Boolean)v);
                }
            } else if (f.getType() == Byte.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putByteVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Byte)v);
                } else {
                    MzTech.unsafe.putByte(isStatic ? f.getDeclaringClass() : o, offset, (Byte)v);
                }
            } else if (f.getType() == Character.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putCharVolatile(isStatic ? f.getDeclaringClass() : o, offset, ((Character)v).charValue());
                } else {
                    MzTech.unsafe.putChar(isStatic ? f.getDeclaringClass() : o, offset, ((Character)v).charValue());
                }
            } else if (f.getType() == Long.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putLongVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Long)v);
                } else {
                    MzTech.unsafe.putLong(isStatic ? f.getDeclaringClass() : o, offset, (Long)v);
                }
            } else if (f.getType() == Short.TYPE) {
                if (isVolatile) {
                    MzTech.unsafe.putShortVolatile(isStatic ? f.getDeclaringClass() : o, offset, (Short)v);
                } else {
                    MzTech.unsafe.putShort(isStatic ? f.getDeclaringClass() : o, offset, (Short)v);
                }
            }
        } else if (isVolatile) {
            MzTech.unsafe.putObjectVolatile(isStatic ? f.getDeclaringClass() : o, offset, f.getType().cast(v));
        } else {
            MzTech.unsafe.putObject(isStatic ? f.getDeclaringClass() : o, offset, f.getType().cast(v));
        }
        return v;
    }

    public static <T> T setStaticFieldValue(Field f, T v) {
        return ReflectionWrapper.setFieldValue(f, null, v);
    }

    public static void copyObjectData(Class<?> clazz, Object src, Object tar) {
        Class<?> s = clazz.getSuperclass();
        if (s != Object.class) {
            ReflectionWrapper.copyObjectData(s, src, tar);
        }
        Field[] fieldArray = clazz.getFields();
        int n = fieldArray.length;
        int n2 = 0;
        while (n2 < n) {
            Field f = fieldArray[n2];
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
                ReflectionWrapper.setFieldValue(f, tar, ReflectionWrapper.getFieldValue(f, src));
            }
            ++n2;
        }
    }

    public static <T, O, A> List<T> invokeMethods(List<Method> methods, O o, A ... args) {
        return methods.stream().map(method -> {
            try {
                return method.invoke(o, args);
            }
            catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public static <T> Class<T[]> getArrayClass(Class<T> c) {
        return Array.newInstance(c, 0).getClass();
    }
}

