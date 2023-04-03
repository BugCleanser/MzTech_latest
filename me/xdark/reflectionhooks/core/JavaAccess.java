/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.xdark.reflectionhooks.api.FieldGetController;
import me.xdark.reflectionhooks.api.FieldSetController;
import me.xdark.reflectionhooks.api.Hook;
import me.xdark.reflectionhooks.api.Invoker;

interface JavaAccess {
    default public void init() throws Throwable {
    }

    public <R> Object newMethodAccessor(Method var1, Invoker<R> var2, Hook var3);

    public Object newFieldAccessor(Field var1, FieldGetController var2, FieldSetController var3, Hook var4);

    public <R> Object newConstructorAccessor(Constructor<R> var1, Invoker<R> var2, Hook var3);

    public Class<?> resolve(String var1);
}

