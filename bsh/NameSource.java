/*
 * Decompiled with CFR 0.152.
 */
package bsh;

public interface NameSource {
    public String[] getAllNames();

    public void addNameSourceListener(Listener var1);

    public static interface Listener {
        public void nameSourceChanged(NameSource var1);
    }
}

