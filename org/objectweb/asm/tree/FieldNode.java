/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

public class FieldNode
extends FieldVisitor {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public Object value;
    public List visibleAnnotations;
    public List invisibleAnnotations;
    public List visibleTypeAnnotations;
    public List invisibleTypeAnnotations;
    public List attrs;
    static /* synthetic */ Class class$org$objectweb$asm$tree$FieldNode;

    public FieldNode(int n, String string, String string2, String string3, Object object) {
        this(327680, n, string, string2, string3, object);
        if (this.getClass() != class$org$objectweb$asm$tree$FieldNode) {
            throw new IllegalStateException();
        }
    }

    public FieldNode(int n, int n2, String string, String string2, String string3, Object object) {
        super(n);
        this.access = n2;
        this.name = string;
        this.desc = string2;
        this.signature = string3;
        this.value = object;
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        AnnotationNode annotationNode = new AnnotationNode(string);
        if (bl) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList(1);
            }
            this.visibleAnnotations.add(annotationNode);
        } else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList(1);
            }
            this.invisibleAnnotations.add(annotationNode);
        }
        return annotationNode;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, string);
        if (bl) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList(1);
            }
            this.visibleTypeAnnotations.add(typeAnnotationNode);
        } else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList(1);
            }
            this.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add(attribute);
    }

    public void visitEnd() {
    }

    public void check(int n) {
        if (n == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }

    public void accept(ClassVisitor classVisitor) {
        AnnotationNode annotationNode;
        int n;
        FieldVisitor fieldVisitor = classVisitor.visitField(this.access, this.name, this.desc, this.signature, this.value);
        if (fieldVisitor == null) {
            return;
        }
        int n2 = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();
        for (n = 0; n < n2; ++n) {
            annotationNode = (AnnotationNode)this.visibleAnnotations.get(n);
            annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, true));
        }
        n2 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();
        for (n = 0; n < n2; ++n) {
            annotationNode = (AnnotationNode)this.invisibleAnnotations.get(n);
            annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, false));
        }
        n2 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();
        for (n = 0; n < n2; ++n) {
            annotationNode = (TypeAnnotationNode)this.visibleTypeAnnotations.get(n);
            annotationNode.accept(fieldVisitor.visitTypeAnnotation(((TypeAnnotationNode)annotationNode).typeRef, ((TypeAnnotationNode)annotationNode).typePath, ((TypeAnnotationNode)annotationNode).desc, true));
        }
        n2 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();
        for (n = 0; n < n2; ++n) {
            annotationNode = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(n);
            annotationNode.accept(fieldVisitor.visitTypeAnnotation(((TypeAnnotationNode)annotationNode).typeRef, ((TypeAnnotationNode)annotationNode).typePath, ((TypeAnnotationNode)annotationNode).desc, false));
        }
        n2 = this.attrs == null ? 0 : this.attrs.size();
        for (n = 0; n < n2; ++n) {
            fieldVisitor.visitAttribute((Attribute)this.attrs.get(n));
        }
        fieldVisitor.visitEnd();
    }

    static /* synthetic */ Class class$(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            String string2 = classNotFoundException.getMessage();
            throw new NoClassDefFoundError(string2);
        }
    }

    static {
        class$org$objectweb$asm$tree$FieldNode = FieldNode.class$("org.objectweb.asm.tree.FieldNode");
    }
}

