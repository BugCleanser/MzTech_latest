/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import java.util.Comparator;
import org.objectweb.asm.commons.TryCatchBlockSorter;
import org.objectweb.asm.tree.TryCatchBlockNode;

class TryCatchBlockSorter$1
implements Comparator {
    final /* synthetic */ TryCatchBlockSorter this$0;

    TryCatchBlockSorter$1(TryCatchBlockSorter tryCatchBlockSorter) {
        this.this$0 = tryCatchBlockSorter;
    }

    public int compare(TryCatchBlockNode tryCatchBlockNode, TryCatchBlockNode tryCatchBlockNode2) {
        int n = this.blockLength(tryCatchBlockNode);
        int n2 = this.blockLength(tryCatchBlockNode2);
        return n - n2;
    }

    private int blockLength(TryCatchBlockNode tryCatchBlockNode) {
        int n = this.this$0.instructions.indexOf(tryCatchBlockNode.start);
        int n2 = this.this$0.instructions.indexOf(tryCatchBlockNode.end);
        return n2 - n;
    }
}

