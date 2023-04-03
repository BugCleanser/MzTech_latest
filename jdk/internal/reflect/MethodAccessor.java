/*
 * Decompiled with CFR 0.152.
 */
package jdk.internal.reflect;

import java.lang.reflect.InvocationTargetException;

public interface MethodAccessor {
    public Object invoke(Object var1, Object[] var2) throws IllegalArgumentException, InvocationTargetException;
}

