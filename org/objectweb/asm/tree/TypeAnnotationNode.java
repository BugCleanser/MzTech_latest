/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AnnotationNode;

public class TypeAnnotationNode
extends AnnotationNode {
    public int typeRef;
    public TypePath typePath;
    static /* synthetic */ Class class$org$objectweb$asm$tree$TypeAnnotationNode;

    public TypeAnnotationNode(int n, TypePath typePath, String string) {
        this(327680, n, typePath, string);
        if (this.getClass() != class$org$objectweb$asm$tree$TypeAnnotationNode) {
            throw new IllegalStateException();
        }
    }

    public TypeAnnotationNode(int n, int n2, TypePath typePath, String string) {
        super(n, string);
        this.typeRef = n2;
        this.typePath = typePath;
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
        class$org$objectweb$asm$tree$TypeAnnotationNode = TypeAnnotationNode.class$("org.objectweb.asm.tree.TypeAnnotationNode");
    }
}

