/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm;

public final class Handle {
    final int a;
    final String b;
    final String c;
    final String d;
    final boolean e;

    public Handle(int n, String string, String string2, String string3) {
        this(n, string, string2, string3, n == 9);
    }

    public Handle(int n, String string, String string2, String string3, boolean bl) {
        this.a = n;
        this.b = string;
        this.c = string2;
        this.d = string3;
        this.e = bl;
    }

    public int getTag() {
        return this.a;
    }

    public String getOwner() {
        return this.b;
    }

    public String getName() {
        return this.c;
    }

    public String getDesc() {
        return this.d;
    }

    public boolean isInterface() {
        return this.e;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Handle)) {
            return false;
        }
        Handle handle = (Handle)object;
        return this.a == handle.a && this.e == handle.e && this.b.equals(handle.b) && this.c.equals(handle.c) && this.d.equals(handle.d);
    }

    public int hashCode() {
        return this.a + (this.e ? 64 : 0) + this.b.hashCode() * this.c.hashCode() * this.d.hashCode();
    }

    public String toString() {
        return this.b + '.' + this.c + this.d + " (" + this.a + (this.e ? " itf" : "") + ')';
    }
}

