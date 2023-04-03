/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

public class JumpInsnNode
extends AbstractInsnNode {
    public LabelNode label;

    public JumpInsnNode(int n, LabelNode labelNode) {
        super(n);
        this.label = labelNode;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 7;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitJumpInsn(this.opcode, this.label.getLabel());
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new JumpInsnNode(this.opcode, JumpInsnNode.clone(this.label, map)).cloneAnnotations(this);
    }
}

