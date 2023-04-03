/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;

public class LdcInsnNode
extends AbstractInsnNode {
    public Object cst;

    public LdcInsnNode(Object object) {
        super(18);
        this.cst = object;
    }

    public int getType() {
        return 9;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(this.cst);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new LdcInsnNode(this.cst).cloneAnnotations(this);
    }
}

