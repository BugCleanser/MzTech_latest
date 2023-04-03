/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package io.github.karlatemp.unsafeaccessor;

import io.github.karlatemp.unsafeaccessor.UsfAccessor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import org.jetbrains.annotations.Contract;

public abstract class Unsafe {
    private static Unsafe theUnsafe;
    public static final int INVALID_FIELD_OFFSET = -1;
    public static final int ARRAY_BOOLEAN_BASE_OFFSET;
    public static final int ARRAY_BYTE_BASE_OFFSET;
    public static final int ARRAY_SHORT_BASE_OFFSET;
    public static final int ARRAY_CHAR_BASE_OFFSET;
    public static final int ARRAY_INT_BASE_OFFSET;
    public static final int ARRAY_LONG_BASE_OFFSET;
    public static final int ARRAY_FLOAT_BASE_OFFSET;
    public static final int ARRAY_DOUBLE_BASE_OFFSET;
    public static final int ARRAY_OBJECT_BASE_OFFSET;
    public static final int ARRAY_BOOLEAN_INDEX_SCALE;
    public static final int ARRAY_BYTE_INDEX_SCALE;
    public static final int ARRAY_SHORT_INDEX_SCALE;
    public static final int ARRAY_CHAR_INDEX_SCALE;
    public static final int ARRAY_INT_INDEX_SCALE;
    public static final int ARRAY_LONG_INDEX_SCALE;
    public static final int ARRAY_FLOAT_INDEX_SCALE;
    public static final int ARRAY_DOUBLE_INDEX_SCALE;
    public static final int ARRAY_OBJECT_INDEX_SCALE;
    public static final int ADDRESS_SIZE;

    @Contract(pure=true)
    public boolean isJava9() {
        return false;
    }

    @Contract(pure=false)
    public static Unsafe getUnsafe() {
        if (theUnsafe == null) {
            theUnsafe = (Unsafe)UsfAccessor.allocateUnsafe();
            return theUnsafe;
        }
        return theUnsafe;
    }

    public abstract int getInt(Object var1, long var2);

    public abstract void putInt(Object var1, long var2, int var4);

    public abstract Object getReference(Object var1, long var2);

    public abstract void putReference(Object var1, long var2, Object var4);

    public abstract boolean getBoolean(Object var1, long var2);

    public abstract void putBoolean(Object var1, long var2, boolean var4);

    public abstract byte getByte(Object var1, long var2);

    public abstract void putByte(Object var1, long var2, byte var4);

    public abstract short getShort(Object var1, long var2);

    public abstract void putShort(Object var1, long var2, short var4);

    public abstract char getChar(Object var1, long var2);

    public abstract void putChar(Object var1, long var2, char var4);

    public abstract long getLong(Object var1, long var2);

    public abstract void putLong(Object var1, long var2, long var4);

    public abstract float getFloat(Object var1, long var2);

    public abstract void putFloat(Object var1, long var2, float var4);

    public abstract double getDouble(Object var1, long var2);

    public abstract void putDouble(Object var1, long var2, double var4);

    public long getAddress(Object o, long offset) {
        if (ADDRESS_SIZE == 4) {
            return Integer.toUnsignedLong(this.getInt(o, offset));
        }
        return this.getLong(o, offset);
    }

    public void putAddress(Object o, long offset, long x) {
        if (ADDRESS_SIZE == 4) {
            this.putInt(o, offset, (int)x);
        } else {
            this.putLong(o, offset, x);
        }
    }

    public abstract Object getUncompressedObject(long var1);

    public abstract byte getByte(long var1);

    public abstract void putByte(long var1, byte var3);

    public abstract short getShort(long var1);

    public abstract void putShort(long var1, short var3);

    public abstract char getChar(long var1);

    public abstract void putChar(long var1, char var3);

    public abstract int getInt(long var1);

    public abstract void putInt(long var1, int var3);

    public abstract long getLong(long var1);

    public abstract void putLong(long var1, long var3);

    public abstract float getFloat(long var1);

    public abstract void putFloat(long var1, float var3);

    public abstract double getDouble(long var1);

    public abstract void putDouble(long var1, double var3);

    public abstract long getAddress(long var1);

    public abstract void putAddress(long var1, long var3);

    public abstract long allocateMemory(long var1);

    public abstract long reallocateMemory(long var1, long var3);

    public abstract void setMemory(Object var1, long var2, long var4, byte var6);

    public abstract void setMemory(long var1, long var3, byte var5);

    public abstract void copyMemory(Object var1, long var2, Object var4, long var5, long var7);

    public abstract void copyMemory(long var1, long var3, long var5);

    public abstract void copySwapMemory(Object var1, long var2, Object var4, long var5, long var7, long var9);

    public abstract void copySwapMemory(long var1, long var3, long var5, long var7);

    public abstract void freeMemory(long var1);

    public abstract long objectFieldOffset(Field var1);

    public abstract long objectFieldOffset(Class<?> var1, String var2);

    public abstract long staticFieldOffset(Field var1);

    public abstract Object staticFieldBase(Field var1);

    public abstract boolean shouldBeInitialized(Class<?> var1);

    public abstract void ensureClassInitialized(Class<?> var1);

    public abstract int arrayBaseOffset(Class<?> var1);

    public abstract int arrayIndexScale(Class<?> var1);

    public abstract int addressSize();

    public abstract int pageSize();

    public abstract Class<?> defineClass(String var1, byte[] var2, int var3, int var4, ClassLoader var5, ProtectionDomain var6);

    public abstract Class<?> defineClass0(String var1, byte[] var2, int var3, int var4, ClassLoader var5, ProtectionDomain var6);

    public abstract Class<?> defineAnonymousClass(Class<?> var1, byte[] var2, Object[] var3);

    public abstract Object allocateInstance(Class<?> var1) throws InstantiationException;

    public abstract Object allocateUninitializedArray(Class<?> var1, int var2);

    public abstract void throwException(Throwable var1);

    public abstract boolean compareAndSetReference(Object var1, long var2, Object var4, Object var5);

    public abstract Object compareAndExchangeReference(Object var1, long var2, Object var4, Object var5);

    public abstract Object compareAndExchangeReferenceAcquire(Object var1, long var2, Object var4, Object var5);

    public abstract Object compareAndExchangeReferenceRelease(Object var1, long var2, Object var4, Object var5);

    public abstract boolean weakCompareAndSetReferencePlain(Object var1, long var2, Object var4, Object var5);

    public abstract boolean weakCompareAndSetReferenceAcquire(Object var1, long var2, Object var4, Object var5);

    public abstract boolean weakCompareAndSetReferenceRelease(Object var1, long var2, Object var4, Object var5);

    public abstract boolean weakCompareAndSetReference(Object var1, long var2, Object var4, Object var5);

    public abstract boolean compareAndSetInt(Object var1, long var2, int var4, int var5);

    public abstract int compareAndExchangeInt(Object var1, long var2, int var4, int var5);

    public abstract int compareAndExchangeIntAcquire(Object var1, long var2, int var4, int var5);

    public abstract int compareAndExchangeIntRelease(Object var1, long var2, int var4, int var5);

    public abstract boolean weakCompareAndSetIntPlain(Object var1, long var2, int var4, int var5);

    public abstract boolean weakCompareAndSetIntAcquire(Object var1, long var2, int var4, int var5);

    public abstract boolean weakCompareAndSetIntRelease(Object var1, long var2, int var4, int var5);

    public abstract boolean weakCompareAndSetInt(Object var1, long var2, int var4, int var5);

    public abstract byte compareAndExchangeByte(Object var1, long var2, byte var4, byte var5);

    public abstract boolean compareAndSetByte(Object var1, long var2, byte var4, byte var5);

    public abstract boolean weakCompareAndSetByte(Object var1, long var2, byte var4, byte var5);

    public abstract boolean weakCompareAndSetByteAcquire(Object var1, long var2, byte var4, byte var5);

    public abstract boolean weakCompareAndSetByteRelease(Object var1, long var2, byte var4, byte var5);

    public abstract boolean weakCompareAndSetBytePlain(Object var1, long var2, byte var4, byte var5);

    public abstract byte compareAndExchangeByteAcquire(Object var1, long var2, byte var4, byte var5);

    public abstract byte compareAndExchangeByteRelease(Object var1, long var2, byte var4, byte var5);

    public abstract short compareAndExchangeShort(Object var1, long var2, short var4, short var5);

    public abstract boolean compareAndSetShort(Object var1, long var2, short var4, short var5);

    public abstract boolean weakCompareAndSetShort(Object var1, long var2, short var4, short var5);

    public abstract boolean weakCompareAndSetShortAcquire(Object var1, long var2, short var4, short var5);

    public abstract boolean weakCompareAndSetShortRelease(Object var1, long var2, short var4, short var5);

    public abstract boolean weakCompareAndSetShortPlain(Object var1, long var2, short var4, short var5);

    public abstract short compareAndExchangeShortAcquire(Object var1, long var2, short var4, short var5);

    public abstract short compareAndExchangeShortRelease(Object var1, long var2, short var4, short var5);

    public abstract boolean compareAndSetChar(Object var1, long var2, char var4, char var5);

    public abstract char compareAndExchangeChar(Object var1, long var2, char var4, char var5);

    public abstract char compareAndExchangeCharAcquire(Object var1, long var2, char var4, char var5);

    public abstract char compareAndExchangeCharRelease(Object var1, long var2, char var4, char var5);

    public abstract boolean weakCompareAndSetChar(Object var1, long var2, char var4, char var5);

    public abstract boolean weakCompareAndSetCharAcquire(Object var1, long var2, char var4, char var5);

    public abstract boolean weakCompareAndSetCharRelease(Object var1, long var2, char var4, char var5);

    public abstract boolean weakCompareAndSetCharPlain(Object var1, long var2, char var4, char var5);

    public abstract boolean compareAndSetBoolean(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean compareAndExchangeBoolean(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean compareAndExchangeBooleanAcquire(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean compareAndExchangeBooleanRelease(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean weakCompareAndSetBoolean(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean weakCompareAndSetBooleanAcquire(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean weakCompareAndSetBooleanRelease(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean weakCompareAndSetBooleanPlain(Object var1, long var2, boolean var4, boolean var5);

    public abstract boolean compareAndSetFloat(Object var1, long var2, float var4, float var5);

    public abstract float compareAndExchangeFloat(Object var1, long var2, float var4, float var5);

    public abstract float compareAndExchangeFloatAcquire(Object var1, long var2, float var4, float var5);

    public abstract float compareAndExchangeFloatRelease(Object var1, long var2, float var4, float var5);

    public abstract boolean weakCompareAndSetFloatPlain(Object var1, long var2, float var4, float var5);

    public abstract boolean weakCompareAndSetFloatAcquire(Object var1, long var2, float var4, float var5);

    public abstract boolean weakCompareAndSetFloatRelease(Object var1, long var2, float var4, float var5);

    public abstract boolean weakCompareAndSetFloat(Object var1, long var2, float var4, float var5);

    public abstract boolean compareAndSetDouble(Object var1, long var2, double var4, double var6);

    public abstract double compareAndExchangeDouble(Object var1, long var2, double var4, double var6);

    public abstract double compareAndExchangeDoubleAcquire(Object var1, long var2, double var4, double var6);

    public abstract double compareAndExchangeDoubleRelease(Object var1, long var2, double var4, double var6);

    public abstract boolean weakCompareAndSetDoublePlain(Object var1, long var2, double var4, double var6);

    public abstract boolean weakCompareAndSetDoubleAcquire(Object var1, long var2, double var4, double var6);

    public abstract boolean weakCompareAndSetDoubleRelease(Object var1, long var2, double var4, double var6);

    public abstract boolean weakCompareAndSetDouble(Object var1, long var2, double var4, double var6);

    public abstract boolean compareAndSetLong(Object var1, long var2, long var4, long var6);

    public abstract long compareAndExchangeLong(Object var1, long var2, long var4, long var6);

    public abstract long compareAndExchangeLongAcquire(Object var1, long var2, long var4, long var6);

    public abstract long compareAndExchangeLongRelease(Object var1, long var2, long var4, long var6);

    public abstract boolean weakCompareAndSetLongPlain(Object var1, long var2, long var4, long var6);

    public abstract boolean weakCompareAndSetLongAcquire(Object var1, long var2, long var4, long var6);

    public abstract boolean weakCompareAndSetLongRelease(Object var1, long var2, long var4, long var6);

    public abstract boolean weakCompareAndSetLong(Object var1, long var2, long var4, long var6);

    public abstract Object getReferenceVolatile(Object var1, long var2);

    public abstract void putReferenceVolatile(Object var1, long var2, Object var4);

    public abstract int getIntVolatile(Object var1, long var2);

    public abstract void putIntVolatile(Object var1, long var2, int var4);

    public abstract boolean getBooleanVolatile(Object var1, long var2);

    public abstract void putBooleanVolatile(Object var1, long var2, boolean var4);

    public abstract byte getByteVolatile(Object var1, long var2);

    public abstract void putByteVolatile(Object var1, long var2, byte var4);

    public abstract short getShortVolatile(Object var1, long var2);

    public abstract void putShortVolatile(Object var1, long var2, short var4);

    public abstract char getCharVolatile(Object var1, long var2);

    public abstract void putCharVolatile(Object var1, long var2, char var4);

    public abstract long getLongVolatile(Object var1, long var2);

    public abstract void putLongVolatile(Object var1, long var2, long var4);

    public abstract float getFloatVolatile(Object var1, long var2);

    public abstract void putFloatVolatile(Object var1, long var2, float var4);

    public abstract double getDoubleVolatile(Object var1, long var2);

    public abstract void putDoubleVolatile(Object var1, long var2, double var4);

    public abstract Object getReferenceAcquire(Object var1, long var2);

    public abstract boolean getBooleanAcquire(Object var1, long var2);

    public abstract byte getByteAcquire(Object var1, long var2);

    public abstract short getShortAcquire(Object var1, long var2);

    public abstract char getCharAcquire(Object var1, long var2);

    public abstract int getIntAcquire(Object var1, long var2);

    public abstract float getFloatAcquire(Object var1, long var2);

    public abstract long getLongAcquire(Object var1, long var2);

    public abstract double getDoubleAcquire(Object var1, long var2);

    public abstract void putReferenceRelease(Object var1, long var2, Object var4);

    public abstract void putBooleanRelease(Object var1, long var2, boolean var4);

    public abstract void putByteRelease(Object var1, long var2, byte var4);

    public abstract void putShortRelease(Object var1, long var2, short var4);

    public abstract void putCharRelease(Object var1, long var2, char var4);

    public abstract void putIntRelease(Object var1, long var2, int var4);

    public abstract void putFloatRelease(Object var1, long var2, float var4);

    public abstract void putLongRelease(Object var1, long var2, long var4);

    public abstract void putDoubleRelease(Object var1, long var2, double var4);

    public abstract Object getReferenceOpaque(Object var1, long var2);

    public abstract boolean getBooleanOpaque(Object var1, long var2);

    public abstract byte getByteOpaque(Object var1, long var2);

    public abstract short getShortOpaque(Object var1, long var2);

    public abstract char getCharOpaque(Object var1, long var2);

    public abstract int getIntOpaque(Object var1, long var2);

    public abstract float getFloatOpaque(Object var1, long var2);

    public abstract long getLongOpaque(Object var1, long var2);

    public abstract double getDoubleOpaque(Object var1, long var2);

    public abstract void putReferenceOpaque(Object var1, long var2, Object var4);

    public abstract void putBooleanOpaque(Object var1, long var2, boolean var4);

    public abstract void putByteOpaque(Object var1, long var2, byte var4);

    public abstract void putShortOpaque(Object var1, long var2, short var4);

    public abstract void putCharOpaque(Object var1, long var2, char var4);

    public abstract void putIntOpaque(Object var1, long var2, int var4);

    public abstract void putFloatOpaque(Object var1, long var2, float var4);

    public abstract void putLongOpaque(Object var1, long var2, long var4);

    public abstract void putDoubleOpaque(Object var1, long var2, double var4);

    public abstract void unpark(Object var1);

    public abstract void park(boolean var1, long var2);

    public abstract int getLoadAverage(double[] var1, int var2);

    public abstract int getAndAddInt(Object var1, long var2, int var4);

    public abstract int getAndAddIntRelease(Object var1, long var2, int var4);

    public abstract int getAndAddIntAcquire(Object var1, long var2, int var4);

    public abstract long getAndAddLong(Object var1, long var2, long var4);

    public abstract long getAndAddLongRelease(Object var1, long var2, long var4);

    public abstract long getAndAddLongAcquire(Object var1, long var2, long var4);

    public abstract byte getAndAddByte(Object var1, long var2, byte var4);

    public abstract byte getAndAddByteRelease(Object var1, long var2, byte var4);

    public abstract byte getAndAddByteAcquire(Object var1, long var2, byte var4);

    public abstract short getAndAddShort(Object var1, long var2, short var4);

    public abstract short getAndAddShortRelease(Object var1, long var2, short var4);

    public abstract short getAndAddShortAcquire(Object var1, long var2, short var4);

    public abstract char getAndAddChar(Object var1, long var2, char var4);

    public abstract char getAndAddCharRelease(Object var1, long var2, char var4);

    public abstract char getAndAddCharAcquire(Object var1, long var2, char var4);

    public abstract float getAndAddFloat(Object var1, long var2, float var4);

    public abstract float getAndAddFloatRelease(Object var1, long var2, float var4);

    public abstract float getAndAddFloatAcquire(Object var1, long var2, float var4);

    public abstract double getAndAddDouble(Object var1, long var2, double var4);

    public abstract double getAndAddDoubleRelease(Object var1, long var2, double var4);

    public abstract double getAndAddDoubleAcquire(Object var1, long var2, double var4);

    public abstract int getAndSetInt(Object var1, long var2, int var4);

    public abstract int getAndSetIntRelease(Object var1, long var2, int var4);

    public abstract int getAndSetIntAcquire(Object var1, long var2, int var4);

    public abstract long getAndSetLong(Object var1, long var2, long var4);

    public abstract long getAndSetLongRelease(Object var1, long var2, long var4);

    public abstract long getAndSetLongAcquire(Object var1, long var2, long var4);

    public abstract Object getAndSetReference(Object var1, long var2, Object var4);

    public abstract Object getAndSetReferenceRelease(Object var1, long var2, Object var4);

    public abstract Object getAndSetReferenceAcquire(Object var1, long var2, Object var4);

    public abstract byte getAndSetByte(Object var1, long var2, byte var4);

    public abstract byte getAndSetByteRelease(Object var1, long var2, byte var4);

    public abstract byte getAndSetByteAcquire(Object var1, long var2, byte var4);

    public abstract boolean getAndSetBoolean(Object var1, long var2, boolean var4);

    public abstract boolean getAndSetBooleanRelease(Object var1, long var2, boolean var4);

    public abstract boolean getAndSetBooleanAcquire(Object var1, long var2, boolean var4);

    public abstract short getAndSetShort(Object var1, long var2, short var4);

    public abstract short getAndSetShortRelease(Object var1, long var2, short var4);

    public abstract short getAndSetShortAcquire(Object var1, long var2, short var4);

    public abstract char getAndSetChar(Object var1, long var2, char var4);

    public abstract char getAndSetCharRelease(Object var1, long var2, char var4);

    public abstract char getAndSetCharAcquire(Object var1, long var2, char var4);

    public abstract float getAndSetFloat(Object var1, long var2, float var4);

    public abstract float getAndSetFloatRelease(Object var1, long var2, float var4);

    public abstract float getAndSetFloatAcquire(Object var1, long var2, float var4);

    public abstract double getAndSetDouble(Object var1, long var2, double var4);

    public abstract double getAndSetDoubleRelease(Object var1, long var2, double var4);

    public abstract double getAndSetDoubleAcquire(Object var1, long var2, double var4);

    public abstract boolean getAndBitwiseOrBoolean(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseOrBooleanRelease(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseOrBooleanAcquire(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseAndBoolean(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseAndBooleanRelease(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseAndBooleanAcquire(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseXorBoolean(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseXorBooleanRelease(Object var1, long var2, boolean var4);

    public abstract boolean getAndBitwiseXorBooleanAcquire(Object var1, long var2, boolean var4);

    public abstract byte getAndBitwiseOrByte(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseOrByteRelease(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseOrByteAcquire(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseAndByte(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseAndByteRelease(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseAndByteAcquire(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseXorByte(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseXorByteRelease(Object var1, long var2, byte var4);

    public abstract byte getAndBitwiseXorByteAcquire(Object var1, long var2, byte var4);

    public abstract char getAndBitwiseOrChar(Object var1, long var2, char var4);

    public abstract char getAndBitwiseOrCharRelease(Object var1, long var2, char var4);

    public abstract char getAndBitwiseOrCharAcquire(Object var1, long var2, char var4);

    public abstract char getAndBitwiseAndChar(Object var1, long var2, char var4);

    public abstract char getAndBitwiseAndCharRelease(Object var1, long var2, char var4);

    public abstract char getAndBitwiseAndCharAcquire(Object var1, long var2, char var4);

    public abstract char getAndBitwiseXorChar(Object var1, long var2, char var4);

    public abstract char getAndBitwiseXorCharRelease(Object var1, long var2, char var4);

    public abstract char getAndBitwiseXorCharAcquire(Object var1, long var2, char var4);

    public abstract short getAndBitwiseOrShort(Object var1, long var2, short var4);

    public abstract short getAndBitwiseOrShortRelease(Object var1, long var2, short var4);

    public abstract short getAndBitwiseOrShortAcquire(Object var1, long var2, short var4);

    public abstract short getAndBitwiseAndShort(Object var1, long var2, short var4);

    public abstract short getAndBitwiseAndShortRelease(Object var1, long var2, short var4);

    public abstract short getAndBitwiseAndShortAcquire(Object var1, long var2, short var4);

    public abstract short getAndBitwiseXorShort(Object var1, long var2, short var4);

    public abstract short getAndBitwiseXorShortRelease(Object var1, long var2, short var4);

    public abstract short getAndBitwiseXorShortAcquire(Object var1, long var2, short var4);

    public abstract int getAndBitwiseOrInt(Object var1, long var2, int var4);

    public abstract int getAndBitwiseOrIntRelease(Object var1, long var2, int var4);

    public abstract int getAndBitwiseOrIntAcquire(Object var1, long var2, int var4);

    public abstract int getAndBitwiseAndInt(Object var1, long var2, int var4);

    public abstract int getAndBitwiseAndIntRelease(Object var1, long var2, int var4);

    public abstract int getAndBitwiseAndIntAcquire(Object var1, long var2, int var4);

    public abstract int getAndBitwiseXorInt(Object var1, long var2, int var4);

    public abstract int getAndBitwiseXorIntRelease(Object var1, long var2, int var4);

    public abstract int getAndBitwiseXorIntAcquire(Object var1, long var2, int var4);

    public abstract long getAndBitwiseOrLong(Object var1, long var2, long var4);

    public abstract long getAndBitwiseOrLongRelease(Object var1, long var2, long var4);

    public abstract long getAndBitwiseOrLongAcquire(Object var1, long var2, long var4);

    public abstract long getAndBitwiseAndLong(Object var1, long var2, long var4);

    public abstract long getAndBitwiseAndLongRelease(Object var1, long var2, long var4);

    public abstract long getAndBitwiseAndLongAcquire(Object var1, long var2, long var4);

    public abstract long getAndBitwiseXorLong(Object var1, long var2, long var4);

    public abstract long getAndBitwiseXorLongRelease(Object var1, long var2, long var4);

    public abstract long getAndBitwiseXorLongAcquire(Object var1, long var2, long var4);

    public abstract void loadFence();

    public abstract void storeFence();

    public abstract void fullFence();

    public abstract void loadLoadFence();

    public abstract void storeStoreFence();

    public abstract boolean isBigEndian();

    public abstract boolean unalignedAccess();

    public abstract long getLongUnaligned(Object var1, long var2);

    public abstract long getLongUnaligned(Object var1, long var2, boolean var4);

    public abstract int getIntUnaligned(Object var1, long var2);

    public abstract int getIntUnaligned(Object var1, long var2, boolean var4);

    public abstract short getShortUnaligned(Object var1, long var2);

    public abstract short getShortUnaligned(Object var1, long var2, boolean var4);

    public abstract char getCharUnaligned(Object var1, long var2);

    public abstract char getCharUnaligned(Object var1, long var2, boolean var4);

    public abstract void putLongUnaligned(Object var1, long var2, long var4);

    public abstract void putLongUnaligned(Object var1, long var2, long var4, boolean var6);

    public abstract void putIntUnaligned(Object var1, long var2, int var4);

    public abstract void putIntUnaligned(Object var1, long var2, int var4, boolean var5);

    public abstract void putShortUnaligned(Object var1, long var2, short var4);

    public abstract void putShortUnaligned(Object var1, long var2, short var4, boolean var5);

    public abstract void putCharUnaligned(Object var1, long var2, char var4);

    public abstract void putCharUnaligned(Object var1, long var2, char var4, boolean var5);

    public abstract void invokeCleaner(ByteBuffer var1);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getObject(Object var1, long var2);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getObjectVolatile(Object var1, long var2);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getObjectAcquire(Object var1, long var2);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getObjectOpaque(Object var1, long var2);

    @Deprecated(since="12", forRemoval=true)
    public abstract void putObject(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract void putObjectVolatile(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract void putObjectOpaque(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract void putObjectRelease(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getAndSetObject(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getAndSetObjectAcquire(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object getAndSetObjectRelease(Object var1, long var2, Object var4);

    @Deprecated(since="12", forRemoval=true)
    public abstract boolean compareAndSetObject(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object compareAndExchangeObject(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object compareAndExchangeObjectAcquire(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract Object compareAndExchangeObjectRelease(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract boolean weakCompareAndSetObject(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract boolean weakCompareAndSetObjectAcquire(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract boolean weakCompareAndSetObjectPlain(Object var1, long var2, Object var4, Object var5);

    @Deprecated(since="12", forRemoval=true)
    public abstract boolean weakCompareAndSetObjectRelease(Object var1, long var2, Object var4, Object var5);

    static {
        ARRAY_BOOLEAN_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(boolean[].class);
        ARRAY_BYTE_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(byte[].class);
        ARRAY_SHORT_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(short[].class);
        ARRAY_CHAR_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(char[].class);
        ARRAY_INT_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(int[].class);
        ARRAY_LONG_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(long[].class);
        ARRAY_FLOAT_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(float[].class);
        ARRAY_DOUBLE_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(double[].class);
        ARRAY_OBJECT_BASE_OFFSET = Unsafe.getUnsafe().arrayBaseOffset(Object[].class);
        ARRAY_BOOLEAN_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(boolean[].class);
        ARRAY_BYTE_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(byte[].class);
        ARRAY_SHORT_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(short[].class);
        ARRAY_CHAR_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(char[].class);
        ARRAY_INT_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(int[].class);
        ARRAY_LONG_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(long[].class);
        ARRAY_FLOAT_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(float[].class);
        ARRAY_DOUBLE_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(double[].class);
        ARRAY_OBJECT_INDEX_SCALE = Unsafe.getUnsafe().arrayIndexScale(Object[].class);
        ADDRESS_SIZE = Unsafe.getUnsafe().addressSize();
    }
}

