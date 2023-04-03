/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm;

public class ByteVector {
    byte[] a;
    int b;

    public ByteVector() {
        this.a = new byte[64];
    }

    public ByteVector(int n) {
        this.a = new byte[n];
    }

    public ByteVector putByte(int n) {
        int n2 = this.b;
        if (n2 + 1 > this.a.length) {
            this.a(1);
        }
        this.a[n2++] = (byte)n;
        this.b = n2;
        return this;
    }

    ByteVector a(int n, int n2) {
        int n3 = this.b;
        if (n3 + 2 > this.a.length) {
            this.a(2);
        }
        byte[] byArray = this.a;
        byArray[n3++] = (byte)n;
        byArray[n3++] = (byte)n2;
        this.b = n3;
        return this;
    }

    public ByteVector putShort(int n) {
        int n2 = this.b;
        if (n2 + 2 > this.a.length) {
            this.a(2);
        }
        byte[] byArray = this.a;
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        this.b = n2;
        return this;
    }

    ByteVector b(int n, int n2) {
        int n3 = this.b;
        if (n3 + 3 > this.a.length) {
            this.a(3);
        }
        byte[] byArray = this.a;
        byArray[n3++] = (byte)n;
        byArray[n3++] = (byte)(n2 >>> 8);
        byArray[n3++] = (byte)n2;
        this.b = n3;
        return this;
    }

    public ByteVector putInt(int n) {
        int n2 = this.b;
        if (n2 + 4 > this.a.length) {
            this.a(4);
        }
        byte[] byArray = this.a;
        byArray[n2++] = (byte)(n >>> 24);
        byArray[n2++] = (byte)(n >>> 16);
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        this.b = n2;
        return this;
    }

    public ByteVector putLong(long l) {
        int n = this.b;
        if (n + 8 > this.a.length) {
            this.a(8);
        }
        byte[] byArray = this.a;
        int n2 = (int)(l >>> 32);
        byArray[n++] = (byte)(n2 >>> 24);
        byArray[n++] = (byte)(n2 >>> 16);
        byArray[n++] = (byte)(n2 >>> 8);
        byArray[n++] = (byte)n2;
        n2 = (int)l;
        byArray[n++] = (byte)(n2 >>> 24);
        byArray[n++] = (byte)(n2 >>> 16);
        byArray[n++] = (byte)(n2 >>> 8);
        byArray[n++] = (byte)n2;
        this.b = n;
        return this;
    }

    public ByteVector putUTF8(String string) {
        int n = string.length();
        if (n > 65535) {
            throw new IllegalArgumentException();
        }
        int n2 = this.b;
        if (n2 + 2 + n > this.a.length) {
            this.a(2 + n);
        }
        byte[] byArray = this.a;
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c < '\u0001' || c > '\u007f') {
                this.b = n2;
                return this.c(string, i, 65535);
            }
            byArray[n2++] = (byte)c;
        }
        this.b = n2;
        return this;
    }

    ByteVector c(String string, int n, int n2) {
        char c;
        int n3;
        int n4 = string.length();
        int n5 = n;
        for (n3 = n; n3 < n4; ++n3) {
            c = string.charAt(n3);
            if (c >= '\u0001' && c <= '\u007f') {
                ++n5;
                continue;
            }
            if (c > '\u07ff') {
                n5 += 3;
                continue;
            }
            n5 += 2;
        }
        if (n5 > n2) {
            throw new IllegalArgumentException();
        }
        n3 = this.b - n - 2;
        if (n3 >= 0) {
            this.a[n3] = (byte)(n5 >>> 8);
            this.a[n3 + 1] = (byte)n5;
        }
        if (this.b + n5 - n > this.a.length) {
            this.a(n5 - n);
        }
        int n6 = this.b;
        for (int i = n; i < n4; ++i) {
            c = string.charAt(i);
            if (c >= '\u0001' && c <= '\u007f') {
                this.a[n6++] = (byte)c;
                continue;
            }
            if (c > '\u07ff') {
                this.a[n6++] = (byte)(0xE0 | c >> 12 & 0xF);
                this.a[n6++] = (byte)(0x80 | c >> 6 & 0x3F);
                this.a[n6++] = (byte)(0x80 | c & 0x3F);
                continue;
            }
            this.a[n6++] = (byte)(0xC0 | c >> 6 & 0x1F);
            this.a[n6++] = (byte)(0x80 | c & 0x3F);
        }
        this.b = n6;
        return this;
    }

    public ByteVector putByteArray(byte[] byArray, int n, int n2) {
        if (this.b + n2 > this.a.length) {
            this.a(n2);
        }
        if (byArray != null) {
            System.arraycopy(byArray, n, this.a, this.b, n2);
        }
        this.b += n2;
        return this;
    }

    private void a(int n) {
        int n2 = 2 * this.a.length;
        int n3 = this.b + n;
        byte[] byArray = new byte[n2 > n3 ? n2 : n3];
        System.arraycopy(this.a, 0, byArray, 0, this.b);
        this.a = byArray;
    }
}

