/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.xml.SAXAdapter;
import org.objectweb.asm.xml.SAXAnnotationAdapter;
import org.objectweb.asm.xml.SAXClassAdapter;
import org.xml.sax.helpers.AttributesImpl;

public final class SAXCodeAdapter
extends MethodVisitor {
    static final String[] TYPES;
    SAXAdapter sa;
    int access;
    private final Map labelNames;

    public SAXCodeAdapter(SAXAdapter sAXAdapter, int n) {
        super(327680);
        this.sa = sAXAdapter;
        this.access = n;
        this.labelNames = new HashMap();
    }

    public void visitParameter(String string, int n) {
        AttributesImpl attributesImpl = new AttributesImpl();
        if (string != null) {
            attributesImpl.addAttribute("", "name", "name", "", string);
        }
        StringBuffer stringBuffer = new StringBuffer();
        SAXClassAdapter.appendAccess(n, stringBuffer);
        attributesImpl.addAttribute("", "access", "access", "", stringBuffer.toString());
        this.sa.addElement("parameter", attributesImpl);
    }

    public final void visitCode() {
        if ((this.access & 0x700) == 0) {
            this.sa.addStart("code", new AttributesImpl());
        }
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        switch (n) {
            case -1: 
            case 0: {
                if (n == -1) {
                    attributesImpl.addAttribute("", "type", "type", "", "NEW");
                } else {
                    attributesImpl.addAttribute("", "type", "type", "", "FULL");
                }
                this.sa.addStart("frame", attributesImpl);
                this.appendFrameTypes(true, n2, objectArray);
                this.appendFrameTypes(false, n3, objectArray2);
                break;
            }
            case 1: {
                attributesImpl.addAttribute("", "type", "type", "", "APPEND");
                this.sa.addStart("frame", attributesImpl);
                this.appendFrameTypes(true, n2, objectArray);
                break;
            }
            case 2: {
                attributesImpl.addAttribute("", "type", "type", "", "CHOP");
                attributesImpl.addAttribute("", "count", "count", "", Integer.toString(n2));
                this.sa.addStart("frame", attributesImpl);
                break;
            }
            case 3: {
                attributesImpl.addAttribute("", "type", "type", "", "SAME");
                this.sa.addStart("frame", attributesImpl);
                break;
            }
            case 4: {
                attributesImpl.addAttribute("", "type", "type", "", "SAME1");
                this.sa.addStart("frame", attributesImpl);
                this.appendFrameTypes(false, 1, objectArray2);
            }
        }
        this.sa.addEnd("frame");
    }

    private void appendFrameTypes(boolean bl, int n, Object[] objectArray) {
        for (int i = 0; i < n; ++i) {
            Object object = objectArray[i];
            AttributesImpl attributesImpl = new AttributesImpl();
            if (object instanceof String) {
                attributesImpl.addAttribute("", "type", "type", "", (String)object);
            } else if (object instanceof Integer) {
                attributesImpl.addAttribute("", "type", "type", "", TYPES[(Integer)object]);
            } else {
                attributesImpl.addAttribute("", "type", "type", "", "uninitialized");
                attributesImpl.addAttribute("", "label", "label", "", this.getLabel((Label)object));
            }
            this.sa.addElement(bl ? "local" : "stack", attributesImpl);
        }
    }

    public final void visitInsn(int n) {
        this.sa.addElement(Printer.OPCODES[n], new AttributesImpl());
    }

    public final void visitIntInsn(int n, int n2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "value", "value", "", Integer.toString(n2));
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public final void visitVarInsn(int n, int n2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "var", "var", "", Integer.toString(n2));
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public final void visitTypeInsn(int n, String string) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "desc", "desc", "", string);
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public final void visitFieldInsn(int n, String string, String string2, String string3) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "owner", "owner", "", string);
        attributesImpl.addAttribute("", "name", "name", "", string2);
        attributesImpl.addAttribute("", "desc", "desc", "", string3);
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public final void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "owner", "owner", "", string);
        attributesImpl.addAttribute("", "name", "name", "", string2);
        attributesImpl.addAttribute("", "desc", "desc", "", string3);
        attributesImpl.addAttribute("", "itf", "itf", "", bl ? "true" : "false");
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "name", "name", "", string);
        attributesImpl.addAttribute("", "desc", "desc", "", string2);
        attributesImpl.addAttribute("", "bsm", "bsm", "", SAXClassAdapter.encode(handle.toString()));
        this.sa.addStart("INVOKEDYNAMIC", attributesImpl);
        for (int i = 0; i < objectArray.length; ++i) {
            this.sa.addElement("bsmArg", SAXCodeAdapter.getConstantAttribute(objectArray[i]));
        }
        this.sa.addEnd("INVOKEDYNAMIC");
    }

    public final void visitJumpInsn(int n, Label label) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "label", "label", "", this.getLabel(label));
        this.sa.addElement(Printer.OPCODES[n], attributesImpl);
    }

    public final void visitLabel(Label label) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "name", "name", "", this.getLabel(label));
        this.sa.addElement("Label", attributesImpl);
    }

    public final void visitLdcInsn(Object object) {
        this.sa.addElement(Printer.OPCODES[18], SAXCodeAdapter.getConstantAttribute(object));
    }

    private static AttributesImpl getConstantAttribute(Object object) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "cst", "cst", "", SAXClassAdapter.encode(object.toString()));
        attributesImpl.addAttribute("", "desc", "desc", "", Type.getDescriptor(object.getClass()));
        return attributesImpl;
    }

    public final void visitIincInsn(int n, int n2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "var", "var", "", Integer.toString(n));
        attributesImpl.addAttribute("", "inc", "inc", "", Integer.toString(n2));
        this.sa.addElement(Printer.OPCODES[132], attributesImpl);
    }

    public final void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "min", "min", "", Integer.toString(n));
        attributesImpl.addAttribute("", "max", "max", "", Integer.toString(n2));
        attributesImpl.addAttribute("", "dflt", "dflt", "", this.getLabel(label));
        String string = Printer.OPCODES[170];
        this.sa.addStart(string, attributesImpl);
        for (int i = 0; i < labelArray.length; ++i) {
            AttributesImpl attributesImpl2 = new AttributesImpl();
            attributesImpl2.addAttribute("", "name", "name", "", this.getLabel(labelArray[i]));
            this.sa.addElement("label", attributesImpl2);
        }
        this.sa.addEnd(string);
    }

    public final void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "dflt", "dflt", "", this.getLabel(label));
        String string = Printer.OPCODES[171];
        this.sa.addStart(string, attributesImpl);
        for (int i = 0; i < labelArray.length; ++i) {
            AttributesImpl attributesImpl2 = new AttributesImpl();
            attributesImpl2.addAttribute("", "name", "name", "", this.getLabel(labelArray[i]));
            attributesImpl2.addAttribute("", "key", "key", "", Integer.toString(nArray[i]));
            this.sa.addElement("label", attributesImpl2);
        }
        this.sa.addEnd(string);
    }

    public final void visitMultiANewArrayInsn(String string, int n) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "desc", "desc", "", string);
        attributesImpl.addAttribute("", "dims", "dims", "", Integer.toString(n));
        this.sa.addElement(Printer.OPCODES[197], attributesImpl);
    }

    public final void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "start", "start", "", this.getLabel(label));
        attributesImpl.addAttribute("", "end", "end", "", this.getLabel(label2));
        attributesImpl.addAttribute("", "handler", "handler", "", this.getLabel(label3));
        if (string != null) {
            attributesImpl.addAttribute("", "type", "type", "", string);
        }
        this.sa.addElement("TryCatch", attributesImpl);
    }

    public final void visitMaxs(int n, int n2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "maxStack", "maxStack", "", Integer.toString(n));
        attributesImpl.addAttribute("", "maxLocals", "maxLocals", "", Integer.toString(n2));
        this.sa.addElement("Max", attributesImpl);
        this.sa.addEnd("code");
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "name", "name", "", string);
        attributesImpl.addAttribute("", "desc", "desc", "", string2);
        if (string3 != null) {
            attributesImpl.addAttribute("", "signature", "signature", "", SAXClassAdapter.encode(string3));
        }
        attributesImpl.addAttribute("", "start", "start", "", this.getLabel(label));
        attributesImpl.addAttribute("", "end", "end", "", this.getLabel(label2));
        attributesImpl.addAttribute("", "var", "var", "", Integer.toString(n));
        this.sa.addElement("LocalVar", attributesImpl);
    }

    public final void visitLineNumber(int n, Label label) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "line", "line", "", Integer.toString(n));
        attributesImpl.addAttribute("", "start", "start", "", this.getLabel(label));
        this.sa.addElement("LineNumber", attributesImpl);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        return new SAXAnnotationAdapter(this.sa, "annotationDefault", 0, null, null);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "annotation", bl ? 1 : -1, null, string);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "typeAnnotation", bl ? 1 : -1, null, string, n, typePath);
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "parameterAnnotation", bl ? 1 : -1, n, string);
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "insnAnnotation", bl ? 1 : -1, null, string, n, typePath);
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "tryCatchAnnotation", bl ? 1 : -1, null, string, n, typePath);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        int n2;
        String[] stringArray = new String[labelArray.length];
        String[] stringArray2 = new String[labelArray2.length];
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            stringArray[n2] = this.getLabel(labelArray[n2]);
        }
        for (n2 = 0; n2 < stringArray2.length; ++n2) {
            stringArray2[n2] = this.getLabel(labelArray2[n2]);
        }
        return new SAXAnnotationAdapter(this.sa, "localVariableAnnotation", bl ? 1 : -1, null, string, n, typePath, stringArray, stringArray2, nArray);
    }

    public void visitEnd() {
        this.sa.addEnd("method");
    }

    private final String getLabel(Label label) {
        String string = (String)this.labelNames.get(label);
        if (string == null) {
            string = Integer.toString(this.labelNames.size());
            this.labelNames.put(label, string);
        }
        return string;
    }

    static {
        SAXCodeAdapter._clinit_();
        TYPES = new String[]{"top", "int", "float", "double", "long", "null", "uninitializedThis"};
    }

    static /* synthetic */ void _clinit_() {
    }
}

