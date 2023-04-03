/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.api;

public class NonDirectReference<T> {
    private T value;

    public NonDirectReference(T referent) {
        this.value = referent;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}

