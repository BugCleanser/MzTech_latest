/*
 * Decompiled with CFR 0.152.
 */
package jdk.internal.reflect;

public interface FieldAccessor {
    public Object get(Object var1) throws IllegalArgumentException;

    public boolean getBoolean(Object var1) throws IllegalArgumentException;

    public byte getByte(Object var1) throws IllegalArgumentException;

    public char getChar(Object var1) throws IllegalArgumentException;

    public short getShort(Object var1) throws IllegalArgumentException;

    public int getInt(Object var1) throws IllegalArgumentException;

    public long getLong(Object var1) throws IllegalArgumentException;

    public float getFloat(Object var1) throws IllegalArgumentException;

    public double getDouble(Object var1) throws IllegalArgumentException;

    public void set(Object var1, Object var2) throws IllegalArgumentException, IllegalAccessException;

    public void setBoolean(Object var1, boolean var2) throws IllegalArgumentException, IllegalAccessException;

    public void setByte(Object var1, byte var2) throws IllegalArgumentException, IllegalAccessException;

    public void setChar(Object var1, char var2) throws IllegalArgumentException, IllegalAccessException;

    public void setShort(Object var1, short var2) throws IllegalArgumentException, IllegalAccessException;

    public void setInt(Object var1, int var2) throws IllegalArgumentException, IllegalAccessException;

    public void setLong(Object var1, long var2) throws IllegalArgumentException, IllegalAccessException;

    public void setFloat(Object var1, float var2) throws IllegalArgumentException, IllegalAccessException;

    public void setDouble(Object var1, double var2) throws IllegalArgumentException, IllegalAccessException;
}

