/*
 * Decompiled with CFR 0.152.
 */
package jdk.internal.reflect;

import java.lang.reflect.InvocationTargetException;

public interface ConstructorAccessor {
    public Object newInstance(Object[] var1) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
}

