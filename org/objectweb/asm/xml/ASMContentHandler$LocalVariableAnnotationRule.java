/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.xml.ASMContentHandler;
import org.objectweb.asm.xml.ASMContentHandler$Rule;
import org.xml.sax.Attributes;

final class ASMContentHandler$LocalVariableAnnotationRule
extends ASMContentHandler$Rule {
    final /* synthetic */ ASMContentHandler this$0;

    ASMContentHandler$LocalVariableAnnotationRule(ASMContentHandler aSMContentHandler) {
        this.this$0 = aSMContentHandler;
        super(aSMContentHandler);
    }

    public void begin(String string, Attributes attributes) {
        String string2 = attributes.getValue("desc");
        boolean bl = Boolean.valueOf(attributes.getValue("visible"));
        int n = Integer.parseInt(attributes.getValue("typeRef"));
        TypePath typePath = TypePath.fromString(attributes.getValue("typePath"));
        String[] stringArray = attributes.getValue("start").split(" ");
        Label[] labelArray = new Label[stringArray.length];
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i] = this.getLabel(stringArray[i]);
        }
        String[] stringArray2 = attributes.getValue("end").split(" ");
        Label[] labelArray2 = new Label[stringArray2.length];
        for (int i = 0; i < labelArray2.length; ++i) {
            labelArray2[i] = this.getLabel(stringArray2[i]);
        }
        String[] stringArray3 = attributes.getValue("index").split(" ");
        int[] nArray = new int[stringArray3.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = Integer.parseInt(stringArray3[i]);
        }
        this.this$0.push(((MethodVisitor)this.this$0.peek()).visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, string2, bl));
    }

    public void end(String string) {
        AnnotationVisitor annotationVisitor = (AnnotationVisitor)this.this$0.pop();
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
    }
}

