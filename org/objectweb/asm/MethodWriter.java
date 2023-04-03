/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.AnnotationWriter;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.CurrentFrame;
import org.objectweb.asm.Edge;
import org.objectweb.asm.Frame;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Handler;
import org.objectweb.asm.Item;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

class MethodWriter
extends MethodVisitor {
    final ClassWriter b;
    private int c;
    private final int d;
    private final int e;
    private final String f;
    String g;
    int h;
    int i;
    int j;
    int[] k;
    private ByteVector l;
    private AnnotationWriter m;
    private AnnotationWriter n;
    private AnnotationWriter U;
    private AnnotationWriter V;
    private AnnotationWriter[] o;
    private AnnotationWriter[] p;
    private int R;
    private Attribute q;
    private ByteVector r = new ByteVector();
    private int s;
    private int t;
    private int T;
    private int u;
    private ByteVector v;
    private int w;
    private int[] x;
    private int[] z;
    private int A;
    private Handler B;
    private Handler C;
    private int Z;
    private ByteVector $;
    private int D;
    private ByteVector E;
    private int F;
    private ByteVector G;
    private int H;
    private ByteVector I;
    private int Y;
    private AnnotationWriter W;
    private AnnotationWriter X;
    private Attribute J;
    private int K;
    private final int L;
    private Label M;
    private Label N;
    private Label O;
    private int P;
    private int Q;

    MethodWriter(ClassWriter classWriter, int n, String string, String string2, String string3, String[] stringArray, int n2) {
        super(327680);
        int n3;
        if (classWriter.D == null) {
            classWriter.D = this;
        } else {
            classWriter.E.mv = this;
        }
        classWriter.E = this;
        this.b = classWriter;
        this.c = n;
        if ("<init>".equals(string)) {
            this.c |= 0x80000;
        }
        this.d = classWriter.newUTF8(string);
        this.e = classWriter.newUTF8(string2);
        this.f = string2;
        this.g = string3;
        if (stringArray != null && stringArray.length > 0) {
            this.j = stringArray.length;
            this.k = new int[this.j];
            for (n3 = 0; n3 < this.j; ++n3) {
                this.k[n3] = classWriter.newClass(stringArray[n3]);
            }
        }
        this.L = n2;
        if (n2 != 3) {
            n3 = Type.getArgumentsAndReturnSizes(this.f) >> 2;
            if ((n & 8) != 0) {
                --n3;
            }
            this.t = n3;
            this.T = n3;
            this.M = new Label();
            this.M.a |= 8;
            this.visitLabel(this.M);
        }
    }

    public void visitParameter(String string, int n) {
        if (this.$ == null) {
            this.$ = new ByteVector();
        }
        ++this.Z;
        this.$.putShort(string == null ? 0 : this.b.newUTF8(string)).putShort(n);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        this.l = new ByteVector();
        return new AnnotationWriter(this.b, false, this.l, null, 0);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (bl) {
            annotationWriter.g = this.m;
            this.m = annotationWriter;
        } else {
            annotationWriter.g = this.n;
            this.n = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(n, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (bl) {
            annotationWriter.g = this.U;
            this.U = annotationWriter;
        } else {
            annotationWriter.g = this.V;
            this.V = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(string)) {
            this.R = Math.max(this.R, n + 1);
            return new AnnotationWriter(this.b, false, byteVector, null, 0);
        }
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (bl) {
            if (this.o == null) {
                this.o = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.o[n];
            this.o[n] = annotationWriter;
        } else {
            if (this.p == null) {
                this.p = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }
            annotationWriter.g = this.p[n];
            this.p[n] = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.a = this.J;
            this.J = attribute;
        } else {
            attribute.a = this.q;
            this.q = attribute;
        }
    }

    public void visitCode() {
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        if (this.L == 0) {
            return;
        }
        if (this.L == 1) {
            if (this.O.h == null) {
                this.O.h = new CurrentFrame();
                this.O.h.b = this.O;
                this.O.h.a(this.b, this.c, Type.getArgumentTypes(this.f), n2);
                this.f();
            } else {
                if (n == -1) {
                    this.O.h.a(this.b, n2, objectArray, n3, objectArray2);
                }
                this.b(this.O.h);
            }
        } else if (n == -1) {
            int n4;
            if (this.x == null) {
                this.f();
            }
            this.T = n2;
            int n5 = this.a(this.r.b, n2, n3);
            for (n4 = 0; n4 < n2; ++n4) {
                this.z[n5++] = objectArray[n4] instanceof String ? 0x1700000 | this.b.c((String)objectArray[n4]) : (objectArray[n4] instanceof Integer ? (Integer)objectArray[n4] : 0x1800000 | this.b.a("", ((Label)objectArray[n4]).c));
            }
            for (n4 = 0; n4 < n3; ++n4) {
                this.z[n5++] = objectArray2[n4] instanceof String ? 0x1700000 | this.b.c((String)objectArray2[n4]) : (objectArray2[n4] instanceof Integer ? (Integer)objectArray2[n4] : 0x1800000 | this.b.a("", ((Label)objectArray2[n4]).c));
            }
            this.b();
        } else {
            int n6;
            if (this.v == null) {
                this.v = new ByteVector();
                n6 = this.r.b;
            } else {
                n6 = this.r.b - this.w - 1;
                if (n6 < 0) {
                    if (n == 3) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            }
            switch (n) {
                case 0: {
                    int n7;
                    this.T = n2;
                    this.v.putByte(255).putShort(n6).putShort(n2);
                    for (n7 = 0; n7 < n2; ++n7) {
                        this.a(objectArray[n7]);
                    }
                    this.v.putShort(n3);
                    for (n7 = 0; n7 < n3; ++n7) {
                        this.a(objectArray2[n7]);
                    }
                    break;
                }
                case 1: {
                    this.T += n2;
                    this.v.putByte(251 + n2).putShort(n6);
                    for (int i = 0; i < n2; ++i) {
                        this.a(objectArray[i]);
                    }
                    break;
                }
                case 2: {
                    this.T -= n2;
                    this.v.putByte(251 - n2).putShort(n6);
                    break;
                }
                case 3: {
                    if (n6 < 64) {
                        this.v.putByte(n6);
                        break;
                    }
                    this.v.putByte(251).putShort(n6);
                    break;
                }
                case 4: {
                    if (n6 < 64) {
                        this.v.putByte(64 + n6);
                    } else {
                        this.v.putByte(247).putShort(n6);
                    }
                    this.a(objectArray2[0]);
                }
            }
            this.w = this.r.b;
            ++this.u;
        }
        this.s = Math.max(this.s, n3);
        this.t = Math.max(this.t, this.T);
    }

    public void visitInsn(int n) {
        this.Y = this.r.b;
        this.r.putByte(n);
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, 0, null, null);
            } else {
                int n2 = this.P + Frame.a[n];
                if (n2 > this.Q) {
                    this.Q = n2;
                }
                this.P = n2;
            }
            if (n >= 172 && n <= 177 || n == 191) {
                this.e();
            }
        }
    }

    public void visitIntInsn(int n, int n2) {
        this.Y = this.r.b;
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, n2, null, null);
            } else if (n != 188) {
                int n3 = this.P + 1;
                if (n3 > this.Q) {
                    this.Q = n3;
                }
                this.P = n3;
            }
        }
        if (n == 17) {
            this.r.b(n, n2);
        } else {
            this.r.a(n, n2);
        }
    }

    public void visitVarInsn(int n, int n2) {
        int n3;
        this.Y = this.r.b;
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, n2, null, null);
            } else if (n == 169) {
                this.O.a |= 0x100;
                this.O.f = this.P;
                this.e();
            } else {
                n3 = this.P + Frame.a[n];
                if (n3 > this.Q) {
                    this.Q = n3;
                }
                this.P = n3;
            }
        }
        if (this.L != 3 && (n3 = n == 22 || n == 24 || n == 55 || n == 57 ? n2 + 2 : n2 + 1) > this.t) {
            this.t = n3;
        }
        if (n2 < 4 && n != 169) {
            n3 = n < 54 ? 26 + (n - 21 << 2) + n2 : 59 + (n - 54 << 2) + n2;
            this.r.putByte(n3);
        } else if (n2 >= 256) {
            this.r.putByte(196).b(n, n2);
        } else {
            this.r.a(n, n2);
        }
        if (n >= 54 && this.L == 0 && this.A > 0) {
            this.visitLabel(new Label());
        }
    }

    public void visitTypeInsn(int n, String string) {
        this.Y = this.r.b;
        Item item = this.b.a(string);
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, this.r.b, this.b, item);
            } else if (n == 187) {
                int n2 = this.P + 1;
                if (n2 > this.Q) {
                    this.Q = n2;
                }
                this.P = n2;
            }
        }
        this.r.b(n, item.a);
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.Y = this.r.b;
        Item item = this.b.a(string, string2, string3);
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, 0, this.b, item);
            } else {
                int n2;
                char c = string3.charAt(0);
                switch (n) {
                    case 178: {
                        n2 = this.P + (c == 'D' || c == 'J' ? 2 : 1);
                        break;
                    }
                    case 179: {
                        n2 = this.P + (c == 'D' || c == 'J' ? -2 : -1);
                        break;
                    }
                    case 180: {
                        n2 = this.P + (c == 'D' || c == 'J' ? 1 : 0);
                        break;
                    }
                    default: {
                        n2 = this.P + (c == 'D' || c == 'J' ? -3 : -2);
                    }
                }
                if (n2 > this.Q) {
                    this.Q = n2;
                }
                this.P = n2;
            }
        }
        this.r.b(n, item.a);
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        this.Y = this.r.b;
        Item item = this.b.a(string, string2, string3, bl);
        int n2 = item.c;
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(n, 0, this.b, item);
            } else {
                int n3;
                if (n2 == 0) {
                    item.c = n2 = Type.getArgumentsAndReturnSizes(string3);
                }
                if ((n3 = n == 184 ? this.P - (n2 >> 2) + (n2 & 3) + 1 : this.P - (n2 >> 2) + (n2 & 3)) > this.Q) {
                    this.Q = n3;
                }
                this.P = n3;
            }
        }
        if (n == 185) {
            if (n2 == 0) {
                item.c = n2 = Type.getArgumentsAndReturnSizes(string3);
            }
            this.r.b(185, item.a).a(n2 >> 2, 0);
        } else {
            this.r.b(n, item.a);
        }
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.Y = this.r.b;
        Item item = this.b.a(string, string2, handle, objectArray);
        int n = item.c;
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(186, 0, this.b, item);
            } else {
                int n2;
                if (n == 0) {
                    item.c = n = Type.getArgumentsAndReturnSizes(string2);
                }
                if ((n2 = this.P - (n >> 2) + (n & 3) + 1) > this.Q) {
                    this.Q = n2;
                }
                this.P = n2;
            }
        }
        this.r.b(186, item.a);
        this.r.putShort(0);
    }

    public void visitJumpInsn(int n, Label label) {
        boolean bl = n >= 200;
        n = bl ? n - 33 : n;
        this.Y = this.r.b;
        Label label2 = null;
        if (this.O != null) {
            if (this.L == 0) {
                this.O.h.a(n, 0, null, null);
                label.a().a |= 0x10;
                this.a(0, label);
                if (n != 167) {
                    label2 = new Label();
                }
            } else if (this.L == 1) {
                this.O.h.a(n, 0, null, null);
            } else if (n == 168) {
                if ((label.a & 0x200) == 0) {
                    label.a |= 0x200;
                    ++this.K;
                }
                this.O.a |= 0x80;
                this.a(this.P + 1, label);
                label2 = new Label();
            } else {
                this.P += Frame.a[n];
                this.a(this.P, label);
            }
        }
        if ((label.a & 2) != 0 && label.c - this.r.b < Short.MIN_VALUE) {
            if (n == 167) {
                this.r.putByte(200);
            } else if (n == 168) {
                this.r.putByte(201);
            } else {
                if (label2 != null) {
                    label2.a |= 0x10;
                }
                this.r.putByte(n <= 166 ? (n + 1 ^ 1) - 1 : n ^ 1);
                this.r.putShort(8);
                this.r.putByte(200);
            }
            label.a(this, this.r, this.r.b - 1, true);
        } else if (bl) {
            this.r.putByte(n + 33);
            label.a(this, this.r, this.r.b - 1, true);
        } else {
            this.r.putByte(n);
            label.a(this, this.r, this.r.b - 1, false);
        }
        if (this.O != null) {
            if (label2 != null) {
                this.visitLabel(label2);
            }
            if (n == 167) {
                this.e();
            }
        }
    }

    public void visitLabel(Label label) {
        this.b.J |= label.a(this, this.r.b, this.r.a);
        if ((label.a & 1) != 0) {
            return;
        }
        if (this.L == 0) {
            if (this.O != null) {
                if (label.c == this.O.c) {
                    this.O.a |= label.a & 0x10;
                    label.h = this.O.h;
                    return;
                }
                this.a(0, label);
            }
            this.O = label;
            if (label.h == null) {
                label.h = new Frame();
                label.h.b = label;
            }
            if (this.N != null) {
                if (label.c == this.N.c) {
                    this.N.a |= label.a & 0x10;
                    label.h = this.N.h;
                    this.O = this.N;
                    return;
                }
                this.N.i = label;
            }
            this.N = label;
        } else if (this.L == 1) {
            if (this.O == null) {
                this.O = label;
            } else {
                this.O.h.b = label;
            }
        } else if (this.L == 2) {
            if (this.O != null) {
                this.O.g = this.Q;
                this.a(this.P, label);
            }
            this.O = label;
            this.P = 0;
            this.Q = 0;
            if (this.N != null) {
                this.N.i = label;
            }
            this.N = label;
        }
    }

    public void visitLdcInsn(Object object) {
        int n;
        this.Y = this.r.b;
        Item item = this.b.a(object);
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(18, 0, this.b, item);
            } else {
                n = item.b == 5 || item.b == 6 ? this.P + 2 : this.P + 1;
                if (n > this.Q) {
                    this.Q = n;
                }
                this.P = n;
            }
        }
        n = item.a;
        if (item.b == 5 || item.b == 6) {
            this.r.b(20, n);
        } else if (n >= 256) {
            this.r.b(19, n);
        } else {
            this.r.a(18, n);
        }
    }

    public void visitIincInsn(int n, int n2) {
        int n3;
        this.Y = this.r.b;
        if (this.O != null && (this.L == 0 || this.L == 1)) {
            this.O.h.a(132, n, null, null);
        }
        if (this.L != 3 && (n3 = n + 1) > this.t) {
            this.t = n3;
        }
        if (n > 255 || n2 > 127 || n2 < -128) {
            this.r.putByte(196).b(132, n).putShort(n2);
        } else {
            this.r.putByte(132).a(n, n2);
        }
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        this.Y = this.r.b;
        int n3 = this.r.b;
        this.r.putByte(170);
        this.r.putByteArray(null, 0, (4 - this.r.b % 4) % 4);
        label.a(this, this.r, n3, true);
        this.r.putInt(n).putInt(n2);
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i].a(this, this.r, n3, true);
        }
        this.a(label, labelArray);
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        this.Y = this.r.b;
        int n = this.r.b;
        this.r.putByte(171);
        this.r.putByteArray(null, 0, (4 - this.r.b % 4) % 4);
        label.a(this, this.r, n, true);
        this.r.putInt(labelArray.length);
        for (int i = 0; i < labelArray.length; ++i) {
            this.r.putInt(nArray[i]);
            labelArray[i].a(this, this.r, n, true);
        }
        this.a(label, labelArray);
    }

    private void a(Label label, Label[] labelArray) {
        if (this.O != null) {
            if (this.L == 0) {
                this.O.h.a(171, 0, null, null);
                this.a(0, label);
                label.a().a |= 0x10;
                for (int i = 0; i < labelArray.length; ++i) {
                    this.a(0, labelArray[i]);
                    labelArray[i].a().a |= 0x10;
                }
            } else {
                --this.P;
                this.a(this.P, label);
                for (int i = 0; i < labelArray.length; ++i) {
                    this.a(this.P, labelArray[i]);
                }
            }
            this.e();
        }
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.Y = this.r.b;
        Item item = this.b.a(string);
        if (this.O != null) {
            if (this.L == 0 || this.L == 1) {
                this.O.h.a(197, n, this.b, item);
            } else {
                this.P += 1 - n;
            }
        }
        this.r.b(197, item.a).putByte(n);
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        n = n & 0xFF0000FF | this.Y << 8;
        AnnotationWriter.a(n, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (bl) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        ++this.A;
        Handler handler = new Handler();
        handler.a = label;
        handler.b = label2;
        handler.c = label3;
        handler.d = string;
        int n = handler.e = string != null ? this.b.newClass(string) : 0;
        if (this.C == null) {
            this.B = handler;
        } else {
            this.C.f = handler;
        }
        this.C = handler;
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.a(n, typePath, byteVector);
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (bl) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        char c;
        int n2;
        if (string3 != null) {
            if (this.G == null) {
                this.G = new ByteVector();
            }
            ++this.F;
            this.G.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(string)).putShort(this.b.newUTF8(string3)).putShort(n);
        }
        if (this.E == null) {
            this.E = new ByteVector();
        }
        ++this.D;
        this.E.putShort(label.c).putShort(label2.c - label.c).putShort(this.b.newUTF8(string)).putShort(this.b.newUTF8(string2)).putShort(n);
        if (this.L != 3 && (n2 = n + ((c = string2.charAt(0)) == 'J' || c == 'D' ? 2 : 1)) > this.t) {
            this.t = n2;
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        int n2;
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(n >>> 24).putShort(labelArray.length);
        for (n2 = 0; n2 < labelArray.length; ++n2) {
            byteVector.putShort(labelArray[n2].c).putShort(labelArray2[n2].c - labelArray[n2].c).putShort(nArray[n2]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            n2 = typePath.a[typePath.b] * 2 + 1;
            byteVector.putByteArray(typePath.a, typePath.b, n2);
        }
        byteVector.putShort(this.b.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, byteVector.b - 2);
        if (bl) {
            annotationWriter.g = this.W;
            this.W = annotationWriter;
        } else {
            annotationWriter.g = this.X;
            this.X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitLineNumber(int n, Label label) {
        if (this.I == null) {
            this.I = new ByteVector();
        }
        ++this.H;
        this.I.putShort(label.c);
        this.I.putShort(n);
    }

    public void visitMaxs(int n, int n2) {
        if (this.L == 0) {
            int n3;
            Edge edge;
            int n4;
            Object object;
            Label label;
            Object object2;
            Handler handler = this.B;
            while (handler != null) {
                object2 = handler.a.a();
                Label label2 = handler.c.a();
                label = handler.b.a();
                object = handler.d == null ? "java/lang/Throwable" : handler.d;
                n4 = 0x1700000 | this.b.c((String)object);
                label2.a |= 0x10;
                while (object2 != label) {
                    edge = new Edge();
                    edge.a = n4;
                    edge.b = label2;
                    edge.c = ((Label)object2).j;
                    ((Label)object2).j = edge;
                    object2 = ((Label)object2).i;
                }
                handler = handler.f;
            }
            object2 = this.M.h;
            ((Frame)object2).a(this.b, this.c, Type.getArgumentTypes(this.f), this.t);
            this.b((Frame)object2);
            int n5 = 0;
            label = this.M;
            while (label != null) {
                object = label;
                label = label.k;
                ((Label)object).k = null;
                object2 = ((Label)object).h;
                if ((((Label)object).a & 0x10) != 0) {
                    ((Label)object).a |= 0x20;
                }
                ((Label)object).a |= 0x40;
                n4 = ((Frame)object2).d.length + ((Label)object).g;
                if (n4 > n5) {
                    n5 = n4;
                }
                edge = ((Label)object).j;
                while (edge != null) {
                    Label label3 = edge.b.a();
                    n3 = ((Frame)object2).a(this.b, label3.h, edge.a) ? 1 : 0;
                    if (n3 != 0 && label3.k == null) {
                        label3.k = label;
                        label = label3;
                    }
                    edge = edge.c;
                }
            }
            object = this.M;
            while (object != null) {
                int n6;
                Label label4;
                int n7;
                object2 = ((Label)object).h;
                if ((((Label)object).a & 0x20) != 0) {
                    this.b((Frame)object2);
                }
                if ((((Label)object).a & 0x40) == 0 && (n7 = ((label4 = ((Label)object).i) == null ? this.r.b : label4.c) - 1) >= (n6 = ((Label)object).c)) {
                    n5 = Math.max(n5, 1);
                    for (n3 = n6; n3 < n7; ++n3) {
                        this.r.a[n3] = 0;
                    }
                    this.r.a[n7] = -65;
                    n3 = this.a(n6, 0, 1);
                    this.z[n3] = 0x1700000 | this.b.c("java/lang/Throwable");
                    this.b();
                    this.B = Handler.a(this.B, (Label)object, label4);
                }
                object = ((Label)object).i;
            }
            handler = this.B;
            this.A = 0;
            while (handler != null) {
                ++this.A;
                handler = handler.f;
            }
            this.s = n5;
        } else if (this.L == 2) {
            Object object;
            Label label;
            Label label5;
            Handler handler = this.B;
            while (handler != null) {
                Label label6 = handler.a;
                label5 = handler.c;
                label = handler.b;
                while (label6 != label) {
                    object = new Edge();
                    ((Edge)object).a = Integer.MAX_VALUE;
                    ((Edge)object).b = label5;
                    if ((label6.a & 0x80) == 0) {
                        ((Edge)object).c = label6.j;
                        label6.j = object;
                    } else {
                        ((Edge)object).c = label6.j.c.c;
                        label6.j.c.c = object;
                    }
                    label6 = label6.i;
                }
                handler = handler.f;
            }
            if (this.K > 0) {
                int n8 = 0;
                this.M.b(null, 1L, this.K);
                label5 = this.M;
                while (label5 != null) {
                    if ((label5.a & 0x80) != 0) {
                        label = label5.j.c.b;
                        if ((label.a & 0x400) == 0) {
                            label.b(null, (long)(++n8) / 32L << 32 | 1L << n8 % 32, this.K);
                        }
                    }
                    label5 = label5.i;
                }
                label5 = this.M;
                while (label5 != null) {
                    if ((label5.a & 0x80) != 0) {
                        label = this.M;
                        while (label != null) {
                            label.a &= 0xFFFFF7FF;
                            label = label.i;
                        }
                        object = label5.j.c.b;
                        ((Label)object).b(label5, 0L, this.K);
                    }
                    label5 = label5.i;
                }
            }
            int n9 = 0;
            label5 = this.M;
            while (label5 != null) {
                label = label5;
                label5 = label5.k;
                int n10 = label.f;
                int n11 = n10 + label.g;
                if (n11 > n9) {
                    n9 = n11;
                }
                Edge edge = label.j;
                if ((label.a & 0x80) != 0) {
                    edge = edge.c;
                }
                while (edge != null) {
                    label = edge.b;
                    if ((label.a & 8) == 0) {
                        label.f = edge.a == Integer.MAX_VALUE ? 1 : n10 + edge.a;
                        label.a |= 8;
                        label.k = label5;
                        label5 = label;
                    }
                    edge = edge.c;
                }
            }
            this.s = Math.max(n, n9);
        } else {
            this.s = n;
            this.t = n2;
        }
    }

    public void visitEnd() {
    }

    private void a(int n, Label label) {
        Edge edge = new Edge();
        edge.a = n;
        edge.b = label;
        edge.c = this.O.j;
        this.O.j = edge;
    }

    private void e() {
        if (this.L == 0) {
            Label label = new Label();
            label.h = new Frame();
            label.h.b = label;
            label.a(this, this.r.b, this.r.a);
            this.N.i = label;
            this.N = label;
        } else {
            this.O.g = this.Q;
        }
        if (this.L != 1) {
            this.O = null;
        }
    }

    private void b(Frame frame) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int[] nArray = frame.c;
        int[] nArray2 = frame.d;
        for (n2 = 0; n2 < nArray.length; ++n2) {
            n = nArray[n2];
            if (n == 0x1000000) {
                ++n3;
            } else {
                n4 += n3 + 1;
                n3 = 0;
            }
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        for (n2 = 0; n2 < nArray2.length; ++n2) {
            n = nArray2[n2];
            ++n5;
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        int n6 = this.a(frame.b.c, n4, n5);
        n2 = 0;
        while (n4 > 0) {
            n = nArray[n2];
            this.z[n6++] = n;
            if (n == 0x1000004 || n == 0x1000003) {
                ++n2;
            }
            ++n2;
            --n4;
        }
        for (n2 = 0; n2 < nArray2.length; ++n2) {
            n = nArray2[n2];
            this.z[n6++] = n;
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        this.b();
    }

    private void f() {
        int n = this.a(0, this.f.length() + 1, 0);
        if ((this.c & 8) == 0) {
            this.z[n++] = (this.c & 0x80000) == 0 ? 0x1700000 | this.b.c(this.b.I) : 6;
        }
        int n2 = 1;
        block8: while (true) {
            int n3 = n2;
            switch (this.f.charAt(n2++)) {
                case 'B': 
                case 'C': 
                case 'I': 
                case 'S': 
                case 'Z': {
                    this.z[n++] = 1;
                    continue block8;
                }
                case 'F': {
                    this.z[n++] = 2;
                    continue block8;
                }
                case 'J': {
                    this.z[n++] = 4;
                    continue block8;
                }
                case 'D': {
                    this.z[n++] = 3;
                    continue block8;
                }
                case '[': {
                    while (this.f.charAt(n2) == '[') {
                        ++n2;
                    }
                    if (this.f.charAt(n2) == 'L') {
                        ++n2;
                        while (this.f.charAt(n2) != ';') {
                            ++n2;
                        }
                    }
                    this.z[n++] = 0x1700000 | this.b.c(this.f.substring(n3, ++n2));
                    continue block8;
                }
                case 'L': {
                    while (this.f.charAt(n2) != ';') {
                        ++n2;
                    }
                    this.z[n++] = 0x1700000 | this.b.c(this.f.substring(n3 + 1, n2++));
                    continue block8;
                }
            }
            break;
        }
        this.z[1] = n - 3;
        this.b();
    }

    private int a(int n, int n2, int n3) {
        int n4 = 3 + n2 + n3;
        if (this.z == null || this.z.length < n4) {
            this.z = new int[n4];
        }
        this.z[0] = n;
        this.z[1] = n2;
        this.z[2] = n3;
        return 3;
    }

    private void b() {
        if (this.x != null) {
            if (this.v == null) {
                this.v = new ByteVector();
            }
            this.c();
            ++this.u;
        }
        this.x = this.z;
        this.z = null;
    }

    private void c() {
        int n = this.z[1];
        int n2 = this.z[2];
        if ((this.b.b & 0xFFFF) < 50) {
            this.v.putShort(this.z[0]).putShort(n);
            this.a(3, 3 + n);
            this.v.putShort(n2);
            this.a(3 + n, 3 + n + n2);
            return;
        }
        int n3 = this.x[1];
        int n4 = 255;
        int n5 = 0;
        int n6 = this.u == 0 ? this.z[0] : this.z[0] - this.x[0] - 1;
        if (n2 == 0) {
            n5 = n - n3;
            switch (n5) {
                case -3: 
                case -2: 
                case -1: {
                    n4 = 248;
                    n3 = n;
                    break;
                }
                case 0: {
                    n4 = n6 < 64 ? 0 : 251;
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    n4 = 252;
                }
            }
        } else if (n == n3 && n2 == 1) {
            int n7 = n4 = n6 < 63 ? 64 : 247;
        }
        if (n4 != 255) {
            int n8 = 3;
            for (int i = 0; i < n3; ++i) {
                if (this.z[n8] != this.x[n8]) {
                    n4 = 255;
                    break;
                }
                ++n8;
            }
        }
        switch (n4) {
            case 0: {
                this.v.putByte(n6);
                break;
            }
            case 64: {
                this.v.putByte(64 + n6);
                this.a(3 + n, 4 + n);
                break;
            }
            case 247: {
                this.v.putByte(247).putShort(n6);
                this.a(3 + n, 4 + n);
                break;
            }
            case 251: {
                this.v.putByte(251).putShort(n6);
                break;
            }
            case 248: {
                this.v.putByte(251 + n5).putShort(n6);
                break;
            }
            case 252: {
                this.v.putByte(251 + n5).putShort(n6);
                this.a(3 + n3, 3 + n);
                break;
            }
            default: {
                this.v.putByte(255).putShort(n6).putShort(n);
                this.a(3, 3 + n);
                this.v.putShort(n2);
                this.a(3 + n, 3 + n + n2);
            }
        }
    }

    private void a(int n, int n2) {
        for (int i = n; i < n2; ++i) {
            int n3 = this.z[i];
            int n4 = n3 & 0xF0000000;
            if (n4 == 0) {
                int n5 = n3 & 0xFFFFF;
                switch (n3 & 0xFF00000) {
                    case 0x1700000: {
                        this.v.putByte(7).putShort(this.b.newClass(this.b.H[n5].g));
                        break;
                    }
                    case 0x1800000: {
                        this.v.putByte(8).putShort(this.b.H[n5].c);
                        break;
                    }
                    default: {
                        this.v.putByte(n5);
                        break;
                    }
                }
                continue;
            }
            StringBuffer stringBuffer = new StringBuffer();
            n4 >>= 28;
            while (n4-- > 0) {
                stringBuffer.append('[');
            }
            if ((n3 & 0xFF00000) == 0x1700000) {
                stringBuffer.append('L');
                stringBuffer.append(this.b.H[n3 & 0xFFFFF].g);
                stringBuffer.append(';');
            } else {
                switch (n3 & 0xF) {
                    case 1: {
                        stringBuffer.append('I');
                        break;
                    }
                    case 2: {
                        stringBuffer.append('F');
                        break;
                    }
                    case 3: {
                        stringBuffer.append('D');
                        break;
                    }
                    case 9: {
                        stringBuffer.append('Z');
                        break;
                    }
                    case 10: {
                        stringBuffer.append('B');
                        break;
                    }
                    case 11: {
                        stringBuffer.append('C');
                        break;
                    }
                    case 12: {
                        stringBuffer.append('S');
                        break;
                    }
                    default: {
                        stringBuffer.append('J');
                    }
                }
            }
            this.v.putByte(7).putShort(this.b.newClass(stringBuffer.toString()));
        }
    }

    private void a(Object object) {
        if (object instanceof String) {
            this.v.putByte(7).putShort(this.b.newClass((String)object));
        } else if (object instanceof Integer) {
            this.v.putByte((Integer)object);
        } else {
            this.v.putByte(8).putShort(((Label)object).c);
        }
    }

    final int a() {
        int n;
        if (this.h != 0) {
            return 6 + this.i;
        }
        int n2 = 8;
        if (this.r.b > 0) {
            if (this.r.b > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.b.newUTF8("Code");
            n2 += 18 + this.r.b + 8 * this.A;
            if (this.E != null) {
                this.b.newUTF8("LocalVariableTable");
                n2 += 8 + this.E.b;
            }
            if (this.G != null) {
                this.b.newUTF8("LocalVariableTypeTable");
                n2 += 8 + this.G.b;
            }
            if (this.I != null) {
                this.b.newUTF8("LineNumberTable");
                n2 += 8 + this.I.b;
            }
            if (this.v != null) {
                n = (this.b.b & 0xFFFF) >= 50 ? 1 : 0;
                this.b.newUTF8(n != 0 ? "StackMapTable" : "StackMap");
                n2 += 8 + this.v.b;
            }
            if (this.W != null) {
                this.b.newUTF8("RuntimeVisibleTypeAnnotations");
                n2 += 8 + this.W.a();
            }
            if (this.X != null) {
                this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
                n2 += 8 + this.X.a();
            }
            if (this.J != null) {
                n2 += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
        }
        if (this.j > 0) {
            this.b.newUTF8("Exceptions");
            n2 += 8 + 2 * this.j;
        }
        if ((this.c & 0x1000) != 0 && ((this.b.b & 0xFFFF) < 49 || (this.c & 0x40000) != 0)) {
            this.b.newUTF8("Synthetic");
            n2 += 6;
        }
        if ((this.c & 0x20000) != 0) {
            this.b.newUTF8("Deprecated");
            n2 += 6;
        }
        if (this.g != null) {
            this.b.newUTF8("Signature");
            this.b.newUTF8(this.g);
            n2 += 8;
        }
        if (this.$ != null) {
            this.b.newUTF8("MethodParameters");
            n2 += 7 + this.$.b;
        }
        if (this.l != null) {
            this.b.newUTF8("AnnotationDefault");
            n2 += 6 + this.l.b;
        }
        if (this.m != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            n2 += 8 + this.m.a();
        }
        if (this.n != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            n2 += 8 + this.n.a();
        }
        if (this.U != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            n2 += 8 + this.U.a();
        }
        if (this.V != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            n2 += 8 + this.V.a();
        }
        if (this.o != null) {
            this.b.newUTF8("RuntimeVisibleParameterAnnotations");
            n2 += 7 + 2 * (this.o.length - this.R);
            for (n = this.o.length - 1; n >= this.R; --n) {
                n2 += this.o[n] == null ? 0 : this.o[n].a();
            }
        }
        if (this.p != null) {
            this.b.newUTF8("RuntimeInvisibleParameterAnnotations");
            n2 += 7 + 2 * (this.p.length - this.R);
            for (n = this.p.length - 1; n >= this.R; --n) {
                n2 += this.p[n] == null ? 0 : this.p[n].a();
            }
        }
        if (this.q != null) {
            n2 += this.q.a(this.b, null, 0, -1, -1);
        }
        return n2;
    }

    final void a(ByteVector byteVector) {
        int n;
        int n2 = 64;
        int n3 = 0xE0000 | (this.c & 0x40000) / 64;
        byteVector.putShort(this.c & ~n3).putShort(this.d).putShort(this.e);
        if (this.h != 0) {
            byteVector.putByteArray(this.b.K.b, this.h, this.i);
            return;
        }
        int n4 = 0;
        if (this.r.b > 0) {
            ++n4;
        }
        if (this.j > 0) {
            ++n4;
        }
        if ((this.c & 0x1000) != 0 && ((this.b.b & 0xFFFF) < 49 || (this.c & 0x40000) != 0)) {
            ++n4;
        }
        if ((this.c & 0x20000) != 0) {
            ++n4;
        }
        if (this.g != null) {
            ++n4;
        }
        if (this.$ != null) {
            ++n4;
        }
        if (this.l != null) {
            ++n4;
        }
        if (this.m != null) {
            ++n4;
        }
        if (this.n != null) {
            ++n4;
        }
        if (this.U != null) {
            ++n4;
        }
        if (this.V != null) {
            ++n4;
        }
        if (this.o != null) {
            ++n4;
        }
        if (this.p != null) {
            ++n4;
        }
        if (this.q != null) {
            n4 += this.q.a();
        }
        byteVector.putShort(n4);
        if (this.r.b > 0) {
            n = 12 + this.r.b + 8 * this.A;
            if (this.E != null) {
                n += 8 + this.E.b;
            }
            if (this.G != null) {
                n += 8 + this.G.b;
            }
            if (this.I != null) {
                n += 8 + this.I.b;
            }
            if (this.v != null) {
                n += 8 + this.v.b;
            }
            if (this.W != null) {
                n += 8 + this.W.a();
            }
            if (this.X != null) {
                n += 8 + this.X.a();
            }
            if (this.J != null) {
                n += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
            byteVector.putShort(this.b.newUTF8("Code")).putInt(n);
            byteVector.putShort(this.s).putShort(this.t);
            byteVector.putInt(this.r.b).putByteArray(this.r.a, 0, this.r.b);
            byteVector.putShort(this.A);
            if (this.A > 0) {
                Handler handler = this.B;
                while (handler != null) {
                    byteVector.putShort(handler.a.c).putShort(handler.b.c).putShort(handler.c.c).putShort(handler.e);
                    handler = handler.f;
                }
            }
            n4 = 0;
            if (this.E != null) {
                ++n4;
            }
            if (this.G != null) {
                ++n4;
            }
            if (this.I != null) {
                ++n4;
            }
            if (this.v != null) {
                ++n4;
            }
            if (this.W != null) {
                ++n4;
            }
            if (this.X != null) {
                ++n4;
            }
            if (this.J != null) {
                n4 += this.J.a();
            }
            byteVector.putShort(n4);
            if (this.E != null) {
                byteVector.putShort(this.b.newUTF8("LocalVariableTable"));
                byteVector.putInt(this.E.b + 2).putShort(this.D);
                byteVector.putByteArray(this.E.a, 0, this.E.b);
            }
            if (this.G != null) {
                byteVector.putShort(this.b.newUTF8("LocalVariableTypeTable"));
                byteVector.putInt(this.G.b + 2).putShort(this.F);
                byteVector.putByteArray(this.G.a, 0, this.G.b);
            }
            if (this.I != null) {
                byteVector.putShort(this.b.newUTF8("LineNumberTable"));
                byteVector.putInt(this.I.b + 2).putShort(this.H);
                byteVector.putByteArray(this.I.a, 0, this.I.b);
            }
            if (this.v != null) {
                boolean bl = (this.b.b & 0xFFFF) >= 50;
                byteVector.putShort(this.b.newUTF8(bl ? "StackMapTable" : "StackMap"));
                byteVector.putInt(this.v.b + 2).putShort(this.u);
                byteVector.putByteArray(this.v.a, 0, this.v.b);
            }
            if (this.W != null) {
                byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.W.a(byteVector);
            }
            if (this.X != null) {
                byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.X.a(byteVector);
            }
            if (this.J != null) {
                this.J.a(this.b, this.r.a, this.r.b, this.t, this.s, byteVector);
            }
        }
        if (this.j > 0) {
            byteVector.putShort(this.b.newUTF8("Exceptions")).putInt(2 * this.j + 2);
            byteVector.putShort(this.j);
            for (n = 0; n < this.j; ++n) {
                byteVector.putShort(this.k[n]);
            }
        }
        if ((this.c & 0x1000) != 0 && ((this.b.b & 0xFFFF) < 49 || (this.c & 0x40000) != 0)) {
            byteVector.putShort(this.b.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.c & 0x20000) != 0) {
            byteVector.putShort(this.b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.g != null) {
            byteVector.putShort(this.b.newUTF8("Signature")).putInt(2).putShort(this.b.newUTF8(this.g));
        }
        if (this.$ != null) {
            byteVector.putShort(this.b.newUTF8("MethodParameters"));
            byteVector.putInt(this.$.b + 1).putByte(this.Z);
            byteVector.putByteArray(this.$.a, 0, this.$.b);
        }
        if (this.l != null) {
            byteVector.putShort(this.b.newUTF8("AnnotationDefault"));
            byteVector.putInt(this.l.b);
            byteVector.putByteArray(this.l.a, 0, this.l.b);
        }
        if (this.m != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.m.a(byteVector);
        }
        if (this.n != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.n.a(byteVector);
        }
        if (this.U != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.U.a(byteVector);
        }
        if (this.V != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.V.a(byteVector);
        }
        if (this.o != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.a(this.o, this.R, byteVector);
        }
        if (this.p != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.a(this.p, this.R, byteVector);
        }
        if (this.q != null) {
            this.q.a(this.b, null, 0, -1, -1, byteVector);
        }
    }
}

