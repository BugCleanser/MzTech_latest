/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

public class TableSwitchInsnNode
extends AbstractInsnNode {
    public int min;
    public int max;
    public LabelNode dflt;
    public List labels;

    public TableSwitchInsnNode(int n, int n2, LabelNode labelNode, LabelNode ... labelNodeArray) {
        super(170);
        this.min = n;
        this.max = n2;
        this.dflt = labelNode;
        this.labels = new ArrayList();
        if (labelNodeArray != null) {
            this.labels.addAll(Arrays.asList(labelNodeArray));
        }
    }

    public int getType() {
        return 11;
    }

    public void accept(MethodVisitor methodVisitor) {
        Label[] labelArray = new Label[this.labels.size()];
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
        }
        methodVisitor.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), labelArray);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new TableSwitchInsnNode(this.min, this.max, TableSwitchInsnNode.clone(this.dflt, map), TableSwitchInsnNode.clone(this.labels, map)).cloneAnnotations(this);
    }
}

