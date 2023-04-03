/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.xml.SAXAdapter;
import org.objectweb.asm.xml.SAXAnnotationAdapter;
import org.objectweb.asm.xml.SAXCodeAdapter;
import org.objectweb.asm.xml.SAXFieldAdapter;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

public final class SAXClassAdapter
extends ClassVisitor {
    SAXAdapter sa;
    private final boolean singleDocument;

    public SAXClassAdapter(ContentHandler contentHandler, boolean bl) {
        super(327680);
        this.sa = new SAXAdapter(contentHandler);
        this.singleDocument = bl;
        if (!bl) {
            this.sa.addDocumentStart();
        }
    }

    public void visitSource(String string, String string2) {
        AttributesImpl attributesImpl = new AttributesImpl();
        if (string != null) {
            attributesImpl.addAttribute("", "file", "file", "", SAXClassAdapter.encode(string));
        }
        if (string2 != null) {
            attributesImpl.addAttribute("", "debug", "debug", "", SAXClassAdapter.encode(string2));
        }
        this.sa.addElement("source", attributesImpl);
    }

    public void visitOuterClass(String string, String string2, String string3) {
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "owner", "owner", "", string);
        if (string2 != null) {
            attributesImpl.addAttribute("", "name", "name", "", string2);
        }
        if (string3 != null) {
            attributesImpl.addAttribute("", "desc", "desc", "", string3);
        }
        this.sa.addElement("outerclass", attributesImpl);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "annotation", bl ? 1 : -1, null, string);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return new SAXAnnotationAdapter(this.sa, "typeAnnotation", bl ? 1 : -1, null, string, n, typePath);
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        StringBuffer stringBuffer = new StringBuffer();
        SAXClassAdapter.appendAccess(n2 | 0x40000, stringBuffer);
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "access", "access", "", stringBuffer.toString());
        if (string != null) {
            attributesImpl.addAttribute("", "name", "name", "", string);
        }
        if (string2 != null) {
            attributesImpl.addAttribute("", "signature", "signature", "", SAXClassAdapter.encode(string2));
        }
        if (string3 != null) {
            attributesImpl.addAttribute("", "parent", "parent", "", string3);
        }
        attributesImpl.addAttribute("", "major", "major", "", Integer.toString(n & 0xFFFF));
        attributesImpl.addAttribute("", "minor", "minor", "", Integer.toString(n >>> 16));
        this.sa.addStart("class", attributesImpl);
        this.sa.addStart("interfaces", new AttributesImpl());
        if (stringArray != null && stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; ++i) {
                AttributesImpl attributesImpl2 = new AttributesImpl();
                attributesImpl2.addAttribute("", "name", "name", "", stringArray[i]);
                this.sa.addElement("interface", attributesImpl2);
            }
        }
        this.sa.addEnd("interfaces");
    }

    public FieldVisitor visitField(int n, String string, String string2, String string3, Object object) {
        StringBuffer stringBuffer = new StringBuffer();
        SAXClassAdapter.appendAccess(n | 0x80000, stringBuffer);
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "access", "access", "", stringBuffer.toString());
        attributesImpl.addAttribute("", "name", "name", "", string);
        attributesImpl.addAttribute("", "desc", "desc", "", string2);
        if (string3 != null) {
            attributesImpl.addAttribute("", "signature", "signature", "", SAXClassAdapter.encode(string3));
        }
        if (object != null) {
            attributesImpl.addAttribute("", "value", "value", "", SAXClassAdapter.encode(object.toString()));
        }
        return new SAXFieldAdapter(this.sa, attributesImpl);
    }

    public MethodVisitor visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        StringBuffer stringBuffer = new StringBuffer();
        SAXClassAdapter.appendAccess(n, stringBuffer);
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "access", "access", "", stringBuffer.toString());
        attributesImpl.addAttribute("", "name", "name", "", string);
        attributesImpl.addAttribute("", "desc", "desc", "", string2);
        if (string3 != null) {
            attributesImpl.addAttribute("", "signature", "signature", "", string3);
        }
        this.sa.addStart("method", attributesImpl);
        this.sa.addStart("exceptions", new AttributesImpl());
        if (stringArray != null && stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; ++i) {
                AttributesImpl attributesImpl2 = new AttributesImpl();
                attributesImpl2.addAttribute("", "name", "name", "", stringArray[i]);
                this.sa.addElement("exception", attributesImpl2);
            }
        }
        this.sa.addEnd("exceptions");
        return new SAXCodeAdapter(this.sa, n);
    }

    public final void visitInnerClass(String string, String string2, String string3, int n) {
        StringBuffer stringBuffer = new StringBuffer();
        SAXClassAdapter.appendAccess(n | 0x100000, stringBuffer);
        AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "access", "access", "", stringBuffer.toString());
        if (string != null) {
            attributesImpl.addAttribute("", "name", "name", "", string);
        }
        if (string2 != null) {
            attributesImpl.addAttribute("", "outerName", "outerName", "", string2);
        }
        if (string3 != null) {
            attributesImpl.addAttribute("", "innerName", "innerName", "", string3);
        }
        this.sa.addElement("innerclass", attributesImpl);
    }

    public final void visitEnd() {
        this.sa.addEnd("class");
        if (!this.singleDocument) {
            this.sa.addDocumentEnd();
        }
    }

    static final String encode(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
                stringBuffer.append("\\\\");
                continue;
            }
            if (c < ' ' || c > '\u007f') {
                stringBuffer.append("\\u");
                if (c < '\u0010') {
                    stringBuffer.append("000");
                } else if (c < '\u0100') {
                    stringBuffer.append("00");
                } else if (c < '\u1000') {
                    stringBuffer.append('0');
                }
                stringBuffer.append(Integer.toString(c, 16));
                continue;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    static void appendAccess(int n, StringBuffer stringBuffer) {
        if ((n & 1) != 0) {
            stringBuffer.append("public ");
        }
        if ((n & 2) != 0) {
            stringBuffer.append("private ");
        }
        if ((n & 4) != 0) {
            stringBuffer.append("protected ");
        }
        if ((n & 0x10) != 0) {
            stringBuffer.append("final ");
        }
        if ((n & 8) != 0) {
            stringBuffer.append("static ");
        }
        if ((n & 0x20) != 0) {
            if ((n & 0x40000) == 0) {
                stringBuffer.append("synchronized ");
            } else {
                stringBuffer.append("super ");
            }
        }
        if ((n & 0x40) != 0) {
            if ((n & 0x80000) == 0) {
                stringBuffer.append("bridge ");
            } else {
                stringBuffer.append("volatile ");
            }
        }
        if ((n & 0x80) != 0) {
            if ((n & 0x80000) == 0) {
                stringBuffer.append("varargs ");
            } else {
                stringBuffer.append("transient ");
            }
        }
        if ((n & 0x100) != 0) {
            stringBuffer.append("native ");
        }
        if ((n & 0x800) != 0) {
            stringBuffer.append("strict ");
        }
        if ((n & 0x200) != 0) {
            stringBuffer.append("interface ");
        }
        if ((n & 0x400) != 0) {
            stringBuffer.append("abstract ");
        }
        if ((n & 0x1000) != 0) {
            stringBuffer.append("synthetic ");
        }
        if ((n & 0x2000) != 0) {
            stringBuffer.append("annotation ");
        }
        if ((n & 0x4000) != 0) {
            stringBuffer.append("enum ");
        }
        if ((n & 0x20000) != 0) {
            stringBuffer.append("deprecated ");
        }
        if ((n & 0x8000) != 0) {
            stringBuffer.append("mandated ");
        }
    }
}

