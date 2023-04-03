/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.core;

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import me.xdark.reflectionhooks.api.BaseHook;
import me.xdark.reflectionhooks.api.FieldGetController;
import me.xdark.reflectionhooks.api.FieldSetController;
import me.xdark.reflectionhooks.api.Hook;
import me.xdark.reflectionhooks.api.InvokeFieldController;
import me.xdark.reflectionhooks.api.InvokeMethodController;
import me.xdark.reflectionhooks.api.Invoker;
import me.xdark.reflectionhooks.api.NonDirectReference;
import me.xdark.reflectionhooks.core.JavaAccess;
import me.xdark.reflectionhooks.core.JavaAccessNew;
import me.xdark.reflectionhooks.core.JavaAccessOld;
import sun.misc.Unsafe;

public final class Environment {
    static final List<InvokeMethodController> INVOKE_METHOD_CONTROLLERS = new ArrayList<InvokeMethodController>();
    static final List<InvokeMethodController> INVOKE_CONSTRUCTOR_CONTROLLERS = new ArrayList<InvokeMethodController>();
    static final List<InvokeFieldController> INVOKE_FIELD_CONTROLLERS = new ArrayList<InvokeFieldController>();
    static final MethodHandles.Lookup LOOKUP;
    private static final Unsafe UNSAFE;
    private static final JavaAccess JAVA_ACCESS;
    private static final MethodHandle MH_METHOD_COPY;
    private static final MethodHandle MH_METHOD_PARENT_GET;
    private static final MethodHandle MH_METHOD_PARENT_SET;
    private static final MethodHandle MH_METHOD_ACCESSOR_SET;
    private static final MethodHandle MH_FIELD_COPY;
    private static final MethodHandle MH_FIELD_PARENT_GET;
    private static final MethodHandle MH_FIELD_PARENT_SET;
    private static final MethodHandle MH_FIELD_ACCESSOR_SET1;
    private static final MethodHandle MH_FIELD_ACCESSOR_SET2;
    private static final MethodHandle MH_CONST_COPY;
    private static final MethodHandle MH_CONST_PARENT_GET;
    private static final MethodHandle MH_CONST_PARENT_SET;
    private static final MethodHandle MH_CONST_ACCESSOR_SET;

    static {
        Object maybeUnsafe = AccessController.doPrivileged(() -> {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                return field.get(null);
            }
            catch (Throwable t) {
                return t;
            }
        });
        if (maybeUnsafe instanceof Throwable) {
            throw new AssertionError("sun.misc.Unsafe is not available!", (Throwable)maybeUnsafe);
        }
        UNSAFE = (Unsafe)maybeUnsafe;
        Object maybeLookup = AccessController.doPrivileged(() -> {
            try {
                MethodHandles.publicLookup();
                Field implLookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                return UNSAFE.getObject(UNSAFE.staticFieldBase(implLookupField), UNSAFE.staticFieldOffset(implLookupField));
            }
            catch (Throwable t) {
                return t;
            }
        });
        if (maybeLookup instanceof Throwable) {
            throw new AssertionError("java.lang.invoke.Lookup#IMPL_LOOKUP not available!", (Throwable)maybeLookup);
        }
        LOOKUP = (MethodHandles.Lookup)maybeLookup;
        try {
            int version;
            Throwable throwable = null;
            Object var4_4 = null;
            try (InputStream in = ClassLoader.getSystemResourceAsStream("java/lang/ClassLoader.class");){
                in.skip(6L);
                version = (in.read() << 8) + in.read() - 44;
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            JAVA_ACCESS = version <= 8 ? new JavaAccessOld() : new JavaAccessNew();
            MH_METHOD_COPY = LOOKUP.findVirtual(Method.class, "copy", MethodType.methodType(Method.class));
            MH_METHOD_PARENT_GET = LOOKUP.findGetter(Method.class, "root", Method.class);
            MH_METHOD_PARENT_SET = LOOKUP.findSetter(Method.class, "root", Method.class);
            MH_METHOD_ACCESSOR_SET = LOOKUP.findSetter(Method.class, "methodAccessor", JAVA_ACCESS.resolve("MethodAccessor"));
            MH_FIELD_COPY = LOOKUP.findVirtual(Field.class, "copy", MethodType.methodType(Field.class));
            MH_FIELD_PARENT_GET = LOOKUP.findGetter(Field.class, "root", Field.class);
            MH_FIELD_PARENT_SET = LOOKUP.findSetter(Field.class, "root", Field.class);
            MH_FIELD_ACCESSOR_SET1 = LOOKUP.findSetter(Field.class, "overrideFieldAccessor", JAVA_ACCESS.resolve("FieldAccessor"));
            MH_FIELD_ACCESSOR_SET2 = LOOKUP.findSetter(Field.class, "fieldAccessor", JAVA_ACCESS.resolve("FieldAccessor"));
            MH_CONST_COPY = LOOKUP.findVirtual(Constructor.class, "copy", MethodType.methodType(Constructor.class));
            MH_CONST_PARENT_GET = LOOKUP.findGetter(Constructor.class, "root", Constructor.class);
            MH_CONST_PARENT_SET = LOOKUP.findSetter(Constructor.class, "root", Constructor.class);
            MH_CONST_ACCESSOR_SET = LOOKUP.findSetter(Constructor.class, "constructorAccessor", JAVA_ACCESS.resolve("ConstructorAccessor"));
        }
        catch (Throwable t) {
            throw new AssertionError("Initial setup failed!", t);
        }
    }

    private Environment() {
    }

    static void prepare() {
        try {
            JAVA_ACCESS.init();
        }
        catch (Throwable t) {
            Environment.sneakyThrow(t);
        }
    }

    static <R> Hook createMethodHook0(Method method, Invoker<R> hook) {
        assert (method != null);
        try {
            Method root = MH_METHOD_PARENT_GET.invokeExact(method);
            if (root == null) {
                root = method;
            }
            Method copyRoot = MH_METHOD_COPY.invokeExact(root);
            Environment.wipeMethod(copyRoot);
            BaseHook delegate = new BaseHook();
            Object hooked = JAVA_ACCESS.newMethodAccessor(copyRoot, hook, delegate);
            MH_METHOD_ACCESSOR_SET.invoke(root, hooked);
            Environment.wipeMethod(method);
            MH_METHOD_ACCESSOR_SET.invoke(method, hooked);
            return delegate;
        }
        catch (Throwable t) {
            return (Hook)Environment.sneakyThrow(t);
        }
    }

    static Hook createFieldHook0(Field field, FieldGetController getController, FieldSetController setController) {
        assert (field != null);
        try {
            Field root = MH_FIELD_PARENT_GET.invokeExact(field);
            if (root == null) {
                root = field;
            }
            Field copyRoot = MH_FIELD_COPY.invokeExact(root);
            Environment.wipeField(copyRoot);
            BaseHook delegate = new BaseHook();
            Object hooked = JAVA_ACCESS.newFieldAccessor(copyRoot, getController, setController, delegate);
            MH_FIELD_ACCESSOR_SET1.invoke(root, hooked);
            MH_FIELD_ACCESSOR_SET2.invoke(root, hooked);
            Environment.wipeField(field);
            MH_FIELD_ACCESSOR_SET1.invoke(field, hooked);
            MH_FIELD_ACCESSOR_SET2.invoke(field, hooked);
            return delegate;
        }
        catch (Throwable t) {
            return (Hook)Environment.sneakyThrow(t);
        }
    }

    static <R> Hook createConstructorHook0(Constructor<R> constructor, Invoker<R> hook) {
        assert (constructor != null);
        try {
            Constructor<R> root = MH_CONST_PARENT_GET.invokeExact(constructor);
            if (root == null) {
                root = constructor;
            }
            Constructor copyRoot = MH_CONST_COPY.invokeExact(root);
            Environment.wipeConstructor(copyRoot);
            BaseHook delegate = new BaseHook();
            Object hooked = JAVA_ACCESS.newConstructorAccessor(constructor, hook, delegate);
            MH_CONST_ACCESSOR_SET.invoke(root, hooked);
            Environment.wipeConstructor(constructor);
            MH_CONST_ACCESSOR_SET.invoke(constructor, hooked);
            return delegate;
        }
        catch (Throwable t) {
            return (Hook)Environment.sneakyThrow(t);
        }
    }

    private static void wipeMethod(Method method) {
        try {
            MH_METHOD_PARENT_SET.invokeExact(method, null);
            MH_METHOD_ACCESSOR_SET.invoke(method, null);
        }
        catch (Throwable t) {
            Environment.sneakyThrow(t);
        }
    }

    private static void wipeField(Field field) {
        try {
            MH_FIELD_PARENT_SET.invokeExact(field, null);
            MH_FIELD_ACCESSOR_SET1.invoke(field, null);
            MH_FIELD_ACCESSOR_SET2.invoke(field, null);
        }
        catch (Throwable t) {
            Environment.sneakyThrow(t);
        }
    }

    private static void wipeConstructor(Constructor<?> constructor) {
        try {
            MH_CONST_PARENT_SET.invokeExact(constructor, null);
            MH_CONST_ACCESSOR_SET.invoke(constructor, null);
        }
        catch (Throwable t) {
            Environment.sneakyThrow(t);
        }
    }

    static <T> T sneakyThrow(Throwable t) {
        UNSAFE.throwException(t);
        return null;
    }

    public static void onMethodHook(NonDirectReference<Class<?>> classRef, NonDirectReference<String> nameRef, NonDirectReference<MethodType> typeRef) {
        int i = 0;
        while (i < INVOKE_METHOD_CONTROLLERS.size()) {
            INVOKE_METHOD_CONTROLLERS.get(i).onFindCalled(null, classRef, nameRef, typeRef);
            ++i;
        }
    }

    public static void onConstructorHook(NonDirectReference<Class<?>> classRef, NonDirectReference<String> nameRef, NonDirectReference<MethodType> typeRef) {
        int i = 0;
        while (i < INVOKE_CONSTRUCTOR_CONTROLLERS.size()) {
            INVOKE_CONSTRUCTOR_CONTROLLERS.get(i).onFindCalled(null, classRef, nameRef, typeRef);
            ++i;
        }
    }
}

