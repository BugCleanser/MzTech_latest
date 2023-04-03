/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;

public class InsnNode
extends AbstractInsnNode {
    public InsnNode(int n) {
        super(n);
    }

    public int getType() {
        return 0;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(this.opcode);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new InsnNode(this.opcode).cloneAnnotations(this);
    }
}

