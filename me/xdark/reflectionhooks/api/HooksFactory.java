/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.xdark.reflectionhooks.api.FieldGetController;
import me.xdark.reflectionhooks.api.FieldSetController;
import me.xdark.reflectionhooks.api.Hook;
import me.xdark.reflectionhooks.api.InvokeFieldController;
import me.xdark.reflectionhooks.api.InvokeMethodController;
import me.xdark.reflectionhooks.api.Invoker;

public interface HooksFactory {
    public <R> Hook createMethodHook(Class<R> var1, Method var2, Invoker<R> var3);

    public Hook createFieldHook(Field var1, FieldGetController var2, FieldSetController var3);

    public <R> Hook createConstructorHook(Class<R> var1, Constructor<R> var2, Invoker<R> var3);

    public void createMethodInvokeHook(InvokeMethodController var1);

    public void createConstructorInvokeHook(InvokeMethodController var1);

    public void createFieldInvokeHook(InvokeFieldController var1);
}

