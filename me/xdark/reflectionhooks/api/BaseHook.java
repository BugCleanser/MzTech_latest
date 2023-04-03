/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

import me.xdark.reflectionhooks.api.Hook;

public class BaseHook
implements Hook {
    protected boolean hooked;

    @Override
    public void hook() {
        this.hooked = true;
    }

    @Override
    public boolean isHooked() {
        return this.hooked;
    }

    @Override
    public void unhook() {
        this.hooked = false;
    }
}

