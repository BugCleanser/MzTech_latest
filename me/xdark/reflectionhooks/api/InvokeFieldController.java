/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

import me.xdark.reflectionhooks.api.FindType;
import me.xdark.reflectionhooks.api.NonDirectReference;

@FunctionalInterface
public interface InvokeFieldController {
    public void onFindCalled(FindType var1, NonDirectReference<Class<?>> var2, NonDirectReference<String> var3, NonDirectReference<Class<?>> var4);
}

