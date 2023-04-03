/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.InterpreterError;
import bsh.ParserConstants;
import bsh.Reflect;
import bsh.Types;
import bsh.UtilEvalError;
import bsh.UtilTargetError;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class Primitive
implements ParserConstants,
Serializable {
    static final Map<Class, Class> wrapperMap = new HashMap<Class, Class>();
    private Object value;
    public static final Primitive NULL;
    public static Primitive TRUE;
    public static Primitive FALSE;
    public static final Primitive VOID;

    private Object readResolve() throws ObjectStreamException {
        if (this.value == Special.NULL_VALUE) {
            return NULL;
        }
        if (this.value == Special.VOID_TYPE) {
            return VOID;
        }
        return this;
    }

    public Primitive(Object value) {
        if (value == null) {
            throw new InterpreterError("Use Primitve.NULL instead of Primitive(null)");
        }
        if (value != Special.NULL_VALUE && value != Special.VOID_TYPE && !Primitive.isWrapperType(value.getClass())) {
            throw new InterpreterError("Not a wrapper type: " + value.getClass());
        }
        this.value = value;
    }

    public Primitive(boolean value) {
        this(value ? Boolean.TRUE : Boolean.FALSE);
    }

    public Primitive(byte value) {
        this((Object)value);
    }

    public Primitive(short value) {
        this((Object)value);
    }

    public Primitive(char value) {
        this(Character.valueOf(value));
    }

    public Primitive(int value) {
        this((Object)value);
    }

    public Primitive(long value) {
        this((Object)value);
    }

    public Primitive(float value) {
        this(Float.valueOf(value));
    }

    public Primitive(double value) {
        this((Object)value);
    }

    public Object getValue() {
        if (this.value == Special.NULL_VALUE) {
            return null;
        }
        if (this.value == Special.VOID_TYPE) {
            throw new InterpreterError("attempt to unwrap void type");
        }
        return this.value;
    }

    public String toString() {
        if (this.value == Special.NULL_VALUE) {
            return "null";
        }
        if (this.value == Special.VOID_TYPE) {
            return "void";
        }
        return this.value.toString();
    }

    public Class getType() {
        if (this == VOID) {
            return Void.TYPE;
        }
        if (this == NULL) {
            return null;
        }
        return Primitive.unboxType(this.value.getClass());
    }

    public static Object binaryOperation(Object obj1, Object obj2, int kind) throws UtilEvalError {
        Object result;
        if (obj1 == NULL || obj2 == NULL) {
            throw new UtilEvalError("Null value or 'null' literal in binary operation");
        }
        if (obj1 == VOID || obj2 == VOID) {
            throw new UtilEvalError("Undefined variable, class, or 'void' literal in binary operation");
        }
        Class<?> lhsOrgType = obj1.getClass();
        Class<?> rhsOrgType = obj2.getClass();
        if (obj1 instanceof Primitive) {
            obj1 = ((Primitive)obj1).getValue();
        }
        if (obj2 instanceof Primitive) {
            obj2 = ((Primitive)obj2).getValue();
        }
        Object[] operands = Primitive.promotePrimitives(obj1, obj2);
        Object lhs = operands[0];
        Object rhs = operands[1];
        if (lhs.getClass() != rhs.getClass()) {
            throw new UtilEvalError("Type mismatch in operator.  " + lhs.getClass() + " cannot be used with " + rhs.getClass());
        }
        try {
            result = Primitive.binaryOperationImpl(lhs, rhs, kind);
        }
        catch (ArithmeticException e) {
            throw new UtilTargetError("Arithemetic Exception in binary op", e);
        }
        if (result instanceof Boolean) {
            return (Boolean)result != false ? TRUE : FALSE;
        }
        if (lhsOrgType == Primitive.class && rhsOrgType == Primitive.class) {
            return new Primitive(result);
        }
        return result;
    }

    static Object binaryOperationImpl(Object lhs, Object rhs, int kind) throws UtilEvalError {
        if (lhs instanceof Boolean) {
            return Primitive.booleanBinaryOperation((Boolean)lhs, (Boolean)rhs, kind);
        }
        if (lhs instanceof Integer) {
            return Primitive.intBinaryOperation((Integer)lhs, (Integer)rhs, kind);
        }
        if (lhs instanceof Long) {
            return Primitive.longBinaryOperation((Long)lhs, (Long)rhs, kind);
        }
        if (lhs instanceof Float) {
            return Primitive.floatBinaryOperation((Float)lhs, (Float)rhs, kind);
        }
        if (lhs instanceof Double) {
            return Primitive.doubleBinaryOperation((Double)lhs, (Double)rhs, kind);
        }
        throw new UtilEvalError("Invalid types in binary operator");
    }

    static Boolean booleanBinaryOperation(Boolean B1, Boolean B2, int kind) {
        boolean lhs = B1;
        boolean rhs = B2;
        switch (kind) {
            case 91: {
                return lhs == rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 96: {
                return lhs != rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 97: 
            case 98: 
            case 109: {
                return lhs || rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 99: 
            case 100: 
            case 107: {
                return lhs && rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 111: {
                return lhs ^ rhs ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        throw new InterpreterError("unimplemented binary operator");
    }

    static Object longBinaryOperation(Long L1, Long L2, int kind) {
        long lhs = L1;
        long rhs = L2;
        switch (kind) {
            case 85: 
            case 86: {
                return lhs < rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 83: 
            case 84: {
                return lhs > rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 91: {
                return lhs == rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 92: 
            case 93: {
                return lhs <= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 94: 
            case 95: {
                return lhs >= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 96: {
                return lhs != rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 103: {
                return new Long(lhs + rhs);
            }
            case 104: {
                return new Long(lhs - rhs);
            }
            case 105: {
                return new Long(lhs * rhs);
            }
            case 106: {
                return new Long(lhs / rhs);
            }
            case 112: {
                return new Long(lhs % rhs);
            }
            case 113: 
            case 114: {
                return new Long(lhs << (int)rhs);
            }
            case 115: 
            case 116: {
                return new Long(lhs >> (int)rhs);
            }
            case 117: 
            case 118: {
                return new Long(lhs >>> (int)rhs);
            }
            case 107: 
            case 108: {
                return new Long(lhs & rhs);
            }
            case 109: 
            case 110: {
                return new Long(lhs | rhs);
            }
            case 111: {
                return new Long(lhs ^ rhs);
            }
        }
        throw new InterpreterError("Unimplemented binary long operator");
    }

    static Object intBinaryOperation(Integer I1, Integer I2, int kind) {
        int lhs = I1;
        int rhs = I2;
        switch (kind) {
            case 85: 
            case 86: {
                return lhs < rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 83: 
            case 84: {
                return lhs > rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 91: {
                return lhs == rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 92: 
            case 93: {
                return lhs <= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 94: 
            case 95: {
                return lhs >= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 96: {
                return lhs != rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 103: {
                return new Integer(lhs + rhs);
            }
            case 104: {
                return new Integer(lhs - rhs);
            }
            case 105: {
                return new Integer(lhs * rhs);
            }
            case 106: {
                return new Integer(lhs / rhs);
            }
            case 112: {
                return new Integer(lhs % rhs);
            }
            case 113: 
            case 114: {
                return new Integer(lhs << rhs);
            }
            case 115: 
            case 116: {
                return new Integer(lhs >> rhs);
            }
            case 117: 
            case 118: {
                return new Integer(lhs >>> rhs);
            }
            case 107: 
            case 108: {
                return new Integer(lhs & rhs);
            }
            case 109: 
            case 110: {
                return new Integer(lhs | rhs);
            }
            case 111: {
                return new Integer(lhs ^ rhs);
            }
        }
        throw new InterpreterError("Unimplemented binary integer operator");
    }

    static Object doubleBinaryOperation(Double D1, Double D2, int kind) throws UtilEvalError {
        double lhs = D1;
        double rhs = D2;
        switch (kind) {
            case 85: 
            case 86: {
                return lhs < rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 83: 
            case 84: {
                return lhs > rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 91: {
                return lhs == rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 92: 
            case 93: {
                return lhs <= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 94: 
            case 95: {
                return lhs >= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 96: {
                return lhs != rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 103: {
                return new Double(lhs + rhs);
            }
            case 104: {
                return new Double(lhs - rhs);
            }
            case 105: {
                return new Double(lhs * rhs);
            }
            case 106: {
                return new Double(lhs / rhs);
            }
            case 112: {
                return new Double(lhs % rhs);
            }
            case 113: 
            case 114: 
            case 115: 
            case 116: 
            case 117: 
            case 118: {
                throw new UtilEvalError("Can't shift doubles");
            }
        }
        throw new InterpreterError("Unimplemented binary double operator");
    }

    static Object floatBinaryOperation(Float F1, Float F2, int kind) throws UtilEvalError {
        float lhs = F1.floatValue();
        float rhs = F2.floatValue();
        switch (kind) {
            case 85: 
            case 86: {
                return lhs < rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 83: 
            case 84: {
                return lhs > rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 91: {
                return lhs == rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 92: 
            case 93: {
                return lhs <= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 94: 
            case 95: {
                return lhs >= rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 96: {
                return lhs != rhs ? Boolean.TRUE : Boolean.FALSE;
            }
            case 103: {
                return new Float(lhs + rhs);
            }
            case 104: {
                return new Float(lhs - rhs);
            }
            case 105: {
                return new Float(lhs * rhs);
            }
            case 106: {
                return new Float(lhs / rhs);
            }
            case 112: {
                return new Float(lhs % rhs);
            }
            case 113: 
            case 114: 
            case 115: 
            case 116: 
            case 117: 
            case 118: {
                throw new UtilEvalError("Can't shift floats ");
            }
        }
        throw new InterpreterError("Unimplemented binary float operator");
    }

    static Object promoteToInteger(Object wrapper) {
        if (wrapper instanceof Character) {
            return new Integer(((Character)wrapper).charValue());
        }
        if (wrapper instanceof Byte || wrapper instanceof Short) {
            return new Integer(((Number)wrapper).intValue());
        }
        return wrapper;
    }

    static Object[] promotePrimitives(Object lhs, Object rhs) {
        lhs = Primitive.promoteToInteger(lhs);
        rhs = Primitive.promoteToInteger(rhs);
        if (lhs instanceof Number && rhs instanceof Number) {
            Number lnum = (Number)lhs;
            Number rnum = (Number)rhs;
            boolean b = lnum instanceof Double;
            if (b || rnum instanceof Double) {
                if (b) {
                    rhs = new Double(rnum.doubleValue());
                } else {
                    lhs = new Double(lnum.doubleValue());
                }
            } else {
                b = lnum instanceof Float;
                if (b || rnum instanceof Float) {
                    if (b) {
                        rhs = new Float(rnum.floatValue());
                    } else {
                        lhs = new Float(lnum.floatValue());
                    }
                } else {
                    b = lnum instanceof Long;
                    if (b || rnum instanceof Long) {
                        if (b) {
                            rhs = new Long(rnum.longValue());
                        } else {
                            lhs = new Long(lnum.longValue());
                        }
                    }
                }
            }
        }
        return new Object[]{lhs, rhs};
    }

    public static Primitive unaryOperation(Primitive val, int kind) throws UtilEvalError {
        if (val == NULL) {
            throw new UtilEvalError("illegal use of null object or 'null' literal");
        }
        if (val == VOID) {
            throw new UtilEvalError("illegal use of undefined object or 'void' literal");
        }
        Class operandType = val.getType();
        Object operand = Primitive.promoteToInteger(val.getValue());
        if (operand instanceof Boolean) {
            return Primitive.booleanUnaryOperation((Boolean)operand, kind) ? TRUE : FALSE;
        }
        if (operand instanceof Integer) {
            int result = Primitive.intUnaryOperation((Integer)operand, kind);
            if (kind == 101 || kind == 102) {
                if (operandType == Byte.TYPE) {
                    return new Primitive((byte)result);
                }
                if (operandType == Short.TYPE) {
                    return new Primitive((short)result);
                }
                if (operandType == Character.TYPE) {
                    return new Primitive((char)result);
                }
            }
            return new Primitive(result);
        }
        if (operand instanceof Long) {
            return new Primitive(Primitive.longUnaryOperation((Long)operand, kind));
        }
        if (operand instanceof Float) {
            return new Primitive(Primitive.floatUnaryOperation((Float)operand, kind));
        }
        if (operand instanceof Double) {
            return new Primitive(Primitive.doubleUnaryOperation((Double)operand, kind));
        }
        throw new InterpreterError("An error occurred.  Please call technical support.");
    }

    static boolean booleanUnaryOperation(Boolean B, int kind) throws UtilEvalError {
        boolean operand = B;
        switch (kind) {
            case 87: {
                return !operand;
            }
        }
        throw new UtilEvalError("Operator inappropriate for boolean");
    }

    static int intUnaryOperation(Integer I, int kind) {
        int operand = I;
        switch (kind) {
            case 103: {
                return operand;
            }
            case 104: {
                return -operand;
            }
            case 88: {
                return ~operand;
            }
            case 101: {
                return operand + 1;
            }
            case 102: {
                return operand - 1;
            }
        }
        throw new InterpreterError("bad integer unaryOperation");
    }

    static long longUnaryOperation(Long L, int kind) {
        long operand = L;
        switch (kind) {
            case 103: {
                return operand;
            }
            case 104: {
                return -operand;
            }
            case 88: {
                return operand ^ 0xFFFFFFFFFFFFFFFFL;
            }
            case 101: {
                return operand + 1L;
            }
            case 102: {
                return operand - 1L;
            }
        }
        throw new InterpreterError("bad long unaryOperation");
    }

    static float floatUnaryOperation(Float F, int kind) {
        float operand = F.floatValue();
        switch (kind) {
            case 103: {
                return operand;
            }
            case 104: {
                return -operand;
            }
            case 101: {
                return operand + 1.0f;
            }
            case 102: {
                return operand - 1.0f;
            }
        }
        throw new InterpreterError("bad float unaryOperation");
    }

    static double doubleUnaryOperation(Double D, int kind) {
        double operand = D;
        switch (kind) {
            case 103: {
                return operand;
            }
            case 104: {
                return -operand;
            }
            case 101: {
                return operand + 1.0;
            }
            case 102: {
                return operand - 1.0;
            }
        }
        throw new InterpreterError("bad double unaryOperation");
    }

    public int intValue() throws UtilEvalError {
        if (this.value instanceof Number) {
            return ((Number)this.value).intValue();
        }
        throw new UtilEvalError("Primitive not a number");
    }

    public boolean booleanValue() throws UtilEvalError {
        if (this.value instanceof Boolean) {
            return (Boolean)this.value;
        }
        throw new UtilEvalError("Primitive not a boolean");
    }

    public boolean isNumber() {
        return !(this.value instanceof Boolean) && this != NULL && this != VOID;
    }

    public Number numberValue() throws UtilEvalError {
        Object value = this.value;
        if (value instanceof Character) {
            value = new Integer(((Character)value).charValue());
        }
        if (value instanceof Number) {
            return (Number)value;
        }
        throw new UtilEvalError("Primitive not a number");
    }

    public boolean equals(Object obj) {
        if (obj instanceof Primitive) {
            return ((Primitive)obj).value.equals(this.value);
        }
        return false;
    }

    public int hashCode() {
        return this.value.hashCode() * 21;
    }

    public static Object unwrap(Object obj) {
        if (obj == VOID) {
            return null;
        }
        if (obj instanceof Primitive) {
            return ((Primitive)obj).getValue();
        }
        return obj;
    }

    public static Object[] unwrap(Object[] args) {
        Object[] oa = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            oa[i] = Primitive.unwrap(args[i]);
        }
        return oa;
    }

    public static Object[] wrap(Object[] args, Class[] paramTypes) {
        if (args == null) {
            return null;
        }
        Object[] oa = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            oa[i] = Primitive.wrap(args[i], paramTypes[i]);
        }
        return oa;
    }

    public static Object wrap(Object value, Class type) {
        if (type == Void.TYPE) {
            return VOID;
        }
        if (value == null) {
            return NULL;
        }
        if (value instanceof Boolean) {
            return (Boolean)value != false ? TRUE : FALSE;
        }
        if (type.isPrimitive() && Primitive.isWrapperType(value.getClass())) {
            return new Primitive(value);
        }
        return value;
    }

    public static Primitive getDefaultValue(Class type) {
        if (type == null || !type.isPrimitive()) {
            return NULL;
        }
        if (type == Boolean.TYPE) {
            return FALSE;
        }
        try {
            return new Primitive(0).castToType(type, 0);
        }
        catch (UtilEvalError e) {
            throw new InterpreterError("bad cast");
        }
    }

    public static Class boxType(Class primitiveType) {
        Class c = wrapperMap.get(primitiveType);
        if (c != null) {
            return c;
        }
        throw new InterpreterError("Not a primitive type: " + primitiveType);
    }

    public static Class unboxType(Class wrapperType) {
        Class c = wrapperMap.get(wrapperType);
        if (c != null) {
            return c;
        }
        throw new InterpreterError("Not a primitive wrapper type: " + wrapperType);
    }

    public Primitive castToType(Class toType, int operation) throws UtilEvalError {
        return Primitive.castPrimitive(toType, this.getType(), this, false, operation);
    }

    static Primitive castPrimitive(Class toType, Class fromType, Primitive fromValue, boolean checkOnly, int operation) throws UtilEvalError {
        if (checkOnly && fromValue != null) {
            throw new InterpreterError("bad cast param 1");
        }
        if (!checkOnly && fromValue == null) {
            throw new InterpreterError("bad cast param 2");
        }
        if (fromType != null && !fromType.isPrimitive()) {
            throw new InterpreterError("bad fromType:" + fromType);
        }
        if (fromValue == NULL && fromType != null) {
            throw new InterpreterError("inconsistent args 1");
        }
        if (fromValue == VOID && fromType != Void.TYPE) {
            throw new InterpreterError("inconsistent args 2");
        }
        if (fromType == Void.TYPE) {
            if (checkOnly) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(Reflect.normalizeClassName(toType), "void value", operation);
        }
        Object value = null;
        if (fromValue != null) {
            value = fromValue.getValue();
        }
        if (toType.isPrimitive()) {
            if (fromType == null) {
                if (checkOnly) {
                    return Types.INVALID_CAST;
                }
                throw Types.castError("primitive type:" + toType, "Null value", operation);
            }
        } else {
            if (fromType == null) {
                return checkOnly ? Types.VALID_CAST : NULL;
            }
            if (checkOnly) {
                return Types.INVALID_CAST;
            }
            throw Types.castError("object type:" + toType, "primitive value", operation);
        }
        if (fromType == Boolean.TYPE) {
            if (toType != Boolean.TYPE) {
                if (checkOnly) {
                    return Types.INVALID_CAST;
                }
                throw Types.castError(toType, fromType, operation);
            }
            return checkOnly ? Types.VALID_CAST : fromValue;
        }
        if (operation == 1 && !Types.isJavaAssignable(toType, fromType)) {
            if (checkOnly) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(toType, fromType, operation);
        }
        return checkOnly ? Types.VALID_CAST : new Primitive(Primitive.castWrapper(toType, value));
    }

    public static boolean isWrapperType(Class type) {
        return wrapperMap.get(type) != null && !type.isPrimitive();
    }

    static Object castWrapper(Class toType, Object value) {
        if (!toType.isPrimitive()) {
            throw new InterpreterError("invalid type in castWrapper: " + toType);
        }
        if (value == null) {
            throw new InterpreterError("null value in castWrapper, guard");
        }
        if (value instanceof Boolean) {
            if (toType != Boolean.TYPE) {
                throw new InterpreterError("bad wrapper cast of boolean");
            }
            return value;
        }
        if (value instanceof Character) {
            value = new Integer(((Character)value).charValue());
        }
        if (!(value instanceof Number)) {
            throw new InterpreterError("bad type in cast");
        }
        Number number = (Number)value;
        if (toType == Byte.TYPE) {
            return new Byte(number.byteValue());
        }
        if (toType == Short.TYPE) {
            return new Short(number.shortValue());
        }
        if (toType == Character.TYPE) {
            return new Character((char)number.intValue());
        }
        if (toType == Integer.TYPE) {
            return new Integer(number.intValue());
        }
        if (toType == Long.TYPE) {
            return new Long(number.longValue());
        }
        if (toType == Float.TYPE) {
            return new Float(number.floatValue());
        }
        if (toType == Double.TYPE) {
            return new Double(number.doubleValue());
        }
        throw new InterpreterError("error in wrapper cast");
    }

    static {
        wrapperMap.put(Boolean.TYPE, Boolean.class);
        wrapperMap.put(Byte.TYPE, Byte.class);
        wrapperMap.put(Short.TYPE, Short.class);
        wrapperMap.put(Character.TYPE, Character.class);
        wrapperMap.put(Integer.TYPE, Integer.class);
        wrapperMap.put(Long.TYPE, Long.class);
        wrapperMap.put(Float.TYPE, Float.class);
        wrapperMap.put(Double.TYPE, Double.class);
        wrapperMap.put(Boolean.class, Boolean.TYPE);
        wrapperMap.put(Byte.class, Byte.TYPE);
        wrapperMap.put(Short.class, Short.TYPE);
        wrapperMap.put(Character.class, Character.TYPE);
        wrapperMap.put(Integer.class, Integer.TYPE);
        wrapperMap.put(Long.class, Long.TYPE);
        wrapperMap.put(Float.class, Float.TYPE);
        wrapperMap.put(Double.class, Double.TYPE);
        NULL = new Primitive(Special.NULL_VALUE);
        TRUE = new Primitive(true);
        FALSE = new Primitive(false);
        VOID = new Primitive(Special.VOID_TYPE);
    }

    private static class Special
    implements Serializable {
        public static final Special NULL_VALUE = new Special(){

            private Object readResolve() throws ObjectStreamException {
                return NULL_VALUE;
            }
        };
        public static final Special VOID_TYPE = new Special(){

            private Object readResolve() throws ObjectStreamException {
                return VOID_TYPE;
            }
        };

        private Special() {
        }
    }
}

