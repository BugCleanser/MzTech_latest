/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

public class TryCatchBlockNode {
    public LabelNode start;
    public LabelNode end;
    public LabelNode handler;
    public String type;
    public List visibleTypeAnnotations;
    public List invisibleTypeAnnotations;

    public TryCatchBlockNode(LabelNode labelNode, LabelNode labelNode2, LabelNode labelNode3, String string) {
        this.start = labelNode;
        this.end = labelNode2;
        this.handler = labelNode3;
        this.type = string;
    }

    public void updateIndex(int n) {
        TypeAnnotationNode typeAnnotationNode;
        Iterator iterator;
        int n2 = 0x42000000 | n << 8;
        if (this.visibleTypeAnnotations != null) {
            iterator = this.visibleTypeAnnotations.iterator();
            while (iterator.hasNext()) {
                typeAnnotationNode = (TypeAnnotationNode)iterator.next();
                typeAnnotationNode.typeRef = n2;
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            iterator = this.invisibleTypeAnnotations.iterator();
            while (iterator.hasNext()) {
                typeAnnotationNode = (TypeAnnotationNode)iterator.next();
                typeAnnotationNode.typeRef = n2;
            }
        }
    }

    public void accept(MethodVisitor methodVisitor) {
        TypeAnnotationNode typeAnnotationNode;
        int n;
        methodVisitor.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), this.handler == null ? null : this.handler.getLabel(), this.type);
        int n2 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();
        for (n = 0; n < n2; ++n) {
            typeAnnotationNode = (TypeAnnotationNode)this.visibleTypeAnnotations.get(n);
            typeAnnotationNode.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
        }
        n2 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();
        for (n = 0; n < n2; ++n) {
            typeAnnotationNode = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(n);
            typeAnnotationNode.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
        }
    }
}

