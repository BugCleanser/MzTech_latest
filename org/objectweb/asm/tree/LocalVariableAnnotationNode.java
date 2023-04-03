/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

public class LocalVariableAnnotationNode
extends TypeAnnotationNode {
    public List start;
    public List end;
    public List index;

    public LocalVariableAnnotationNode(int n, TypePath typePath, LabelNode[] labelNodeArray, LabelNode[] labelNodeArray2, int[] nArray, String string) {
        this(327680, n, typePath, labelNodeArray, labelNodeArray2, nArray, string);
    }

    public LocalVariableAnnotationNode(int n, int n2, TypePath typePath, LabelNode[] labelNodeArray, LabelNode[] labelNodeArray2, int[] nArray, String string) {
        super(n, n2, typePath, string);
        this.start = new ArrayList(labelNodeArray.length);
        this.start.addAll(Arrays.asList(labelNodeArray));
        this.end = new ArrayList(labelNodeArray2.length);
        this.end.addAll(Arrays.asList(labelNodeArray2));
        this.index = new ArrayList(nArray.length);
        int[] nArray2 = nArray;
        int n3 = nArray2.length;
        for (int i = 0; i < n3; ++i) {
            int n4 = nArray2[i];
            this.index.add(new Integer(n4));
        }
    }

    public void accept(MethodVisitor methodVisitor, boolean bl) {
        Label[] labelArray = new Label[this.start.size()];
        Label[] labelArray2 = new Label[this.end.size()];
        int[] nArray = new int[this.index.size()];
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i] = ((LabelNode)this.start.get(i)).getLabel();
            labelArray2[i] = ((LabelNode)this.end.get(i)).getLabel();
            nArray[i] = (Integer)this.index.get(i);
        }
        this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, labelArray, labelArray2, nArray, this.desc, true));
    }
}

