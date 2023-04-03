/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

import java.lang.invoke.MethodType;
import me.xdark.reflectionhooks.api.FindType;
import me.xdark.reflectionhooks.api.NonDirectReference;

@FunctionalInterface
public interface InvokeMethodController {
    public void onFindCalled(FindType var1, NonDirectReference<Class<?>> var2, NonDirectReference<String> var3, NonDirectReference<MethodType> var4);
}

