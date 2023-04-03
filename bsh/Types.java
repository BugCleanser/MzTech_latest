/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.InterpreterError;
import bsh.Primitive;
import bsh.Reflect;
import bsh.This;
import bsh.UtilEvalError;
import bsh.UtilTargetError;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Types {
    static final int CAST = 0;
    static final int ASSIGNMENT = 1;
    private static final Map<Class<?>, Integer> NUMBER_ORDER = Collections.unmodifiableMap(new HashMap<Class<?>, Integer>(){
        private static final long serialVersionUID = 1L;
        {
            this.put(Byte.TYPE, 0);
            this.put(Byte.class, 1);
            this.put(Short.TYPE, 2);
            this.put(Short.class, 3);
            this.put(Character.TYPE, 4);
            this.put(Character.class, 5);
            this.put(Integer.TYPE, 6);
            this.put(Integer.class, 7);
            this.put(Long.TYPE, 8);
            this.put(Long.class, 9);
            this.put(Float.TYPE, 10);
            this.put(Float.class, 11);
            this.put(Double.TYPE, 12);
            this.put(Double.class, 13);
            this.put(BigInteger.class, 14);
            this.put(BigDecimal.class, 15);
        }
    });
    static final int JAVA_BASE_ASSIGNABLE = 1;
    static final int JAVA_BOX_TYPES_ASSIGABLE = 2;
    static final int JAVA_VARARGS_ASSIGNABLE = 3;
    static final int BSH_ASSIGNABLE = 4;
    static final int FIRST_ROUND_ASSIGNABLE = 1;
    static final int LAST_ROUND_ASSIGNABLE = 4;
    static Primitive VALID_CAST = new Primitive(1);
    static Primitive INVALID_CAST = new Primitive(-1);

    Types() {
    }

    public static Class<?>[] getTypes(Object[] args) {
        if (args == null) {
            return new Class[0];
        }
        Class[] types = new Class[args.length];
        for (int i = 0; i < args.length; ++i) {
            types[i] = args[i] == null ? null : (args[i] instanceof Primitive ? ((Primitive)args[i]).getType() : args[i].getClass());
        }
        return types;
    }

    public static Class<?> getType(Object arg) {
        return Types.getType(arg, false);
    }

    public static Class<?> getType(Object arg, boolean boxed) {
        if (null == arg || Primitive.NULL == arg) {
            return null;
        }
        if (arg instanceof Primitive && !boxed) {
            return ((Primitive)arg).getType();
        }
        return Primitive.unwrap(arg).getClass();
    }

    static boolean isSignatureAssignable(Class[] from, Class[] to, int round) {
        if (round != 3 && from.length != to.length) {
            return false;
        }
        switch (round) {
            case 1: {
                for (int i = 0; i < from.length; ++i) {
                    if (Types.isJavaBaseAssignable(to[i], from[i])) continue;
                    return false;
                }
                return true;
            }
            case 2: {
                for (int i = 0; i < from.length; ++i) {
                    if (Types.isJavaBoxTypesAssignable(to[i], from[i])) continue;
                    return false;
                }
                return true;
            }
            case 3: {
                return Types.isSignatureVarargsAssignable(from, to);
            }
            case 4: {
                for (int i = 0; i < from.length; ++i) {
                    if (Types.isBshAssignable(to[i], from[i])) continue;
                    return false;
                }
                return true;
            }
        }
        throw new InterpreterError("bad case");
    }

    static boolean areSignaturesEqual(Class[] from, Class[] to) {
        if (from.length != to.length) {
            return false;
        }
        for (int i = 0; i < from.length; ++i) {
            if (from[i] == to[i]) continue;
            return false;
        }
        return true;
    }

    private static boolean isSignatureVarargsAssignable(Class<?>[] from, Class<?>[] to) {
        int i;
        if (to.length == 0 || to.length > from.length + 1) {
            return false;
        }
        int last = to.length - 1;
        if (to[last] == null || !to[last].isArray()) {
            return false;
        }
        if (from.length == to.length && from[last] != null && from[last].isArray() && !Types.isJavaAssignable(to[last].getComponentType(), from[last].getComponentType())) {
            return false;
        }
        if (from.length >= to.length && from[last] != null && !from[last].isArray()) {
            for (i = last; i < from.length; ++i) {
                if (Types.isJavaAssignable(to[last].getComponentType(), from[i])) continue;
                return false;
            }
        }
        for (i = 0; i < last; ++i) {
            if (Types.isJavaAssignable(to[i], from[i])) continue;
            return false;
        }
        return true;
    }

    static boolean isJavaAssignable(Class lhsType, Class rhsType) {
        return Types.isJavaBaseAssignable(lhsType, rhsType) || Types.isJavaBoxTypesAssignable(lhsType, rhsType);
    }

    static boolean isJavaBaseAssignable(Class<?> lhsType, Class<?> rhsType) {
        if (lhsType == null) {
            return false;
        }
        if (rhsType == null) {
            return lhsType == String.class;
        }
        if (lhsType.isPrimitive() && rhsType.isPrimitive()) {
            if (lhsType == rhsType) {
                return true;
            }
            if (NUMBER_ORDER.containsKey(rhsType) && NUMBER_ORDER.containsKey(lhsType)) {
                return NUMBER_ORDER.get(rhsType) < NUMBER_ORDER.get(lhsType);
            }
        } else if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        return false;
    }

    static boolean isJavaBoxTypesAssignable(Class lhsType, Class rhsType) {
        if (lhsType == null) {
            return false;
        }
        if (lhsType == Object.class) {
            return true;
        }
        if (rhsType == null) {
            return !lhsType.isPrimitive() && !lhsType.isArray();
        }
        if (lhsType == Number.class && rhsType != Character.TYPE && rhsType != Boolean.TYPE) {
            return true;
        }
        if (Primitive.wrapperMap.get(lhsType) == rhsType) {
            return true;
        }
        return Types.isJavaBaseAssignable(lhsType, rhsType);
    }

    static boolean isBshAssignable(Class toType, Class fromType) {
        try {
            return Types.castObject(toType, fromType, null, 1, true) == VALID_CAST;
        }
        catch (UtilEvalError e) {
            throw new InterpreterError("err in cast check: " + e);
        }
    }

    public static Class<?> arrayElementType(Class<?> arrType) {
        if (null == arrType) {
            return null;
        }
        while (arrType.isArray()) {
            arrType = arrType.getComponentType();
        }
        return arrType;
    }

    public static int arrayDimensions(Class<?> arrType) {
        if (null == arrType || !arrType.isArray()) {
            return 0;
        }
        return arrType.getName().lastIndexOf(91) + 1;
    }

    public static Class<?> getCommonType(Class<?> common, Class<?> compare) {
        if (null == common) {
            return compare;
        }
        if (null == compare || common.isAssignableFrom(compare)) {
            return common;
        }
        if (NUMBER_ORDER.containsKey(common) && NUMBER_ORDER.containsKey(compare)) {
            if (NUMBER_ORDER.get(common) >= NUMBER_ORDER.get(compare)) {
                return common;
            }
            return compare;
        }
        Class<?> supr = common;
        while (null != (supr = supr.getSuperclass()) && Object.class != supr) {
            if (!supr.isAssignableFrom(compare)) continue;
            return supr;
        }
        return Object.class;
    }

    public static Object castObject(Object fromValue, Class<?> toType, int operation) throws UtilEvalError {
        if (fromValue == null) {
            throw new InterpreterError("null fromValue");
        }
        Class fromType = fromValue instanceof Primitive ? ((Primitive)fromValue).getType() : fromValue.getClass();
        return Types.castObject(toType, fromType, fromValue, operation, false);
    }

    private static Object castObject(Class<?> toType, Class<?> fromType, Object fromValue, int operation, boolean checkOnly) throws UtilEvalError {
        if (checkOnly && fromValue != null) {
            throw new InterpreterError("bad cast params 1");
        }
        if (!checkOnly && fromValue == null) {
            throw new InterpreterError("bad cast params 2");
        }
        if (fromType == Primitive.class) {
            throw new InterpreterError("bad from Type, need to unwrap");
        }
        if (fromValue == Primitive.NULL && fromType != null) {
            throw new InterpreterError("inconsistent args 1");
        }
        if (fromValue == Primitive.VOID && fromType != Void.TYPE) {
            throw new InterpreterError("inconsistent args 2");
        }
        if (toType == Void.TYPE) {
            throw new InterpreterError("loose toType should be null");
        }
        if (toType == null || toType == fromType) {
            return checkOnly ? VALID_CAST : fromValue;
        }
        if (toType.isPrimitive()) {
            if (fromType == Void.TYPE || fromType == null || fromType.isPrimitive()) {
                return Primitive.castPrimitive(toType, fromType, (Primitive)fromValue, checkOnly, operation);
            }
            if (Primitive.isWrapperType(fromType)) {
                Class unboxedFromType = Primitive.unboxType(fromType);
                Primitive primFromValue = checkOnly ? null : (Primitive)Primitive.wrap(fromValue, unboxedFromType);
                return Primitive.castPrimitive(toType, unboxedFromType, primFromValue, checkOnly, operation);
            }
            if (checkOnly) {
                return INVALID_CAST;
            }
            throw Types.castError(toType, fromType, operation);
        }
        if (fromType == Void.TYPE || fromType == null || fromType.isPrimitive()) {
            if (Primitive.isWrapperType(toType) && fromType != Void.TYPE && fromType != null) {
                return checkOnly ? VALID_CAST : Primitive.castWrapper(Primitive.unboxType(toType), ((Primitive)fromValue).getValue());
            }
            if (toType == Object.class && fromType != Void.TYPE && fromType != null) {
                return checkOnly ? VALID_CAST : ((Primitive)fromValue).getValue();
            }
            return Primitive.castPrimitive(toType, fromType, (Primitive)fromValue, checkOnly, operation);
        }
        if (toType.isAssignableFrom(fromType)) {
            return checkOnly ? VALID_CAST : fromValue;
        }
        if (toType.isInterface() && This.class.isAssignableFrom(fromType)) {
            return checkOnly ? VALID_CAST : ((This)fromValue).getInterface(toType);
        }
        if (Primitive.isWrapperType(toType) && Primitive.isWrapperType(fromType)) {
            return checkOnly ? VALID_CAST : Primitive.castWrapper(toType, fromValue);
        }
        if (checkOnly) {
            return INVALID_CAST;
        }
        throw Types.castError(toType, fromType, operation);
    }

    static UtilEvalError castError(Class lhsType, Class rhsType, int operation) {
        return Types.castError(Reflect.normalizeClassName(lhsType), Reflect.normalizeClassName(rhsType), operation);
    }

    static UtilEvalError castError(String lhs, String rhs, int operation) {
        if (operation == 1) {
            return new UtilEvalError("Can't assign " + rhs + " to " + lhs);
        }
        ClassCastException cce = new ClassCastException("Cannot cast " + rhs + " to " + lhs);
        return new UtilTargetError(cce);
    }

    public static String getBaseName(String className) {
        int i = className.indexOf("$");
        if (i == -1) {
            return className;
        }
        return className.substring(i + 1);
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == BigInteger.class || type == BigDecimal.class;
    }

    public static boolean isNumeric(Object value) {
        return value instanceof Number || value instanceof Character;
    }

    public static boolean isFloatingpoint(Object number) {
        return number instanceof Float || number instanceof Double || number instanceof BigDecimal;
    }

    public static boolean isPropertyTypeMap(Object obj) {
        return obj instanceof Map;
    }

    public static boolean isPropertyTypeMap(Class<?> clas) {
        return Map.class.isAssignableFrom(clas);
    }

    public static boolean isPropertyTypeEntry(Object obj) {
        return obj instanceof Map.Entry;
    }

    public static boolean isPropertyTypeEntry(Class<?> clas) {
        return Map.Entry.class.isAssignableFrom(clas);
    }

    public static boolean isPropertyTypeEntryList(Class<?> clas) {
        return clas.isArray() && Types.isPropertyTypeEntry(clas.getComponentType());
    }

    public static boolean isPropertyType(Class<?> clas) {
        return Types.isPropertyTypeMap(clas) || Types.isPropertyTypeEntry(clas) || Types.isPropertyTypeEntryList(clas);
    }

    public static boolean isCollectionType(Class<?> clas) {
        return Collection.class.isAssignableFrom(clas) || Map.class.isAssignableFrom(clas) || Map.Entry.class.isAssignableFrom(clas);
    }

    public static class Suffix {
        private static final Map<String, Class<?>> m = Collections.unmodifiableMap(new HashMap<String, Class<?>>(){
            private static final long serialVersionUID = 1L;
            {
                this.put("O", Byte.TYPE);
                this.put("S", Short.TYPE);
                this.put("I", Integer.TYPE);
                this.put("L", Long.TYPE);
                this.put("W", BigInteger.class);
                this.put("w", BigDecimal.class);
                this.put("d", Double.TYPE);
                this.put("f", Float.TYPE);
            }
        });

        private static String toUpperKey(Character key) {
            return key.toString().toUpperCase();
        }

        private static String toLowerKey(Character key) {
            return key.toString().toLowerCase();
        }

        public static boolean isIntegral(Character key) {
            return m.containsKey(Suffix.toUpperKey(key));
        }

        public static Class<?> getIntegralType(Character key) {
            return m.get(Suffix.toUpperKey(key));
        }

        public static boolean isFloatingPoint(Character key) {
            return m.containsKey(Suffix.toLowerKey(key));
        }

        public static Class<?> getFloatingPointType(Character key) {
            return m.get(Suffix.toLowerKey(key));
        }
    }
}

