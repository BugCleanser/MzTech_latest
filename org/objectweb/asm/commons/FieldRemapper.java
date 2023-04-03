/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.AnnotationRemapper;
import org.objectweb.asm.commons.Remapper;

public class FieldRemapper
extends FieldVisitor {
    private final Remapper remapper;

    public FieldRemapper(FieldVisitor fieldVisitor, Remapper remapper) {
        this(327680, fieldVisitor, remapper);
    }

    protected FieldRemapper(int n, FieldVisitor fieldVisitor, Remapper remapper) {
        super(n, fieldVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        AnnotationVisitor annotationVisitor = this.fv.visitAnnotation(this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? null : new AnnotationRemapper(annotationVisitor, this.remapper);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(n, typePath, this.remapper.mapDesc(string), bl);
        return annotationVisitor == null ? null : new AnnotationRemapper(annotationVisitor, this.remapper);
    }
}

