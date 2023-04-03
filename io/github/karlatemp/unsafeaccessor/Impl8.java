/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.misc.Cleaner
 */
package io.github.karlatemp.unsafeaccessor;

import io.github.karlatemp.unsafeaccessor.Unsafe;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

class Impl8
extends Unsafe {
    private static final sun.misc.Unsafe usf = Impl8.openUnsafe();

    Impl8() {
    }

    private static sun.misc.Unsafe openUnsafe() {
        Class<sun.misc.Unsafe> usf = sun.misc.Unsafe.class;
        try {
            Field theUnsafe = usf.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (sun.misc.Unsafe)theUnsafe.get(null);
        }
        catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public int getInt(Object o, long offset) {
        return usf.getInt(o, offset);
    }

    @Override
    public void putInt(Object o, long offset, int x) {
        usf.putInt(o, offset, x);
    }

    @Override
    public Object getReference(Object o, long offset) {
        return usf.getObject(o, offset);
    }

    @Override
    public void putReference(Object o, long offset, Object x) {
        usf.putObject(o, offset, x);
    }

    @Override
    public Object getObject(Object o, long offset) {
        return usf.getObject(o, offset);
    }

    @Override
    public void putObject(Object o, long offset, Object x) {
        usf.putObject(o, offset, x);
    }

    @Override
    public boolean getBoolean(Object o, long offset) {
        return usf.getBoolean(o, offset);
    }

    @Override
    public void putBoolean(Object o, long offset, boolean x) {
        usf.putBoolean(o, offset, x);
    }

    @Override
    public byte getByte(Object o, long offset) {
        return usf.getByte(o, offset);
    }

    @Override
    public void putByte(Object o, long offset, byte x) {
        usf.putByte(o, offset, x);
    }

    @Override
    public short getShort(Object o, long offset) {
        return usf.getShort(o, offset);
    }

    @Override
    public void putShort(Object o, long offset, short x) {
        usf.putShort(o, offset, x);
    }

    @Override
    public char getChar(Object o, long offset) {
        return usf.getChar(o, offset);
    }

    @Override
    public void putChar(Object o, long offset, char x) {
        usf.putChar(o, offset, x);
    }

    @Override
    public long getLong(Object o, long offset) {
        return usf.getLong(o, offset);
    }

    @Override
    public void putLong(Object o, long offset, long x) {
        usf.putLong(o, offset, x);
    }

    @Override
    public float getFloat(Object o, long offset) {
        return usf.getFloat(o, offset);
    }

    @Override
    public void putFloat(Object o, long offset, float x) {
        usf.putFloat(o, offset, x);
    }

    @Override
    public double getDouble(Object o, long offset) {
        return usf.getDouble(o, offset);
    }

    @Override
    public void putDouble(Object o, long offset, double x) {
        usf.putDouble(o, offset, x);
    }

    @Override
    public Object getUncompressedObject(long address) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByte(long address) {
        return usf.getByte(address);
    }

    @Override
    public void putByte(long address, byte x) {
        usf.putByte(address, x);
    }

    @Override
    public short getShort(long address) {
        return usf.getShort(address);
    }

    @Override
    public void putShort(long address, short x) {
        usf.putShort(address, x);
    }

    @Override
    public char getChar(long address) {
        return usf.getChar(address);
    }

    @Override
    public void putChar(long address, char x) {
        usf.putChar(address, x);
    }

    @Override
    public int getInt(long address) {
        return usf.getInt(address);
    }

    @Override
    public void putInt(long address, int x) {
        usf.putInt(address, x);
    }

    @Override
    public long getLong(long address) {
        return usf.getLong(address);
    }

    @Override
    public void putLong(long address, long x) {
        usf.putLong(address, x);
    }

    @Override
    public float getFloat(long address) {
        return usf.getFloat(address);
    }

    @Override
    public void putFloat(long address, float x) {
        usf.putFloat(address, x);
    }

    @Override
    public double getDouble(long address) {
        return usf.getDouble(address);
    }

    @Override
    public void putDouble(long address, double x) {
        usf.putDouble(address, x);
    }

    @Override
    public long getAddress(long address) {
        return usf.getAddress(address);
    }

    @Override
    public void putAddress(long address, long x) {
        usf.putAddress(address, x);
    }

    @Override
    public long allocateMemory(long bytes) {
        return usf.allocateMemory(bytes);
    }

    @Override
    public long reallocateMemory(long address, long bytes) {
        return usf.reallocateMemory(address, bytes);
    }

    @Override
    public void setMemory(Object o, long offset, long bytes, byte value) {
        usf.setMemory(o, offset, bytes, value);
    }

    @Override
    public void setMemory(long address, long bytes, byte value) {
        usf.setMemory(address, bytes, value);
    }

    @Override
    public void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) {
        usf.copyMemory(srcBase, srcOffset, destBase, destOffset, bytes);
    }

    @Override
    public void copyMemory(long srcAddress, long destAddress, long bytes) {
        usf.copyMemory(srcAddress, destAddress, bytes);
    }

    @Override
    public void copySwapMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes, long elemSize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void copySwapMemory(long srcAddress, long destAddress, long bytes, long elemSize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void freeMemory(long address) {
        usf.freeMemory(address);
    }

    @Override
    public long objectFieldOffset(Field f) {
        return usf.objectFieldOffset(f);
    }

    @Override
    public long objectFieldOffset(Class<?> c2, String name) {
        Field field = null;
        try {
            field = c2.getDeclaredField(name);
            return this.objectFieldOffset(field);
        }
        catch (NoSuchFieldException e) {
            throw new NoSuchFieldError(name);
        }
    }

    @Override
    public long staticFieldOffset(Field f) {
        return usf.staticFieldOffset(f);
    }

    @Override
    public Object staticFieldBase(Field f) {
        return usf.staticFieldBase(f);
    }

    @Override
    public boolean shouldBeInitialized(Class<?> c2) {
        return usf.shouldBeInitialized(c2);
    }

    @Override
    public void ensureClassInitialized(Class<?> c2) {
        usf.ensureClassInitialized(c2);
    }

    @Override
    public int arrayBaseOffset(Class<?> arrayClass) {
        return usf.arrayBaseOffset(arrayClass);
    }

    @Override
    public int arrayIndexScale(Class<?> arrayClass) {
        return usf.arrayIndexScale(arrayClass);
    }

    @Override
    public int addressSize() {
        return usf.addressSize();
    }

    @Override
    public int pageSize() {
        return usf.pageSize();
    }

    @Override
    public Class<?> defineClass(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) {
        return usf.defineClass(name, b, off, len, loader, protectionDomain);
    }

    @Override
    public Class<?> defineClass0(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) {
        return usf.defineClass(name, b, off, len, loader, protectionDomain);
    }

    @Override
    public Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches) {
        return usf.defineAnonymousClass(hostClass, data, cpPatches);
    }

    @Override
    public Object allocateInstance(Class<?> cls) throws InstantiationException {
        return usf.allocateInstance(cls);
    }

    @Override
    public Object allocateUninitializedArray(Class<?> componentType, int length) {
        return null;
    }

    @Override
    public void throwException(Throwable ee) {
        usf.throwException(ee);
    }

    @Override
    public boolean compareAndSetReference(Object o, long offset, Object expected, Object x) {
        return usf.compareAndSwapObject(o, offset, expected, x);
    }

    @Override
    public Object compareAndExchangeReference(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object compareAndExchangeReferenceAcquire(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object compareAndExchangeReferenceRelease(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetReferencePlain(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetReferenceAcquire(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetReferenceRelease(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetReference(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetInt(Object o, long offset, int expected, int x) {
        return usf.compareAndSwapInt(o, offset, expected, x);
    }

    @Override
    public int compareAndExchangeInt(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareAndExchangeIntAcquire(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareAndExchangeIntRelease(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetIntPlain(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetIntAcquire(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetIntRelease(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetInt(Object o, long offset, int expected, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte compareAndExchangeByte(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetByte(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetByte(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetByteAcquire(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetByteRelease(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetBytePlain(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte compareAndExchangeByteAcquire(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte compareAndExchangeByteRelease(Object o, long offset, byte expected, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short compareAndExchangeShort(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetShort(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetShort(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetShortAcquire(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetShortRelease(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetShortPlain(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short compareAndExchangeShortAcquire(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short compareAndExchangeShortRelease(Object o, long offset, short expected, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetChar(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char compareAndExchangeChar(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char compareAndExchangeCharAcquire(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char compareAndExchangeCharRelease(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetChar(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetCharAcquire(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetCharRelease(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetCharPlain(Object o, long offset, char expected, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndExchangeBoolean(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndExchangeBooleanAcquire(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndExchangeBooleanRelease(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetBooleanAcquire(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetBooleanRelease(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetBooleanPlain(Object o, long offset, boolean expected, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetFloat(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float compareAndExchangeFloat(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float compareAndExchangeFloatAcquire(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float compareAndExchangeFloatRelease(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetFloatPlain(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetFloatAcquire(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetFloatRelease(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetFloat(Object o, long offset, float expected, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetDouble(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double compareAndExchangeDouble(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double compareAndExchangeDoubleAcquire(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double compareAndExchangeDoubleRelease(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetDoublePlain(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetDoubleAcquire(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetDoubleRelease(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetDouble(Object o, long offset, double expected, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean compareAndSetLong(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long compareAndExchangeLong(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long compareAndExchangeLongAcquire(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long compareAndExchangeLongRelease(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetLongPlain(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetLongAcquire(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetLongRelease(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetLong(Object o, long offset, long expected, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getReferenceVolatile(Object o, long offset) {
        return null;
    }

    @Override
    public void putReferenceVolatile(Object o, long offset, Object x) {
        throw new UnsupportedOperationException();
    }

    public boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) {
        return usf.compareAndSwapObject(o, offset, expected, x);
    }

    public boolean compareAndSwapInt(Object o, long offset, int expected, int x) {
        return usf.compareAndSwapInt(o, offset, expected, x);
    }

    public boolean compareAndSwapLong(Object o, long offset, long expected, long x) {
        return usf.compareAndSwapLong(o, offset, expected, x);
    }

    @Override
    public Object getObjectVolatile(Object o, long offset) {
        return usf.getObjectVolatile(o, offset);
    }

    @Override
    public Object getObjectAcquire(Object o, long offset) {
        return null;
    }

    @Override
    public Object getObjectOpaque(Object o, long offset) {
        return null;
    }

    @Override
    public void putObjectVolatile(Object o, long offset, Object x) {
        usf.putObjectVolatile(o, offset, x);
    }

    @Override
    public void putObjectOpaque(Object o, long offset, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putObjectRelease(Object o, long offset, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntVolatile(Object o, long offset) {
        return usf.getIntVolatile(o, offset);
    }

    @Override
    public void putIntVolatile(Object o, long offset, int x) {
        usf.putIntVolatile(o, offset, x);
    }

    @Override
    public boolean getBooleanVolatile(Object o, long offset) {
        return usf.getBooleanVolatile(o, offset);
    }

    @Override
    public void putBooleanVolatile(Object o, long offset, boolean x) {
        usf.putBooleanVolatile(o, offset, x);
    }

    @Override
    public byte getByteVolatile(Object o, long offset) {
        return usf.getByteVolatile(o, offset);
    }

    @Override
    public void putByteVolatile(Object o, long offset, byte x) {
        usf.putByteVolatile(o, offset, x);
    }

    @Override
    public short getShortVolatile(Object o, long offset) {
        return usf.getShortVolatile(o, offset);
    }

    @Override
    public void putShortVolatile(Object o, long offset, short x) {
        usf.putShortVolatile(o, offset, x);
    }

    @Override
    public char getCharVolatile(Object o, long offset) {
        return usf.getCharVolatile(o, offset);
    }

    @Override
    public void putCharVolatile(Object o, long offset, char x) {
        usf.putCharVolatile(o, offset, x);
    }

    @Override
    public long getLongVolatile(Object o, long offset) {
        return usf.getLongVolatile(o, offset);
    }

    @Override
    public void putLongVolatile(Object o, long offset, long x) {
        usf.putLongVolatile(o, offset, x);
    }

    @Override
    public float getFloatVolatile(Object o, long offset) {
        return usf.getFloatVolatile(o, offset);
    }

    @Override
    public void putFloatVolatile(Object o, long offset, float x) {
        usf.putFloatVolatile(o, offset, x);
    }

    @Override
    public double getDoubleVolatile(Object o, long offset) {
        return usf.getDoubleVolatile(o, offset);
    }

    @Override
    public void putDoubleVolatile(Object o, long offset, double x) {
        usf.putDoubleVolatile(o, offset, x);
    }

    @Override
    public Object getReferenceAcquire(Object o, long offset) {
        return null;
    }

    @Override
    public boolean getBooleanAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByteAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShortAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getCharAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getFloatAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLongAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getDoubleAcquire(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putReferenceRelease(Object o, long offset, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putBooleanRelease(Object o, long offset, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putByteRelease(Object o, long offset, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putShortRelease(Object o, long offset, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putCharRelease(Object o, long offset, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putIntRelease(Object o, long offset, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putFloatRelease(Object o, long offset, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putLongRelease(Object o, long offset, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putDoubleRelease(Object o, long offset, double x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getReferenceOpaque(Object o, long offset) {
        return null;
    }

    @Override
    public boolean getBooleanOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByteOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShortOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getCharOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getFloatOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLongOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getDoubleOpaque(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putReferenceOpaque(Object o, long offset, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putBooleanOpaque(Object o, long offset, boolean x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putByteOpaque(Object o, long offset, byte x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putShortOpaque(Object o, long offset, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putCharOpaque(Object o, long offset, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putIntOpaque(Object o, long offset, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putFloatOpaque(Object o, long offset, float x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putLongOpaque(Object o, long offset, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putDoubleOpaque(Object o, long offset, double x) {
        throw new UnsupportedOperationException();
    }

    public void putOrderedObject(Object o, long offset, Object x) {
        usf.putOrderedObject(o, offset, x);
    }

    public void putOrderedInt(Object o, long offset, int x) {
        usf.putOrderedInt(o, offset, x);
    }

    public void putOrderedLong(Object o, long offset, long x) {
        usf.putOrderedLong(o, offset, x);
    }

    @Override
    public void unpark(Object thread) {
        usf.unpark(thread);
    }

    @Override
    public void park(boolean isAbsolute, long time) {
        usf.park(isAbsolute, time);
    }

    @Override
    public int getLoadAverage(double[] loadavg, int nelems) {
        return usf.getLoadAverage(loadavg, nelems);
    }

    @Override
    public int getAndAddInt(Object o, long offset, int delta) {
        return usf.getAndAddInt(o, offset, delta);
    }

    @Override
    public int getAndAddIntRelease(Object o, long offset, int delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndAddIntAcquire(Object o, long offset, int delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndAddLong(Object o, long offset, long delta) {
        return usf.getAndAddLong(o, offset, delta);
    }

    @Override
    public long getAndAddLongRelease(Object o, long offset, long delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndAddLongAcquire(Object o, long offset, long delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndAddByte(Object o, long offset, byte delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndAddByteRelease(Object o, long offset, byte delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndAddByteAcquire(Object o, long offset, byte delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndAddShort(Object o, long offset, short delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndAddShortRelease(Object o, long offset, short delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndAddShortAcquire(Object o, long offset, short delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndAddChar(Object o, long offset, char delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndAddCharRelease(Object o, long offset, char delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndAddCharAcquire(Object o, long offset, char delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndAddFloat(Object o, long offset, float delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndAddFloatRelease(Object o, long offset, float delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndAddFloatAcquire(Object o, long offset, float delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndAddDouble(Object o, long offset, double delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndAddDoubleRelease(Object o, long offset, double delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndAddDoubleAcquire(Object o, long offset, double delta) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndSetInt(Object o, long offset, int newValue) {
        return usf.getAndSetInt(o, offset, newValue);
    }

    @Override
    public int getAndSetIntRelease(Object o, long offset, int newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndSetIntAcquire(Object o, long offset, int newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndSetLong(Object o, long offset, long newValue) {
        return usf.getAndSetLong(o, offset, newValue);
    }

    @Override
    public long getAndSetLongRelease(Object o, long offset, long newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndSetLongAcquire(Object o, long offset, long newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAndSetReference(Object o, long offset, Object newValue) {
        return null;
    }

    @Override
    public Object getAndSetReferenceRelease(Object o, long offset, Object newValue) {
        return null;
    }

    @Override
    public Object getAndSetReferenceAcquire(Object o, long offset, Object newValue) {
        return null;
    }

    @Override
    public byte getAndSetByte(Object o, long offset, byte newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndSetByteRelease(Object o, long offset, byte newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndSetByteAcquire(Object o, long offset, byte newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndSetBoolean(Object o, long offset, boolean newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndSetBooleanRelease(Object o, long offset, boolean newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndSetBooleanAcquire(Object o, long offset, boolean newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndSetShort(Object o, long offset, short newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndSetShortRelease(Object o, long offset, short newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndSetShortAcquire(Object o, long offset, short newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndSetChar(Object o, long offset, char newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndSetCharRelease(Object o, long offset, char newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndSetCharAcquire(Object o, long offset, char newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndSetFloat(Object o, long offset, float newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndSetFloatRelease(Object o, long offset, float newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAndSetFloatAcquire(Object o, long offset, float newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndSetDouble(Object o, long offset, double newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndSetDoubleRelease(Object o, long offset, double newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAndSetDoubleAcquire(Object o, long offset, double newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseOrBoolean(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseOrBooleanRelease(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseOrBooleanAcquire(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseAndBoolean(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseAndBooleanRelease(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseAndBooleanAcquire(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseXorBoolean(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseXorBooleanRelease(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAndBitwiseXorBooleanAcquire(Object o, long offset, boolean mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseOrByte(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseOrByteRelease(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseOrByteAcquire(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseAndByte(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseAndByteRelease(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseAndByteAcquire(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseXorByte(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseXorByteRelease(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getAndBitwiseXorByteAcquire(Object o, long offset, byte mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseOrChar(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseOrCharRelease(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseOrCharAcquire(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseAndChar(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseAndCharRelease(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseAndCharAcquire(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseXorChar(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseXorCharRelease(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAndBitwiseXorCharAcquire(Object o, long offset, char mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseOrShort(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseOrShortRelease(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseOrShortAcquire(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseAndShort(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseAndShortRelease(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseAndShortAcquire(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseXorShort(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseXorShortRelease(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getAndBitwiseXorShortAcquire(Object o, long offset, short mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseOrInt(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseOrIntRelease(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseOrIntAcquire(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseAndInt(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseAndIntRelease(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseAndIntAcquire(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseXorInt(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseXorIntRelease(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAndBitwiseXorIntAcquire(Object o, long offset, int mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseOrLong(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseOrLongRelease(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseOrLongAcquire(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseAndLong(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseAndLongRelease(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseAndLongAcquire(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseXorLong(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseXorLongRelease(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getAndBitwiseXorLongAcquire(Object o, long offset, long mask) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAndSetObject(Object o, long offset, Object newValue) {
        return usf.getAndSetObject(o, offset, newValue);
    }

    @Override
    public Object getAndSetObjectAcquire(Object o, long offset, Object newValue) {
        return null;
    }

    @Override
    public Object getAndSetObjectRelease(Object o, long offset, Object newValue) {
        return null;
    }

    @Override
    public boolean compareAndSetObject(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object compareAndExchangeObject(Object o, long offset, Object expected, Object x) {
        return null;
    }

    @Override
    public Object compareAndExchangeObjectAcquire(Object o, long offset, Object expected, Object x) {
        return null;
    }

    @Override
    public Object compareAndExchangeObjectRelease(Object o, long offset, Object expected, Object x) {
        return null;
    }

    @Override
    public boolean weakCompareAndSetObject(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetObjectAcquire(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetObjectPlain(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean weakCompareAndSetObjectRelease(Object o, long offset, Object expected, Object x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadFence() {
        usf.loadFence();
    }

    @Override
    public void storeFence() {
        usf.storeFence();
    }

    @Override
    public void fullFence() {
        usf.fullFence();
    }

    @Override
    public void loadLoadFence() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void storeStoreFence() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBigEndian() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unalignedAccess() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLongUnaligned(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLongUnaligned(Object o, long offset, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntUnaligned(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntUnaligned(Object o, long offset, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShortUnaligned(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShortUnaligned(Object o, long offset, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getCharUnaligned(Object o, long offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getCharUnaligned(Object o, long offset, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putLongUnaligned(Object o, long offset, long x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putLongUnaligned(Object o, long offset, long x, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putIntUnaligned(Object o, long offset, int x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putIntUnaligned(Object o, long offset, int x, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putShortUnaligned(Object o, long offset, short x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putShortUnaligned(Object o, long offset, short x, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putCharUnaligned(Object o, long offset, char x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putCharUnaligned(Object o, long offset, char x, boolean bigEndian) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invokeCleaner(ByteBuffer directBuffer) {
        if (!directBuffer.isDirect()) {
            throw new IllegalArgumentException("buffer is non-direct");
        }
        DirectBuffer db = (DirectBuffer)((Object)directBuffer);
        if (db.attachment() != null) {
            throw new IllegalArgumentException("duplicate or slice");
        }
        Cleaner cleaner = db.cleaner();
        if (cleaner != null) {
            cleaner.clean();
        }
    }
}

