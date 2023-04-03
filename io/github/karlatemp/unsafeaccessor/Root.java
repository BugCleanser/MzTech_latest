/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package io.github.karlatemp.unsafeaccessor;

import io.github.karlatemp.unsafeaccessor.Unsafe;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import org.jetbrains.annotations.Contract;

public class Root {
    @Contract(pure=false)
    public static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }

    @Contract(pure=true)
    public static MethodHandles.Lookup getTrusted() {
        return RootLookupHolder.ROOT;
    }

    @Contract(pure=false, value="null, _ -> fail")
    public static void setAccessible(AccessibleObject object, boolean isAccessible) {
        OpenAccess.openAccess(object, isAccessible);
    }

    @Contract(pure=false, value="null -> fail")
    public static <T extends AccessibleObject> T openAccess(T object) {
        Root.setAccessible(object, true);
        return object;
    }

    @Contract(pure=false)
    public static <T> T throw0(Throwable throwable) {
        if (throwable == null) {
            throw new NullPointerException();
        }
        Root.getUnsafe().throwException(throwable);
        throw new RuntimeException();
    }

    @Contract(pure=false)
    public static <T> T allocate(Class<T> klass) throws InstantiationException {
        return (T)Root.getUnsafe().allocateInstance(klass);
    }

    private static class RootLookupHolder {
        private static final MethodHandles.Lookup ROOT;

        private RootLookupHolder() {
        }

        static {
            try {
                MethodHandles.Lookup lookup;
                Unsafe unsafe = Root.getUnsafe();
                try {
                    Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                    Root.openAccess(field);
                    lookup = (MethodHandles.Lookup)field.get(null);
                }
                catch (Throwable any) {
                    if (unsafe.isJava9()) {
                        lookup = MethodHandles.lookup();
                        unsafe.putReference(lookup, unsafe.objectFieldOffset(MethodHandles.Lookup.class, "lookupClass"), Object.class);
                        unsafe.putInt(lookup, unsafe.objectFieldOffset(MethodHandles.Lookup.class, "allowedModes"), -1);
                    }
                    throw any;
                }
                ROOT = lookup;
            }
            catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static class OpenAccess {
        private static final Unsafe usf = Root.getUnsafe();
        private static final long overrideOffset;

        private OpenAccess() {
        }

        static void openAccess(AccessibleObject object, boolean isAccessible) {
            if (object == null) {
                throw new NullPointerException("object");
            }
            usf.putBoolean(object, overrideOffset, isAccessible);
        }

        static {
            if (usf.isJava9()) {
                overrideOffset = usf.objectFieldOffset(AccessibleObject.class, "override");
            } else {
                try {
                    Field field = AccessibleObject.class.getDeclaredField("override");
                    overrideOffset = usf.objectFieldOffset(field);
                }
                catch (Throwable throwable) {
                    throw new ExceptionInInitializerError(throwable);
                }
            }
        }
    }
}

