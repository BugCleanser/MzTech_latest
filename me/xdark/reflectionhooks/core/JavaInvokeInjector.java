/*
 * Decompiled with CFR 0.152.
 */
package me.xdark.reflectionhooks.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Consumer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import sun.misc.Unsafe;

public final class JavaInvokeInjector {
    public static void inject() throws Throwable {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
        byte[] transformed = JavaInvokeInjector.transform("java/lang/invoke/MethodHandles.class", new Consumer<ClassNode>(){

            @Override
            public void accept(ClassNode cw) {
                cw.visit(52, 1, "java/lang/invoke/MethodHandles", null, "java/lang/Object", null);
            }
        });
        JavaInvokeInjector.defineClass(unsafe, "java.lang.invoke.MethodHandles", transformed, null);
        transformed = JavaInvokeInjector.transform("java/lang/invoke/MethodHandles$Lookup.class", new Consumer<ClassNode>(){

            @Override
            public void accept(ClassNode cw) {
                JavaInvokeInjector.injectMethod(JavaInvokeInjector.findMethodByName(cw.methods, "findVirtual"));
                JavaInvokeInjector.injectMethod(JavaInvokeInjector.findMethodByName(cw.methods, "findStatic"));
                JavaInvokeInjector.injectUnreflectMethod(JavaInvokeInjector.findMethodByNameAndDesc(cw.methods, "unreflect", "(Ljava/lang/reflect/Method;)Ljava/lang/invoke/MethodHandle;"));
            }
        });
        Files.write(Paths.get(".", new String[0]).resolve("Test.class"), transformed, StandardOpenOption.CREATE);
        JavaInvokeInjector.defineClass(unsafe, "java.lang.invoke.MethodHandles$Lookup", transformed, null);
    }

    private static MethodNode findMethodByName(List<MethodNode> nodes, String name) {
        for (MethodNode mn : nodes) {
            if (!name.equals(mn.name)) continue;
            return mn;
        }
        throw new RuntimeException("MethodNode " + name + " was not found!");
    }

    private static MethodNode findMethodByNameAndDesc(List<MethodNode> nodes, String name, String desc) {
        for (MethodNode mn : nodes) {
            if (!name.equals(mn.name) || !desc.equals(mn.desc)) continue;
            return mn;
        }
        throw new RuntimeException("MethodNode " + name + " was not found!");
    }

    private static void injectMethod(MethodNode mn) {
        InsnList list = mn.instructions;
        AbstractInsnNode first = list.getFirst();
        JavaInvokeInjector.createRef(list, first, 1, 4);
        JavaInvokeInjector.createRef(list, first, 2, 5);
        JavaInvokeInjector.createRef(list, first, 3, 6);
        list.insertBefore(first, new VarInsnNode(25, 4));
        list.insertBefore(first, new VarInsnNode(25, 5));
        list.insertBefore(first, new VarInsnNode(25, 6));
        list.insertBefore(first, new MethodInsnNode(184, "me/xdark/reflectionhooks/core/Environment", "onMethodHook", "(Lme/xdark/reflectionhooks/api/NonDirectReference;Lme/xdark/reflectionhooks/api/NonDirectReference;Lme/xdark/reflectionhooks/api/NonDirectReference;)V", false));
        JavaInvokeInjector.getAndSet(list, first, 4, 1);
        JavaInvokeInjector.getAndSet(list, first, 5, 2);
        JavaInvokeInjector.getAndSet(list, first, 6, 3);
    }

    private static void injectUnreflectMethod(MethodNode mn) {
        InsnList list = mn.instructions;
        AbstractInsnNode first = list.getFirst();
        list.insertBefore(first, new VarInsnNode(25, 1));
        list.insertBefore(first, new MethodInsnNode(182, "java/lang/reflect/Method", "getReturnType", "()Ljava/lang/Class;", false));
        list.insertBefore(first, new VarInsnNode(25, 1));
        list.insertBefore(first, new MethodInsnNode(182, "java/lang/reflect/Method", "getParameterTypes", "()[Ljava/lang/Class;", false));
        list.insertBefore(first, new MethodInsnNode(184, "java/lang/invoke/MethodType", "methodType", "(Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/invoke/MethodType;", false));
        list.insertBefore(first, new VarInsnNode(58, 2));
        list.insertBefore(first, new VarInsnNode(25, 1));
        list.insertBefore(first, new MethodInsnNode(182, "java/lang/reflect/Method", "getDeclaringClass", "()Ljava/lang/Class;", false));
        list.insertBefore(first, new VarInsnNode(58, 3));
        list.insertBefore(first, new VarInsnNode(25, 1));
        list.insertBefore(first, new MethodInsnNode(182, "java/lang/reflect/Method", "getName", "()Ljava/lang/String;", false));
        list.insertBefore(first, new VarInsnNode(58, 4));
    }

    private static void getAndSet(InsnList list, AbstractInsnNode first, int aload, int astore) {
        list.insertBefore(first, new VarInsnNode(25, aload));
        list.insertBefore(first, new MethodInsnNode(182, "me/xdark/reflectionhooks/api/NonDirectReference", "get", "()Ljava/lang/Object;", false));
        list.insertBefore(first, new VarInsnNode(58, astore));
    }

    private static void createRef(InsnList list, AbstractInsnNode first, int aload, int astore) {
        list.insertBefore(first, new TypeInsnNode(187, "me/xdark/reflectionhooks/api/NonDirectReference"));
        list.insertBefore(first, new InsnNode(89));
        list.insertBefore(first, new VarInsnNode(25, aload));
        list.insertBefore(first, new MethodInsnNode(183, "me/xdark/reflectionhooks/api/NonDirectReference", "<init>", "(Ljava/lang/Object;)V", false));
        list.insertBefore(first, new VarInsnNode(58, astore));
    }

    private static void defineClass(Unsafe unsafe, String className, byte[] code, Class<?> root) {
        if (root == null) {
            unsafe.defineClass(className, code, 0, code.length, null, null);
        } else {
            unsafe.defineAnonymousClass(root, code, null);
        }
    }

    private static byte[] transform(String resource, Consumer<ClassNode> consumer) throws IOException {
        ClassReader cr;
        Throwable throwable = null;
        Object var4_4 = null;
        try (InputStream in = ClassLoader.getSystemResourceAsStream(resource);){
            cr = new ClassReader(in);
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        consumer.accept(cn);
        ClassWriter cw = new ClassWriter(1);
        cn.accept(cw);
        return cw.toByteArray();
    }
}

