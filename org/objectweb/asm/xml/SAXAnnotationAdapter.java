/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.xml.SAXAdapter;
import org.objectweb.asm.xml.SAXClassAdapter;
import org.xml.sax.helpers.AttributesImpl;

public final class SAXAnnotationAdapter
extends AnnotationVisitor {
    SAXAdapter sa;
    private final String elementName;

    public SAXAnnotationAdapter(SAXAdapter sAXAdapter, String string, int n, String string2, String string3) {
        this(327680, sAXAdapter, string, n, string3, string2, -1, -1, null, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sAXAdapter, String string, int n, int n2, String string2) {
        this(327680, sAXAdapter, string, n, string2, null, n2, -1, null, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sAXAdapter, String string, int n, String string2, String string3, int n2, TypePath typePath) {
        this(327680, sAXAdapter, string, n, string3, string2, -1, n2, typePath, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sAXAdapter, String string, int n, String string2, String string3, int n2, TypePath typePath, String[] stringArray, String[] stringArray2, int[] nArray) {
        this(327680, sAXAdapter, string, n, string3, string2, -1, n2, typePath, stringArray, stringArray2, nArray);
    }

    protected SAXAnnotationAdapter(int n, SAXAdapter sAXAdapter, String string, int n2, String string2, String string3, int n3) {
        this(n, sAXAdapter, string, n2, string2, string3, n3, -1, null, null, null, null);
    }

    protected SAXAnnotationAdapter(int n, SAXAdapter sAXAdapter, String string, int n2, String string2, String string3, int n3, int n4, TypePath typePath, String[] stringArray, String[] stringArray2, int[] nArray) {
        super(n);
        int n5;
        StringBuffer stringBuffer;
        this.sa = sAXAdapter;
        this.elementName = string;
        AttributesImpl attributesImpl = new AttributesImpl();
        if (string3 != null) {
            attributesImpl.addAttribute("", "name", "name", "", string3);
        }
        if (n2 != 0) {
            attributesImpl.addAttribute("", "visible", "visible", "", n2 > 0 ? "true" : "false");
        }
        if (n3 != -1) {
            attributesImpl.addAttribute("", "parameter", "parameter", "", Integer.toString(n3));
        }
        if (string2 != null) {
            attributesImpl.addAttribute("", "desc", "desc", "", string2);
        }
        if (n4 != -1) {
            attributesImpl.addAttribute("", "typeRef", "typeRef", "", Integer.toString(n4));
        }
        if (typePath != null) {
            attributesImpl.addAttribute("", "typePath", "typePath", "", typePath.toString());
        }
        if (stringArray != null) {
            stringBuffer = new StringBuffer(stringArray[0]);
            for (n5 = 1; n5 < stringArray.length; ++n5) {
                stringBuffer.append(" ").append(stringArray[n5]);
            }
            attributesImpl.addAttribute("", "start", "start", "", stringBuffer.toString());
        }
        if (stringArray2 != null) {
            stringBuffer = new StringBuffer(stringArray2[0]);
            for (n5 = 1; n5 < stringArray2.length; ++n5) {
                stringBuffer.append(" ").append(stringArray2[n5]);
            }
            attributesImpl.addAttribute("", "end", "end", "", stringBuffer.toString());
        }
        if (nArray != null) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(nArray[0]);
            for (n5 = 1; n5 < nArray.length; ++n5) {
                stringBuffer.append(" ").append(nArray[n5]);
            }
            attributesImpl.addAttribute("", "index", "index", "", stringBuffer.toString());
        }
        sAXAdapter.addStart(string, attributesImpl);
    }

    public void visit(String string, Object object) {
        Class<?> clazz = object.getClass();
        if (clazz.isArray()) {
            AnnotationVisitor annotationVisitor = this.visitArray(string);
            if (object instanceof byte[]) {
                byte[] byArray = (byte[])object;
                for (int i = 0; i < byArray.length; ++i) {
                    annotationVisitor.visit(null, new Byte(byArray[i]));
                }
            } else if (object instanceof char[]) {
                char[] cArray = (char[])object;
                for (int i = 0; i < cArray.length; ++i) {
                    annotationVisitor.visit(null, new Character(cArray[i]));
                }
            } else if (object instanceof short[]) {
                short[] sArray = (short[])object;
                for (int i = 0; i < sArray.length; ++i) {
                    annotationVisitor.visit(null, new Short(sArray[i]));
                }
            } else if (object instanceof boolean[]) {
                boolean[] blArray = (boolean[])object;
                for (int i = 0; i < blArray.length; ++i) {
                    annotationVisitor.visit(null, blArray[i]);
                }
            } else if (object instanceof int[]) {
                int[] nArray = (int[])object;
                for (int i = 0; i < nArray.length; ++i) {
                    annotationVisitor.visit(null, new Integer(nArray[i]));
                }
            } else if (object instanceof long[]) {
                long[] lArray = (long[])object;
                for (int i = 0; i < lArray.length; ++i) {
                    annotationVisitor.visit(null, new Long(lArray[i]));
                }
            } else if (object instanceof float[]) {
                float[] fArray = (float[])object;
                for (int i = 0; i < fArray.length; ++i) {
                    annotationVisitor.visit(null, new Float(fArray[i]));
                }
            } else if (object instanceof double[]) {
                double[] dArray = (double[])object;
                for (int i = 0; i < dArray.length; ++i) {
                    annotationVisitor.visit(null, new Double(dArray[i]));
                }
            }
            annotationVisitor.visitEnd();
        } else {
            this.addValueElement("annotationValue", string, Type.getDescriptor(clazz), object.toString());
        }
    }

    public void visitEnum(String string, String string2, String string3) {
        this.addValueElement("annotationValueEnum", string, string2, string3);
    }

    public AnnotationVisitor visitAnnotation(String string, String string2) {
        return new SAXAnnotationAdapter(this.sa, "annotationValueAnnotation", 0, string, string2);
    }

    public AnnotationVisitor visitArray(String string) {
        return new SAXAnnotationAdapter(this.sa, "annotationValueArray", 0, string, null);
    }

    public void visitEnd() {
        this.sa.addEnd(this.elementName);
    }

    private void addValueElement(String string, String string2, String string3, String string4) {
        AttributesImpl attributesImpl = new AttributesImpl();
        if (string2 != null) {
            attributesImpl.addAttribute("", "name", "name", "", string2);
        }
        if (string3 != null) {
            attributesImpl.addAttribute("", "desc", "desc", "", string3);
        }
        if (string4 != null) {
            attributesImpl.addAttribute("", "value", "value", "", SAXClassAdapter.encode(string4));
        }
        this.sa.addElement(string, attributesImpl);
    }
}

