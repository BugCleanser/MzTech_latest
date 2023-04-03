/*
 * Decompiled with CFR 0.152.
 */
package io.github.karlatemp.unsafeaccessor;

import io.github.karlatemp.unsafeaccessor.Unsafe;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;

class Impl9
extends Unsafe {
    Impl9() {
    }

    @Override
    public boolean isJava9() {
        return true;
    }

    @Override
    public int getInt(Object o, long offset) {
        return UsfHolder.usf.getInt(o, offset);
    }

    @Override
    public void putInt(Object o, long offset, int x) {
        UsfHolder.usf.putInt(o, offset, x);
    }

    @Override
    public Object getReference(Object o, long offset) {
        return UsfHolder.usf.getReference(o, offset);
    }

    @Override
    public void putReference(Object o, long offset, Object x) {
        UsfHolder.usf.putReference(o, offset, x);
    }

    @Override
    public boolean getBoolean(Object o, long offset) {
        return UsfHolder.usf.getBoolean(o, offset);
    }

    @Override
    public void putBoolean(Object o, long offset, boolean x) {
        UsfHolder.usf.putBoolean(o, offset, x);
    }

    @Override
    public byte getByte(Object o, long offset) {
        return UsfHolder.usf.getByte(o, offset);
    }

    @Override
    public void putByte(Object o, long offset, byte x) {
        UsfHolder.usf.putByte(o, offset, x);
    }

    @Override
    public short getShort(Object o, long offset) {
        return UsfHolder.usf.getShort(o, offset);
    }

    @Override
    public void putShort(Object o, long offset, short x) {
        UsfHolder.usf.putShort(o, offset, x);
    }

    @Override
    public char getChar(Object o, long offset) {
        return UsfHolder.usf.getChar(o, offset);
    }

    @Override
    public void putChar(Object o, long offset, char x) {
        UsfHolder.usf.putChar(o, offset, x);
    }

    @Override
    public long getLong(Object o, long offset) {
        return UsfHolder.usf.getLong(o, offset);
    }

    @Override
    public void putLong(Object o, long offset, long x) {
        UsfHolder.usf.putLong(o, offset, x);
    }

    @Override
    public float getFloat(Object o, long offset) {
        return UsfHolder.usf.getFloat(o, offset);
    }

    @Override
    public void putFloat(Object o, long offset, float x) {
        UsfHolder.usf.putFloat(o, offset, x);
    }

    @Override
    public double getDouble(Object o, long offset) {
        return UsfHolder.usf.getDouble(o, offset);
    }

    @Override
    public void putDouble(Object o, long offset, double x) {
        UsfHolder.usf.putDouble(o, offset, x);
    }

    @Override
    public long getAddress(Object o, long offset) {
        return UsfHolder.usf.getAddress(o, offset);
    }

    @Override
    public void putAddress(Object o, long offset, long x) {
        UsfHolder.usf.putAddress(o, offset, x);
    }

    @Override
    public Object getUncompressedObject(long address) {
        return UsfHolder.usf.getUncompressedObject(address);
    }

    @Override
    public byte getByte(long address) {
        return UsfHolder.usf.getByte(address);
    }

    @Override
    public void putByte(long address, byte x) {
        UsfHolder.usf.putByte(address, x);
    }

    @Override
    public short getShort(long address) {
        return UsfHolder.usf.getShort(address);
    }

    @Override
    public void putShort(long address, short x) {
        UsfHolder.usf.putShort(address, x);
    }

    @Override
    public char getChar(long address) {
        return UsfHolder.usf.getChar(address);
    }

    @Override
    public void putChar(long address, char x) {
        UsfHolder.usf.putChar(address, x);
    }

    @Override
    public int getInt(long address) {
        return UsfHolder.usf.getInt(address);
    }

    @Override
    public void putInt(long address, int x) {
        UsfHolder.usf.putInt(address, x);
    }

    @Override
    public long getLong(long address) {
        return UsfHolder.usf.getLong(address);
    }

    @Override
    public void putLong(long address, long x) {
        UsfHolder.usf.putLong(address, x);
    }

    @Override
    public float getFloat(long address) {
        return UsfHolder.usf.getFloat(address);
    }

    @Override
    public void putFloat(long address, float x) {
        UsfHolder.usf.putFloat(address, x);
    }

    @Override
    public double getDouble(long address) {
        return UsfHolder.usf.getDouble(address);
    }

    @Override
    public void putDouble(long address, double x) {
        UsfHolder.usf.putDouble(address, x);
    }

    @Override
    public long getAddress(long address) {
        return UsfHolder.usf.getAddress(address);
    }

    @Override
    public void putAddress(long address, long x) {
        UsfHolder.usf.putAddress(address, x);
    }

    @Override
    public long allocateMemory(long bytes) {
        return UsfHolder.usf.allocateMemory(bytes);
    }

    @Override
    public long reallocateMemory(long address, long bytes) {
        return UsfHolder.usf.reallocateMemory(address, bytes);
    }

    @Override
    public void setMemory(Object o, long offset, long bytes, byte value) {
        UsfHolder.usf.setMemory(o, offset, bytes, value);
    }

    @Override
    public void setMemory(long address, long bytes, byte value) {
        UsfHolder.usf.setMemory(address, bytes, value);
    }

    @Override
    public void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) {
        UsfHolder.usf.copyMemory(srcBase, srcOffset, destBase, destOffset, bytes);
    }

    @Override
    public void copyMemory(long srcAddress, long destAddress, long bytes) {
        UsfHolder.usf.copyMemory(srcAddress, destAddress, bytes);
    }

    @Override
    public void copySwapMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes, long elemSize) {
        UsfHolder.usf.copySwapMemory(srcBase, srcOffset, destBase, destOffset, bytes, elemSize);
    }

    @Override
    public void copySwapMemory(long srcAddress, long destAddress, long bytes, long elemSize) {
        UsfHolder.usf.copySwapMemory(srcAddress, destAddress, bytes, elemSize);
    }

    @Override
    public void freeMemory(long address) {
        UsfHolder.usf.freeMemory(address);
    }

    @Override
    public long objectFieldOffset(Field f) {
        return UsfHolder.usf.objectFieldOffset(f);
    }

    @Override
    public long objectFieldOffset(Class<?> c2, String name) {
        return UsfHolder.usf.objectFieldOffset(c2, name);
    }

    @Override
    public long staticFieldOffset(Field f) {
        return UsfHolder.usf.staticFieldOffset(f);
    }

    @Override
    public Object staticFieldBase(Field f) {
        return UsfHolder.usf.staticFieldBase(f);
    }

    @Override
    public boolean shouldBeInitialized(Class<?> c2) {
        return UsfHolder.usf.shouldBeInitialized(c2);
    }

    @Override
    public void ensureClassInitialized(Class<?> c2) {
        UsfHolder.usf.ensureClassInitialized(c2);
    }

    @Override
    public int arrayBaseOffset(Class<?> arrayClass) {
        return UsfHolder.usf.arrayBaseOffset(arrayClass);
    }

    @Override
    public int arrayIndexScale(Class<?> arrayClass) {
        return UsfHolder.usf.arrayIndexScale(arrayClass);
    }

    @Override
    public int addressSize() {
        return UsfHolder.usf.addressSize();
    }

    @Override
    public int pageSize() {
        return UsfHolder.usf.pageSize();
    }

    @Override
    public Class<?> defineClass(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) {
        return UsfHolder.usf.defineClass(name, b, off, len, loader, protectionDomain);
    }

    @Override
    public Class<?> defineClass0(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) {
        return UsfHolder.usf.defineClass0(name, b, off, len, loader, protectionDomain);
    }

    @Override
    public Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches) {
        return UsfHolder.usf.defineAnonymousClass(hostClass, data, cpPatches);
    }

    @Override
    public Object allocateInstance(Class<?> cls) throws InstantiationException {
        return UsfHolder.usf.allocateInstance(cls);
    }

    @Override
    public Object allocateUninitializedArray(Class<?> componentType, int length) {
        return UsfHolder.usf.allocateUninitializedArray(componentType, length);
    }

    @Override
    public void throwException(Throwable ee) {
        UsfHolder.usf.throwException(ee);
    }

    @Override
    public boolean compareAndSetReference(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndSetReference(o, offset, expected, x);
    }

    @Override
    public Object compareAndExchangeReference(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeReference(o, offset, expected, x);
    }

    @Override
    public Object compareAndExchangeReferenceAcquire(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeReferenceAcquire(o, offset, expected, x);
    }

    @Override
    public Object compareAndExchangeReferenceRelease(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeReferenceRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetReferencePlain(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetReferencePlain(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetReferenceAcquire(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetReferenceAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetReferenceRelease(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetReferenceRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetReference(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetReference(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetInt(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.compareAndSetInt(o, offset, expected, x);
    }

    @Override
    public int compareAndExchangeInt(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.compareAndExchangeInt(o, offset, expected, x);
    }

    @Override
    public int compareAndExchangeIntAcquire(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.compareAndExchangeIntAcquire(o, offset, expected, x);
    }

    @Override
    public int compareAndExchangeIntRelease(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.compareAndExchangeIntRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetIntPlain(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.weakCompareAndSetIntPlain(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetIntAcquire(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.weakCompareAndSetIntAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetIntRelease(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.weakCompareAndSetIntRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetInt(Object o, long offset, int expected, int x) {
        return UsfHolder.usf.weakCompareAndSetInt(o, offset, expected, x);
    }

    @Override
    public byte compareAndExchangeByte(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.compareAndExchangeByte(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetByte(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.compareAndSetByte(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetByte(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.weakCompareAndSetByte(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetByteAcquire(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.weakCompareAndSetByteAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetByteRelease(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.weakCompareAndSetByteRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetBytePlain(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.weakCompareAndSetBytePlain(o, offset, expected, x);
    }

    @Override
    public byte compareAndExchangeByteAcquire(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.compareAndExchangeByteAcquire(o, offset, expected, x);
    }

    @Override
    public byte compareAndExchangeByteRelease(Object o, long offset, byte expected, byte x) {
        return UsfHolder.usf.compareAndExchangeByteRelease(o, offset, expected, x);
    }

    @Override
    public short compareAndExchangeShort(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.compareAndExchangeShort(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetShort(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.compareAndSetShort(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetShort(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.weakCompareAndSetShort(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetShortAcquire(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.weakCompareAndSetShortAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetShortRelease(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.weakCompareAndSetShortRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetShortPlain(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.weakCompareAndSetShortPlain(o, offset, expected, x);
    }

    @Override
    public short compareAndExchangeShortAcquire(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.compareAndExchangeShortAcquire(o, offset, expected, x);
    }

    @Override
    public short compareAndExchangeShortRelease(Object o, long offset, short expected, short x) {
        return UsfHolder.usf.compareAndExchangeShortRelease(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetChar(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.compareAndSetChar(o, offset, expected, x);
    }

    @Override
    public char compareAndExchangeChar(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.compareAndExchangeChar(o, offset, expected, x);
    }

    @Override
    public char compareAndExchangeCharAcquire(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.compareAndExchangeCharAcquire(o, offset, expected, x);
    }

    @Override
    public char compareAndExchangeCharRelease(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.compareAndExchangeCharRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetChar(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.weakCompareAndSetChar(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetCharAcquire(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.weakCompareAndSetCharAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetCharRelease(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.weakCompareAndSetCharRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetCharPlain(Object o, long offset, char expected, char x) {
        return UsfHolder.usf.weakCompareAndSetCharPlain(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.compareAndSetBoolean(o, offset, expected, x);
    }

    @Override
    public boolean compareAndExchangeBoolean(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.compareAndExchangeBoolean(o, offset, expected, x);
    }

    @Override
    public boolean compareAndExchangeBooleanAcquire(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.compareAndExchangeBooleanAcquire(o, offset, expected, x);
    }

    @Override
    public boolean compareAndExchangeBooleanRelease(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.compareAndExchangeBooleanRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetBoolean(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.weakCompareAndSetBoolean(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetBooleanAcquire(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.weakCompareAndSetBooleanAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetBooleanRelease(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.weakCompareAndSetBooleanRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetBooleanPlain(Object o, long offset, boolean expected, boolean x) {
        return UsfHolder.usf.weakCompareAndSetBooleanPlain(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetFloat(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.compareAndSetFloat(o, offset, expected, x);
    }

    @Override
    public float compareAndExchangeFloat(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.compareAndExchangeFloat(o, offset, expected, x);
    }

    @Override
    public float compareAndExchangeFloatAcquire(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.compareAndExchangeFloatAcquire(o, offset, expected, x);
    }

    @Override
    public float compareAndExchangeFloatRelease(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.compareAndExchangeFloatRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetFloatPlain(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.weakCompareAndSetFloatPlain(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetFloatAcquire(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.weakCompareAndSetFloatAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetFloatRelease(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.weakCompareAndSetFloatRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetFloat(Object o, long offset, float expected, float x) {
        return UsfHolder.usf.weakCompareAndSetFloat(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetDouble(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.compareAndSetDouble(o, offset, expected, x);
    }

    @Override
    public double compareAndExchangeDouble(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.compareAndExchangeDouble(o, offset, expected, x);
    }

    @Override
    public double compareAndExchangeDoubleAcquire(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.compareAndExchangeDoubleAcquire(o, offset, expected, x);
    }

    @Override
    public double compareAndExchangeDoubleRelease(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.compareAndExchangeDoubleRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetDoublePlain(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.weakCompareAndSetDoublePlain(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetDoubleAcquire(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.weakCompareAndSetDoubleAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetDoubleRelease(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.weakCompareAndSetDoubleRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetDouble(Object o, long offset, double expected, double x) {
        return UsfHolder.usf.weakCompareAndSetDouble(o, offset, expected, x);
    }

    @Override
    public boolean compareAndSetLong(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.compareAndSetLong(o, offset, expected, x);
    }

    @Override
    public long compareAndExchangeLong(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.compareAndExchangeLong(o, offset, expected, x);
    }

    @Override
    public long compareAndExchangeLongAcquire(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.compareAndExchangeLongAcquire(o, offset, expected, x);
    }

    @Override
    public long compareAndExchangeLongRelease(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.compareAndExchangeLongRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetLongPlain(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.weakCompareAndSetLongPlain(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetLongAcquire(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.weakCompareAndSetLongAcquire(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetLongRelease(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.weakCompareAndSetLongRelease(o, offset, expected, x);
    }

    @Override
    public boolean weakCompareAndSetLong(Object o, long offset, long expected, long x) {
        return UsfHolder.usf.weakCompareAndSetLong(o, offset, expected, x);
    }

    @Override
    public Object getReferenceVolatile(Object o, long offset) {
        return UsfHolder.usf.getReferenceVolatile(o, offset);
    }

    @Override
    public void putReferenceVolatile(Object o, long offset, Object x) {
        UsfHolder.usf.putReferenceVolatile(o, offset, x);
    }

    @Override
    public int getIntVolatile(Object o, long offset) {
        return UsfHolder.usf.getIntVolatile(o, offset);
    }

    @Override
    public void putIntVolatile(Object o, long offset, int x) {
        UsfHolder.usf.putIntVolatile(o, offset, x);
    }

    @Override
    public boolean getBooleanVolatile(Object o, long offset) {
        return UsfHolder.usf.getBooleanVolatile(o, offset);
    }

    @Override
    public void putBooleanVolatile(Object o, long offset, boolean x) {
        UsfHolder.usf.putBooleanVolatile(o, offset, x);
    }

    @Override
    public byte getByteVolatile(Object o, long offset) {
        return UsfHolder.usf.getByteVolatile(o, offset);
    }

    @Override
    public void putByteVolatile(Object o, long offset, byte x) {
        UsfHolder.usf.putByteVolatile(o, offset, x);
    }

    @Override
    public short getShortVolatile(Object o, long offset) {
        return UsfHolder.usf.getShortVolatile(o, offset);
    }

    @Override
    public void putShortVolatile(Object o, long offset, short x) {
        UsfHolder.usf.putShortVolatile(o, offset, x);
    }

    @Override
    public char getCharVolatile(Object o, long offset) {
        return UsfHolder.usf.getCharVolatile(o, offset);
    }

    @Override
    public void putCharVolatile(Object o, long offset, char x) {
        UsfHolder.usf.putCharVolatile(o, offset, x);
    }

    @Override
    public long getLongVolatile(Object o, long offset) {
        return UsfHolder.usf.getLongVolatile(o, offset);
    }

    @Override
    public void putLongVolatile(Object o, long offset, long x) {
        UsfHolder.usf.putLongVolatile(o, offset, x);
    }

    @Override
    public float getFloatVolatile(Object o, long offset) {
        return UsfHolder.usf.getFloatVolatile(o, offset);
    }

    @Override
    public void putFloatVolatile(Object o, long offset, float x) {
        UsfHolder.usf.putFloatVolatile(o, offset, x);
    }

    @Override
    public double getDoubleVolatile(Object o, long offset) {
        return UsfHolder.usf.getDoubleVolatile(o, offset);
    }

    @Override
    public void putDoubleVolatile(Object o, long offset, double x) {
        UsfHolder.usf.putDoubleVolatile(o, offset, x);
    }

    @Override
    public Object getReferenceAcquire(Object o, long offset) {
        return UsfHolder.usf.getReferenceAcquire(o, offset);
    }

    @Override
    public boolean getBooleanAcquire(Object o, long offset) {
        return UsfHolder.usf.getBooleanAcquire(o, offset);
    }

    @Override
    public byte getByteAcquire(Object o, long offset) {
        return UsfHolder.usf.getByteAcquire(o, offset);
    }

    @Override
    public short getShortAcquire(Object o, long offset) {
        return UsfHolder.usf.getShortAcquire(o, offset);
    }

    @Override
    public char getCharAcquire(Object o, long offset) {
        return UsfHolder.usf.getCharAcquire(o, offset);
    }

    @Override
    public int getIntAcquire(Object o, long offset) {
        return UsfHolder.usf.getIntAcquire(o, offset);
    }

    @Override
    public float getFloatAcquire(Object o, long offset) {
        return UsfHolder.usf.getFloatAcquire(o, offset);
    }

    @Override
    public long getLongAcquire(Object o, long offset) {
        return UsfHolder.usf.getLongAcquire(o, offset);
    }

    @Override
    public double getDoubleAcquire(Object o, long offset) {
        return UsfHolder.usf.getDoubleAcquire(o, offset);
    }

    @Override
    public void putReferenceRelease(Object o, long offset, Object x) {
        UsfHolder.usf.putReferenceRelease(o, offset, x);
    }

    @Override
    public void putBooleanRelease(Object o, long offset, boolean x) {
        UsfHolder.usf.putBooleanRelease(o, offset, x);
    }

    @Override
    public void putByteRelease(Object o, long offset, byte x) {
        UsfHolder.usf.putByteRelease(o, offset, x);
    }

    @Override
    public void putShortRelease(Object o, long offset, short x) {
        UsfHolder.usf.putShortRelease(o, offset, x);
    }

    @Override
    public void putCharRelease(Object o, long offset, char x) {
        UsfHolder.usf.putCharRelease(o, offset, x);
    }

    @Override
    public void putIntRelease(Object o, long offset, int x) {
        UsfHolder.usf.putIntRelease(o, offset, x);
    }

    @Override
    public void putFloatRelease(Object o, long offset, float x) {
        UsfHolder.usf.putFloatRelease(o, offset, x);
    }

    @Override
    public void putLongRelease(Object o, long offset, long x) {
        UsfHolder.usf.putLongRelease(o, offset, x);
    }

    @Override
    public void putDoubleRelease(Object o, long offset, double x) {
        UsfHolder.usf.putDoubleRelease(o, offset, x);
    }

    @Override
    public Object getReferenceOpaque(Object o, long offset) {
        return UsfHolder.usf.getReferenceOpaque(o, offset);
    }

    @Override
    public boolean getBooleanOpaque(Object o, long offset) {
        return UsfHolder.usf.getBooleanOpaque(o, offset);
    }

    @Override
    public byte getByteOpaque(Object o, long offset) {
        return UsfHolder.usf.getByteOpaque(o, offset);
    }

    @Override
    public short getShortOpaque(Object o, long offset) {
        return UsfHolder.usf.getShortOpaque(o, offset);
    }

    @Override
    public char getCharOpaque(Object o, long offset) {
        return UsfHolder.usf.getCharOpaque(o, offset);
    }

    @Override
    public int getIntOpaque(Object o, long offset) {
        return UsfHolder.usf.getIntOpaque(o, offset);
    }

    @Override
    public float getFloatOpaque(Object o, long offset) {
        return UsfHolder.usf.getFloatOpaque(o, offset);
    }

    @Override
    public long getLongOpaque(Object o, long offset) {
        return UsfHolder.usf.getLongOpaque(o, offset);
    }

    @Override
    public double getDoubleOpaque(Object o, long offset) {
        return UsfHolder.usf.getDoubleOpaque(o, offset);
    }

    @Override
    public void putReferenceOpaque(Object o, long offset, Object x) {
        UsfHolder.usf.putReferenceOpaque(o, offset, x);
    }

    @Override
    public void putBooleanOpaque(Object o, long offset, boolean x) {
        UsfHolder.usf.putBooleanOpaque(o, offset, x);
    }

    @Override
    public void putByteOpaque(Object o, long offset, byte x) {
        UsfHolder.usf.putByteOpaque(o, offset, x);
    }

    @Override
    public void putShortOpaque(Object o, long offset, short x) {
        UsfHolder.usf.putShortOpaque(o, offset, x);
    }

    @Override
    public void putCharOpaque(Object o, long offset, char x) {
        UsfHolder.usf.putCharOpaque(o, offset, x);
    }

    @Override
    public void putIntOpaque(Object o, long offset, int x) {
        UsfHolder.usf.putIntOpaque(o, offset, x);
    }

    @Override
    public void putFloatOpaque(Object o, long offset, float x) {
        UsfHolder.usf.putFloatOpaque(o, offset, x);
    }

    @Override
    public void putLongOpaque(Object o, long offset, long x) {
        UsfHolder.usf.putLongOpaque(o, offset, x);
    }

    @Override
    public void putDoubleOpaque(Object o, long offset, double x) {
        UsfHolder.usf.putDoubleOpaque(o, offset, x);
    }

    @Override
    public void unpark(Object thread) {
        UsfHolder.usf.unpark(thread);
    }

    @Override
    public void park(boolean isAbsolute, long time) {
        UsfHolder.usf.park(isAbsolute, time);
    }

    @Override
    public int getLoadAverage(double[] loadavg, int nelems) {
        return UsfHolder.usf.getLoadAverage(loadavg, nelems);
    }

    @Override
    public int getAndAddInt(Object o, long offset, int delta) {
        return UsfHolder.usf.getAndAddInt(o, offset, delta);
    }

    @Override
    public int getAndAddIntRelease(Object o, long offset, int delta) {
        return UsfHolder.usf.getAndAddIntRelease(o, offset, delta);
    }

    @Override
    public int getAndAddIntAcquire(Object o, long offset, int delta) {
        return UsfHolder.usf.getAndAddIntAcquire(o, offset, delta);
    }

    @Override
    public long getAndAddLong(Object o, long offset, long delta) {
        return UsfHolder.usf.getAndAddLong(o, offset, delta);
    }

    @Override
    public long getAndAddLongRelease(Object o, long offset, long delta) {
        return UsfHolder.usf.getAndAddLongRelease(o, offset, delta);
    }

    @Override
    public long getAndAddLongAcquire(Object o, long offset, long delta) {
        return UsfHolder.usf.getAndAddLongAcquire(o, offset, delta);
    }

    @Override
    public byte getAndAddByte(Object o, long offset, byte delta) {
        return UsfHolder.usf.getAndAddByte(o, offset, delta);
    }

    @Override
    public byte getAndAddByteRelease(Object o, long offset, byte delta) {
        return UsfHolder.usf.getAndAddByteRelease(o, offset, delta);
    }

    @Override
    public byte getAndAddByteAcquire(Object o, long offset, byte delta) {
        return UsfHolder.usf.getAndAddByteAcquire(o, offset, delta);
    }

    @Override
    public short getAndAddShort(Object o, long offset, short delta) {
        return UsfHolder.usf.getAndAddShort(o, offset, delta);
    }

    @Override
    public short getAndAddShortRelease(Object o, long offset, short delta) {
        return UsfHolder.usf.getAndAddShortRelease(o, offset, delta);
    }

    @Override
    public short getAndAddShortAcquire(Object o, long offset, short delta) {
        return UsfHolder.usf.getAndAddShortAcquire(o, offset, delta);
    }

    @Override
    public char getAndAddChar(Object o, long offset, char delta) {
        return UsfHolder.usf.getAndAddChar(o, offset, delta);
    }

    @Override
    public char getAndAddCharRelease(Object o, long offset, char delta) {
        return UsfHolder.usf.getAndAddCharRelease(o, offset, delta);
    }

    @Override
    public char getAndAddCharAcquire(Object o, long offset, char delta) {
        return UsfHolder.usf.getAndAddCharAcquire(o, offset, delta);
    }

    @Override
    public float getAndAddFloat(Object o, long offset, float delta) {
        return UsfHolder.usf.getAndAddFloat(o, offset, delta);
    }

    @Override
    public float getAndAddFloatRelease(Object o, long offset, float delta) {
        return UsfHolder.usf.getAndAddFloatRelease(o, offset, delta);
    }

    @Override
    public float getAndAddFloatAcquire(Object o, long offset, float delta) {
        return UsfHolder.usf.getAndAddFloatAcquire(o, offset, delta);
    }

    @Override
    public double getAndAddDouble(Object o, long offset, double delta) {
        return UsfHolder.usf.getAndAddDouble(o, offset, delta);
    }

    @Override
    public double getAndAddDoubleRelease(Object o, long offset, double delta) {
        return UsfHolder.usf.getAndAddDoubleRelease(o, offset, delta);
    }

    @Override
    public double getAndAddDoubleAcquire(Object o, long offset, double delta) {
        return UsfHolder.usf.getAndAddDoubleAcquire(o, offset, delta);
    }

    @Override
    public int getAndSetInt(Object o, long offset, int newValue) {
        return UsfHolder.usf.getAndSetInt(o, offset, newValue);
    }

    @Override
    public int getAndSetIntRelease(Object o, long offset, int newValue) {
        return UsfHolder.usf.getAndSetIntRelease(o, offset, newValue);
    }

    @Override
    public int getAndSetIntAcquire(Object o, long offset, int newValue) {
        return UsfHolder.usf.getAndSetIntAcquire(o, offset, newValue);
    }

    @Override
    public long getAndSetLong(Object o, long offset, long newValue) {
        return UsfHolder.usf.getAndSetLong(o, offset, newValue);
    }

    @Override
    public long getAndSetLongRelease(Object o, long offset, long newValue) {
        return UsfHolder.usf.getAndSetLongRelease(o, offset, newValue);
    }

    @Override
    public long getAndSetLongAcquire(Object o, long offset, long newValue) {
        return UsfHolder.usf.getAndSetLongAcquire(o, offset, newValue);
    }

    @Override
    public Object getAndSetReference(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetReference(o, offset, newValue);
    }

    @Override
    public Object getAndSetReferenceRelease(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetReferenceRelease(o, offset, newValue);
    }

    @Override
    public Object getAndSetReferenceAcquire(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetReferenceAcquire(o, offset, newValue);
    }

    @Override
    public byte getAndSetByte(Object o, long offset, byte newValue) {
        return UsfHolder.usf.getAndSetByte(o, offset, newValue);
    }

    @Override
    public byte getAndSetByteRelease(Object o, long offset, byte newValue) {
        return UsfHolder.usf.getAndSetByteRelease(o, offset, newValue);
    }

    @Override
    public byte getAndSetByteAcquire(Object o, long offset, byte newValue) {
        return UsfHolder.usf.getAndSetByteAcquire(o, offset, newValue);
    }

    @Override
    public boolean getAndSetBoolean(Object o, long offset, boolean newValue) {
        return UsfHolder.usf.getAndSetBoolean(o, offset, newValue);
    }

    @Override
    public boolean getAndSetBooleanRelease(Object o, long offset, boolean newValue) {
        return UsfHolder.usf.getAndSetBooleanRelease(o, offset, newValue);
    }

    @Override
    public boolean getAndSetBooleanAcquire(Object o, long offset, boolean newValue) {
        return UsfHolder.usf.getAndSetBooleanAcquire(o, offset, newValue);
    }

    @Override
    public short getAndSetShort(Object o, long offset, short newValue) {
        return UsfHolder.usf.getAndSetShort(o, offset, newValue);
    }

    @Override
    public short getAndSetShortRelease(Object o, long offset, short newValue) {
        return UsfHolder.usf.getAndSetShortRelease(o, offset, newValue);
    }

    @Override
    public short getAndSetShortAcquire(Object o, long offset, short newValue) {
        return UsfHolder.usf.getAndSetShortAcquire(o, offset, newValue);
    }

    @Override
    public char getAndSetChar(Object o, long offset, char newValue) {
        return UsfHolder.usf.getAndSetChar(o, offset, newValue);
    }

    @Override
    public char getAndSetCharRelease(Object o, long offset, char newValue) {
        return UsfHolder.usf.getAndSetCharRelease(o, offset, newValue);
    }

    @Override
    public char getAndSetCharAcquire(Object o, long offset, char newValue) {
        return UsfHolder.usf.getAndSetCharAcquire(o, offset, newValue);
    }

    @Override
    public float getAndSetFloat(Object o, long offset, float newValue) {
        return UsfHolder.usf.getAndSetFloat(o, offset, newValue);
    }

    @Override
    public float getAndSetFloatRelease(Object o, long offset, float newValue) {
        return UsfHolder.usf.getAndSetFloatRelease(o, offset, newValue);
    }

    @Override
    public float getAndSetFloatAcquire(Object o, long offset, float newValue) {
        return UsfHolder.usf.getAndSetFloatAcquire(o, offset, newValue);
    }

    @Override
    public double getAndSetDouble(Object o, long offset, double newValue) {
        return UsfHolder.usf.getAndSetDouble(o, offset, newValue);
    }

    @Override
    public double getAndSetDoubleRelease(Object o, long offset, double newValue) {
        return UsfHolder.usf.getAndSetDoubleRelease(o, offset, newValue);
    }

    @Override
    public double getAndSetDoubleAcquire(Object o, long offset, double newValue) {
        return UsfHolder.usf.getAndSetDoubleAcquire(o, offset, newValue);
    }

    @Override
    public boolean getAndBitwiseOrBoolean(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseOrBoolean(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseOrBooleanRelease(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseOrBooleanRelease(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseOrBooleanAcquire(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseOrBooleanAcquire(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseAndBoolean(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseAndBoolean(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseAndBooleanRelease(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseAndBooleanRelease(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseAndBooleanAcquire(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseAndBooleanAcquire(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseXorBoolean(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseXorBoolean(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseXorBooleanRelease(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseXorBooleanRelease(o, offset, mask);
    }

    @Override
    public boolean getAndBitwiseXorBooleanAcquire(Object o, long offset, boolean mask) {
        return UsfHolder.usf.getAndBitwiseXorBooleanAcquire(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseOrByte(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseOrByte(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseOrByteRelease(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseOrByteRelease(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseOrByteAcquire(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseOrByteAcquire(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseAndByte(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseAndByte(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseAndByteRelease(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseAndByteRelease(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseAndByteAcquire(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseAndByteAcquire(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseXorByte(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseXorByte(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseXorByteRelease(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseXorByteRelease(o, offset, mask);
    }

    @Override
    public byte getAndBitwiseXorByteAcquire(Object o, long offset, byte mask) {
        return UsfHolder.usf.getAndBitwiseXorByteAcquire(o, offset, mask);
    }

    @Override
    public char getAndBitwiseOrChar(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseOrChar(o, offset, mask);
    }

    @Override
    public char getAndBitwiseOrCharRelease(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseOrCharRelease(o, offset, mask);
    }

    @Override
    public char getAndBitwiseOrCharAcquire(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseOrCharAcquire(o, offset, mask);
    }

    @Override
    public char getAndBitwiseAndChar(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseAndChar(o, offset, mask);
    }

    @Override
    public char getAndBitwiseAndCharRelease(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseAndCharRelease(o, offset, mask);
    }

    @Override
    public char getAndBitwiseAndCharAcquire(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseAndCharAcquire(o, offset, mask);
    }

    @Override
    public char getAndBitwiseXorChar(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseXorChar(o, offset, mask);
    }

    @Override
    public char getAndBitwiseXorCharRelease(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseXorCharRelease(o, offset, mask);
    }

    @Override
    public char getAndBitwiseXorCharAcquire(Object o, long offset, char mask) {
        return UsfHolder.usf.getAndBitwiseXorCharAcquire(o, offset, mask);
    }

    @Override
    public short getAndBitwiseOrShort(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseOrShort(o, offset, mask);
    }

    @Override
    public short getAndBitwiseOrShortRelease(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseOrShortRelease(o, offset, mask);
    }

    @Override
    public short getAndBitwiseOrShortAcquire(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseOrShortAcquire(o, offset, mask);
    }

    @Override
    public short getAndBitwiseAndShort(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseAndShort(o, offset, mask);
    }

    @Override
    public short getAndBitwiseAndShortRelease(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseAndShortRelease(o, offset, mask);
    }

    @Override
    public short getAndBitwiseAndShortAcquire(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseAndShortAcquire(o, offset, mask);
    }

    @Override
    public short getAndBitwiseXorShort(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseXorShort(o, offset, mask);
    }

    @Override
    public short getAndBitwiseXorShortRelease(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseXorShortRelease(o, offset, mask);
    }

    @Override
    public short getAndBitwiseXorShortAcquire(Object o, long offset, short mask) {
        return UsfHolder.usf.getAndBitwiseXorShortAcquire(o, offset, mask);
    }

    @Override
    public int getAndBitwiseOrInt(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseOrInt(o, offset, mask);
    }

    @Override
    public int getAndBitwiseOrIntRelease(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseOrIntRelease(o, offset, mask);
    }

    @Override
    public int getAndBitwiseOrIntAcquire(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseOrIntAcquire(o, offset, mask);
    }

    @Override
    public int getAndBitwiseAndInt(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseAndInt(o, offset, mask);
    }

    @Override
    public int getAndBitwiseAndIntRelease(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseAndIntRelease(o, offset, mask);
    }

    @Override
    public int getAndBitwiseAndIntAcquire(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseAndIntAcquire(o, offset, mask);
    }

    @Override
    public int getAndBitwiseXorInt(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseXorInt(o, offset, mask);
    }

    @Override
    public int getAndBitwiseXorIntRelease(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseXorIntRelease(o, offset, mask);
    }

    @Override
    public int getAndBitwiseXorIntAcquire(Object o, long offset, int mask) {
        return UsfHolder.usf.getAndBitwiseXorIntAcquire(o, offset, mask);
    }

    @Override
    public long getAndBitwiseOrLong(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseOrLong(o, offset, mask);
    }

    @Override
    public long getAndBitwiseOrLongRelease(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseOrLongRelease(o, offset, mask);
    }

    @Override
    public long getAndBitwiseOrLongAcquire(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseOrLongAcquire(o, offset, mask);
    }

    @Override
    public long getAndBitwiseAndLong(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseAndLong(o, offset, mask);
    }

    @Override
    public long getAndBitwiseAndLongRelease(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseAndLongRelease(o, offset, mask);
    }

    @Override
    public long getAndBitwiseAndLongAcquire(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseAndLongAcquire(o, offset, mask);
    }

    @Override
    public long getAndBitwiseXorLong(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseXorLong(o, offset, mask);
    }

    @Override
    public long getAndBitwiseXorLongRelease(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseXorLongRelease(o, offset, mask);
    }

    @Override
    public long getAndBitwiseXorLongAcquire(Object o, long offset, long mask) {
        return UsfHolder.usf.getAndBitwiseXorLongAcquire(o, offset, mask);
    }

    @Override
    public void loadFence() {
        UsfHolder.usf.loadFence();
    }

    @Override
    public void storeFence() {
        UsfHolder.usf.storeFence();
    }

    @Override
    public void fullFence() {
        UsfHolder.usf.fullFence();
    }

    @Override
    public void loadLoadFence() {
        UsfHolder.usf.loadLoadFence();
    }

    @Override
    public void storeStoreFence() {
        UsfHolder.usf.storeStoreFence();
    }

    @Override
    public boolean isBigEndian() {
        return UsfHolder.usf.isBigEndian();
    }

    @Override
    public boolean unalignedAccess() {
        return UsfHolder.usf.unalignedAccess();
    }

    @Override
    public long getLongUnaligned(Object o, long offset) {
        return UsfHolder.usf.getLongUnaligned(o, offset);
    }

    @Override
    public long getLongUnaligned(Object o, long offset, boolean bigEndian) {
        return UsfHolder.usf.getLongUnaligned(o, offset, bigEndian);
    }

    @Override
    public int getIntUnaligned(Object o, long offset) {
        return UsfHolder.usf.getIntUnaligned(o, offset);
    }

    @Override
    public int getIntUnaligned(Object o, long offset, boolean bigEndian) {
        return UsfHolder.usf.getIntUnaligned(o, offset, bigEndian);
    }

    @Override
    public short getShortUnaligned(Object o, long offset) {
        return UsfHolder.usf.getShortUnaligned(o, offset);
    }

    @Override
    public short getShortUnaligned(Object o, long offset, boolean bigEndian) {
        return UsfHolder.usf.getShortUnaligned(o, offset, bigEndian);
    }

    @Override
    public char getCharUnaligned(Object o, long offset) {
        return UsfHolder.usf.getCharUnaligned(o, offset);
    }

    @Override
    public char getCharUnaligned(Object o, long offset, boolean bigEndian) {
        return UsfHolder.usf.getCharUnaligned(o, offset, bigEndian);
    }

    @Override
    public void putLongUnaligned(Object o, long offset, long x) {
        UsfHolder.usf.putLongUnaligned(o, offset, x);
    }

    @Override
    public void putLongUnaligned(Object o, long offset, long x, boolean bigEndian) {
        UsfHolder.usf.putLongUnaligned(o, offset, x, bigEndian);
    }

    @Override
    public void putIntUnaligned(Object o, long offset, int x) {
        UsfHolder.usf.putIntUnaligned(o, offset, x);
    }

    @Override
    public void putIntUnaligned(Object o, long offset, int x, boolean bigEndian) {
        UsfHolder.usf.putIntUnaligned(o, offset, x, bigEndian);
    }

    @Override
    public void putShortUnaligned(Object o, long offset, short x) {
        UsfHolder.usf.putShortUnaligned(o, offset, x);
    }

    @Override
    public void putShortUnaligned(Object o, long offset, short x, boolean bigEndian) {
        UsfHolder.usf.putShortUnaligned(o, offset, x, bigEndian);
    }

    @Override
    public void putCharUnaligned(Object o, long offset, char x) {
        UsfHolder.usf.putCharUnaligned(o, offset, x);
    }

    @Override
    public void putCharUnaligned(Object o, long offset, char x, boolean bigEndian) {
        UsfHolder.usf.putCharUnaligned(o, offset, x, bigEndian);
    }

    @Override
    public void invokeCleaner(ByteBuffer directBuffer) {
        UsfHolder.usf.invokeCleaner(directBuffer);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getObject(Object o, long offset) {
        return UsfHolder.usf.getObject(o, offset);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getObjectVolatile(Object o, long offset) {
        return UsfHolder.usf.getObjectVolatile(o, offset);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getObjectAcquire(Object o, long offset) {
        return UsfHolder.usf.getObjectAcquire(o, offset);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getObjectOpaque(Object o, long offset) {
        return UsfHolder.usf.getObjectOpaque(o, offset);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public void putObject(Object o, long offset, Object x) {
        UsfHolder.usf.putObject(o, offset, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public void putObjectVolatile(Object o, long offset, Object x) {
        UsfHolder.usf.putObjectVolatile(o, offset, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public void putObjectOpaque(Object o, long offset, Object x) {
        UsfHolder.usf.putObjectOpaque(o, offset, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public void putObjectRelease(Object o, long offset, Object x) {
        UsfHolder.usf.putObjectRelease(o, offset, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getAndSetObject(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetObject(o, offset, newValue);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getAndSetObjectAcquire(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetObjectAcquire(o, offset, newValue);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object getAndSetObjectRelease(Object o, long offset, Object newValue) {
        return UsfHolder.usf.getAndSetObjectRelease(o, offset, newValue);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public boolean compareAndSetObject(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndSetObject(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object compareAndExchangeObject(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeObject(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object compareAndExchangeObjectAcquire(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeObjectAcquire(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public Object compareAndExchangeObjectRelease(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.compareAndExchangeObjectRelease(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public boolean weakCompareAndSetObject(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetObject(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public boolean weakCompareAndSetObjectAcquire(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetObjectAcquire(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public boolean weakCompareAndSetObjectPlain(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetObjectPlain(o, offset, expected, x);
    }

    @Override
    @Deprecated(since="12", forRemoval=true)
    public boolean weakCompareAndSetObjectRelease(Object o, long offset, Object expected, Object x) {
        return UsfHolder.usf.weakCompareAndSetObjectRelease(o, offset, expected, x);
    }

    static class UsfHolder {
        static final jdk.internal.misc.Unsafe usf = jdk.internal.misc.Unsafe.getUnsafe();

        UsfHolder() {
        }
    }
}

