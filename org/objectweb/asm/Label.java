/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm;

import org.objectweb.asm.ByteVector;
import org.objectweb.asm.Edge;
import org.objectweb.asm.Frame;
import org.objectweb.asm.MethodWriter;

public class Label {
    public Object info;
    int a;
    int b;
    int c;
    private int d;
    private int[] e;
    int f;
    int g;
    Frame h;
    Label i;
    Edge j;
    Label k;

    public int getOffset() {
        if ((this.a & 2) == 0) {
            throw new IllegalStateException("Label offset position has not been resolved yet");
        }
        return this.c;
    }

    void a(MethodWriter methodWriter, ByteVector byteVector, int n, boolean bl) {
        if ((this.a & 2) == 0) {
            if (bl) {
                this.a(-1 - n, byteVector.b);
                byteVector.putInt(-1);
            } else {
                this.a(n, byteVector.b);
                byteVector.putShort(-1);
            }
        } else if (bl) {
            byteVector.putInt(this.c - n);
        } else {
            byteVector.putShort(this.c - n);
        }
    }

    private void a(int n, int n2) {
        if (this.e == null) {
            this.e = new int[6];
        }
        if (this.d >= this.e.length) {
            int[] nArray = new int[this.e.length + 6];
            System.arraycopy(this.e, 0, nArray, 0, this.e.length);
            this.e = nArray;
        }
        this.e[this.d++] = n;
        this.e[this.d++] = n2;
    }

    boolean a(MethodWriter methodWriter, int n, byte[] byArray) {
        boolean bl = false;
        this.a |= 2;
        this.c = n;
        int n2 = 0;
        while (n2 < this.d) {
            int n3;
            int n4 = this.e[n2++];
            int n5 = this.e[n2++];
            if (n4 >= 0) {
                n3 = n - n4;
                if (n3 < Short.MIN_VALUE || n3 > Short.MAX_VALUE) {
                    int n6 = byArray[n5 - 1] & 0xFF;
                    byArray[n5 - 1] = n6 <= 168 ? (byte)(n6 + 49) : (byte)(n6 + 20);
                    bl = true;
                }
                byArray[n5++] = (byte)(n3 >>> 8);
                byArray[n5] = (byte)n3;
                continue;
            }
            n3 = n + n4 + 1;
            byArray[n5++] = (byte)(n3 >>> 24);
            byArray[n5++] = (byte)(n3 >>> 16);
            byArray[n5++] = (byte)(n3 >>> 8);
            byArray[n5] = (byte)n3;
        }
        return bl;
    }

    Label a() {
        return this.h == null ? this : this.h.b;
    }

    boolean a(long l) {
        if ((this.a & 0x400) != 0) {
            return (this.e[(int)(l >>> 32)] & (int)l) != 0;
        }
        return false;
    }

    boolean a(Label label) {
        if ((this.a & 0x400) == 0 || (label.a & 0x400) == 0) {
            return false;
        }
        for (int i = 0; i < this.e.length; ++i) {
            if ((this.e[i] & label.e[i]) == 0) continue;
            return true;
        }
        return false;
    }

    void a(long l, int n) {
        if ((this.a & 0x400) == 0) {
            this.a |= 0x400;
            this.e = new int[n / 32 + 1];
        }
        int n2 = (int)(l >>> 32);
        this.e[n2] = this.e[n2] | (int)l;
    }

    void b(Label label, long l, int n) {
        Label label2 = this;
        while (label2 != null) {
            Edge edge;
            Label label3 = label2;
            label2 = label3.k;
            label3.k = null;
            if (label != null) {
                if ((label3.a & 0x800) != 0) continue;
                label3.a |= 0x800;
                if ((label3.a & 0x100) != 0 && !label3.a(label)) {
                    edge = new Edge();
                    edge.a = label3.f;
                    edge.b = label.j.b;
                    edge.c = label3.j;
                    label3.j = edge;
                }
            } else {
                if (label3.a(l)) continue;
                label3.a(l, n);
            }
            edge = label3.j;
            while (edge != null) {
                if (((label3.a & 0x80) == 0 || edge != label3.j.c) && edge.b.k == null) {
                    edge.b.k = label2;
                    label2 = edge.b;
                }
                edge = edge.c;
            }
        }
    }

    public String toString() {
        return "L" + System.identityHashCode(this);
    }
}

