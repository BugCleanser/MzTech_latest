/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import org.objectweb.asm.tree.MethodNode;

class MethodNode$1
extends ArrayList {
    final /* synthetic */ MethodNode this$0;

    MethodNode$1(MethodNode methodNode, int n) {
        this.this$0 = methodNode;
        super(n);
    }

    public boolean add(Object object) {
        this.this$0.annotationDefault = object;
        return super.add(object);
    }
}

