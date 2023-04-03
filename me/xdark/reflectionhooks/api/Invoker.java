/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

@FunctionalInterface
public interface Invoker<R> {
    public R invoke(Invoker<R> var1, Object var2, Object ... var3) throws Throwable;
}

