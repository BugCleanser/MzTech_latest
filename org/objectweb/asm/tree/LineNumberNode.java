/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

public class LineNumberNode
extends AbstractInsnNode {
    public int line;
    public LabelNode start;

    public LineNumberNode(int n, LabelNode labelNode) {
        super(-1);
        this.line = n;
        this.start = labelNode;
    }

    public int getType() {
        return 15;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLineNumber(this.line, this.start.getLabel());
    }

    public AbstractInsnNode clone(Map map) {
        return new LineNumberNode(this.line, LineNumberNode.clone(this.start, map));
    }
}

