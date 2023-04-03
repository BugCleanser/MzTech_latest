/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import org.objectweb.asm.MethodVisitor;

public class ParameterNode {
    public String name;
    public int access;

    public ParameterNode(String string, int n) {
        this.name = string;
        this.access = n;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitParameter(this.name, this.access);
    }
}

