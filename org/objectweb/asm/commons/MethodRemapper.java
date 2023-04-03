/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AnnotationRemapper;
import org.objectweb.asm.commons.Remapper;

public class MethodRemapper
extends MethodVisitor {
    protected final Remapper remapper;

    public MethodRemapper(MethodVisitor methodVisitor, Remapper remapper) {
        this(327680, methodVisitor, remapper);
    }

    protected MethodRemapper(int n, MethodVisitor methodVisitor, Remapper remapper) {
        super(n, methodVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotationDefault() {
        AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(n, typePath, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(n, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        super.visitFrame(n, n2, this.remapEntries(n2, objectArray), n3, this.remapEntries(n3, objectArray2));
    }

    private Object[] remapEntries(int n, Object[] objectArray) {
        for (int i = 0; i < n; ++i) {
            if (!(objectArray[i] instanceof String)) continue;
            Object[] objectArray2 = new Object[n];
            if (i > 0) {
                System.arraycopy(objectArray, 0, objectArray2, 0, i);
            }
            do {
                Object object = objectArray[i];
                Object object2 = objectArray2[i++] = object instanceof String ? this.remapper.mapType((String)object) : object;
            } while (i < n);
            return objectArray2;
        }
        return objectArray;
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        super.visitFieldInsn(n, this.remapper.mapType(string), this.remapper.mapFieldName(string, string2, string3), this.remapper.mapDesc(string3));
    }

    public void visitMethodInsn(int n, String string, String string2, String string3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, string, string2, string3);
            return;
        }
        this.doVisitMethodInsn(n, string, string2, string3, n == 185);
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, string, string2, string3, bl);
            return;
        }
        this.doVisitMethodInsn(n, string, string2, string3, bl);
    }

    private void doVisitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, this.remapper.mapType(string), this.remapper.mapMethodName(string, string2, string3), this.remapper.mapMethodDesc(string3), bl);
        }
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        for (int i = 0; i < objectArray.length; ++i) {
            objectArray[i] = this.remapper.mapValue(objectArray[i]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(string, string2), this.remapper.mapMethodDesc(string2), (Handle)this.remapper.mapValue(handle), objectArray);
    }

    public void visitTypeInsn(int n, String string) {
        super.visitTypeInsn(n, this.remapper.mapType(string));
    }

    public void visitLdcInsn(Object object) {
        super.visitLdcInsn(this.remapper.mapValue(object));
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(string), n);
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(n, typePath, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        super.visitTryCatchBlock(label, label2, label3, string == null ? null : this.remapper.mapType(string));
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(n, typePath, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        super.visitLocalVariable(string, this.remapper.mapDesc(string2), this.remapper.mapSignature(string3, true), label, label2, n);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? annotationVisitor : new AnnotationRemapper(annotationVisitor, this.remapper);
    }
}

