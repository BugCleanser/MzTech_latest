/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.commons.TableSwitchGenerator;

public class GeneratorAdapter
extends LocalVariablesSorter {
    private static final String CLDESC = "Ljava/lang/Class;";
    private static final Type BYTE_TYPE;
    private static final Type BOOLEAN_TYPE;
    private static final Type SHORT_TYPE;
    private static final Type CHARACTER_TYPE;
    private static final Type INTEGER_TYPE;
    private static final Type FLOAT_TYPE;
    private static final Type LONG_TYPE;
    private static final Type DOUBLE_TYPE;
    private static final Type NUMBER_TYPE;
    private static final Type OBJECT_TYPE;
    private static final Method BOOLEAN_VALUE;
    private static final Method CHAR_VALUE;
    private static final Method INT_VALUE;
    private static final Method FLOAT_VALUE;
    private static final Method LONG_VALUE;
    private static final Method DOUBLE_VALUE;
    public static final int ADD = 96;
    public static final int SUB = 100;
    public static final int MUL = 104;
    public static final int DIV = 108;
    public static final int REM = 112;
    public static final int NEG = 116;
    public static final int SHL = 120;
    public static final int SHR = 122;
    public static final int USHR = 124;
    public static final int AND = 126;
    public static final int OR = 128;
    public static final int XOR = 130;
    public static final int EQ = 153;
    public static final int NE = 154;
    public static final int LT = 155;
    public static final int GE = 156;
    public static final int GT = 157;
    public static final int LE = 158;
    private final int access;
    private final Type returnType;
    private final Type[] argumentTypes;
    private final List localTypes = new ArrayList();
    static /* synthetic */ Class class$org$objectweb$asm$commons$GeneratorAdapter;

    public GeneratorAdapter(MethodVisitor methodVisitor, int n, String string, String string2) {
        this(327680, methodVisitor, n, string, string2);
        if (this.getClass() != class$org$objectweb$asm$commons$GeneratorAdapter) {
            throw new IllegalStateException();
        }
    }

    protected GeneratorAdapter(int n, MethodVisitor methodVisitor, int n2, String string, String string2) {
        super(n, n2, string2, methodVisitor);
        this.access = n2;
        this.returnType = Type.getReturnType(string2);
        this.argumentTypes = Type.getArgumentTypes(string2);
    }

    public GeneratorAdapter(int n, Method method, MethodVisitor methodVisitor) {
        this(methodVisitor, n, null, method.getDescriptor());
    }

    public GeneratorAdapter(int n, Method method, String string, Type[] typeArray, ClassVisitor classVisitor) {
        this(n, method, classVisitor.visitMethod(n, method.getName(), method.getDescriptor(), string, GeneratorAdapter.getInternalNames(typeArray)));
    }

    private static String[] getInternalNames(Type[] typeArray) {
        if (typeArray == null) {
            return null;
        }
        String[] stringArray = new String[typeArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = typeArray[i].getInternalName();
        }
        return stringArray;
    }

    public void push(boolean bl) {
        this.push(bl ? 1 : 0);
    }

    public void push(int n) {
        if (n >= -1 && n <= 5) {
            this.mv.visitInsn(3 + n);
        } else if (n >= -128 && n <= 127) {
            this.mv.visitIntInsn(16, n);
        } else if (n >= Short.MIN_VALUE && n <= Short.MAX_VALUE) {
            this.mv.visitIntInsn(17, n);
        } else {
            this.mv.visitLdcInsn(new Integer(n));
        }
    }

    public void push(long l) {
        if (l == 0L || l == 1L) {
            this.mv.visitInsn(9 + (int)l);
        } else {
            this.mv.visitLdcInsn(new Long(l));
        }
    }

    public void push(float f) {
        int n = Float.floatToIntBits(f);
        if ((long)n == 0L || n == 1065353216 || n == 0x40000000) {
            this.mv.visitInsn(11 + (int)f);
        } else {
            this.mv.visitLdcInsn(new Float(f));
        }
    }

    public void push(double d) {
        long l = Double.doubleToLongBits(d);
        if (l == 0L || l == 0x3FF0000000000000L) {
            this.mv.visitInsn(14 + (int)d);
        } else {
            this.mv.visitLdcInsn(new Double(d));
        }
    }

    public void push(String string) {
        if (string == null) {
            this.mv.visitInsn(1);
        } else {
            this.mv.visitLdcInsn(string);
        }
    }

    public void push(Type type) {
        if (type == null) {
            this.mv.visitInsn(1);
        } else {
            switch (type.getSort()) {
                case 1: {
                    this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", CLDESC);
                    break;
                }
                case 2: {
                    this.mv.visitFieldInsn(178, "java/lang/Character", "TYPE", CLDESC);
                    break;
                }
                case 3: {
                    this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", CLDESC);
                    break;
                }
                case 4: {
                    this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", CLDESC);
                    break;
                }
                case 5: {
                    this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", CLDESC);
                    break;
                }
                case 6: {
                    this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", CLDESC);
                    break;
                }
                case 7: {
                    this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", CLDESC);
                    break;
                }
                case 8: {
                    this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", CLDESC);
                    break;
                }
                default: {
                    this.mv.visitLdcInsn(type);
                }
            }
        }
    }

    public void push(Handle handle) {
        this.mv.visitLdcInsn(handle);
    }

    private int getArgIndex(int n) {
        int n2 = (this.access & 8) == 0 ? 1 : 0;
        for (int i = 0; i < n; ++i) {
            n2 += this.argumentTypes[i].getSize();
        }
        return n2;
    }

    private void loadInsn(Type type, int n) {
        this.mv.visitVarInsn(type.getOpcode(21), n);
    }

    private void storeInsn(Type type, int n) {
        this.mv.visitVarInsn(type.getOpcode(54), n);
    }

    public void loadThis() {
        if ((this.access & 8) != 0) {
            throw new IllegalStateException("no 'this' pointer within static method");
        }
        this.mv.visitVarInsn(25, 0);
    }

    public void loadArg(int n) {
        this.loadInsn(this.argumentTypes[n], this.getArgIndex(n));
    }

    public void loadArgs(int n, int n2) {
        int n3 = this.getArgIndex(n);
        for (int i = 0; i < n2; ++i) {
            Type type = this.argumentTypes[n + i];
            this.loadInsn(type, n3);
            n3 += type.getSize();
        }
    }

    public void loadArgs() {
        this.loadArgs(0, this.argumentTypes.length);
    }

    public void loadArgArray() {
        this.push(this.argumentTypes.length);
        this.newArray(OBJECT_TYPE);
        for (int i = 0; i < this.argumentTypes.length; ++i) {
            this.dup();
            this.push(i);
            this.loadArg(i);
            this.box(this.argumentTypes[i]);
            this.arrayStore(OBJECT_TYPE);
        }
    }

    public void storeArg(int n) {
        this.storeInsn(this.argumentTypes[n], this.getArgIndex(n));
    }

    public Type getLocalType(int n) {
        return (Type)this.localTypes.get(n - this.firstLocal);
    }

    protected void setLocalType(int n, Type type) {
        int n2 = n - this.firstLocal;
        while (this.localTypes.size() < n2 + 1) {
            this.localTypes.add(null);
        }
        this.localTypes.set(n2, type);
    }

    public void loadLocal(int n) {
        this.loadInsn(this.getLocalType(n), n);
    }

    public void loadLocal(int n, Type type) {
        this.setLocalType(n, type);
        this.loadInsn(type, n);
    }

    public void storeLocal(int n) {
        this.storeInsn(this.getLocalType(n), n);
    }

    public void storeLocal(int n, Type type) {
        this.setLocalType(n, type);
        this.storeInsn(type, n);
    }

    public void arrayLoad(Type type) {
        this.mv.visitInsn(type.getOpcode(46));
    }

    public void arrayStore(Type type) {
        this.mv.visitInsn(type.getOpcode(79));
    }

    public void pop() {
        this.mv.visitInsn(87);
    }

    public void pop2() {
        this.mv.visitInsn(88);
    }

    public void dup() {
        this.mv.visitInsn(89);
    }

    public void dup2() {
        this.mv.visitInsn(92);
    }

    public void dupX1() {
        this.mv.visitInsn(90);
    }

    public void dupX2() {
        this.mv.visitInsn(91);
    }

    public void dup2X1() {
        this.mv.visitInsn(93);
    }

    public void dup2X2() {
        this.mv.visitInsn(94);
    }

    public void swap() {
        this.mv.visitInsn(95);
    }

    public void swap(Type type, Type type2) {
        if (type2.getSize() == 1) {
            if (type.getSize() == 1) {
                this.swap();
            } else {
                this.dupX2();
                this.pop();
            }
        } else if (type.getSize() == 1) {
            this.dup2X1();
            this.pop2();
        } else {
            this.dup2X2();
            this.pop2();
        }
    }

    public void math(int n, Type type) {
        this.mv.visitInsn(type.getOpcode(n));
    }

    public void not() {
        this.mv.visitInsn(4);
        this.mv.visitInsn(130);
    }

    public void iinc(int n, int n2) {
        this.mv.visitIincInsn(n, n2);
    }

    public void cast(Type type, Type type2) {
        if (type != type2) {
            if (type == Type.DOUBLE_TYPE) {
                if (type2 == Type.FLOAT_TYPE) {
                    this.mv.visitInsn(144);
                } else if (type2 == Type.LONG_TYPE) {
                    this.mv.visitInsn(143);
                } else {
                    this.mv.visitInsn(142);
                    this.cast(Type.INT_TYPE, type2);
                }
            } else if (type == Type.FLOAT_TYPE) {
                if (type2 == Type.DOUBLE_TYPE) {
                    this.mv.visitInsn(141);
                } else if (type2 == Type.LONG_TYPE) {
                    this.mv.visitInsn(140);
                } else {
                    this.mv.visitInsn(139);
                    this.cast(Type.INT_TYPE, type2);
                }
            } else if (type == Type.LONG_TYPE) {
                if (type2 == Type.DOUBLE_TYPE) {
                    this.mv.visitInsn(138);
                } else if (type2 == Type.FLOAT_TYPE) {
                    this.mv.visitInsn(137);
                } else {
                    this.mv.visitInsn(136);
                    this.cast(Type.INT_TYPE, type2);
                }
            } else if (type2 == Type.BYTE_TYPE) {
                this.mv.visitInsn(145);
            } else if (type2 == Type.CHAR_TYPE) {
                this.mv.visitInsn(146);
            } else if (type2 == Type.DOUBLE_TYPE) {
                this.mv.visitInsn(135);
            } else if (type2 == Type.FLOAT_TYPE) {
                this.mv.visitInsn(134);
            } else if (type2 == Type.LONG_TYPE) {
                this.mv.visitInsn(133);
            } else if (type2 == Type.SHORT_TYPE) {
                this.mv.visitInsn(147);
            }
        }
    }

    private static Type getBoxedType(Type type) {
        switch (type.getSort()) {
            case 3: {
                return BYTE_TYPE;
            }
            case 1: {
                return BOOLEAN_TYPE;
            }
            case 4: {
                return SHORT_TYPE;
            }
            case 2: {
                return CHARACTER_TYPE;
            }
            case 5: {
                return INTEGER_TYPE;
            }
            case 6: {
                return FLOAT_TYPE;
            }
            case 7: {
                return LONG_TYPE;
            }
            case 8: {
                return DOUBLE_TYPE;
            }
        }
        return type;
    }

    public void box(Type type) {
        if (type.getSort() == 10 || type.getSort() == 9) {
            return;
        }
        if (type == Type.VOID_TYPE) {
            this.push((String)null);
        } else {
            Type type2 = GeneratorAdapter.getBoxedType(type);
            this.newInstance(type2);
            if (type.getSize() == 2) {
                this.dupX2();
                this.dupX2();
                this.pop();
            } else {
                this.dupX1();
                this.swap();
            }
            this.invokeConstructor(type2, new Method("<init>", Type.VOID_TYPE, new Type[]{type}));
        }
    }

    public void valueOf(Type type) {
        if (type.getSort() == 10 || type.getSort() == 9) {
            return;
        }
        if (type == Type.VOID_TYPE) {
            this.push((String)null);
        } else {
            Type type2 = GeneratorAdapter.getBoxedType(type);
            this.invokeStatic(type2, new Method("valueOf", type2, new Type[]{type}));
        }
    }

    public void unbox(Type type) {
        Type type2 = NUMBER_TYPE;
        Method method = null;
        switch (type.getSort()) {
            case 0: {
                return;
            }
            case 2: {
                type2 = CHARACTER_TYPE;
                method = CHAR_VALUE;
                break;
            }
            case 1: {
                type2 = BOOLEAN_TYPE;
                method = BOOLEAN_VALUE;
                break;
            }
            case 8: {
                method = DOUBLE_VALUE;
                break;
            }
            case 6: {
                method = FLOAT_VALUE;
                break;
            }
            case 7: {
                method = LONG_VALUE;
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                method = INT_VALUE;
            }
        }
        if (method == null) {
            this.checkCast(type);
        } else {
            this.checkCast(type2);
            this.invokeVirtual(type2, method);
        }
    }

    public Label newLabel() {
        return new Label();
    }

    public void mark(Label label) {
        this.mv.visitLabel(label);
    }

    public Label mark() {
        Label label = new Label();
        this.mv.visitLabel(label);
        return label;
    }

    public void ifCmp(Type type, int n, Label label) {
        switch (type.getSort()) {
            case 7: {
                this.mv.visitInsn(148);
                break;
            }
            case 8: {
                this.mv.visitInsn(n == 156 || n == 157 ? 151 : 152);
                break;
            }
            case 6: {
                this.mv.visitInsn(n == 156 || n == 157 ? 149 : 150);
                break;
            }
            case 9: 
            case 10: {
                switch (n) {
                    case 153: {
                        this.mv.visitJumpInsn(165, label);
                        return;
                    }
                    case 154: {
                        this.mv.visitJumpInsn(166, label);
                        return;
                    }
                }
                throw new IllegalArgumentException("Bad comparison for type " + type);
            }
            default: {
                int n2 = -1;
                switch (n) {
                    case 153: {
                        n2 = 159;
                        break;
                    }
                    case 154: {
                        n2 = 160;
                        break;
                    }
                    case 156: {
                        n2 = 162;
                        break;
                    }
                    case 155: {
                        n2 = 161;
                        break;
                    }
                    case 158: {
                        n2 = 164;
                        break;
                    }
                    case 157: {
                        n2 = 163;
                    }
                }
                this.mv.visitJumpInsn(n2, label);
                return;
            }
        }
        this.mv.visitJumpInsn(n, label);
    }

    public void ifICmp(int n, Label label) {
        this.ifCmp(Type.INT_TYPE, n, label);
    }

    public void ifZCmp(int n, Label label) {
        this.mv.visitJumpInsn(n, label);
    }

    public void ifNull(Label label) {
        this.mv.visitJumpInsn(198, label);
    }

    public void ifNonNull(Label label) {
        this.mv.visitJumpInsn(199, label);
    }

    public void goTo(Label label) {
        this.mv.visitJumpInsn(167, label);
    }

    public void ret(int n) {
        this.mv.visitVarInsn(169, n);
    }

    public void tableSwitch(int[] nArray, TableSwitchGenerator tableSwitchGenerator) {
        float f = nArray.length == 0 ? 0.0f : (float)nArray.length / (float)(nArray[nArray.length - 1] - nArray[0] + 1);
        this.tableSwitch(nArray, tableSwitchGenerator, f >= 0.5f);
    }

    public void tableSwitch(int[] nArray, TableSwitchGenerator tableSwitchGenerator, boolean bl) {
        for (int i = 1; i < nArray.length; ++i) {
            if (nArray[i] >= nArray[i - 1]) continue;
            throw new IllegalArgumentException("keys must be sorted ascending");
        }
        Label label = this.newLabel();
        Label label2 = this.newLabel();
        if (nArray.length > 0) {
            int n = nArray.length;
            int n2 = nArray[0];
            int n3 = nArray[n - 1];
            int n4 = n3 - n2 + 1;
            if (bl) {
                int n5;
                Object[] objectArray = new Label[n4];
                Arrays.fill(objectArray, label);
                for (n5 = 0; n5 < n; ++n5) {
                    objectArray[nArray[n5] - n2] = this.newLabel();
                }
                this.mv.visitTableSwitchInsn(n2, n3, label, (Label[])objectArray);
                for (n5 = 0; n5 < n4; ++n5) {
                    Object object = objectArray[n5];
                    if (object == label) continue;
                    this.mark((Label)object);
                    tableSwitchGenerator.generateCase(n5 + n2, label2);
                }
            } else {
                int n6;
                Label[] labelArray = new Label[n];
                for (n6 = 0; n6 < n; ++n6) {
                    labelArray[n6] = this.newLabel();
                }
                this.mv.visitLookupSwitchInsn(label, nArray, labelArray);
                for (n6 = 0; n6 < n; ++n6) {
                    this.mark(labelArray[n6]);
                    tableSwitchGenerator.generateCase(nArray[n6], label2);
                }
            }
        }
        this.mark(label);
        tableSwitchGenerator.generateDefault();
        this.mark(label2);
    }

    public void returnValue() {
        this.mv.visitInsn(this.returnType.getOpcode(172));
    }

    private void fieldInsn(int n, Type type, String string, Type type2) {
        this.mv.visitFieldInsn(n, type.getInternalName(), string, type2.getDescriptor());
    }

    public void getStatic(Type type, String string, Type type2) {
        this.fieldInsn(178, type, string, type2);
    }

    public void putStatic(Type type, String string, Type type2) {
        this.fieldInsn(179, type, string, type2);
    }

    public void getField(Type type, String string, Type type2) {
        this.fieldInsn(180, type, string, type2);
    }

    public void putField(Type type, String string, Type type2) {
        this.fieldInsn(181, type, string, type2);
    }

    private void invokeInsn(int n, Type type, Method method, boolean bl) {
        String string = type.getSort() == 9 ? type.getDescriptor() : type.getInternalName();
        this.mv.visitMethodInsn(n, string, method.getName(), method.getDescriptor(), bl);
    }

    public void invokeVirtual(Type type, Method method) {
        this.invokeInsn(182, type, method, false);
    }

    public void invokeConstructor(Type type, Method method) {
        this.invokeInsn(183, type, method, false);
    }

    public void invokeStatic(Type type, Method method) {
        this.invokeInsn(184, type, method, false);
    }

    public void invokeInterface(Type type, Method method) {
        this.invokeInsn(185, type, method, true);
    }

    public void invokeDynamic(String string, String string2, Handle handle, Object ... objectArray) {
        this.mv.visitInvokeDynamicInsn(string, string2, handle, objectArray);
    }

    private void typeInsn(int n, Type type) {
        this.mv.visitTypeInsn(n, type.getInternalName());
    }

    public void newInstance(Type type) {
        this.typeInsn(187, type);
    }

    public void newArray(Type type) {
        int n;
        switch (type.getSort()) {
            case 1: {
                n = 4;
                break;
            }
            case 2: {
                n = 5;
                break;
            }
            case 3: {
                n = 8;
                break;
            }
            case 4: {
                n = 9;
                break;
            }
            case 5: {
                n = 10;
                break;
            }
            case 6: {
                n = 6;
                break;
            }
            case 7: {
                n = 11;
                break;
            }
            case 8: {
                n = 7;
                break;
            }
            default: {
                this.typeInsn(189, type);
                return;
            }
        }
        this.mv.visitIntInsn(188, n);
    }

    public void arrayLength() {
        this.mv.visitInsn(190);
    }

    public void throwException() {
        this.mv.visitInsn(191);
    }

    public void throwException(Type type, String string) {
        this.newInstance(type);
        this.dup();
        this.push(string);
        this.invokeConstructor(type, Method.getMethod("void <init> (String)"));
        this.throwException();
    }

    public void checkCast(Type type) {
        if (!type.equals(OBJECT_TYPE)) {
            this.typeInsn(192, type);
        }
    }

    public void instanceOf(Type type) {
        this.typeInsn(193, type);
    }

    public void monitorEnter() {
        this.mv.visitInsn(194);
    }

    public void monitorExit() {
        this.mv.visitInsn(195);
    }

    public void endMethod() {
        if ((this.access & 0x400) == 0) {
            this.mv.visitMaxs(0, 0);
        }
        this.mv.visitEnd();
    }

    public void catchException(Label label, Label label2, Type type) {
        Label label3 = new Label();
        if (type == null) {
            this.mv.visitTryCatchBlock(label, label2, label3, null);
        } else {
            this.mv.visitTryCatchBlock(label, label2, label3, type.getInternalName());
        }
        this.mark(label3);
    }

    static {
        GeneratorAdapter._clinit_();
        BYTE_TYPE = Type.getObjectType("java/lang/Byte");
        BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
        SHORT_TYPE = Type.getObjectType("java/lang/Short");
        CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
        INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
        FLOAT_TYPE = Type.getObjectType("java/lang/Float");
        LONG_TYPE = Type.getObjectType("java/lang/Long");
        DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
        NUMBER_TYPE = Type.getObjectType("java/lang/Number");
        OBJECT_TYPE = Type.getObjectType("java/lang/Object");
        BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
        CHAR_VALUE = Method.getMethod("char charValue()");
        INT_VALUE = Method.getMethod("int intValue()");
        FLOAT_VALUE = Method.getMethod("float floatValue()");
        LONG_VALUE = Method.getMethod("long longValue()");
        DOUBLE_VALUE = Method.getMethod("double doubleValue()");
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

    private static void _clinit_() {
        class$org$objectweb$asm$commons$GeneratorAdapter = GeneratorAdapter.class$("org.objectweb.asm.commons.GeneratorAdapter");
    }
}

