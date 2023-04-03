/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;

public class AnnotationNode
extends AnnotationVisitor {
    public String desc;
    public List values;
    static /* synthetic */ Class class$org$objectweb$asm$tree$AnnotationNode;

    public AnnotationNode(String string) {
        this(327680, string);
        if (this.getClass() != class$org$objectweb$asm$tree$AnnotationNode) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int n, String string) {
        super(n);
        this.desc = string;
    }

    AnnotationNode(List list) {
        super(327680);
        this.values = list;
    }

    public void visit(String string, Object object) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            ArrayList<Byte> arrayList = new ArrayList<Byte>(byArray.length);
            byte[] byArray2 = byArray;
            int n = byArray2.length;
            for (int i = 0; i < n; ++i) {
                byte by = byArray2[i];
                arrayList.add(new Byte(by));
            }
            this.values.add(arrayList);
        } else if (object instanceof boolean[]) {
            boolean[] blArray = (boolean[])object;
            ArrayList<Boolean> arrayList = new ArrayList<Boolean>(blArray.length);
            boolean[] blArray2 = blArray;
            int n = blArray2.length;
            for (int i = 0; i < n; ++i) {
                boolean bl = blArray2[i];
                arrayList.add(bl);
            }
            this.values.add(arrayList);
        } else if (object instanceof short[]) {
            short[] sArray = (short[])object;
            ArrayList<Short> arrayList = new ArrayList<Short>(sArray.length);
            short[] sArray2 = sArray;
            int n = sArray2.length;
            for (int i = 0; i < n; ++i) {
                short s = sArray2[i];
                arrayList.add(new Short(s));
            }
            this.values.add(arrayList);
        } else if (object instanceof char[]) {
            char[] cArray = (char[])object;
            ArrayList<Character> arrayList = new ArrayList<Character>(cArray.length);
            char[] cArray2 = cArray;
            int n = cArray2.length;
            for (int i = 0; i < n; ++i) {
                char c = cArray2[i];
                arrayList.add(new Character(c));
            }
            this.values.add(arrayList);
        } else if (object instanceof int[]) {
            int[] nArray = (int[])object;
            ArrayList<Integer> arrayList = new ArrayList<Integer>(nArray.length);
            int[] nArray2 = nArray;
            int n = nArray2.length;
            for (int i = 0; i < n; ++i) {
                int n2 = nArray2[i];
                arrayList.add(new Integer(n2));
            }
            this.values.add(arrayList);
        } else if (object instanceof long[]) {
            long[] lArray = (long[])object;
            ArrayList<Long> arrayList = new ArrayList<Long>(lArray.length);
            long[] lArray2 = lArray;
            int n = lArray2.length;
            for (int i = 0; i < n; ++i) {
                long l = lArray2[i];
                arrayList.add(new Long(l));
            }
            this.values.add(arrayList);
        } else if (object instanceof float[]) {
            float[] fArray = (float[])object;
            ArrayList<Float> arrayList = new ArrayList<Float>(fArray.length);
            float[] fArray2 = fArray;
            int n = fArray2.length;
            for (int i = 0; i < n; ++i) {
                float f = fArray2[i];
                arrayList.add(new Float(f));
            }
            this.values.add(arrayList);
        } else if (object instanceof double[]) {
            double[] dArray = (double[])object;
            ArrayList<Double> arrayList = new ArrayList<Double>(dArray.length);
            double[] dArray2 = dArray;
            int n = dArray2.length;
            for (int i = 0; i < n; ++i) {
                double d = dArray2[i];
                arrayList.add(new Double(d));
            }
            this.values.add(arrayList);
        } else {
            this.values.add(object);
        }
    }

    public void visitEnum(String string, String string2, String string3) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        this.values.add(new String[]{string2, string3});
    }

    public AnnotationVisitor visitAnnotation(String string, String string2) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        AnnotationNode annotationNode = new AnnotationNode(string2);
        this.values.add(annotationNode);
        return annotationNode;
    }

    public AnnotationVisitor visitArray(String string) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        ArrayList arrayList = new ArrayList();
        this.values.add(arrayList);
        return new AnnotationNode(arrayList);
    }

    public void visitEnd() {
    }

    public void check(int n) {
    }

    public void accept(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    String string = (String)this.values.get(i);
                    Object e = this.values.get(i + 1);
                    AnnotationNode.accept(annotationVisitor, string, e);
                }
            }
            annotationVisitor.visitEnd();
        }
    }

    static void accept(AnnotationVisitor annotationVisitor, String string, Object object) {
        if (annotationVisitor != null) {
            if (object instanceof String[]) {
                String[] stringArray = (String[])object;
                annotationVisitor.visitEnum(string, stringArray[0], stringArray[1]);
            } else if (object instanceof AnnotationNode) {
                AnnotationNode annotationNode = (AnnotationNode)object;
                annotationNode.accept(annotationVisitor.visitAnnotation(string, annotationNode.desc));
            } else if (object instanceof List) {
                AnnotationVisitor annotationVisitor2 = annotationVisitor.visitArray(string);
                if (annotationVisitor2 != null) {
                    List list = (List)object;
                    for (int i = 0; i < list.size(); ++i) {
                        AnnotationNode.accept(annotationVisitor2, null, list.get(i));
                    }
                    annotationVisitor2.visitEnd();
                }
            } else {
                annotationVisitor.visit(string, object);
            }
        }
    }

    static /* synthetic */ Class class$(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            String string2 = classNotFoundException.getMessage();
            throw new NoClassDefFoundError(string2);
        }
    }

    static {
        class$org$objectweb$asm$tree$AnnotationNode = AnnotationNode.class$("org.objectweb.asm.tree.AnnotationNode");
    }
}

