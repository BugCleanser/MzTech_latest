/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.xml.ASMContentHandler;
import org.objectweb.asm.xml.ASMContentHandler$Rule;
import org.xml.sax.Attributes;

final class ASMContentHandler$TryCatchAnnotationRule
extends ASMContentHandler$Rule {
    final /* synthetic */ ASMContentHandler this$0;

    ASMContentHandler$TryCatchAnnotationRule(ASMContentHandler aSMContentHandler) {
        this.this$0 = aSMContentHandler;
        super(aSMContentHandler);
    }

    public void begin(String string, Attributes attributes) {
        String string2 = attributes.getValue("desc");
        boolean bl = Boolean.valueOf(attributes.getValue("visible"));
        int n = Integer.parseInt(attributes.getValue("typeRef"));
        TypePath typePath = TypePath.fromString(attributes.getValue("typePath"));
        this.this$0.push(((MethodVisitor)this.this$0.peek()).visitTryCatchAnnotation(n, typePath, string2, bl));
    }

    public void end(String string) {
        AnnotationVisitor annotationVisitor = (AnnotationVisitor)this.this$0.pop();
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
    }
}

