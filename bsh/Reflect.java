/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshClassManager;
import bsh.CallStack;
import bsh.Capabilities;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.InterpreterError;
import bsh.LHS;
import bsh.Primitive;
import bsh.ReflectError;
import bsh.SimpleNode;
import bsh.StringUtil;
import bsh.This;
import bsh.Types;
import bsh.UtilEvalError;
import bsh.UtilTargetError;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

final class Reflect {
    public static final Comparator<Method> METHOD_COMPARATOR = new Comparator<Method>(){

        @Override
        public int compare(Method a, Method b) {
            int scoreB;
            int scoreA = Reflect.getVisibility(a);
            return scoreA < (scoreB = Reflect.getVisibility(b)) ? -1 : (scoreA == scoreB ? 0 : 1);
        }
    };

    Reflect() {
    }

    public static Object invokeObjectMethod(Object object, String methodName, Object[] args, Interpreter interpreter, CallStack callstack, SimpleNode callerInfo) throws ReflectError, EvalError, InvocationTargetException {
        if (object instanceof This && !This.isExposedThisMethod(methodName)) {
            return ((This)object).invokeMethod(methodName, args, interpreter, callstack, callerInfo, false);
        }
        try {
            BshClassManager bcm = interpreter == null ? null : interpreter.getClassManager();
            Class<?> clas = object.getClass();
            Method method = Reflect.resolveExpectedJavaMethod(bcm, clas, object, methodName, args, false);
            return Reflect.invokeMethod(method, object, args);
        }
        catch (UtilEvalError e) {
            throw e.toEvalError(callerInfo, callstack);
        }
    }

    public static Object invokeStaticMethod(BshClassManager bcm, Class clas, String methodName, Object[] args) throws ReflectError, UtilEvalError, InvocationTargetException {
        Interpreter.debug("invoke static Method");
        Method method = Reflect.resolveExpectedJavaMethod(bcm, clas, null, methodName, args, true);
        return Reflect.invokeMethod(method, null, args);
    }

    static Object invokeMethod(Method method, Object object, Object[] args) throws ReflectError, InvocationTargetException {
        if (args == null) {
            args = new Object[]{};
        }
        Reflect.logInvokeMethod("Invoking method (entry): ", method, args);
        boolean isVarArgs = method.isVarArgs();
        Class<?>[] types = method.getParameterTypes();
        Object[] tmpArgs = new Object[types.length];
        int fixedArgLen = types.length;
        if (isVarArgs) {
            if (fixedArgLen == args.length && types[fixedArgLen - 1].isAssignableFrom(args[fixedArgLen - 1].getClass())) {
                isVarArgs = false;
            } else {
                --fixedArgLen;
            }
        }
        try {
            for (int i = 0; i < fixedArgLen; ++i) {
                tmpArgs[i] = Types.castObject(args[i], types[i], 1);
            }
            if (isVarArgs) {
                Class<?> varType = types[fixedArgLen].getComponentType();
                Object varArgs = Array.newInstance(varType, args.length - fixedArgLen);
                int i = fixedArgLen;
                int j = 0;
                while (i < args.length) {
                    Array.set(varArgs, j, Primitive.unwrap(Types.castObject(args[i], varType, 1)));
                    ++i;
                    ++j;
                }
                tmpArgs[fixedArgLen] = varArgs;
            }
        }
        catch (UtilEvalError e) {
            throw new InterpreterError("illegal argument type in method invocation: " + e);
        }
        tmpArgs = Primitive.unwrap(tmpArgs);
        Reflect.logInvokeMethod("Invoking method (after massaging values): ", method, tmpArgs);
        try {
            Object returnValue = method.invoke(object, tmpArgs);
            if (returnValue == null) {
                returnValue = Primitive.NULL;
            }
            Class<?> returnType = method.getReturnType();
            return Primitive.wrap(returnValue, returnType);
        }
        catch (IllegalAccessException e) {
            throw new ReflectError("Cannot access method " + StringUtil.methodString(method.getName(), method.getParameterTypes()) + " in '" + method.getDeclaringClass() + "' :" + e, e);
        }
    }

    public static Object getIndex(Object array, int index) throws ReflectError, UtilTargetError {
        if (Interpreter.DEBUG) {
            Interpreter.debug("getIndex: " + array + ", index=" + index);
        }
        try {
            Object val = Array.get(array, index);
            return Primitive.wrap(val, array.getClass().getComponentType());
        }
        catch (ArrayIndexOutOfBoundsException e1) {
            throw new UtilTargetError(e1);
        }
        catch (Exception e) {
            throw new ReflectError("Array access:" + e);
        }
    }

    public static void setIndex(Object array, int index, Object val) throws ReflectError, UtilTargetError {
        try {
            val = Primitive.unwrap(val);
            Array.set(array, index, val);
        }
        catch (ArrayStoreException e2) {
            throw new UtilTargetError(e2);
        }
        catch (IllegalArgumentException e1) {
            throw new UtilTargetError(new ArrayStoreException(e1.toString()));
        }
        catch (Exception e) {
            throw new ReflectError("Array access:" + e);
        }
    }

    public static Object getStaticFieldValue(Class clas, String fieldName) throws UtilEvalError, ReflectError {
        return Reflect.getFieldValue(clas, null, fieldName, true);
    }

    public static Object getObjectFieldValue(Object object, String fieldName) throws UtilEvalError, ReflectError {
        if (object instanceof This) {
            return ((This)object).namespace.getVariable(fieldName);
        }
        if (object == Primitive.NULL) {
            throw new UtilTargetError(new NullPointerException("Attempt to access field '" + fieldName + "' on null value"));
        }
        try {
            return Reflect.getFieldValue(object.getClass(), object, fieldName, false);
        }
        catch (ReflectError e) {
            if (Reflect.hasObjectPropertyGetter(object.getClass(), fieldName)) {
                return Reflect.getObjectProperty(object, fieldName);
            }
            throw e;
        }
    }

    static LHS getLHSStaticField(Class clas, String fieldName) throws UtilEvalError, ReflectError {
        Field f = Reflect.resolveExpectedJavaField(clas, fieldName, true);
        return new LHS(f);
    }

    static LHS getLHSObjectField(Object object, String fieldName) throws UtilEvalError, ReflectError {
        if (object instanceof This) {
            boolean recurse = false;
            return new LHS(((This)object).namespace, fieldName, recurse);
        }
        try {
            Field f = Reflect.resolveExpectedJavaField(object.getClass(), fieldName, false);
            return new LHS(object, f);
        }
        catch (ReflectError e) {
            if (Reflect.hasObjectPropertySetter(object.getClass(), fieldName)) {
                return new LHS(object, fieldName);
            }
            throw e;
        }
    }

    private static Object getFieldValue(Class clas, Object object, String fieldName, boolean staticOnly) throws UtilEvalError, ReflectError {
        try {
            Field f = Reflect.resolveExpectedJavaField(clas, fieldName, staticOnly);
            Object value = f.get(object);
            Class<?> returnType = f.getType();
            return Primitive.wrap(value, returnType);
        }
        catch (NullPointerException e) {
            throw new ReflectError("???" + fieldName + " is not a static field.");
        }
        catch (IllegalAccessException e) {
            throw new ReflectError("Can't access field: " + fieldName);
        }
    }

    protected static Field resolveJavaField(Class clas, String fieldName, boolean staticOnly) throws UtilEvalError {
        try {
            return Reflect.resolveExpectedJavaField(clas, fieldName, staticOnly);
        }
        catch (ReflectError e) {
            return null;
        }
    }

    protected static Field resolveExpectedJavaField(Class clas, String fieldName, boolean staticOnly) throws UtilEvalError, ReflectError {
        Field field;
        try {
            field = Capabilities.haveAccessibility() ? Reflect.findAccessibleField(clas, fieldName) : clas.getField(fieldName);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectError("No such field: " + fieldName, e);
        }
        catch (SecurityException e) {
            throw new UtilTargetError("Security Exception while searching fields of: " + clas, e);
        }
        if (staticOnly && !Modifier.isStatic(field.getModifiers())) {
            throw new UtilEvalError("Can't reach instance field: " + fieldName + " from static context: " + clas.getName());
        }
        return field;
    }

    private static Field findAccessibleField(Class clas, String fieldName) throws UtilEvalError, NoSuchFieldException {
        try {
            return clas.getField(fieldName);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            if (Capabilities.haveAccessibility()) {
                try {
                    while (clas != null) {
                        Field[] declaredFields = clas.getDeclaredFields();
                        for (int i = 0; i < declaredFields.length; ++i) {
                            Field field = declaredFields[i];
                            if (!field.getName().equals(fieldName)) continue;
                            field.setAccessible(true);
                            return field;
                        }
                        clas = clas.getSuperclass();
                    }
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
            }
            throw new NoSuchFieldException(fieldName);
        }
    }

    protected static Method resolveExpectedJavaMethod(BshClassManager bcm, Class clas, Object object, String name, Object[] args, boolean staticOnly) throws ReflectError, UtilEvalError {
        if (object == Primitive.NULL) {
            throw new UtilTargetError(new NullPointerException("Attempt to invoke method " + name + " on null value"));
        }
        Class[] types = Types.getTypes(args);
        Method method = Reflect.resolveJavaMethod(bcm, clas, name, types, staticOnly);
        if (method == null) {
            throw new ReflectError((staticOnly ? "Static method " : "Method ") + StringUtil.methodString(name, types) + " not found in class'" + clas.getName() + "'");
        }
        return method;
    }

    protected static Method resolveJavaMethod(BshClassManager bcm, Class clas, String name, Class[] types, boolean staticOnly) throws UtilEvalError {
        if (clas == null) {
            throw new InterpreterError("null class");
        }
        Method method = null;
        if (bcm == null) {
            Interpreter.debug("resolveJavaMethod UNOPTIMIZED lookup");
        } else {
            method = bcm.getResolvedMethod(clas, name, types, staticOnly);
        }
        if (method == null) {
            boolean publicOnly = !Capabilities.haveAccessibility();
            try {
                method = Reflect.findOverloadedMethod(clas, name, types, publicOnly);
            }
            catch (SecurityException e) {
                throw new UtilTargetError("Security Exception while searching methods of: " + clas, e);
            }
            Reflect.checkFoundStaticMethod(method, staticOnly, clas);
            if (method != null && !Reflect.isPublic(method)) {
                if (publicOnly) {
                    Interpreter.debug("resolveJavaMethod - no accessible method found");
                    method = null;
                } else {
                    Interpreter.debug("resolveJavaMethod - setting method accessible");
                    try {
                        method.setAccessible(true);
                    }
                    catch (SecurityException e) {
                        Interpreter.debug("resolveJavaMethod - setting accessible failed: " + e);
                        method = null;
                    }
                }
            }
            if (method != null && bcm != null) {
                bcm.cacheResolvedMethod(clas, types, method);
            }
        }
        return method;
    }

    private static Method findOverloadedMethod(Class baseClass, String methodName, Class[] types, boolean publicOnly) {
        if (Interpreter.DEBUG) {
            Interpreter.debug("Searching for method: " + StringUtil.methodString(methodName, types) + " in '" + baseClass.getName() + "'");
        }
        ArrayList<Method> publicMethods = new ArrayList<Method>();
        DummyCollection<Method> nonPublicMethods = publicOnly ? new DummyCollection() : new ArrayList();
        Reflect.collectMethods(baseClass, methodName, types.length, publicMethods, nonPublicMethods);
        Collections.sort(publicMethods, METHOD_COMPARATOR);
        Method method = Reflect.findMostSpecificMethod(types, publicMethods);
        if (method == null) {
            method = Reflect.findMostSpecificMethod(types, nonPublicMethods);
        }
        return method;
    }

    private static void collectMethods(Class baseClass, String methodName, int numArgs, Collection<Method> publicMethods, Collection<Method> nonPublicMethods) {
        Class superclass = baseClass.getSuperclass();
        if (superclass != null) {
            Reflect.collectMethods(superclass, methodName, numArgs, publicMethods, nonPublicMethods);
        }
        Method[] methods = baseClass.getDeclaredMethods();
        for (Method method : methods) {
            if (!Reflect.matchesNameAndSignature(method, methodName, numArgs)) continue;
            if (Reflect.isPublic(method.getDeclaringClass()) && Reflect.isPublic(method)) {
                publicMethods.add(method);
                continue;
            }
            nonPublicMethods.add(method);
        }
        for (GenericDeclaration genericDeclaration : baseClass.getInterfaces()) {
            Reflect.collectMethods((Class)genericDeclaration, methodName, numArgs, publicMethods, nonPublicMethods);
        }
    }

    private static boolean matchesNameAndSignature(Method m, String methodName, int numArgs) {
        return m.getName().equals(methodName) && (m.isVarArgs() ? m.getParameterTypes().length - 1 <= numArgs : m.getParameterTypes().length == numArgs);
    }

    static Object constructObject(Class clas, Object[] args) throws ReflectError, InvocationTargetException {
        Constructor con;
        Constructor[] constructors;
        if (clas.isInterface()) {
            throw new ReflectError("Can't create instance of an interface: " + clas);
        }
        Class[] types = Types.getTypes(args);
        Constructor[] constructorArray = constructors = Capabilities.haveAccessibility() ? clas.getDeclaredConstructors() : clas.getConstructors();
        if (Interpreter.DEBUG) {
            Interpreter.debug("Looking for most specific constructor: " + clas);
        }
        if ((con = Reflect.findMostSpecificConstructor(types, constructors)) == null) {
            throw Reflect.cantFindConstructor(clas, types);
        }
        if (!Reflect.isPublic(con) && Capabilities.haveAccessibility()) {
            con.setAccessible(true);
        }
        args = Primitive.unwrap(args);
        try {
            return con.newInstance(args);
        }
        catch (InstantiationException e) {
            throw new ReflectError("The class " + clas + " is abstract ", e);
        }
        catch (IllegalAccessException e) {
            throw new ReflectError("We don't have permission to create an instance. Use setAccessibility(true) to enable access.", e);
        }
        catch (IllegalArgumentException e) {
            throw new ReflectError("The number of arguments was wrong", e);
        }
    }

    static Constructor findMostSpecificConstructor(Class[] idealMatch, Constructor[] constructors) {
        int match = Reflect.findMostSpecificConstructorIndex(idealMatch, constructors);
        return match == -1 ? null : constructors[match];
    }

    static int findMostSpecificConstructorIndex(Class[] idealMatch, Constructor[] constructors) {
        Class[][] candidates = new Class[constructors.length][];
        for (int i = 0; i < candidates.length; ++i) {
            candidates[i] = constructors[i].getParameterTypes();
        }
        return Reflect.findMostSpecificSignature(idealMatch, candidates);
    }

    private static Method findMostSpecificMethod(Class[] idealMatch, Collection<Method> methods) {
        if (Interpreter.DEBUG) {
            Interpreter.debug("Looking for most specific method");
        }
        ArrayList<Class[]> candidateSigs = new ArrayList<Class[]>();
        ArrayList<Method> methodList = new ArrayList<Method>();
        for (Method method : methods) {
            int j;
            Class<?>[] parameterTypes = method.getParameterTypes();
            methodList.add(method);
            candidateSigs.add(parameterTypes);
            if (!method.isVarArgs()) continue;
            Class[] candidateSig = new Class[idealMatch.length];
            for (j = 0; j < parameterTypes.length - 1; ++j) {
                candidateSig[j] = parameterTypes[j];
            }
            Class<?> varType = parameterTypes[j].getComponentType();
            while (j < idealMatch.length) {
                candidateSig[j] = varType;
                ++j;
            }
            methodList.add(method);
            candidateSigs.add(candidateSig);
        }
        int match = Reflect.findMostSpecificSignature(idealMatch, (Class[][])candidateSigs.toArray((T[])new Class[candidateSigs.size()][]));
        return match == -1 ? null : (Method)methodList.get(match);
    }

    static int findMostSpecificSignature(Class[] idealMatch, Class[][] candidates) {
        for (int round = 1; round <= 4; ++round) {
            Class[] bestMatch = null;
            int bestMatchIndex = -1;
            for (int i = 0; i < candidates.length; ++i) {
                Class[] targetMatch = candidates[i];
                if (!Types.isSignatureAssignable(idealMatch, targetMatch, round) || bestMatch != null && !Types.isSignatureAssignable(targetMatch, bestMatch, 1)) continue;
                bestMatch = targetMatch;
                bestMatchIndex = i;
            }
            if (bestMatch == null) continue;
            return bestMatchIndex;
        }
        return -1;
    }

    private static String accessorName(String getorset, String propName) {
        return getorset + String.valueOf(Character.toUpperCase(propName.charAt(0))) + propName.substring(1);
    }

    public static boolean hasObjectPropertyGetter(Class clas, String propName) {
        if (clas == Primitive.class) {
            return false;
        }
        String getterName = Reflect.accessorName("get", propName);
        try {
            clas.getMethod(getterName, new Class[0]);
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            getterName = Reflect.accessorName("is", propName);
            try {
                Method m = clas.getMethod(getterName, new Class[0]);
                return m.getReturnType() == Boolean.TYPE;
            }
            catch (NoSuchMethodException e) {
                return false;
            }
        }
    }

    public static boolean hasObjectPropertySetter(Class clas, String propName) {
        Method[] methods;
        String setterName = Reflect.accessorName("set", propName);
        for (Method method : methods = clas.getMethods()) {
            if (!method.getName().equals(setterName)) continue;
            return true;
        }
        return false;
    }

    public static Object getObjectProperty(Object obj, String propName) throws UtilEvalError, ReflectError {
        String accessorName;
        Object[] args = new Object[]{};
        Interpreter.debug("property access: ");
        Method method = null;
        Exception e1 = null;
        Exception e2 = null;
        try {
            accessorName = Reflect.accessorName("get", propName);
            method = Reflect.resolveExpectedJavaMethod(null, obj.getClass(), obj, accessorName, args, false);
        }
        catch (Exception e) {
            e1 = e;
        }
        if (method == null) {
            try {
                accessorName = Reflect.accessorName("is", propName);
                method = Reflect.resolveExpectedJavaMethod(null, obj.getClass(), obj, accessorName, args, false);
                if (method.getReturnType() != Boolean.TYPE) {
                    method = null;
                }
            }
            catch (Exception e) {
                e2 = e;
            }
        }
        if (method == null) {
            throw new ReflectError("Error in property getter: " + e1 + (e2 != null ? " : " + e2 : ""));
        }
        try {
            return Reflect.invokeMethod(method, obj, args);
        }
        catch (InvocationTargetException e) {
            throw new UtilEvalError("Property accessor threw exception: " + e.getTargetException());
        }
    }

    public static void setObjectProperty(Object obj, String propName, Object value) throws ReflectError, UtilEvalError {
        String accessorName = Reflect.accessorName("set", propName);
        Object[] args = new Object[]{value};
        Interpreter.debug("property access: ");
        try {
            Method method = Reflect.resolveExpectedJavaMethod(null, obj.getClass(), obj, accessorName, args, false);
            Reflect.invokeMethod(method, obj, args);
        }
        catch (InvocationTargetException e) {
            throw new UtilEvalError("Property accessor threw exception: " + e.getTargetException());
        }
    }

    public static String normalizeClassName(Class type) {
        if (!type.isArray()) {
            return type.getName();
        }
        StringBuilder className = new StringBuilder();
        try {
            className.append(Reflect.getArrayBaseType(type).getName()).append(' ');
            for (int i = 0; i < Reflect.getArrayDimensions(type); ++i) {
                className.append("[]");
            }
        }
        catch (ReflectError reflectError) {
            // empty catch block
        }
        return className.toString();
    }

    public static int getArrayDimensions(Class arrayClass) {
        if (!arrayClass.isArray()) {
            return 0;
        }
        return arrayClass.getName().lastIndexOf(91) + 1;
    }

    public static Class getArrayBaseType(Class arrayClass) throws ReflectError {
        if (!arrayClass.isArray()) {
            throw new ReflectError("The class is not an array.");
        }
        return arrayClass.getComponentType();
    }

    public static Object invokeCompiledCommand(Class commandClass, Object[] args, Interpreter interpreter, CallStack callstack) throws UtilEvalError {
        Object[] invokeArgs = new Object[args.length + 2];
        invokeArgs[0] = interpreter;
        invokeArgs[1] = callstack;
        System.arraycopy(args, 0, invokeArgs, 2, args.length);
        BshClassManager bcm = interpreter.getClassManager();
        try {
            return Reflect.invokeStaticMethod(bcm, commandClass, "invoke", invokeArgs);
        }
        catch (InvocationTargetException e) {
            throw new UtilEvalError("Error in compiled command: " + e.getTargetException(), e);
        }
        catch (ReflectError e) {
            throw new UtilEvalError("Error invoking compiled command: " + e, e);
        }
    }

    private static void logInvokeMethod(String msg, Method method, Object[] args) {
        if (Interpreter.DEBUG) {
            Interpreter.debug(msg + method + " with args:");
            for (int i = 0; i < args.length; ++i) {
                Object arg = args[i];
                Interpreter.debug("args[" + i + "] = " + arg + " type = " + (arg == null ? "<unkown>" : arg.getClass()));
            }
        }
    }

    private static void checkFoundStaticMethod(Method method, boolean staticOnly, Class clas) throws UtilEvalError {
        if (method != null && staticOnly && !Reflect.isStatic(method)) {
            throw new UtilEvalError("Cannot reach instance method: " + StringUtil.methodString(method.getName(), method.getParameterTypes()) + " from static context: " + clas.getName());
        }
    }

    private static ReflectError cantFindConstructor(Class clas, Class[] types) {
        if (types.length == 0) {
            return new ReflectError("Can't find default constructor for: " + clas);
        }
        return new ReflectError("Can't find constructor: " + StringUtil.methodString(clas.getName(), types) + " in class: " + clas.getName());
    }

    private static boolean isPublic(Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    private static boolean isPublic(Class clazz) {
        return Modifier.isPublic(clazz.getModifiers());
    }

    private static boolean isStatic(Method m) {
        return Modifier.isStatic(m.getModifiers());
    }

    static void setAccessible(Field field) {
        if (!Reflect.isPublic(field) && Capabilities.haveAccessibility()) {
            field.setAccessible(true);
        }
    }

    private static int getVisibility(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isInterface()) {
            return 2;
        }
        if (Reflect.isPublic(declaringClass)) {
            return 1;
        }
        return 0;
    }

    private static class DummyCollection<T>
    extends AbstractCollection<T> {
        private DummyCollection() {
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.emptySet().iterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean add(T t) {
            return false;
        }
    }
}

