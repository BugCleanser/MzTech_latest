/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.reflect.ConstructorAccessor
 *  sun.reflect.FieldAccessor
 *  sun.reflect.MethodAccessor
 */
package me.xdark.reflectionhooks.core;

import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.xdark.reflectionhooks.api.FieldGetController;
import me.xdark.reflectionhooks.api.FieldSetController;
import me.xdark.reflectionhooks.api.Hook;
import me.xdark.reflectionhooks.api.Invoker;
import me.xdark.reflectionhooks.core.Environment;
import me.xdark.reflectionhooks.core.JavaAccess;
import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.MethodAccessor;
import sun.reflect.ReflectionFactory;

final class JavaAccessOld
implements JavaAccess {
    private final ReflectionFactory factory = ReflectionFactory.getReflectionFactory();

    JavaAccessOld() {
    }

    @Override
    public void init() throws Throwable {
        Environment.LOOKUP.findStaticSetter(Class.class, "useCaches", Boolean.TYPE).invokeExact(true);
        Environment.LOOKUP.findStatic(ReflectionFactory.class, "checkInitted", MethodType.methodType(Void.TYPE)).invokeExact();
    }

    @Override
    public <R> Object newMethodAccessor(Method method, Invoker<R> invoker, Hook hook) {
        MethodAccessor accessor = this.factory.newMethodAccessor(method);
        Invoker<Object> parent = (parent1, handle, args) -> accessor.invoke(handle, args);
        return (handle, args) -> {
            try {
                if (!hook.isHooked()) {
                    return parent.invoke(null, handle, args);
                }
                return invoker.invoke(parent, handle, args);
            }
            catch (Throwable t) {
                throw new InvocationTargetException(t);
            }
        };
    }

    @Override
    public Object newFieldAccessor(Field field, final FieldGetController getController, final FieldSetController setController, final Hook hook) {
        final FieldAccessor accessor = this.factory.newFieldAccessor(field, true);
        final FieldGetController parentGet = (parent, handle) -> accessor.get(handle);
        final FieldSetController parentSet = (parent, handle, value) -> {
            try {
                accessor.set(handle, value);
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        };
        return new FieldAccessor(){

            public Object get(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.get(o) : getController.get(parentGet, o);
            }

            public boolean getBoolean(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getBoolean(o) : ((Boolean)getController.get(parentGet, o)).booleanValue();
            }

            public byte getByte(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getByte(o) : ((Byte)getController.get(parentGet, o)).byteValue();
            }

            public char getChar(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getChar(o) : ((Character)getController.get(parentGet, o)).charValue();
            }

            public short getShort(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getShort(o) : ((Short)getController.get(parentGet, o)).shortValue();
            }

            public int getInt(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getInt(o) : ((Integer)getController.get(parentGet, o)).intValue();
            }

            public long getLong(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getLong(o) : ((Long)getController.get(parentGet, o)).longValue();
            }

            public float getFloat(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getFloat(o) : ((Float)getController.get(parentGet, o)).floatValue();
            }

            public double getDouble(Object o) throws IllegalArgumentException {
                return getController == null || !hook.isHooked() ? accessor.getDouble(o) : ((Double)getController.get(parentGet, o)).doubleValue();
            }

            public void set(Object o, Object o1) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.set(o, o1);
                } else {
                    setController.set(parentSet, o, o1);
                }
            }

            public void setBoolean(Object o, boolean b) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setBoolean(o, b);
                } else {
                    setController.set(parentSet, o, b);
                }
            }

            public void setByte(Object o, byte b) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setByte(o, b);
                } else {
                    setController.set(parentSet, o, b);
                }
            }

            public void setChar(Object o, char c) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setChar(o, c);
                } else {
                    setController.set(parentSet, o, Character.valueOf(c));
                }
            }

            public void setShort(Object o, short i) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setShort(o, i);
                } else {
                    setController.set(parentSet, o, i);
                }
            }

            public void setInt(Object o, int i) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setInt(o, i);
                } else {
                    setController.set(parentSet, o, i);
                }
            }

            public void setLong(Object o, long l) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setLong(o, l);
                } else {
                    setController.set(parentSet, o, l);
                }
            }

            public void setFloat(Object o, float v) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setFloat(o, v);
                } else {
                    setController.set(parentSet, o, Float.valueOf(v));
                }
            }

            public void setDouble(Object o, double v) throws IllegalArgumentException, IllegalAccessException {
                if (setController == null || !hook.isHooked()) {
                    accessor.setDouble(o, v);
                } else {
                    setController.set(parentSet, o, v);
                }
            }
        };
    }

    @Override
    public <R> Object newConstructorAccessor(Constructor<R> constructor, Invoker<R> invoker, Hook hook) {
        ConstructorAccessor accessor = this.factory.newConstructorAccessor(constructor);
        Invoker<Object> parent = (parent1, handle, args) -> accessor.newInstance(args);
        return args -> {
            try {
                if (!hook.isHooked()) {
                    return parent.invoke(null, null, args);
                }
                return invoker.invoke(parent, null, args);
            }
            catch (Throwable t) {
                throw new InvocationTargetException(t);
            }
        };
    }

    @Override
    public Class<?> resolve(String target) {
        try {
            return Class.forName("sun.reflect." + target);
        }
        catch (ClassNotFoundException ex) {
            return (Class)Environment.sneakyThrow(ex);
        }
    }
}

