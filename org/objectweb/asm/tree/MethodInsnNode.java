/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;

public class MethodInsnNode
extends AbstractInsnNode {
    public String owner;
    public String name;
    public String desc;
    public boolean itf;

    public MethodInsnNode(int n, String string, String string2, String string3) {
        this(n, string, string2, string3, n == 185);
    }

    public MethodInsnNode(int n, String string, String string2, String string3, boolean bl) {
        super(n);
        this.owner = string;
        this.name = string2;
        this.desc = string3;
        this.itf = bl;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 5;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf);
    }
}

