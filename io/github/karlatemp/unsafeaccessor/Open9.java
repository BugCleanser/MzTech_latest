/*
 * Decompiled with CFR 0.152.
 */
package io.github.karlatemp.unsafeaccessor;

import io.github.karlatemp.unsafeaccessor.Impl9;
import io.github.karlatemp.unsafeaccessor.Unsafe;
import io.github.karlatemp.unsafeaccessor.UsfPutObj;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.AllPermission;
import java.security.ProtectionDomain;
import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;

class Open9
extends ClassLoader {
    private Open9() {
        super(Open9.class.getClassLoader());
    }

    static byte[] replace(byte[] source, byte[] replace, byte[] target) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(source.length);
        int sourceLength = source.length;
        int replaceLength = replace.length;
        int targetLength = target.length;
        int replaceLengthR1 = replaceLength - 1;
        block0: for (int i = 0; i < sourceLength; ++i) {
            if (i + replaceLength <= sourceLength) {
                for (int z = 0; z < replaceLength; ++z) {
                    if (replace[z] == source[i + z]) continue;
                    outputStream.write(source[i]);
                    continue block0;
                }
                outputStream.write(target, 0, targetLength);
                i += replaceLengthR1;
                continue;
            }
            outputStream.write(source[i]);
        }
        return outputStream.toByteArray();
    }

    static byte[] replace(byte[] classfile, String const1, String const2) {
        return Open9.replace(classfile, Open9.toJvm(const1), Open9.toJvm(const2));
    }

    static byte[] toJvm(String const0) {
        byte[] bytes = const0.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length + 2);
        try {
            new DataOutputStream(bos).writeShort(bytes.length);
        }
        catch (IOException ioException) {
            throw new AssertionError((Object)ioException);
        }
        bos.write(bytes, 0, bytes.length);
        return bos.toByteArray();
    }

    Class<?> define(byte[] code) {
        ProtectionDomain domain = new ProtectionDomain(null, new AllPermission().newPermissionCollection());
        return this.defineClass(null, code, 0, code.length, domain);
    }

    static Class<?> findSS() throws ClassNotFoundException {
        String[] classpath = new String[]{"jdk.internal.access.SharedSecrets", "jdk.internal.misc.SharedSecrets"};
        return Open9.findClass(classpath);
    }

    static Class<?> findClass(String[] classpath) throws ClassNotFoundException {
        ClassNotFoundException cnfe = null;
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        for (String s : classpath) {
            try {
                return Class.forName(s, true, loader);
            }
            catch (ClassNotFoundException ce) {
                if (cnfe == null) {
                    cnfe = ce;
                    continue;
                }
                cnfe.addSuppressed(ce);
            }
        }
        assert (cnfe != null);
        throw cnfe;
    }

    static Class<?> findJLA() throws ClassNotFoundException {
        String[] classpath = new String[]{"jdk.internal.access.JavaLangAccess", "jdk.internal.misc.JavaLangAccess"};
        return Open9.findClass(classpath);
    }

    static Unsafe open() {
        Open9 loader = new Open9();
        try (InputStream source = Open9.class.getResourceAsStream("Open9$Injector.class");){
            byte[] data = source.readAllBytes();
            Class<?> JLA = Open9.findJLA();
            Class<?> SS = Open9.findSS();
            Object proxy = Proxy.newProxyInstance(loader, new Class[]{JLA}, (proxy0, method, args) -> null);
            String namespace = proxy.getClass().getPackageName();
            String targetName = namespace + ".Injector";
            String targetJvmName = targetName.replace('.', '/');
            data = Open9.replace(data, "io/github/karlatemp/unsafeaccessor/Open9$Injector", targetJvmName);
            data = Open9.replace(data, "Lio/github/karlatemp/unsafeaccessor/Open9$Injector;", "L" + targetJvmName + ";");
            data = Open9.replace(data, "Ljdk/internal/access/JavaLangAccess;", "L" + JLA.getName().replace('.', '/') + ";");
            data = Open9.replace(data, "jdk/internal/access/JavaLangAccess", JLA.getName().replace('.', '/'));
            data = Open9.replace(data, "()Ljdk/internal/access/JavaLangAccess;", "()L" + JLA.getName().replace('.', '/') + ";");
            data = Open9.replace(data, "Ljdk/internal/access/SharedSecrets;", "L" + SS.getName().replace('.', '/') + ";");
            data = Open9.replace(data, "jdk/internal/access/SharedSecrets", SS.getName().replace('.', '/'));
            Class<?> injectorClass = loader.define(data);
            Class.forName(injectorClass.getName(), true, loader);
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
        try {
            jdk.internal.misc.Unsafe.class.getDeclaredMethod("getReference", Object.class, Long.TYPE);
            return new Impl9();
        }
        catch (NoSuchMethodException e) {
            return new UsfPutObj();
        }
    }

    static class Injector {
        Injector() {
        }

        static {
            Class<Injector> klass = Injector.class;
            Module module = klass.getModule();
            Module open = klass.getClassLoader().getClass().getModule();
            module.addExports(klass.getPackageName(), open);
            JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
            javaLangAccess.addExports(Object.class.getModule(), "jdk.internal.misc", open);
        }
    }
}

